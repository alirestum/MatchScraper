import hu.restumali.matchscraper.scrapers.MultiThreadedScraper;

public class Scraper {

    public static void main(String[] args) {

       MultiThreadedScraper scraper = new MultiThreadedScraper(4);
       scraper.start();
    }
}
