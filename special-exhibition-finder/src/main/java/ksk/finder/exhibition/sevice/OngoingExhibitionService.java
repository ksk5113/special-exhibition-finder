package ksk.finder.exhibition.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.ExhibitionRepository;

@Service
public class OngoingExhibitionService {

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	public List<Exhibition> getAllExhibitions() {
		return exhibitionRepo.findAll();
	}

	public List<Exhibition> getSomeExhibitions() {
		List<Exhibition> allExhibitions = exhibitionRepo.findAll();
		List<Exhibition> exhibitionList = new ArrayList<>();

		if (!allExhibitions.isEmpty()) {
			for (int i = 0; i < allExhibitions.size() / 2; i++) {
				exhibitionList.add(allExhibitions.get(i));
			}
		}

		return exhibitionList;
	}

	public Exhibition getAnExhibitionByName(String exhibitionName) {
		return exhibitionRepo.findByName(exhibitionName);
	}
}
