import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Statistics {

    private TeamValue possession;
    private TeamValue shots;
    private TeamValue shotsOnTarget;
    private TeamValue shotsMissed;
    private TeamValue blockedShots;
    private TeamValue freeKicks;
    private TeamValue corners;
    private TeamValue offside;
    private TeamValue saves;
    private TeamValue faults;
    private TeamValue yellowCards;
    private TeamValue redCards;
    private TeamValue passes;
    private TeamValue interceptions;
    private TeamValue attacks;
    private TeamValue dangerous_attacks;
}
