package Persistence;

import Business.PlayerInputDataProcessor;
import Model.PlayerInputData;
import Model.PlayerOutputData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerInputDataPersistence {
    public static final String DATA_REGEX = "([^0-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9]+)[ ]+([1-9])+[\n]?";
    public PlayerInputDataProcessor.ProcessPlayerInputData processPlayerInputData;

    public PlayerInputDataPersistence(PlayerInputDataProcessor.ProcessPlayerInputData processPlayerInputData) {
        this.processPlayerInputData = processPlayerInputData;
    }

    /**
     * Read the file line by line and match each line to the regex pattern of our input data,
     * create the PlayerInputData object and compare it to the scoreboard.
     * For the sake of efficiency, the single responsibility principle is violated at the gain
     * of having to keep almost no data stored in the memory due to the on-the-spot processing.
     *
     * @param fileName   file to be processed
     * @param holeNumber length of the score array(=number of holes in the golf game)
     * @throws IOException
     */
    public void getTopPlayersFromInputData(String fileName, int holeNumber) throws IOException {
        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        Pattern dataPattern = Pattern.compile(DATA_REGEX);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Matcher dataMatcher = dataPattern.matcher(line);
            while (dataMatcher.find()) {
                String name = dataMatcher.group(1);

                int[] scores = new int[holeNumber];
                /* captured group[0] being the whole match(the line) by default and captured group[1] being the name,
                the groups containing the scores are starting at index 2 of the matched groups array and are a total
                of holeNumber. Thus there are a total of holeNumber + 2 captured groups(whole match, name, holeNumber
                scores) for our given regex pattern */
                int totalCaptureGroups = holeNumber + 2;
                // build the scores array for the currently processed player
                for (int i = 2; i < totalCaptureGroups; i++) {
                    scores[i - 2] = Integer.parseInt(dataMatcher.group(i));
                }

                PlayerInputData playerInputData = new PlayerInputData(name, scores);
                processPlayerInputData.comparePlayer(new PlayerOutputData(playerInputData));
            }
        }
    }
}
