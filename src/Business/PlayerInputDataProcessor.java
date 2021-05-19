package Business;

import Model.PlayerInputData;
import Model.PlayerOutputData;
import Persistence.PlayerInputDataPersistence;

import java.io.IOException;

public class PlayerInputDataProcessor {
    // create 3 dummy objects in the beginning so we avoid checking for null each time when needing to make changes to the scoreboard
    public PlayerOutputData[] top3ScoringPlayers = {
            new PlayerOutputData(),
            new PlayerOutputData(),
            new PlayerOutputData(),
    };

    private final PlayerInputDataPersistence playerInputDataPersistence = new PlayerInputDataPersistence(PlayerInputDataProcessor.this::comparePlayer);

    /**
     * The top 3 players are computed and printed in the stdout.
     *
     * @param fileName file to be processed
     */
    public void printTopPlayers(String fileName) {
        try {
            playerInputDataPersistence.getTopPlayersFromInputData(fileName, PlayerInputData.holeNumber);
            System.out.println(top3ScoringPlayers[0]);
            System.out.println(top3ScoringPlayers[1]);
            System.out.println(top3ScoringPlayers[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Compares current top 3 players to the new player and updates the list.
     *
     * @param playerOutputData new player to be checked against the list
     */
    private void comparePlayer(PlayerOutputData playerOutputData) {
        if (top3ScoringPlayers[0].total > playerOutputData.total) {
            top3ScoringPlayers[2] = top3ScoringPlayers[1];
            top3ScoringPlayers[1] = top3ScoringPlayers[0];
            top3ScoringPlayers[0] = playerOutputData;
        } else if (top3ScoringPlayers[1].total > playerOutputData.total) {
            top3ScoringPlayers[2] = top3ScoringPlayers[1];
            top3ScoringPlayers[1] = playerOutputData;
        } else if (top3ScoringPlayers[2].total > playerOutputData.total) {
            top3ScoringPlayers[2] = playerOutputData;
        }
    }

    /**
     * The purpose of this interface is to maintain the cohesion of the classes PlayerInputDataProcessor
     * and PlayerInputDataPersistence by keeping the implementation of the comparing method in the business layer.
     * This necessity arises due to the on-the-spot processing of the input data, thus not needing to keep
     * anything else stored but the top 3 players found so far and a 4th one being processed at that given moment.
     */
    public interface ProcessPlayerInputData {
        void comparePlayer(PlayerOutputData playerOutputData);
    }
}
