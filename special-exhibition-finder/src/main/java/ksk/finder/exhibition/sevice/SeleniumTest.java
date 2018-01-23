package ksk.finder.exhibition.sevice;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeleniumTest {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\KIM\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.get("http://www.museum.seoul.kr/www/board/NR_boardList.do?bbsCd=1002&q_exhSttus=next&sso=ok");
		List<WebElement> elementList = driver.findElements(By.cssSelector(".exhibit_gallery li"));

		System.out.println("for문 시작!");
		for (WebElement ele : elementList) {
			String place = ele.findElement(By.className("place")).getText();
			System.out.println("###" + place);

			if (place.contains("서울역사박물관")) {
				try {
					WebElement aelement = ele.findElement(By.tagName("a"));
					System.out.println("클릭 전");
					aelement.click();
					System.out.println("클릭 후");
					synchronized (driver) {
						driver.wait(2000);
						System.out.println("다시 시작");
					}
				} catch (InterruptedException e) {
					System.out.println("wait ERROR");
				}
				driver.navigate().back();
			}
		}

		// Close the browser
		driver.quit();
	}
}
