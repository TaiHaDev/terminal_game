import java.util.ArrayList;
import java.util.List;

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

    boolean intersects(Room other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
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

