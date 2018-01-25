package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GimhaeNationalMuseumScraper implements MuseumScraper {

	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://gimhae.museum.go.kr/html/kr/exh/exh_02_01.html";
		Document originalDoc = Jsoup.connect(originalLink).get();

		// 진행 중인 전시가 없을 경우, 수정 필요
		Elements divElements = originalDoc.select("div.s25wrap").first().children();
		Elements trElements = divElements.select("div.s25tbl table tbody").first().children();

		// (아마) 동시에 진행 중인 전시가 없을 듯 -> for문 필요없음!
		Exhibition exhibition = new Exhibition();
		exhibition.setOriginalLink(originalLink);

		exhibition.setSpecificLink(originalLink);
		exhibition.setImage(
				"http://gimhae.museum.go.kr" + divElements.select("div.s25imgwrap div.s25img img").attr("src"));
		exhibition.setName(trElements.get(0).select("td").text().trim());
		exhibition.setPeriod(trElements.get(1).select("td").text().replaceAll(" ", ""));
		exhibition.setRoom(trElements.get(2).select("td").text().trim());
		exhibition.setDescription(divElements.select("div.exh_cont_middle div.econtent").text().trim());
		exhibition.setMuseum(museumRepo.findOne("국립김해박물관"));

		exhibitionRepo.save(exhibition);
	}
}
