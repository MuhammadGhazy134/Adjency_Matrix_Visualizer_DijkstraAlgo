import java.awt.*;

class Player {
    private String name;
    private Color color;
    private int currentPosition;
    private static final int PLAYER_SIZE = 20;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.currentPosition = 1; // Start at position 1
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

    public void move(int steps) {
        currentPosition += steps;
    }

    public void draw(Graphics g, Node node, int playerIndex, int totalPlayers) {
        // Calculate offset for multiple players on same node
        int offsetX = (playerIndex % 2) * (PLAYER_SIZE + 5);
        int offsetY = (playerIndex / 2) * (PLAYER_SIZE + 5);

        int centerX = node.getX() + (node.getSize() / 2) - PLAYER_SIZE + offsetX;
        int centerY = node.getY() + (node.getSize() / 2) - PLAYER_SIZE + offsetY;

        g.setColor(color);
        g.fillOval(centerX, centerY, PLAYER_SIZE, PLAYER_SIZE);
        g.setColor(Color.BLACK);
        g.drawOval(centerX, centerY, PLAYER_SIZE, PLAYER_SIZE);
    }
}