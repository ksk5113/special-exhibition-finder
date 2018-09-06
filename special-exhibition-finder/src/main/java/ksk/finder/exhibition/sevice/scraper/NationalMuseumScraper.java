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
public class NationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://www.museum.go.kr/site/main/exhiSpecialTheme/list/current";
		Document originalDoc = Jsoup.connect(originalLink).get();
		int exhibitionNum = Integer.parseInt(originalDoc.select("div.allPage ul li span").text().substring(0, 1));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			Elements liElements = originalDoc.select("div.exhibit_list ul.list_view li");

			for (Element li : liElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				Element aElement = li.select("div.info a.btnDetail").first();
				String specificLink = "http://www.museum.go.kr" + aElement.attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setName((specificDoc.select("div.outveiw_text ul li").get(0).child(1).text()));
				exhibition.setRoom((specificDoc.select("div.outveiw_text ul li").get(1).child(1).text()));
				exhibition.setPeriod((specificDoc.select("div.outveiw_text ul li").get(2).text().substring(4)));
				exhibition
						.setImage("http://www.museum.go.kr" + specificDoc.select("div.outveiw_img_v2 img").attr("src"));
				exhibition.setDescription(specificDoc.select("div.lh18 p").text());
				exhibition.setMuseum(museumRepo.findOne("국립중앙박물관"));

				exhibitionList.add(exhibition);
			}
		}
		return exhibitionList;
	}
}