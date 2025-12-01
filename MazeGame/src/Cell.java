public class Cell {
    private boolean topWall;
    private boolean rightWall;
    private boolean bottomWall;
    private boolean leftWall;
    private boolean visited;
    private TerrainType terrain;

    public Cell() {
        this.topWall = true;
        this.rightWall = true;
        this.bottomWall = true;
        this.leftWall = true;
        this.visited = false;
        this.terrain = TerrainType.DEFAULT;
    }

    // Getters and Setters
    public boolean hasTopWall() { return topWall; }
    public boolean hasRightWall() { return rightWall; }
    public boolean hasBottomWall() { return bottomWall; }
    public boolean hasLeftWall() { return leftWall; }
    public boolean isVisited() { return visited; }
    public TerrainType getTerrain() { return terrain; }

    public void setTopWall(boolean topWall) { this.topWall = topWall; }
    public void setRightWall(boolean rightWall) { this.rightWall = rightWall; }
    public void setBottomWall(boolean bottomWall) { this.bottomWall = bottomWall; }
    public void setLeftWall(boolean leftWall) { this.leftWall = leftWall; }
    public void setVisited(boolean visited) { this.visited = visited; }
    public void setTerrain(TerrainType terrain) { this.terrain = terrain; }
}