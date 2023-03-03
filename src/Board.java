import java.util.ArrayList;
import java.util.Random;

// Manages the playfield and the Ships
public class Board {

    enum ShipDirection { up, down, left, right, none };
    ArrayList<Ship> ships = new ArrayList<Ship>();

    char[][] playfield = {
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'},
        {'~', '~', '~', '~', '~', '~', '~', '~', '~'}
    };


    // Default Constructor for Board
    public Board() {
        // Add the Carrier, Battleship, Submarine, and Patrol Boat respectively
        ships.add(new Ship(5, generateShipPosition(5)));
        ships.add(new Ship(4, generateShipPosition(4)));
        ships.add(new Ship(3, generateShipPosition(3)));
        ships.add(new Ship(2, generateShipPosition(2)));
    }


    // Clears the terminal and displays the board and status of each ship
    public void displayBoard(int turn) {
        String shipStatus;

        // Clear the terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Prints the column characters
        System.out.println("     A  B  C  D  E  F  G  H  I      Ships:");
        
        // Print row 1
        System.out.print(" 1   ");
        printBoardRow(0, true);

        // Print row 2
        System.out.print(" 2   ");
        printBoardRow(1, false);
        shipStatus = ((ships.get(0).isSunk) == false) ? "    Active" : "    Sunk";
        System.out.println("    Carrier(5): " + shipStatus);
        
        // Print row 3
        System.out.print(" 3   ");
        printBoardRow(2, false);
        shipStatus = ((ships.get(1).isSunk) == false) ? " Active" : " Sunk";
        System.out.println("    Battleship(4): " + shipStatus);

        // Print row 4
        System.out.print(" 4   ");
        printBoardRow(3, false);
        shipStatus = ((ships.get(2).isSunk) == false) ? "  Active" : "  Sunk";
        System.out.println("    Submarine(3): " + shipStatus);

        // Print row 5
        System.out.print(" 5   ");
        printBoardRow(4, false);
        shipStatus = ((ships.get(3).isSunk) == false) ? "Active" : "Sunk";
        System.out.println("    Patrol Boat(2): " + shipStatus);

        // Print row 6
        System.out.print(" 6   ");
        printBoardRow(5, true);

        // Print row 7
        System.out.print(" 7   ");
        printBoardRow(6, true);

        // Print row 8
        System.out.print(" 8   ");
        printBoardRow(7, false);
        System.out.println("    Turn: " + turn);

        // Print row 9
        System.out.print(" 9   ");
        printBoardRow(8, true);
        System.out.println("\n");
    }


    // Prints the entire specified row of the playfield
    private void printBoardRow(int row, boolean printNewLine) {
        // Iterates through each element in a row and prints it
        for (int i = 0; i < 9; i++)
            System.out.print(playfield[row][i] + "  ");
        
        // Print a new line char if requested
        if (printNewLine)
            System.out.println();
    }


    // Returns legal coordinates to place a ship
    private ArrayList<Coordinate> generateShipPosition(int shipLength) {
        ArrayList<Coordinate> shipPosition = new ArrayList<Coordinate>();
        Random rand = new Random();
        Coordinate tempCoord = new Coordinate();
        ShipDirection shipDirection = ShipDirection.none;
        boolean didShipCollide = false;

        // Choose a random direction for the ship to "move" when determining a valid location
        switch(rand.nextInt(4)) {
            case 0:
                shipDirection = ShipDirection.up;
                break;
            case 1:
                shipDirection = ShipDirection.down;
                break;
            case 2:
                shipDirection = ShipDirection.left;
                break;
            case 3:
                shipDirection = ShipDirection.right;
                break;
        }

        // Attempts to find a legal position for the given ship length until one is found
        while (true) {
            // Generate a random coordinate for the "start" of the ship
            tempCoord.setCoordinate(rand.nextInt(9), rand.nextInt(9));

            // Reset the ship position from any previous failed ship position
            shipPosition.clear();

            // If the ship fits on the board starting in the random location, attempt to set the ship's position
            if (willShipFitInBounds(shipLength, shipDirection, tempCoord)) {
                for (int i = 0; i < shipLength; i++) {
                    // Add the current coordinate to the new shipPosition being generated
                    shipPosition.add(new Coordinate(tempCoord));

                    // Check to see if current space is already occupied by another ship
                    if (isSpaceOccupied(tempCoord))
                        didShipCollide = true;
                    
                    // Find the next coord position if the ship hasn't collided with another
                    if (!didShipCollide) {
                        // Set tempCoord to next board coordinate that the ship will cover  
                        if (shipDirection == ShipDirection.up)
                            tempCoord.setRow(tempCoord.getRow() - 1);
                        else if (shipDirection == ShipDirection.down)
                            tempCoord.setRow(tempCoord.getRow() + 1);
                        else if (shipDirection == ShipDirection.left)
                            tempCoord.setCol(tempCoord.getCol() - 1);
                        else if (shipDirection == ShipDirection.right)
                            tempCoord.setCol(tempCoord.getCol() + 1);
                    }
                }

                // Return the completed shipPosition if it does not collide with another ship
                if (!didShipCollide)
                    return shipPosition;

                // If the ship collided with another, reset it to false for next iteration
                didShipCollide = false;
            }
        }
    }


    // Determines if a ship can fit on the board given the specified startCoord, direction, and ship length
    private boolean willShipFitInBounds(int shipLength, ShipDirection shipDirection, Coordinate startCoord) {
        // If the ship can fit on the board starting on startCoord and "moving" in it's direction, then it fits
        if (shipDirection == ShipDirection.up && startCoord.getRow() - shipLength >= 0)
            return true;
        else if (shipDirection == ShipDirection.down && startCoord.getRow() + shipLength < 9)
            return true;
        else if (shipDirection == ShipDirection.left && startCoord.getCol() - shipLength >= 0)
            return true; 
        else if (shipDirection == ShipDirection.right && startCoord.getCol() + shipLength < 9)
            return true;

        // The ship does not fit in this location 
        return false;
    }


    // Checks to see if the given coordinate is occupied by a ship
    private boolean isSpaceOccupied(Coordinate checkCoord) {
        for (Ship ship : ships)
            for (Coordinate coord : ship.getPosition()) 
                if (coord.getRow() == checkCoord.getRow() && coord.getCol() == checkCoord.getCol())
                    return true;

        // The given space is empty 
        return false;
    }


    // "Shoots" at the specified location and alters the playField accordingly. Returns false if coordinates were invalid.
    public boolean shootCoord(int row, int col) {
        boolean isShipAtCoord = false;

        // Immediently return false if invalid coordinates are passed in
        if (row < 0 || row > 8 || col < 0 || col > 8)
            return false;

        // Check the coordinates of each ship to see if a ship is at the given coord
        for (Ship ship : ships) 
            for (Coordinate coord : ship.getPosition()) 
                if (coord.getRow() == row && coord.getCol() == col)
                    isShipAtCoord = true;
        
        // Set the playfield to an 'X' or 'o' based on whether it hit or missed a ship
        if (isShipAtCoord)
            playfield[row][col] = 'X';
        else
            playfield[row][col] = 'o';
        
        
        // Check for ships ready to sink (each segment has been hit) and sink them
        for (int i = 0; i < ships.size(); i++)
            if (isShipToSink(i))
                ships.get(i).sink();

        // The shot was registered and at valid coordinates
        return true;
    }


    // Checks if the given ship is ready to sink
    private boolean isShipToSink(int shipIndex) {
        boolean isShipSunk = true;
    
        // Checks each coordinate of the given ship and checks if each segment has been hit
        for (Coordinate coord : ships.get(shipIndex).getPosition())
            if (playfield[coord.getRow()][coord.getCol()] != 'X')
                isShipSunk = false;
    
        return isShipSunk;
    }


    // Checks to see if all the ships are sunk
    public boolean areAllShipsSunk() {
        boolean areAllSunk = true;

        for (Ship ship : ships)
            if (!ship.isSunk())
                areAllSunk = false;

        return areAllSunk;
    }
}