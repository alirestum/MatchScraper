package hu.restumali.matchscraper.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data @AllArgsConstructor
public class LineUp {
    private HashMap<String, Integer> homePlayers = new HashMap<>();
    private HashMap<String, Integer> awayPlayers = new HashMap<>();

    public LineUp(){

    }
}
