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
public class ChuncheonNationalMuseumScraper implements MuseumScraper {

	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		String originalLink = "http://chuncheon.museum.go.kr/html/kr/display/display_02_02.html?tmpl=kr&linkid=020202&dis_gubun=01";
		Document originalDoc = Jsoup.connect(originalLink).get();

		boolean isOngoing = originalDoc.select("ul.display_showList li").first().children().hasClass("list");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			Elements liElements = originalDoc.select("ul.display_showList").first().children();

			for (Element li : liElements) {
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(originalLink);

				String specificLink = "http://chuncheon.museum.go.kr" + li.select("ul.list a").attr("href");
				exhibition.setSpecificLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setImage(
						"http://chuncheon.museum.go.kr" + specificDoc.select("div.contentView img").attr("src"));
				exhibition.setName((specificDoc.select("div.contentView div.showInfo h4").text()));
				exhibition.setPeriod((specificDoc.select("div.contentView div.showInfo ul li").get(0).text()
						.substring(6).replaceAll(" ", "")));
				exhibition
						.setRoom((specificDoc.select("div.contentView div.showInfo ul li").get(1).text().substring(6)));
				exhibition.setDescription(specificDoc.select("div.contentView div.showCont p.no_mar").text().trim());

				exhibition.setMuseum(museumRepo.findOne("국립춘천박물관"));
				exhibitionList.add(exhibition);
			}
		}
		return exhibitionList;
	}
}
