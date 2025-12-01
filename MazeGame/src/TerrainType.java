import java.awt.Color;

public enum TerrainType {
    DEFAULT(1, new Color(248, 250, 252), "Default", 0.4),
    GRASS(2, new Color(134, 239, 172), "Grass", 0.3),
    MUD(5, new Color(212, 165, 116), "Mud", 0.2),
    WATER(10, new Color(125, 211, 252), "Water", 0.1);

    private final int weight;
    private final Color color;
    private final String name;
    private final double probability;

    TerrainType(int weight, Color color, String name, double probability) {
        this.weight = weight;
        this.color = color;
        this.name = name;
        this.probability = probability;
    }

    public int getWeight() { return weight; }
    public Color getColor() { return color; }
    public String getName() { return name; }
    public double getProbability() { return probability; }

    public static TerrainType getRandomTerrain() {
        double rand = Math.random();
        double cumulative = 0;

        for (TerrainType terrain : values()) {
            cumulative += terrain.probability;
            if (rand <= cumulative) {
                return terrain;
            }
        }
        return DEFAULT;
    }
}