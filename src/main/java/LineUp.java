import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data @AllArgsConstructor
public class LineUp {
    private HashMap<String, Integer> players = new HashMap<>();

    public LineUp(){

    }
}
