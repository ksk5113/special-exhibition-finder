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
public class BuyeoNationalMuseumScraper implements MuseumScraper {

	@Autowired
	private MuseumRepository museumRepo;

	@Autowired
	private ExhibitionRepository exhibitionRepo;

	@Override
	public void parseMuseum() throws IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\KIM\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://buyeo.museum.go.kr/display/dispnowList.do?disp_due_gb=NOW&menu_nix=348JBF7O");
		int exhibitionNum = Integer.parseInt(
				driver.findElements(By.cssSelector("div.float_wrap p.articles span.point01")).get(0).getText());

		// 진행 중인 전시가 있음!
		if (exhibitionNum > 0) {
			for (int i = 0; i < exhibitionNum; i++) {
				driver.get("http://buyeo.museum.go.kr/display/dispnowList.do?disp_due_gb=NOW&menu_nix=348JBF7O");
				WebElement liElement = driver.findElements(By.cssSelector("ul.exhibit_list li")).get(i);

				// specificLink 파싱 불가능
				Exhibition exhibition = new Exhibition();
				exhibition.setOriginalLink(
						"http://buyeo.museum.go.kr/display/dispnowList.do?disp_due_gb=NOW&menu_nix=348JBF7O");

				WebElement aElement = liElement.findElement(By.tagName("a"));
				aElement.click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				// 여기서 전시 상세페이지의 정보 파싱
				exhibition.setImage(driver.findElement(By.cssSelector("section.exhibit_infoArea span.thmb img"))
						.getAttribute("src"));
				exhibition.setName(
						driver.findElement(By.cssSelector("div.exhibit_info h2.underline_title")).getText().trim());
				exhibition.setPeriod(driver.findElements(By.cssSelector("div.exhibit_info ul.list_info li")).get(0)
						.findElement(By.cssSelector("span.txt")).getText().replaceAll(" ", ""));
				exhibition.setRoom(driver.findElements(By.cssSelector("div.exhibit_info ul.list_info li")).get(1)
						.findElement(By.cssSelector("span.txt")).getText().replaceAll(" ", ""));
				exhibition.setDescription(driver.findElements(By.cssSelector("div.exhibit_info ul.list_info li")).get(2)
						.findElement(By.cssSelector("span.txt")).getText().replaceAll(" ", ""));
				exhibition.setMuseum(museumRepo.findOne("국립부여박물관"));

				exhibitionRepo.save(exhibition);
			}
		}
		driver.quit();
	}
}
