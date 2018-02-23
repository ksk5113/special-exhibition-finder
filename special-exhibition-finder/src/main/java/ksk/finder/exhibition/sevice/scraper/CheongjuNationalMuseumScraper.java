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
public class CheongjuNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "https://cheongju.museum.go.kr/www/selectSpeclExbiList.do?key=453";
		Document originalDoc = Jsoup.connect(originalLink).get();
		int exhibitionNum = Integer.parseInt(originalDoc.select("span.posts").text().substring(6, 7));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			Elements liElements = originalDoc.select("div#exhibit_list ul").first().children();

			for (Element li : liElements) {
				// 현재전시 O / 예정전시 X
				if (li.select("li.exhibit_tit img").attr("alt").equals("현재전시")) {
					Exhibition exhibition = new Exhibition();
					exhibition.setOriginalLink(originalLink);

					String specificLink = "https://cheongju.museum.go.kr"
							+ li.select("a").attr("href").replaceFirst(".", "/www");
					exhibition.setSpecificLink(specificLink);

					// 여기서 specificLink(전시 상세페이지)의 정보 파싱
					Document specificDoc = Jsoup.connect(specificLink).get();
					liElements = specificDoc.select("div.photo_article ul").first().children();

					exhibition.setName(liElements.get(0).text().substring(3));
					exhibition.setImage(
							"https://cheongju.museum.go.kr" + specificDoc.select("span.photo_wrap img").attr("src"));
					exhibition.setDescription(specificDoc.select("div.box4 p").text().trim());
					exhibition.setPeriod(liElements.get(1).text().substring(4).replaceAll(" ", ""));
					exhibition.setRoom(liElements.get(2).text().substring(4));
					exhibition.setMuseum(museumRepo.findOne("국립청주박물관"));

					exhibitionList.add(exhibition);
				}
			}
		}
		return exhibitionList;
	}
}
