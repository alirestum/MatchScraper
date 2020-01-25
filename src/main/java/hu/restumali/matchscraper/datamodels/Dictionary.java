package hu.restumali.matchscraper.datamodels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.restumali.matchscraper.helpers.CommandLineColors;
import lombok.Data;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Data
public class Dictionary {

    private HashMap<String, String> dictionary = new HashMap<>();

    public Dictionary() throws IOException {
      this.readDictionary();
    }

    public void writeDictionary(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try {
            mapper.writeValue(new File("dictionary.json"), dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(CommandLineColors.ANSI_PURPLE.getColor()+"hu.restumali.matchscraper.datamodels.Dictionary written to JSON file!" + CommandLineColors.ANSI_RESET.getColor());
    }

    public void readDictionary() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        TypeReference<HashMap<String, String>> typref = new TypeReference<HashMap<String, String>>() {};
        dictionary = mapper.readValue(new File("dictionary.json"), typref);
    }

    public String getEnglishName(String key){
        return dictionary.get(key);
    }
}
