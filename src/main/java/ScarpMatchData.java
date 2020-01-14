import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;


public class ScarpMatchData implements Scrap {

    private WebDriver driver;
    private Actions webActions;
    @Getter
    private ArrayList<Match> matches;
    @Getter
    private List<WebElement> matchnodes;


    public ScarpMatchData(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        webActions = new Actions(driver);
        matches = new ArrayList<>();
        matchnodes = new ArrayList<>();
    }

    @Override
    public void getData() {
        driver.get("https://www.eredmenyek.com/foci/spanyolorszag/laliga/eredmenyek/");

        //Remove cookie notice
        WebElement cookie = driver.findElement(By.xpath("//span[@class='button cookie-law-accept']"));
        webActions.moveToElement(cookie).click().perform();

        //Load more all matches
        WebElement moreMatches = driver.findElement(By.xpath("//a[@class='event__more event__more--static']"));
        webActions.moveToElement(moreMatches).click().perform();

        matchnodes = driver.findElements(By.cssSelector(".event__match"));

        for (WebElement element: matchnodes) {
            String url = "https://www.eredmenyek.com/merkozes/" + element.getAttribute("id").substring(4) + "/#a-merkozes-statisztikaja;0";
            String team1 = element.findElement(By.xpath(".//div[2]")).getText();
            String result = element.findElement(By.xpath(".//div[3]")).getText().replace("\n - \n", "");
            String team2 = element.findElement(By.xpath(".//div[4]")).getText();
            matches.add(new Match(team1,team2,result,url,null,null,null));
        }

    }
}
