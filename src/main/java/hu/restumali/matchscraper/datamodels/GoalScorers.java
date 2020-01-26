package hu.restumali.matchscraper.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

@Data @NoArgsConstructor @AllArgsConstructor
public class GoalScorers {

    private ArrayList<GoalScorer> homeScorers;
    private ArrayList<GoalScorer> awayScorers;
}
