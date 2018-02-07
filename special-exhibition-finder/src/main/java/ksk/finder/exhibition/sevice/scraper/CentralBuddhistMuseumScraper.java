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
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CentralBuddhistMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://museum.buddhism.or.kr/?c=2/12";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Element divElement = originalDoc.select("div#bbslist").first().children().get(1);
		boolean isOngoing = divElement.hasClass("gallery");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			Elements divElements = originalDoc.select("div#bbslist div.picbox");

			for (Element div : divElements) {
				if (div.select("div.sbjx span.namex").text().contains("테마전")) {
					Exhibition exhibition = new Exhibition();
					exhibition.setOriginalLink(originalLink);

					// 목록에서 이미지 먼저 파싱
					exhibition.setImage(div.select("div.pic img").attr("src"));

					String specificLink = "http://museum.buddhism.or.kr" + div.select("a").first().attr("href");
					exhibition.setSpecificLink(specificLink);

					// 여기서 specificLink(전시 상세페이지)의 정보 파싱
					Document specificDoc = Jsoup.connect(specificLink).get();

					// 전시명과 전시기간 처리
					String exhibitionName = specificDoc.select("div.subject h1").text();
					String exhibitionPeriod = specificDoc.select("div.subject span").text();
					exhibitionName = exhibitionName.substring(0, exhibitionName.indexOf(exhibitionPeriod)).trim();

					String tmpPeriod = "";
					for (int i = 0; i < exhibitionPeriod.length(); i++) {
						if (Character.isDigit(exhibitionPeriod.charAt(i))) {
							tmpPeriod += exhibitionPeriod.charAt(i) + "";
						}
					}
					String tmpYear = tmpPeriod.substring(0, 4);
					tmpPeriod = tmpPeriod.substring(0, 8) + tmpYear + tmpPeriod.substring(8);
					exhibitionPeriod = tmpPeriod.substring(0, 4) + "-" + tmpPeriod.substring(4, 6) + "-"
							+ tmpPeriod.substring(6, 8) + "~" + tmpPeriod.substring(8, 12) + "-"
							+ tmpPeriod.substring(12, 14) + "-" + tmpPeriod.substring(14, 16);

					exhibition.setName(exhibitionName);
					exhibition.setPeriod(exhibitionPeriod);

					exhibition.setRoom("불교중앙박물관");

					// 전시 설명 처리
					Elements pElements = specificDoc.select("div#vContent p.0");
					String exhibitionDescription = "";
					for (Element p : pElements) {
						if (!p.text().contains("전시구성")) {
							exhibitionDescription += p.text();
						} else {
							break;
						}
					}
					exhibition.setDescription(exhibitionDescription);
					exhibition.setMuseum(museumRepo.findOne("불교중앙박물관"));

					exhibitionList.add(exhibition);
				}
			}
		}
		return exhibitionList;
	}
}