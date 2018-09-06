/*package ksk.finder.exhibition.sevice.scraper;

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
public class SilhakMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		// 진행 중인 전시만 표시해줌!
		String originalLink = "http://silhak.ggcf.kr/archives/calendar/exhibit-all/exhibit-special?ptype=exhibit&ongoing=1";
		Document originalDoc = Jsoup.connect(originalLink).get();

		boolean isOngoing = originalDoc.select("div.archive-content").first().child(1).hasClass("exhibition-list-wrap");

		if (isOngoing) {
			Elements liElements = originalDoc.select("ul.exhibition-list-wrap").first().children();

			for (Element li : liElements) {
				boolean isHistorical = li.select("div.info strong").text().contains("특별");

				if (isHistorical) {
					Exhibition exhibition = new Exhibition();
					exhibition.setOriginalLink(originalLink);

					String specificLink = li.select("div.thumbnail a").attr("href");
					exhibition.setSpecificLink(specificLink);

					// 여기서 specificLink(전시 상세페이지)의 정보 파싱
					Document specificDoc = Jsoup.connect(specificLink).get();
					Elements divElements = specificDoc.select("div.common-data div.con");

					exhibition.setRoom(li.select("div.info ul li").first().text().substring(4).trim());
					exhibition.setImage(li.select("div.thumbnail a img").attr("src"));
					exhibition.setName(divElements.get(0).text().trim());

					String exhibitionPeriod = divElements.get(1).text().replaceAll(" ", "").replaceAll("\\.", "-")
							.trim();
					exhibition.setPeriod(exhibitionPeriod.substring(0, 10) + exhibitionPeriod.substring(13, 24));

					exhibition.setDescription(divElements.get(3).text().trim());
					exhibition.setMuseum(museumRepo.findOne("실학박물관"));

					exhibitionList.add(exhibition);
				}
			}
		}
		return exhibitionList;
	}
}
*/