package ksk.finder.exhibition.sevice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import ksk.finder.exhibition.sevice.scraper.task.ExhibitionTask;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Initializer {
	public String updated;

	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Autowired
	private List<MuseumScraper> museumScrapers;

	@Autowired
	private List<ExhibitionTask> exhibitionTasks;

	// 추가기능 : scraper 추가하기 / 현재 크롤링 중인 박물관 목록 표기
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

		this.updated = getCurrentTime();
		log.info("최근 업데이트 : {}", updated);
		log.info("########## initialization End ##########");
	}

	// 매일 10시 00분에 업데이트!
	@Scheduled(cron = "0 0 10 * * *")
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

	// 추후 수정 예정
	@Transactional
	private void initMuseum() {
		Map<String, List<String>> museumMap = new HashMap<>();
		museumMap.put("seoul", new ArrayList<String>(
				Arrays.asList("국립중앙박물관", "국립고궁박물관", "서울역사박물관", "국립민속박물관", "국립한글박물관", "불교중앙박물관", "DDP")));
		museumMap.put("gyeonggi", new ArrayList<String>(Arrays.asList("실학박물관")));
		museumMap.put("gangwon", new ArrayList<String>(Arrays.asList("국립춘천박물관")));
		museumMap.put("chungcheong", new ArrayList<String>(Arrays.asList("국립공주박물관", "국립청주박물관", "국립부여박물관")));
		museumMap.put("yeongnam", new ArrayList<String>(Arrays.asList("국립김해박물관", "국립대구박물관", "국립진주박물관")));
		museumMap.put("honam", new ArrayList<String>(Arrays.asList("국립광주박물관", "국립전주박물관")));
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
		for (MuseumScraper ms : museumScrapers) {
			List<Exhibition> exhibitionList = null;

			try {
				exhibitionList = ms.parseMuseum();
			} catch (IOException e) {
				log.error("fail", e);
			}

			if (exhibitionList != null && !exhibitionList.isEmpty()) {
				for (Exhibition ex : exhibitionList) {
					Exhibition exhibition = new Exhibition();

					for (ExhibitionTask task : exhibitionTasks) {
						exhibition = task.doTask(ex);
					}
					exhibitionRepo.save(exhibition);
				}
			}
		}

		log.info("########## initExhibition End ##########");
	}

	public String getUpdated() {
		return this.updated;
	}

	private String getCurrentTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = now.format(formatter);

		return formatDateTime;
	}
}
