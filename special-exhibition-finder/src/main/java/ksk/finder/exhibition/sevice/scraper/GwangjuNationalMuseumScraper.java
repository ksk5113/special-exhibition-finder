package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;

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
public class GwangjuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://gwangju.museum.go.kr/sub3/sub1.do";
		Document originalDoc = Jsoup.connect(originalLink).get();
		boolean isOngoing = originalDoc.select("tbody tr td").hasClass("num");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			Elements trElements = originalDoc.select("tbody tr");

			for (Element tr : trElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://gwangju.museum.go.kr" + tr.child(1).select("a").attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				// 이미지 먼저 파싱
				exhibition.setImage(
						"http://gwangju.museum.go.kr" + specificDoc.select("div.special div img").attr("src"));

				// 나머지 정보 파싱
				Elements ulElements = specificDoc.select("ul.info");

				// 전시명 파싱 (특별전 / 기획특집전 / 테마전 / 제00회 )
				String exhibitionName = ulElements.select("li.tit").text();
				if (exhibitionName.startsWith("특별전")) {
					exhibitionName = exhibitionName.replace("특별전", "").trim().replace("<", "").replace(">", "");
				}
				exhibition.setName(exhibitionName);

				// 나머지 정보 파싱
				exhibition.setPeriod(ulElements.select("li").get(1).text().substring(4).replaceAll(" ", ""));
				exhibition.setDescription(ulElements.select("li").get(3).select("p").text().trim());
				exhibition.setRoom("기획전시실");
				exhibition.setMuseum(museumRepo.findOne("국립광주박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}
