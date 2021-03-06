package ksk.finder.exhibition.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Exhibition implements Comparable<Exhibition> {

	@Id
	private String name;

	private String image;

	private String period;

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne
	private Museum museum; // 박물관

	private String room; // 전시실

	@Column(length = 1000000)
	private String description;

	private String originalLink;

	private String specificLink;

	@Override
	public int compareTo(Exhibition o) {
		return 0;
	}

	public String getCalculatedRemainingDays() {
		LocalDate today = LocalDate.now();
		long remainingDays = ChronoUnit.DAYS.between(today, this.endDate);

		if (remainingDays < 1) {
			return "오늘";
		} else {
			return remainingDays + "일 후";
		}
	}
}
