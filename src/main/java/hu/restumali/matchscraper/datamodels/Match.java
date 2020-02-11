package hu.restumali.matchscraper.datamodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data @AllArgsConstructor @NoArgsConstructor
public class Match{

    private String team1;
    private String team2;
    private Integer team1Goals;
    private Integer team2Goals;
  //  private String result;
    private String linkToStatistics;
    private Statistics stats;
    private LineUp lineUp;
    private GoalScorers goalScorers;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd.mm.yyyy HH:mm")
    private String matchDate;
    private Integer round;


    public void print(){
        System.out.println(this.toString());
    }


    @Override
    public String toString() {
        return "Match{" +
                "team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", team1Goals=" + team1Goals +
                ", team2Goals=" + team2Goals +
                ", linkToStatistics='" + linkToStatistics + '\'' +
                ", stats=" + stats +
                ", lineUp=" + lineUp +
                ", goalScorers=" + goalScorers +
                ", matchDate='" + matchDate + '\'' +
                ", round=" + round +
                '}';
    }
}
