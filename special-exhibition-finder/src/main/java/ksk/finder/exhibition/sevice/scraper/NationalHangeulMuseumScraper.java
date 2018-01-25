/*package ksk.finder.exhibition.sevice.scraper;

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
public class NationalHangeulMuseumScraper {
	
	 * @Autowired private static MuseumRepository museumRepo;
	 * 
	 * @Autowired private static ExhibitionRepository exhibitionRepo;
	 

	public static void main(String[] args) throws IOException {
		String originalLink = "http://www.hangeul.go.kr/specialExh/specialExhList.do?target=1&curr_menu_cd=0103020100";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Element pElement = originalDoc.select("p.sechInfo").first();
		int exhibitionNum = Integer.parseInt(pElement.text().substring(2, 3));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			Elements divElements = originalDoc.select("div.section02").first().children();
			// System.out.println(divElements);

			for (Element div : divElements) {
			}
		}
	}
}
*/