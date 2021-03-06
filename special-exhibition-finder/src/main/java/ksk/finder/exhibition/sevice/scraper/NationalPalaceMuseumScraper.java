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
public class NationalPalaceMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		// 진행 중인 전시가 여러개일 경우, 수정 필요
		Exhibition exhibition = new Exhibition();
		String originalLink = "http://www.gogung.go.kr/specialNowView.do";
		exhibition.setOriginalLink(originalLink);

		Document originalDoc = Jsoup.connect(originalLink).get();
		boolean isOngoing = originalDoc.select("div.special_view p").first().hasClass("sec_tit");

		if (isOngoing) {
			Element divElements = originalDoc.select("p.sec_tit").first();

			// 현재전시가 하나 -> 추후 수정 필요!
			Elements siblings = divElements.siblingElements();

			// 여기서 specificLink(전시 상세페이지)의 정보 파싱
			exhibition.setSpecificLink(originalLink);
			exhibition.setName(divElements.text());
			exhibition.setPeriod(siblings.get(1).text().substring(7).replaceAll(" ", ""));
			exhibition.setRoom(siblings.get(2).text().substring(7));
			exhibition.setImage(siblings.get(5).child(0).attr("src"));
			exhibition.setDescription(siblings.get(6).text());
			exhibition.setMuseum(museumRepo.findOne("국립고궁박물관"));

			exhibitionList.add(exhibition);
		}
		return exhibitionList;
	}
}
*/