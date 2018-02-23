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
public class JinjuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://jinju.museum.go.kr/html/kr/exhibition/exhibition_02.html";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Elements liElements = originalDoc.select("ul.special_exhi").first().children();

		for (Element li : liElements) {
			// 진행 중인 전시가 있음!
			if (li.select("dl dd.button img").attr("alt").equals("진행중")) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://jinju.museum.go.kr" + li.select("dl dd.button a").attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setImage("http://jinju.museum.go.kr"
						+ specificDoc.select("div.exhi_specview p.viewBig img").attr("src"));
				exhibition.setName(specificDoc.select("div.exhi_specview p.viewBig img").attr("alt").trim());
				exhibition.setPeriod(specificDoc.select("div.exhi_specview dl dd").get(0).text().substring(4)
						.replaceAll(" ", "").trim());
				exhibition.setRoom(specificDoc.select("div.exhi_specview dl dd").get(1).text().substring(4).trim());
				exhibition.setDescription(specificDoc.select("div.exhi_specview dl dd.contbox div.graycont").text());
				exhibition.setMuseum(museumRepo.findOne("국립진주박물관"));

				exhibitionList.add(exhibition);
			}
		}
		return exhibitionList;
	}
}