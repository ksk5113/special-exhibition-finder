package ksk.finder.heritage.news.model;

import lombok.Data;

@Data
public class Item {
	private String title;
	private String originallink;
	private String link;
	private String description;
	private String pubDate;

	@Override
	public String toString() {
		return "ITEM [title=" + title + ", originallink=" + originallink + ", link=" + link + ", description="
				+ description + ", pubDate=" + pubDate + "]";
	}
}
