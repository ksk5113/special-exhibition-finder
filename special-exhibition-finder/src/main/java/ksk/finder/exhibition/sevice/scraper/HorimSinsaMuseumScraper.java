package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HorimSinsaMuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	public void parseMuseum() throws IOException {
		String originalLink = "http://horimmuseum.org/sinsa/exhibit/%ED%98%84%EC%9E%AC-%EC%A0%84%EC%8B%9C";
		Document originalDoc = Jsoup.connect(originalLink).get();

		boolean exhibitionNum = originalDoc.select("div.wpb_wrapper").first().child(0)
				.hasClass("teaser_grid_container");

		// 진행 중인 전시가 있음!
		if (exhibitionNum) {
		}
	}
}
