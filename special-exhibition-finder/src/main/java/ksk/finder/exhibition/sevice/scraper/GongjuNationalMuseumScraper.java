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
public class GongjuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://gongju.museum.go.kr/_prog/special_exhibit/?site_dvs_cd=kr&menu_dvs_cd=0302&gubun=1";
		Document originalDoc = Jsoup.connect(originalLink).get();
		int exhibitionNum = Integer.parseInt(originalDoc.select("p.page_num").text().substring(4, 5));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			Elements liElements = originalDoc.select("ul.edu_ul").first().children();

			for (Element li : liElements) {
				Exhibition exhibition = new Exhibition();
				String specificLink = "http://gongju.museum.go.kr" + li.select("div.edu_img a").attr("href");
				exhibition.setLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();
				Elements ulElements = specificDoc.select("ul.ul_color").first().children();

				exhibition.setName(specificDoc.select("div.edu_left3_view h4").text());
				exhibition.setImage("http://gongju.museum.go.kr" + specificDoc.select("div.edu_img img").attr("src"));
				exhibition.setPeriod(ulElements.get(0).text().substring(5).replaceAll(" ", ""));
				exhibition.setDescription(ulElements.get(3).text().substring(4).trim());
				exhibition.setRoom("기획전시실");
				exhibition.setMuseum(museumRepo.findOne("국립공주박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}
