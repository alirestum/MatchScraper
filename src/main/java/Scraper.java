import hu.restumali.matchscraper.scrapers.MultiThreadedScraper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scraper {

    public static void main(String[] args) {

       MultiThreadedScraper scraper = new MultiThreadedScraper(8);
       scraper.start();


      /*  ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.eredmenyek.com/merkozes/ATxl4NyO/#osszeallitasok;1");

        //Get players table
        WebElement playersTable = driver.findElement(By.xpath("//div[@class='lineups-wrapper']//tbody"));

        //Get trs from table
        List<WebElement> trList = playersTable.findElements(By.tagName("tr"));

        List<WebElement> homeTdList = new ArrayList<>();
        List<WebElement> awayTdList = new ArrayList<>();


        for (int i = 1; i < 12; i++) {
            System.out.println(trList.get(i).findElement(By.cssSelector("td.summary-vertical.fl")).
                    findElement(By.xpath(".//div[2]/a[1]")).getText());
        }




        driver.quit();*/
    }
}
