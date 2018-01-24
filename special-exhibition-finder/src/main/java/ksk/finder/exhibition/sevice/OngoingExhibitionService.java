package ksk.finder.exhibition.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.ExhibitionRepository;

@Service
public class OngoingExhibitionService {

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	public List<Exhibition> getAllExhibition() {
		return exhibitionRepo.findAll();
	}

	public Exhibition getAnExhibitionByName(String exhibitionName) {
		return exhibitionRepo.findByName(exhibitionName);
	}
}
