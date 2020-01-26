import hu.restumali.matchscraper.scrapers.MultiThreadedScraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Scraper {

    public static void main(String[] args) {


        String threadcount = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            threadcount = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MultiThreadedScraper scraper = new MultiThreadedScraper(Integer.parseInt(threadcount));
        scraper.start();
    }
}
