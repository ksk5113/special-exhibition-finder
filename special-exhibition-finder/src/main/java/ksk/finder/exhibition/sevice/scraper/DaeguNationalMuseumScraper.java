package ksk.finder.exhibition.sevice.scraper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksk.finder.exhibition.model.Exhibition;
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DaeguNationalMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\KIM\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://daegu.museum.go.kr/display/dispnowList.do?menu_nix=hlu4IXv0");
		boolean isOngoing = driver.findElements(By.cssSelector("article#detail_content *")).get(0).getAttribute("class")
				.equals("exhibit_poster");

		// 진행 중인 전시가 있음!
		if (isOngoing) {
			int exhibitionNum = driver.findElements(By.cssSelector("ul.exhibit_poster li")).size();

			for (int i = 0; i < exhibitionNum; i++) {
				driver.get("http://daegu.museum.go.kr/display/dispnowList.do?menu_nix=hlu4IXv0");
				WebElement liElement = driver.findElements(By.cssSelector("ul.exhibit_poster li")).get(i);

				Exhibition exhibition = new Exhibition();
				WebElement aElement = liElement.findElement(By.tagName("a"));
				aElement.click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				// 여기서 전시 상세페이지의 정보 파싱
				exhibition.setImage(
						driver.findElement(By.cssSelector("div.press_cover span.cover img")).getAttribute("src"));
				exhibition.setName(driver.findElement(By.cssSelector("span.title")).getText().trim());
				exhibition.setPeriod(driver.findElement(By.cssSelector("span.date")).getText().replaceAll(" ", ""));
				exhibition.setRoom(driver.findElements(By.cssSelector("div.press_info ul li")).get(3).getText()
						.substring(5).trim());
				exhibition.setDescription(driver.findElement(By.cssSelector("div.dateil_info")).getText().trim());
				exhibition.setMuseum(museumRepo.findOne("국립대구박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
		driver.quit();
	}
}
