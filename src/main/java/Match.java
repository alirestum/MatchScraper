import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;


@Data @AllArgsConstructor
public class Match {

    private String team1;
    private String team2;
    private String result;
    private String linkToStatistics;
    private Statistics stats;
    private LineUp lineUp;
    private HashMap<String, Integer> goalScorer;

    public void print(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Match{" +
                "team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", result='" + result + '\'' +
                ", linkToStatistics='" + linkToStatistics + '\'' +
                ", stats=" + stats +
                ", lineUp=" + lineUp +
                ", goalScorer=" + goalScorer +
                '}';
    }


}
