package ksk.finder.exhibition.sevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksk.finder.exhibition.model.Museum;
import ksk.finder.exhibition.repository.MuseumRepository;
import ksk.finder.exhibition.sevice.scraper.MuseumScraper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Initializer {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private List<MuseumScraper> museumScrapers;

	@PostConstruct
	public void initStart() {
		if (museumRepo.count() != 0) {
			log.info("Museums are already initiatied");
			return;
		}

		initMuseum();
		initExhibition();
	}

	// 추후 수정 예정
	@Transactional
	private void initMuseum() {
		log.info("########## initMuseum Start ##########");

		Map<String, List<String>> museumMap = new HashMap<>();
		museumMap.put("seoul", new ArrayList<String>(Arrays.asList("국립중앙박물관", "국립고궁박물관", "DDP")));
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
		log.info("########## initExhibition Start ##########");

		try {
			for (MuseumScraper ms : museumScrapers) {
				ms.parseMuseum();
			}
		} catch (IOException e) {
			log.error("fail", e);
		}

		log.info("########## initExhibition End ##########");
	}
}
