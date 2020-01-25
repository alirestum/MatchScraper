package hu.restumali.matchscraper.scrapers;

import java.text.ParseException;

public interface Scrap {

    public void getBasicData();

    public void getStatsData() throws ParseException;
}
