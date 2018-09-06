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
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NationalFolkMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://www.nfm.go.kr/user/planexhibition/home/63/selectPlanExhibitionNList.do";
		Document originalDoc = Jsoup.connect(originalLink).get();
		
		boolean isOngoing = !originalDoc.select("ul.d-exhibition__list").first().text().contains("데이터가 없습니다.");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			Elements liElements = originalDoc.select("ul.d-exhibition__list").first().children();
			
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

					exhibitionList.add(exhibition);
				}
			}
		}
		return exhibitionList;
	}
}
