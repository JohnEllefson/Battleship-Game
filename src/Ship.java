import java.util.ArrayList;

// Represents Ships of various sizes that occupy multiple coordinates
public class Ship {
    boolean isSunk;
    int length;
    ArrayList<Coordinate> position;


    // Default Constructor for Ship
    public Ship() {
        length = 0;
        isSunk = false;
    }

    // Non-default Constructor for Ship
    public Ship(int length, ArrayList<Coordinate> position) {
        isSunk = false;
        this.length = length;
        this.position = position;
    }

    // Returns the Ship's position
    public ArrayList<Coordinate> getPosition() {
        return position;
    }

    // Returns if the ship has sunk
    public boolean isSunk() {
        return isSunk;
    }

    // Sinks this ship
    public void sink() {
        isSunk = true;
    }
}