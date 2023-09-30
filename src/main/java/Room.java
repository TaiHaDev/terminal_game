import java.util.ArrayList;
import java.util.List;

/**
 * Defines a room with necessary attributes that will later be drawn
 * on the {@link Board#grid} with the logic to determine if two rooms are intersect
 * @author Phuoc Ha u7454578
 */
public class Room {
    private int x, y, width, height;
    private List<DoorDirection> doorDirection = new ArrayList<>();
    Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Room(int x, int y, int width, int height, List<DoorDirection> doorDirection) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.doorDirection = doorDirection;
    }

    /**
     * Determines if two rooms are intersected. This is an important
     * function that is used two generate random rooms to make sure that no room
     * collapse another.
     * @param other a room
     * @return a boolean to indicate whether they are intersected
     */
    boolean intersects(Room other) {
        return this.x < other.x + other.width + 2 &&
                this.x + this.width + 2 > other.x &&
                this.y < other.y + other.height + 2 &&
                this.y + this.height + 2 > other.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Room{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public List<DoorDirection> getDoorDirection() {
        return doorDirection;
    }

    public void setDoorDirection(List<DoorDirection> doorDirection) {
        this.doorDirection = doorDirection;
    }
}

