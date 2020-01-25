package hu.restumali.matchscraper.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

@Data @AllArgsConstructor @NoArgsConstructor
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
    private TeamValue fouls;
    private TeamValue redCards;
    private TeamValue yellowCards;
    private TeamValue passes;
    private TeamValue interceptions;
    private TeamValue attacks;
    private TeamValue dangerous_attacks;
    private TeamValue successful_passes;

    @JsonIgnore
    private Field[] fields = this.getClass().getDeclaredFields();


    @SneakyThrows
    public TeamValue getNthField(int n){
        return (TeamValue) fields[n].get(this);
    }

    @SneakyThrows
    public void setNthField(int n, TeamValue value){
        fields[n-1].set(this, value);
    }

    public void setFieldByName(String fieldName, TeamValue value) throws NoSuchFieldException, IllegalAccessException {
        if(fieldName != null)
            this.getClass().getDeclaredField(fieldName).set(this, value);
    }

    public TeamValue getFieldByName(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return (TeamValue) this.getClass().getDeclaredField(fieldName).get(this);
    }


    @Override
    public String toString() {
        return "hu.restumali.matchscraper.datamodels.Statistics{" +
                "possession=" + possession +
                ", shots=" + shots +
                ", shotsOnTarget=" + shotsOnTarget +
                ", shotsMissed=" + shotsMissed +
                ", blockedShots=" + blockedShots +
                ", freeKicks=" + freeKicks +
                ", corners=" + corners +
                ", offside=" + offside +
                ", saves=" + saves +
                ", fouls=" + fouls +
                ", redCards=" + redCards +
                ", yellowCards=" + yellowCards +
                ", passes=" + passes +
                ", interceptions=" + interceptions +
                ", attacks=" + attacks +
                ", dangerous_attacks=" + dangerous_attacks +
                ", succesful_passes=" + successful_passes +
                '}';
    }
}


