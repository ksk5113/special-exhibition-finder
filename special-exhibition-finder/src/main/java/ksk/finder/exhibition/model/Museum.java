package ksk.finder.exhibition.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Museum implements Comparable<Museum> {

	@Id
	private String name;

	private String location;

	private String founder;

	@OneToMany(mappedBy = "museum", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Exhibition> exhibitions;

	public Museum tmpcons(String name, String location, String founder) {
		this.name = name;
		this.location = location;
		this.founder = founder;

		return this;
	}

	@Override
	public int compareTo(Museum o) {
		return 0;
	}
}
