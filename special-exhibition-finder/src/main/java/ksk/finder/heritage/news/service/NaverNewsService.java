package ksk.finder.heritage.news.service;

import org.springframework.stereotype.Service;

@Service
public class NaverNewsService {
	/*
	private static String clientID = "";
	private static String clientSecret = "";

	public List<Book> searchBook(String keyword, int display, int start) {
		List<Book> list = null;
		try {
			URL url;
			url = new URL(
					"https://openapi.naver.com/v1/search/" + "book.xml?query=" + URLEncoder.encode(keyword, "UTF-8")
							+ (display != 0 ? "&display=" + display : "") + (start != 0 ? "&start=" + start : ""));

			URLConnection urlConn = url.openConnection();
			urlConn.setRequestProperty("X-Naver-Client-Id", clientID);
			urlConn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new InputStreamReader(urlConn.getInputStream()));
			// Test에서 했던 방식은 DOM방식이기때문에?
			// 그래서 이렇게 해도 된다?!??!??!?

			int eventType = parser.getEventType();
			Book b = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.END_DOCUMENT: // 문서의 끝
					break;
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<Book>();
					break;
				case XmlPullParser.END_TAG: {
					String tag = parser.getName();
					if (tag.equals("item")) {
						list.add(b);
						b = null;
					}
				}
				case XmlPullParser.START_TAG: {
					String tag = parser.getName();
					switch (tag) {
					case "item":
						b = new Book();
						break;
					case "title":
						if (b != null)
							b.setTitle(parser.nextText());
						break;
					case "link":
						if (b != null)
							b.setLink(parser.nextText());
						break;
					case "image":
						if (b != null)
							b.setImage(parser.nextText());
						break;
					case "author":
						if (b != null)
							b.setAuthor(parser.nextText());
						break;
					case "price":
						if (b != null)
							b.setPrice(parser.nextText());
						break;
					case "discount":
						if (b != null)
							b.setDiscount(parser.nextText());
						break;
					case "publisher":
						if (b != null)
							b.setPublisher(parser.nextText());
						break;
					case "pubdate":
						if (b != null)
							b.setPubdate(parser.nextText());
						break;
					case "isbn":
						if (b != null)
							b.setIsbn(parser.nextText());
						break;
					case "description":
						if (b != null)
							b.setDescription(parser.nextText());
						break;
					}

				}
				}
				eventType = parser.next();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}*/
}
