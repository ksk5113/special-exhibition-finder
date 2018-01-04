package ksk.finder.heritage.news.model;

import java.util.List;

import lombok.Data;

@Data
public class NaverNews {
	private String lastBuildDate;
	private int total;
	private int start;
	private int display;
	private List<Item> items;

	@Override
	public String toString() {
		return "NEWS [lastBuildDate=" + lastBuildDate + ", total=" + total + ", start=" + start + ", display=" + display
				+ "]";
	}
}