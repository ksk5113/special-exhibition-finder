package ksk.finder.heritage.news.api.naver.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test {
	public static void main(String[] args) throws Exception {
		String clientID = "";
		String clientSecret = "";
		URL url = new URL("https://openapi.naver.com/v1/search/news.json?query=museum");

		// openConnection 해당 요청에 대해서 쓸 수 있는 connection 객체
		URLConnection urlConn = url.openConnection();

		urlConn.setRequestProperty("X-Naver-Client-ID", clientID);
		urlConn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

		String msg = null;
		while ((msg = br.readLine()) != null) {
			System.out.println(msg);
		}
	}
}
