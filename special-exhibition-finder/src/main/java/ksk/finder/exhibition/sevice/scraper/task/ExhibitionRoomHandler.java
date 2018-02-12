package ksk.finder.exhibition.sevice.scraper.task;

import org.springframework.stereotype.Component;

import ksk.finder.exhibition.model.Exhibition;

@Component
public class ExhibitionRoomHandler implements ExhibitionTask {
	// 전시실이 박물관 이름으로 시작하는 경우 변경
	@Override
	public Exhibition doTask(Exhibition exhibition) {
		exhibition.setRoom(calExhibitionRoom(exhibition.getMuseum().getName(), exhibition.getRoom()));

		return exhibition;
	}

	private String calExhibitionRoom(String museumName, String room) {
		if (room.startsWith(museumName)) {
			return room.replaceAll(museumName, "").trim();
		} else {
			return room;
		}
	}
}
