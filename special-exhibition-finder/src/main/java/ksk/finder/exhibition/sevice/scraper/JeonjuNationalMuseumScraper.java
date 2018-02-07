package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JeonjuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://jeonju.museum.go.kr/special.es?mid=a10201010100";
		Document originalDoc = Jsoup.connect(originalLink).get();

		// 진행 중인 전시가 없을 경우, 수정 필요! (임시 isOngoing)
		boolean isOngoing = originalDoc.select("div.img-list01 ul").first().hasClass("list");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			Elements liElements = originalDoc.select("div.img-list01 ul").first().children();

			// 여기서 전시 시간 유효성 검사 필요!
			for (Element li : liElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://jeonju.museum.go.kr" + li.select("a").attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setImage(
						"http://jeonju.museum.go.kr" + specificDoc.select("ul.list02 li.left p.pic img").attr("src"));
				exhibition.setName(specificDoc.select("ul.list02 li.right h3.title").text().trim());
				exhibition.setRoom("기획전시실");

				String exhibitionPeriod = specificDoc.select("ul.list02 li.right dl").first().select("dd.color-pink")
						.text().replaceAll("\\.", "-");
				exhibitionPeriod = exhibitionPeriod.substring(0, 10) + exhibitionPeriod.substring(11, 22);
				exhibition.setPeriod(exhibitionPeriod);

				exhibition.setDescription(specificDoc.select("div.paragraph40").text().trim());
				exhibition.setMuseum(museumRepo.findOne("국립전주박물관"));

				exhibitionList.add(exhibition);
			}
		}
		return exhibitionList;
	}
}