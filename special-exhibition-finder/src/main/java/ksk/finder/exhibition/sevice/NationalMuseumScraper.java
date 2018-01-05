package ksk.finder.exhibition.sevice;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;

public class NationalMuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	public void parseNationalMuseum() throws IOException {

		String originalLink = "http://www.museum.go.kr/site/main/exhiSpecialTheme/list/specialGallery";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Elements aElements = originalDoc.select("div.text_inner a");
		// 현재전시가 여러 개일 경우
		for (Element a : aElements) {
			if (a.child(0).child(0).text().equals("현재전시")) {
				String specificLink = "http://www.museum.go.kr" + a.attr("href");

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Exhibition exhibition = new Exhibition();
				Document specificDoc = Jsoup.connect(specificLink).get();
				
				exhibition.setName((specificDoc.select("div.outveiw_text ul li").get(0).child(1).text()));
				exhibition.setRoom((specificDoc.select("div.outveiw_text ul li").get(1).child(1).text()));
				exhibition.setPeriod((specificDoc.select("div.outveiw_text ul li").get(2).text().substring(4)));
				exhibition.setImage(specificDoc.select("div.outveiw_img_v2 img").attr("src"));
				exhibition.setDescription(specificDoc.select("p.0").get(0).text());
				exhibition.setMuseum(museumRepo.findOne("국립중앙박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}
