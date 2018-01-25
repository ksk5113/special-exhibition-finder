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
public class JejuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://jeju.museum.go.kr/_prog/special_exhibit/index.php?site_dvs_cd=kr&menu_dvs_cd=040201&gubun=1";
		Document originalDoc = Jsoup.connect(originalLink).get();
		int exhibitionNum = Integer.parseInt(originalDoc.select("p.page_num").text().substring(4, 5));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			Elements arElements = originalDoc.select("article.list_exhib").first().children();

			for (Element ar : arElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://jeju.museum.go.kr" + ar.select("div.exhib_img figure a").attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();
				Elements divElements = specificDoc.select("article.view_exhibition");
				Elements liElements = divElements.select("div.exhib_info ul").first().children();

				exhibition.setImage(
						"http://jeju.museum.go.kr" + divElements.select("div.img_exhib figure img").attr("src"));
				exhibition.setDescription(divElements.select("div.exhib_detail div.exhib_detail_txt p").text().trim());
				exhibition.setName(liElements.get(0).select("span").text().trim());
				exhibition.setRoom(liElements.get(1).select("span").text().trim());
				exhibition.setPeriod(liElements.get(2).select("span").text().replaceAll(" ", "").trim());
				exhibition.setMuseum(museumRepo.findOne("국립제주박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}
