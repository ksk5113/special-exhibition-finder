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
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import ksk.finder.exhibition.sevice.scraper.GwangjuNationalMuseumScraper;
import ksk.finder.exhibition.sevice.scraper.NationalMuseumScraper;
import ksk.finder.exhibition.sevice.scraper.NationalPalaceMuseumScraper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Initializer {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Autowired
	private NationalMuseumScraper nmScraper;

	@Autowired
	private NationalPalaceMuseumScraper npmScraper;

	@Autowired
	private GwangjuNationalMuseumScraper gnmScraper;

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
		museumMap.put("seoul", new ArrayList<String>(Arrays.asList("국립중앙박물관", "국립고궁박물관")));
		museumMap.put("honam", new ArrayList<String>(Arrays.asList("국립광주박물관")));

		System.out.println(museumMap);

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
			nmScraper.parseNationalMuseum();
			npmScraper.parseNationalPalaceMuseum();
			gnmScraper.parseGwangjuNationalMuseum();
		} catch (IOException e) {
			log.error("fail", e);
		}
		
		log.info("########## initExhibition End ##########");
	}
}
