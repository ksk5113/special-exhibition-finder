package ksk.finder.exhibition.sevice;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.model.Museum;
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import ksk.finder.exhibition.sevice.scraper.MuseumScraper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Initializer {
	public String updated = "";

	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Autowired
	private List<MuseumScraper> museumScrapers;

	// 추가기능 : scraper 추가하기 / 현재 크롤링 중인 박물관 목록 표기
	// 동적 페이지 크롤링은 셀레늄!
	// 어느정도 완성되면 서버 올리기
	// 심심하면 : 배너 랜덤 이미지 출력

	@PostConstruct
	public void initStart() {
		log.info("########## initialization Start ##########");
		if (museumRepo.count() != 0) {
			log.info("Museums are already initiatied");
			return;
		}

		initMuseum();
		initExhibition();
		updateClosingDate();

		this.updated = getCurrentTime();
		log.info("최근 업데이트 : {}", updated);
		log.info("########## initialization End ##########");
	}

	// 매주 수요일 06시 00분에 업데이트!
	@Scheduled(cron = "0 0 6 * * WED")
	public void updateStart() {
		log.info("########## update Start ##########");

		museumRepo.deleteAll();

		if (museumRepo.count() != 0) {
			log.info("Museums are not deleted");
			return;
		}

		initMuseum();
		initExhibition();

		this.updated = getCurrentTime();
		log.info("최근 업데이트 : {}", updated);
		log.info("########## update End ##########");
	}

	// 매일 00시 00분에 업데이트!
	@Scheduled(cron = "0 0 0 * * *")
	public void updateClosingDate() {
		List<Exhibition> exhibitionList = exhibitionRepo.findAll();

		for (Exhibition ex : exhibitionList) {
			ex.setClosingDate(calClosingDate(ex.getPeriod().substring(11)));
			exhibitionRepo.save(ex);
		}
	}

	private long calClosingDate(String period) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date(cal.getTimeInMillis());
			Date periodDate = sdf.parse(period);

			long difference = currentDate.getTime() - periodDate.getTime();
			long closingDate = difference / (24 * 60 * 60 * 1000);
			closingDate = Math.abs(closingDate) + 1;

			return closingDate;
		} catch (ParseException e) {
			log.error("fail", e);
		}
		return 0;
	}

	// 추후 수정 예정
	@Transactional
	private void initMuseum() {
		Map<String, List<String>> museumMap = new HashMap<>();
		museumMap.put("seoul", new ArrayList<String>(Arrays.asList("국립중앙박물관", "국립고궁박물관", "서울역사박물관")));
		museumMap.put("gyeonggi", new ArrayList<String>(Arrays.asList("실학박물관")));
		museumMap.put("gangwon", new ArrayList<String>(Arrays.asList()));
		museumMap.put("chungcheong", new ArrayList<String>(Arrays.asList("국립공주박물관", "국립청주박물관")));
		museumMap.put("yeongnam", new ArrayList<String>(Arrays.asList("국립김해박물관")));
		museumMap.put("honam", new ArrayList<String>(Arrays.asList("국립광주박물관")));
		museumMap.put("jeju", new ArrayList<String>(Arrays.asList("국립제주박물관")));

		for (String key : museumMap.keySet()) {
			List<String> museumList = museumMap.get(key);
			for (String name : museumList) {
				Museum museum = new Museum();
				museum.setName(name);
				museum.setLocation(key);
				museumRepo.save(museum);
			}
		}

		log.info("########## initMuseum End ##########");
	}

	@Transactional
	private void initExhibition() {
		try {
			for (MuseumScraper ms : museumScrapers) {
				ms.parseMuseum();
			}
		} catch (IOException e) {
			log.error("fail", e);
		}

		log.info("########## initExhibition End ##########");
	}

	public String getUpdated() {
		return this.updated;
	}

	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		String updatedTime = sdf.format(cal.getTime());

		return updatedTime;
	}
}
