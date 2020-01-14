import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Scraper {

    public static void main(String[] args) {

        ScarpMatchData matchDataScraper = new ScarpMatchData();

        matchDataScraper.getData();

        List<Match> lista = matchDataScraper.getMatches();
        lista.forEach(System.out::println);

    }
}
