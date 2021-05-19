package Model;

public class PlayerOutputData {
    public String name;
    public int half1, half2, total;

    public PlayerOutputData() {
        this.name = "Name Placeholder";
        this.half1 = Integer.MAX_VALUE;
        this.half2 = Integer.MAX_VALUE;
        this.total = Integer.MAX_VALUE;
    }

    public PlayerOutputData(String name, int half1, int half2, int total) {
        this.name = name;
        this.half1 = half1;
        this.half2 = half2;
        this.total = total;
    }

    public PlayerOutputData(PlayerInputData playerInputData) {
        this.name = playerInputData.name;
        half1 = 0;
        half2 = 0;
        for (int i = 0; i < playerInputData.scores.length / 2; i++) {
            half1 += playerInputData.scores[i];
            half2 += playerInputData.scores[playerInputData.scores.length - i - 1];
        }
        total = half1 + half2;
    }

    @Override
    public String toString() {
        return name + ' ' + total + " (" + half1 + "+" + half2 + ")";
    }
}
