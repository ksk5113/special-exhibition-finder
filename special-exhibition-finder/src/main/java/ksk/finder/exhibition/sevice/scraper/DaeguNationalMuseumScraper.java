package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DaeguNationalMuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	public void parseGwangjuNationalMuseum() throws IOException {
		String originalLink = "http://daegu.museum.go.kr/display/dispnowList.do?menu_nix=hlu4IXv0";
		Document originalDoc = Jsoup.connect(originalLink).get();
		boolean isOngoing = originalDoc.select("article#detail_content").first().child(0).hasClass("exhibit_poster");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			// Elements trElements = originalDoc.select("tbody tr");
		}
	}
}
