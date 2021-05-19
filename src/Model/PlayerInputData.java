package Model;

import java.util.Arrays;

public class PlayerInputData {
    public static final int holeNumber = 18;
    public String name;
    public int[] scores;

    public PlayerInputData(String name, int[] scores) {
        this.name = name;
        this.scores = scores;
    }

    @Override
    public String toString() {
        return name + ' ' + Arrays.toString(scores);
    }
}
