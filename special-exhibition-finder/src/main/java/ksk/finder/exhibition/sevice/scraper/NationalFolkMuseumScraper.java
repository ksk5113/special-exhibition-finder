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
public class NationalFolkMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://www.nfm.go.kr/Display/disIng_list.nfm";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Elements liElements = originalDoc.select("ul.dising_list").first().children();

		// 진행 중인 전시가 없을 경우, 수정 필요!
		for (Element li : liElements) {
			Exhibition exhibition = new Exhibition();
			String specificLink = "http://www.nfm.go.kr" + li.select("div.plan span a").attr("href");
			exhibition.setLink(specificLink);

			// 여기서 specificLink(전시 상세페이지)의 정보 파싱
			Document specificDoc = Jsoup.connect(specificLink).get();

			exhibition.setName(specificDoc.select("div.bookbox h3.title").text());
			exhibition
					.setPeriod(specificDoc.select("div.bookbox ul li").get(0).text().substring(4).replaceAll(" ", ""));
			exhibition.setRoom(specificDoc.select("div.bookbox ul li").get(1).text().substring(7));
			exhibition.setImage("http://www.nfm.go.kr" + specificDoc.select("div#container p.book img").attr("src"));
			exhibition.setDescription(specificDoc.select("div.boardReadbody blockquote p").text());
			exhibition.setMuseum(museumRepo.findOne("국립민속박물관"));

			exhibitionRepo.save(exhibition);
		}
	}
}
*/