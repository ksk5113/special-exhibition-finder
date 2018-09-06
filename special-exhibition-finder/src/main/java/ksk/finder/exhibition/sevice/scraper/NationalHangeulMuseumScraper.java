package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NationalHangeulMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Override
	public List<Exhibition> parseMuseum() throws IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\KIM\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.hangeul.go.kr/specialExh/specialExhList.do?target=1&curr_menu_cd=0103020100");

		int exhibitionNum = Integer
				.parseInt(driver.findElement(By.cssSelector("div.infoArea p.sechInfo span")).getText());
		// boolean isOngoing = driver.findElements(By.cssSelector("div.section02
		// div")).get(0).getAttribute("class").equals("photo_lst02");

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			for (int i = 0; i < exhibitionNum; i++) {
				driver.get("http://www.hangeul.go.kr/specialExh/specialExhList.do?target=1&curr_menu_cd=0103020100");
				WebElement divElement = driver.findElements(By.cssSelector("div.section02 div.photo_lst02")).get(i);

				// specificLink 파싱 불가능
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(
						"http://www.hangeul.go.kr/specialExh/specialExhList.do?target=1&curr_menu_cd=0103020100");

				WebElement aElement = divElement.findElement(By.tagName("a"));
				aElement.click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				// 여기서 전시 상세페이지의 정보 파싱
				exhibition.setImage(driver.findElement(By.cssSelector("span.poster_img img")).getAttribute("src"));
				exhibition.setName(driver.findElement(By.cssSelector("ul.description li h6")).getText());
				exhibition.setPeriod(driver.findElements(By.cssSelector("div.head dl")).get(0)
						.findElement(By.cssSelector("dd")).getText());
				exhibition.setRoom(driver.findElements(By.cssSelector("div.head dl")).get(1)
						.findElement(By.cssSelector("dd")).getText());
				exhibition.setDescription(driver.findElement(By.cssSelector("div.exhibition_composition")).getText());
				exhibition.setMuseum(museumRepo.findOne("국립한글박물관"));

				exhibitionList.add(exhibition);
			}
		}
		driver.quit();
		return exhibitionList;
	}
}