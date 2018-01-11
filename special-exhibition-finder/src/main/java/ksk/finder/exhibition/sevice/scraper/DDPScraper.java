package ksk.finder.exhibition.sevice.scraper;

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
public class DDPScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		String originalLink = "http://www.ddp.or.kr/event/list?status=&menuId=20&cateCode=01";
		Document originalDoc = Jsoup.connect(originalLink).get();

		// 진행 중인 전시가 없을 경우, 수정 필요
		Elements liElements = originalDoc.select("div.brd_area ul.gallery_list2").first().children();

		for (Element li : liElements) {
			boolean isHistorical = li.select("a div.galctnt div.galarea").text().startsWith("배움터 2층");

			if (isHistorical) {
				Exhibition exhibition = new Exhibition();

				// 이미지 먼저 파싱
				// 이미지 파싱이 안됨 ㅠㅠ
				exhibition.setImage(li.select("a div.thumbimg img.lazy").attr("data-original").substring(2));

				String specificLink = "http://www.ddp.or.kr/" + li.select("a").attr("href");
				exhibition.setLink(specificLink);

				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Document specificDoc = Jsoup.connect(specificLink).get();
				Elements ddElements = specificDoc.select("div.brd_area dl.brd_list1 dd");

				exhibition.setName(ddElements.get(0).select("span").text().trim());
				exhibition
						.setPeriod(ddElements.get(1).select("span").text().replaceAll(" ", "").replaceAll("\\.", "-"));
				exhibition.setRoom(ddElements.get(2).select("span").text().trim());
				exhibition.setDescription(specificDoc.select("div.program_dtview div.directsel02 p").text().trim());
				exhibition.setMuseum(museumRepo.findOne("DDP"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
}
