/*package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;

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
public class NajuNationalMuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	public static void main(String[] args) throws IOException {

		String originalLink = "https://naju.museum.go.kr/html/kr/exhibition/exhibition_030301.html";
		Document originalDoc = Jsoup.connect(originalLink).timeout(60000).validateTLSCertificates(false).get();

		Element liElement = originalDoc.select("div.exhibitionsList ul").first().select("li").first();
		if (!liElement.text().contains("존재하지 않습니다.")) {
			Elements liElements = originalDoc.select("div.exhibitionsList ul").first().children();

			for (Element li : liElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "https://naju.museum.go.kr" + li.select("a").first().attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).timeout(60000).validateTLSCertificates(false).get();
				// 진행 중인 전시가 생기면 여기서부터 다시 하기

				exhibition.setImage("https://naju.museum.go.kr"
						+ specificDoc.select("div.exhibitview div.photoList img").attr("src"));
				exhibition.setName((specificDoc.select("div.exhibitview div.content h4").text()));
				exhibition.setPeriod((specificDoc.select("div.exhibitview div.content dl dd").get(0).text()
						.replaceAll(" ", "").replaceAll(".", "-")));
				exhibition.setRoom((specificDoc.select("div.exhibitview div.content dl dd").get(1).text()));
				exhibition.setDescription(specificDoc.select("div.event_text_box").text());
				// exhibition.setMuseum(museumRepo.findOne("국립중앙박물관"));

				// exhibitionList.add(exhibition);

				System.out.println(exhibition);
			}
		} else {
			System.out.println("진행 중인 전시가 없습니다.");
		}

		// return exhibitionList;
	}
}*/