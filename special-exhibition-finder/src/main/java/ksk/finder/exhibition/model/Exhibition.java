package ksk.finder.exhibition.model;

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

	@ManyToOne
	private Museum museum; // 박물관

	private String room; // 전시실

	private String description;

	@Override
	public int compareTo(Exhibition o) {
		return 0;
	}
}
