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
import ksk.finder.exhibition.repository.ExhibitionRepository;
import ksk.finder.exhibition.repository.MuseumRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeoulHistoryMuseumScraper implements MuseumScraper {
	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\KIM\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.museum.seoul.kr/www/board/NR_boardList.do?bbsCd=1002&q_exhSttus=next&sso=ok");
		int exhibitionNum = Integer
				.parseInt(driver.findElements(By.cssSelector(".bbs_info span")).get(1).getText().substring(8, 9));

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			for (int i = 0; i < exhibitionNum; i++) {
				driver.get("http://www.museum.seoul.kr/www/board/NR_boardList.do?bbsCd=1002&q_exhSttus=next&sso=ok");
				WebElement liElement = driver.findElements(By.cssSelector(".exhibit_gallery li")).get(i);
				String place = liElement.findElement(By.className("place")).getText();

				if (place.contains("서울역사박물관")) {
					// specificLink 파싱 불가능
					Exhibition exhibition = new Exhibition();
					exhibition.setOriginalLink(
							"http://www.museum.seoul.kr/www/board/NR_boardList.do?bbsCd=1002&q_exhSttus=next&sso=ok");

					WebElement aElement = liElement.findElement(By.tagName("a"));
					aElement.click();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					// 여기서 전시 상세페이지의 정보 파싱
					exhibition.setImage(driver.findElement(By.cssSelector(".poster_area img")).getAttribute("src"));
					exhibition.setName(driver.findElement(By.cssSelector(".exhibit_info .tit")).getText().trim());

					List<WebElement> pElements = driver.findElements(By.cssSelector(".bbs_article .output_area p"));
					exhibition.setDescription(pElements.get(0).getText() + pElements.get(1).getText()
							+ pElements.get(2).getText() + pElements.get(3).getText());

					List<WebElement> ddElements = driver.findElements(By.cssSelector(".exhibit_info dl dd"));
					exhibition.setPeriod(ddElements.get(0).getText().replaceAll(" ", ""));
					exhibition.setRoom(ddElements.get(1).getText().trim());

					exhibition.setMuseum(museumRepo.findOne("서울역사박물관"));

					exhibitionRepo.save(exhibition);
				}
			}
		}
		driver.quit();
	}
}
