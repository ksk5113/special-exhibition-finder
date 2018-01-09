package ksk.finder.exhibition.sevice.scraper;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeoulHistoryMuseumScraper {
	/*
	 * @Autowired
	private static MuseumRepository museumRepo;

	@Autowired
	private static ExhibitionRepository exhibitionRepo;

	public static void main(String[] args) throws IOException {
		String originalLink = "http://www.museum.seoul.kr/www/board/NR_boardList.do?bbsCd=1002&q_exhSttus=next&sso=ok";
		Document originalDoc = Jsoup.connect(originalLink).get();

		Elements liElements = originalDoc.select("ul.exhibit_gallery li");

		// 현재전시가 여러 개일 경우
		for (Element li : liElements) {
			if (li.child(0).child(3).text().contains("서울역사박물관")) {
				String specificLink = li.child(0).attr("href");
				System.out.println(specificLink);
				
				
				// 여기서 specificLink(전시 상세페이지)의 정보 파싱
				Exhibition exhibition = new Exhibition();
				Document specificDoc = Jsoup.connect(specificLink).get();

				exhibition.setName((specificDoc.select("div.outveiw_text ul li").get(0).child(1).text()));
				exhibition.setRoom((specificDoc.select("div.outveiw_text ul li").get(1).child(1).text()));
				exhibition.setPeriod((specificDoc.select("div.outveiw_text ul li").get(2).text().substring(4)));
				exhibition.setImage(specificDoc.select("div.outveiw_img_v2 img").attr("src"));
				exhibition.setDescription(specificDoc.select("p.0").get(0).text());
				exhibition.setMuseum(museumRepo.findOne("국립중앙박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
	}
	*/
}
