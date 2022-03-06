package stickman.config.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stickman.model.entity.Prototype;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the json file containing all the level filenames
 */
public class LevelsParser {

    private JSONArray levelsArr;
    private List<String> levels = new ArrayList<>();
    private String configPath;

    /**
     * Class constructor
     *
     * @param configPath the relative filepath of the JSON file containing the levels filenames
     */
    public LevelsParser(String configPath) {
        this.configPath = configPath;
        try (Reader fileReader =
                     new InputStreamReader(getClass().getResourceAsStream("/" + configPath))) {
            levelsArr = (JSONArray)((JSONObject) new JSONParser().parse(fileReader)).get("levels");
            fileReader.close();
        } catch (NullPointerException | IOException | ParseException e) {
            System.out.println("Error: Level configuration file missing or malformed.");
            System.out.println("Exiting Program.");
            throw new IllegalArgumentException("Missing or malformed configuration file");
        }
        parse();
    }

    private void parse(){
        try{
            if(levelsArr.size() == 0){
                throw new NullPointerException();
            }
            for (Object level : levelsArr) {
                levels.add(level.toString());
            }
        } catch (NullPointerException e) {
            System.out.println("Error: Level configuration file empty.");
            System.out.println("Exiting Program.");
            throw new IllegalArgumentException("Empty level configuration file");
        }
    }

    /**
     * Gets the list of levels found in the config file
     *
     * @return list of levels' filenames
     */
    public List<String> getLevels(){
        return levels;
    }

}
