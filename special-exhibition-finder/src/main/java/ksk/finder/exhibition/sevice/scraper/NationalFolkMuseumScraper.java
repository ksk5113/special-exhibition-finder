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
public class NationalFolkMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://www.nfm.go.kr/user/planexhibition/home/63/selectPlanExhibitionNList.do";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Elements liElements = originalDoc.select("ul.d-exhibition__list").first().children();

		// 진행 중인 전시가 없을 경우, 수정 필요!
		for (Element li : liElements) {
			if (li.select("div.c-labels span").first().text().equals("현재전시")) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://www.nfm.go.kr" + li.select("a.d-exhibition__link").attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setImage(
						"http://www.nfm.go.kr" + specificDoc.select("div.d-exhibition__poster img").attr("src"));
				exhibition.setName(specificDoc.select("div.d-exhibition__title").text().trim());

				Elements divElements = specificDoc.select("div.d-exhibition__datas").first().children();
				exhibition.setRoom(divElements.get(0).select("div.d-exhibition__value").text().trim());
				exhibition.setPeriod(divElements.get(1).select("div.d-exhibition__value").text().trim());

				exhibition.setDescription(specificDoc.select("div.d-exhibition__article blockquote p").text());
				exhibition.setMuseum(museumRepo.findOne("국립민속박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}
