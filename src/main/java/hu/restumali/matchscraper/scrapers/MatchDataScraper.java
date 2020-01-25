package hu.restumali.matchscraper.scrapers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.restumali.matchscraper.datamodels.*;
import hu.restumali.matchscraper.helpers.CommandLineColors;
import hu.restumali.matchscraper.helpers.JsonSerialiser;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.sampled.Line;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MatchDataScraper extends Thread implements Scrap, JsonSerialiser {

    private WebDriver driver;
    private Actions webActions;

    @Getter
    @Setter
    private List<Match> matches;
    @Getter
    private List<WebElement> matchnodes;
    private ObjectMapper mapper = new ObjectMapper();
    private static Dictionary dictionary;
    private String leagueToScrap;
    private boolean headless;


    public MatchDataScraper(boolean headless) {
        this.headless = headless;
        matchnodes = new ArrayList<>();
//        matches = new ArrayList<>();
        matches = Collections.synchronizedList(new ArrayList<>());
        try {
            dictionary = new Dictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MatchDataScraper(String league, boolean headless) {
        this.headless = headless;
        this.leagueToScrap = league;
        matchnodes = new ArrayList<>();
//        matches = new ArrayList<>();
        matches = Collections.synchronizedList(new ArrayList<>());
        try {
            dictionary = new Dictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        webActions = new Actions(driver);
    }

    @Override
    public void getBasicData() {
        if (!readJson()) {

            initializeDriver();

            driver.get("https://www.eredmenyek.com/foci/spanyolorszag/laliga-2018-2019/eredmenyek/");

            //Remove cookie notice
            WebElement cookie = driver.findElement(By.xpath("//span[@class='button cookie-law-accept']"));
            webActions.moveToElement(cookie).click().perform();

            //Load more all matches
            /*WebElement moreMatches = driver.findElement(By.xpath("//a[@class='event__more event__more--static']"));
            webActions.moveToElement(driver.findElement(By.xpath("//div[contains(text(),'Mobilalkalmazások')]"))).perform();
            webActions.moveToElement(moreMatches).click().perform();
*/


            while (true) {
                WebElement moreMatches;
                try {
                    moreMatches = driver.findElement(By.xpath("//a[@class='event__more event__more--static']"));
                } catch (Exception e) {
                    System.out.println(CommandLineColors.ANSI_RED.getColor() + "No more matches to load!" + CommandLineColors.ANSI_RESET.getColor());
                    break;
                }
                webActions.moveToElement(driver.findElement(By.xpath("//div[contains(text(),'Mobilalkalmazások')]"))).perform();
                webActions.moveToElement(moreMatches).click().perform();
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            matchnodes = driver.findElements(By.cssSelector(".event__match"));
            for (WebElement element : matchnodes) {
                String url = "https://www.eredmenyek.com/merkozes/" + element.getAttribute("id").substring(4) + "/#a-merkozes-statisztikaja;0";
                String team1 = element.findElement(By.xpath(".//div[2]")).getText();
                String result = element.findElement(By.xpath(".//div[3]")).getText().replace("\n - \n", "");
                String team2 = element.findElement(By.xpath(".//div[4]")).getText();

                matches.add(new Match(team1, team2, result, url, null, null, null, null, null));
            }

            writeJson();
        }

    }

    public void quitDriver() {
        driver.quit();
    }


    synchronized public void writeJson(String filename) {

        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        if (filename == null) {
            try {
                mapper.writeValue(new File("matches.json"), matches);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\u001B[36mData written to JSON file!\u001B[0m");
        } else {
            try {
                mapper.writeValue(new File(filename), matches);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\u001B[36mData written to JSON file!\u001B[0m");
        }
    }

    @Override
    synchronized public void writeJson() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            mapper.writeValue(new File("matches.json"), matches);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\u001B[36mData written to JSON file!\u001B[0m");
    }

    @Override
    public boolean readJson() {
        File f = new File(".");
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals("matches.json")) {
                System.out.println("\u001B[36mFound matches file, importing from there!\u001B[0m");
                try {
                    matches = mapper.readValue(new File("matches.json"), mapper.getTypeFactory().constructCollectionType(List.class, Match.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public void getStatsData() throws ParseException {

        initializeDriver();


        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy HH:mm");

        for (Match match : matches) {
            //Load the match statistics page
            if (match.getStats() == null) {
                driver.get(match.getLinkToStatistics());

                //Wait to load all stats
                Pattern loadedpattern = Pattern.compile("[0-9]+");
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[4]/div[12]/div[2]/div[4]/div[1]/div[13]/div[1]/div[3]")));
//                wait.until(ExpectedConditions.textMatches(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[4]/div[12]/div[2]/div[4]/div[1]/div[14]/div[1]/div[3]"), loadedpattern));

                //Get and Set match date
                match.setMatchDate(formatter.parse(driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]")).getText()));


                //Set League round
                Pattern roundPattern = Pattern.compile("(\\d\\d|\\d)");
                Matcher roundMatcher = roundPattern.matcher(driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/div[1]/span[2]/a[1]")).getText());
                Integer round = null;
                if (roundMatcher.find()) {
                    round = Integer.valueOf(roundMatcher.group(0));
                }
                match.setRound(round);

                //Get stats element
                WebElement stats = driver.findElement(By.xpath("//div[@id='tab-statistics-0-statistic']"));

                Statistics statistics = new Statistics();

                //Set posession and shots
                try {
                statistics.setPossession(new TeamValue(
                        Integer.valueOf(stats.findElement(By.xpath(".//div[1]/div[1]/div[1]")).getText().replace("%", "")),
                        Integer.valueOf(stats.findElement(By.xpath(".//div[1]/div[1]/div[3]")).getText().replace("%", ""))));
                } catch (Exception e){
                    continue;
                }

                statistics.setShots(new TeamValue(
                        Integer.valueOf(driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[4]/div[12]/div[2]/div[4]/div[1]/div[2]/div[1]/div[1]")).getText()),
                        Integer.valueOf(stats.findElement(By.xpath(".//div[2]/div[1]/div[3]")).getText())));


                int stats_size = stats.findElements(By.xpath(".//div")).size() / 12;

                //Set all other Stat values, by name
                for (int i = 3; i <= stats_size; i++) {
                    try {
                        statistics.setFieldByName(dictionary.getEnglishName(stats.findElement(By.xpath(".//div[" + i + "]/div[1]/div[2]")).getText()),
                                new TeamValue(
                                        Integer.valueOf(stats.findElement(By.xpath(".//div[" + i + "]/div[1]/div[1]")).getText()),
                                        Integer.valueOf(stats.findElement(By.xpath(".//div[" + i + "]/div[1]/div[3]")).getText())
                                ));
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }


                System.out.print(super.getId() + ": ");
                System.out.println(CommandLineColors.ANSI_BLUE.getColor() + match.getTeam1() + " vs " + match.getTeam2() + " stats done" + CommandLineColors.ANSI_RESET.getColor());
                match.setStats(statistics);
                //writeJson();

            } else {
                System.out.print(super.getId() + ": ");
                System.out.println(CommandLineColors.ANSI_GREEN.getColor() + match.getTeam1() + " vs " + match.getTeam2() + " stats already done" + CommandLineColors.ANSI_RESET.getColor());
            }
        }

        quitDriver();

    }

    public void getLineUp(){
        initializeDriver();

        for (Match match : matches){
            if (match.getLineUp() == null){

                //Get lineup site
                String lineUpLink = match.getLinkToStatistics().replace("#a-merkozes-statisztikaja;0","#osszeallitasok;1");
                driver.get(lineUpLink);
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'Edz')]")));


                LineUp lineUp = new LineUp();
                HashMap<String, Integer> awayPlayers = new HashMap<>();
                HashMap<String, Integer> homePlayers = new HashMap<>();

                //Get players table
                WebElement playersTable = driver.findElement(By.xpath("//div[@class='lineups-wrapper']//tbody"));


                //Get trs from table
                List<WebElement> trList = playersTable.findElements(By.tagName("tr"));


                //Get players from trList
                for (int i = 1; i < 12; i++) {

                    String homePlayer = trList.get(i).findElement(By.cssSelector("td.summary-vertical.fl")).
                            findElement(By.xpath(".//div[2]/a[1]")).getText();
                    String awayPlayer = trList.get(i).findElement(By.cssSelector("td.summary-vertical.fr")).
                            findElement(By.xpath(".//div[2]/a[1]")).getText();

                    //Remove Captain and Goalkeeper tags
                    if (homePlayer.contains(" (K)")){
                        homePlayer.replace(" (K)", "");
                    } else if (homePlayer.contains(" (C)")){
                        homePlayer.replace(" (C)", "");
                    } else if (awayPlayer.contains(" (K)")){
                        awayPlayer.replace(" (K)", "");
                    } else if (awayPlayer.contains(" (C)")) {
                        awayPlayer.replace(" (C)", "");
                    }

                    homePlayers.put(homePlayer, 0);
                    awayPlayers.put(awayPlayer,0);

                }

                lineUp.setAwayPlayers(awayPlayers);
                lineUp.setHomePlayers(homePlayers);

                match.setLineUp(lineUp);


                System.out.print(super.getId() + ": ");
                System.out.println(CommandLineColors.ANSI_BLUE.getColor() + match.getTeam1() + " vs " + match.getTeam2() + " lineup done" + CommandLineColors.ANSI_RESET.getColor());


            } else{
                System.out.print(super.getId() + ": ");
                System.out.println(CommandLineColors.ANSI_GREEN.getColor() + match.getTeam1() + " vs " + match.getTeam2() + " lineup already done" + CommandLineColors.ANSI_RESET.getColor());
            }
        }

        quitDriver();
    }

    public void run() {
        try {
            getStatsData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getLineUp();

        //writeJson(super.getId() + "asd.json");
    }

}
