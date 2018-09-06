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
public class GyeongjuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://gyeongju.museum.go.kr/kor/html/sub02/0202.html";
		Document originalDoc = Jsoup.connect(originalLink).get();

		// 진행 중인 전시가 없을 경우, 수정 필요!
		Elements figureElement = originalDoc.select("div.table_wrap figure");

		// 진행 중인 전시가 있음!
		for (Element figure : figureElement) {
			Exhibition exhibition = new Exhibition();
			exhibition.setOriginalLink(originalLink);

			String specificLink = "http://gyeongju.museum.go.kr" + figure.select("div.img_box a").attr("href");
			exhibition.setSpecificLink(specificLink);

			// 여기서 specificLink(전시 상세페이지)의 정보 파싱
			Document specificDoc = Jsoup.connect(specificLink).get();

			exhibition.setName((specificDoc.select("div.res_title h3").text()));
			exhibition.setRoom(
					(specificDoc.select("div.ex_info").first().select("ul li").first().text().replaceAll("장소", "")));
			exhibition.setPeriod((specificDoc.select("div.res_title p").text().replaceAll(" ", "")));
			exhibition.setImage("http://gyeongju.museum.go.kr"
					+ specificDoc.select("div.thm_figure_slide span.thm_figure img").attr("src"));
			exhibition.setDescription(specificDoc.select("div.ex_info p").text());
			exhibition.setMuseum(museumRepo.findOne("국립경주박물관"));

			exhibitionList.add(exhibition);
		}
		return exhibitionList;
	}
}