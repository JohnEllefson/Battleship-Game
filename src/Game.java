import java.util.Scanner;

// Manages the Battleship game
public class Game {
    Board board;
    boolean isGameOver;
    int turn;
    Scanner stringScanner = new Scanner(System.in);


    // Default constructor for Game
    public Game() {
        board = new Board();
        isGameOver = false;
        turn = 1;
    }


    // Continuously runs the Game
    public void runGame() {
        while (!isGameOver) {
            board.displayBoard(turn);

            isGameOver = board.areAllShipsSunk();

            // Request a coordinate from the user if the game is not over
            if (!isGameOver)
                requestUserInput();
            else
                System.out.println("Congratulations, you sunk all enemy ships in " + turn + " turns!\n");
        }
    }


    // Requests a coordinate to shoot from the user
    private void requestUserInput() {
        String coordInput;
        boolean isCoordValid = false;

        // Request user input until valid coordinates are given
        while (!isCoordValid) {
            // Get input
            System.out.print("Please enter a coordinate to shoot: ");
            coordInput = stringScanner.nextLine();

            // Call shootCoord() with given coordinates IF they're valid (i.e. coord is 2 characters, one alphabetical, one a digit) 
            if (coordInput.length() == 2)
                if ((Character.isLetter(coordInput.charAt(0)) && Character.isDigit(coordInput.charAt(1))) ||
                    (Character.isDigit(coordInput.charAt(0)) && Character.isLetter(coordInput.charAt(1))))
                {
                    // Convert the alpha and digit chars into ints for a coordinate to call shootCoord()
                    if (Character.isLetter(coordInput.charAt(0))) {
                        isCoordValid = board.shootCoord(Character.getNumericValue(coordInput.charAt(1) - 1),
                                                        coordAlphaToNum(coordInput.charAt(0)));
                    }
                    else {
                        isCoordValid = board.shootCoord(Character.getNumericValue(coordInput.charAt(0) - 1),
                                                        coordAlphaToNum(coordInput.charAt(1)));
                    }
                }  

                // If invalid coordinates are given, print an error message
                if (!isCoordValid)
                    System.out.println("ERROR: Coordinates are invalid\n");
        }

        turn++;
    }


    // Converts the coordinate alpha character to it's respective column index
    private int coordAlphaToNum(char alphaChar) {
        switch (Character.toUpperCase(alphaChar)) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            default:
                return -1;
        }
    }
}