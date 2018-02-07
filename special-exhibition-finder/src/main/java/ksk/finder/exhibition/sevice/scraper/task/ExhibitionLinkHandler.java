package ksk.finder.exhibition.sevice.scraper.task;

import org.springframework.stereotype.Component;

import ksk.finder.exhibition.model.Exhibition;

@Component
public class ExhibitionLinkHandler implements ExhibitionTask {
	// 셀레늄으로 크롤링하는 경우, 링크 처리
	@Override
	public Exhibition doTask(Exhibition exhibition) {
		exhibition.setSpecificLink(calExhibitionLink(exhibition.getOriginalLink(), exhibition.getSpecificLink()));

		return exhibition;
	}

	private String calExhibitionLink(String originalLink, String specificLink) {
		if (specificLink == null) {
			return originalLink;
		}
		return specificLink;
	}
}
