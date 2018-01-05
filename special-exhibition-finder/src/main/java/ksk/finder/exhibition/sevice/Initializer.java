package ksk.finder.exhibition.sevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksk.finder.exhibition.model.Museum;
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
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

	@PostConstruct
	public void initStart() {
		if (museumRepo.count() != 0) {
			log.info("Museums are already initiatied");
			return;
		}

		initMuseum();
		initExhibition();
	}

	@Transactional
	private void initMuseum() {
		log.info("########## initMuseum Start ##########");

		List<String> museumList = new ArrayList<>();
		museumList.add("국립중앙박물관");

		for (String name : museumList) {
			Museum ms = new Museum();
			ms.setName(name);
			ms.setLocation("서울");
			ms.setFounder("국립");
			museumRepo.save(ms);
		}
		log.info("########## initMuseum End ##########");
	}

	// 우선 국중박만!
	@Transactional
	private void initExhibition() {
		log.info("########## initExhibition Start ##########");
		try {
			nmScraper.parseNationalMuseum();
		} catch (IOException e) {
			log.error("fail", e);
		}
		log.info("########## initExhibition End ##########");
	}
}