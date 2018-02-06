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
public class NationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://www.museum.go.kr/site/main/exhiSpecialTheme/list/current";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Element liElement = originalDoc.select("div.allPage div ul li").first();
		int exhibitionNum = Integer.parseInt(liElement.text().substring(4, 5));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			Elements aElements = originalDoc.select("li.clear div.img_center a");

			for (Element a : aElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://www.museum.go.kr" + a.attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setName((specificDoc.select("div.outveiw_text ul li").get(0).child(1).text()));
				exhibition.setRoom((specificDoc.select("div.outveiw_text ul li").get(1).child(1).text()));
				exhibition.setPeriod((specificDoc.select("div.outveiw_text ul li").get(2).text().substring(4)));
				exhibition
						.setImage("http://www.museum.go.kr" + specificDoc.select("div.outveiw_img_v2 img").attr("src"));
				exhibition.setDescription(
						specificDoc.select("p.0").get(0).text() + specificDoc.select("p.0").get(1).text());
				exhibition.setMuseum(museumRepo.findOne("국립중앙박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}