import java.awt.*;
import java.util.*;

// Dice class for rolling
class Dice {
    private Random random;
    private int lastRoll;
    private boolean isGreen; // true = green (forward), false = red (backward)

    public Dice() {
        random = new Random();
        lastRoll = 0;
        isGreen = true;
    }

    public int roll() {
        lastRoll = random.nextInt(6) + 1; // 1 to 6

        // 70% chance for green (forward), 30% chance for red (backward)
        double probability = random.nextDouble();
        isGreen = probability < 0.7;

        return lastRoll;
    }

    public int getLastRoll() {
        return lastRoll;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public String getColorText() {
        return isGreen ? "GREEN (Forward)" : "RED (Backward)";
    }

    public Color getColor() {
        return isGreen ? new Color(34, 139, 34) : new Color(220, 20, 60);
    }
}