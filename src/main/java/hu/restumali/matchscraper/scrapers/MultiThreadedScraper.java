package hu.restumali.matchscraper.scrapers;

import hu.restumali.matchscraper.datamodels.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
public class MultiThreadedScraper {

    @Getter @Setter
    private int threads;

    @Setter
    private List<Match> matchList = Collections.synchronizedList(new ArrayList<>());

    public MultiThreadedScraper(int threads){
        this.threads = threads;
    }

    public void start(){
      statsAndBasicData();
    }



    public void statsAndBasicData(){
        MatchDataScraper scraper = new MatchDataScraper(true);
        scraper.getBasicData();
        this.matchList = scraper.getMatches();


        //Dividing the matches between scrapers(threads)
        ArrayList<MatchDataScraper> scrapers = new ArrayList<>();
        for (int i = 0; i <threads; i++) {
            scrapers.add(new MatchDataScraper(true));

            int perThreadValue = matchList.size()/threads;
            int remaing = matchList.size()%threads;
            List<Match> subSetOfMatches;

            if (i<threads-1){
                subSetOfMatches = this.matchList.subList(perThreadValue*i, perThreadValue*(i+1));
            } else {
                subSetOfMatches = this.matchList.subList(perThreadValue*i, perThreadValue*(i+1)+remaing);
            }

            scrapers.get(i).setMatches(subSetOfMatches);


        }

        //Starting all threads and wait for them to finish
        scrapers.forEach(Thread::start);
        scrapers.forEach(scrapMatchData -> {
            try {
                scrapMatchData.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //Get all sub-results from all the threads.
        this.matchList = new ArrayList<>();
        for (MatchDataScraper scp : scrapers) {
            this.matchList.addAll(scp.getMatches());
        }
        scraper.setMatches(this.matchList);

        //Writing the data to file
        scraper.writeJson();
    }


}
