// Represents a Coordinate on a grid
public class Coordinate {
    int row;
    int col;


    // Default Constructor
    public Coordinate() {
        row = 0;
        col = 0;
    } 

    // Non-default Constructor
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    } 

    // Copy Constructor
    public Coordinate(Coordinate coord) {
        this.row = coord.getRow();
        this.col = coord.getCol();
    } 

    // Sets the Coordinate with a row and col
    public void setCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Sets the Coordinate based on a passed in coord
    public void setCoordinate(Coordinate coord) {
        this.row = coord.getRow();
        this.col = coord.getCol();
    }

    // Sets the Coordinate's row
    public void setRow(int row) {
        this.row = row;
    }

    // Sets the Coordinate's column
    public void setCol(int col) {
        this.col = col;
    }

    // Returns the Coordinate's row
    public int getRow() {
        return row;
    }

    // Returns the Coordinate's column
    public int getCol() {
        return col;
    }
}