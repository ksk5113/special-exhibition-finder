package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ksk.finder.exhibition.model.Exhibition;

public interface MuseumScraper {
	// exhibitonList가 중복되나 확인!
	List<Exhibition> exhibitionList = new ArrayList<>();

	public List<Exhibition> parseMuseum() throws IOException;
}
