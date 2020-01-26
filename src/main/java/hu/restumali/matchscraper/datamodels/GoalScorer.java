package hu.restumali.matchscraper.datamodels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class GoalScorer {

    private String scorer;
    private String assist;
    private String minute;

}
