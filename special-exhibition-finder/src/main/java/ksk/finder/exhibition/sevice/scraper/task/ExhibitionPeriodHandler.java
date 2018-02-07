package ksk.finder.exhibition.sevice.scraper.task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import ksk.finder.exhibition.model.Exhibition;

@Component
public class ExhibitionPeriodHandler implements ExhibitionTask {
	// 크롤링한 데이터 period를 이용하여, startDate / endDate 처리
	@Override
	public Exhibition doTask(Exhibition exhibition) {
		String period = exhibition.getPeriod();
		String startDate = period.substring(0, 10);
		String endDate = period.substring(11, 21);

		LocalDate localStartDate = LocalDate.parse(startDate);
		LocalDate localEndDate = LocalDate.parse(endDate);

		exhibition.setStartDate(localStartDate);
		exhibition.setEndDate(localEndDate);

		// LocalDate today = LocalDate.now();
		// exhibition.setRemaining(ChronoUnit.DAYS.between(today, exhibition.getEndDate()));

		return exhibition;
	}
}
