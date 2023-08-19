import java.util.*;

public class Project1 {
    public static boolean checkWin(int[][] inputArray) { //Method to check if the player has reached 2048
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (inputArray[i][j] == 2048)
                    return true;
            }
        }
        return false;
    }

    public static boolean checkFullBoard(int[][] inputArray) { //Method to check if the board is full
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (inputArray[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public static boolean checkMovable(int[][] inputArray) { //Method to check if there is any valid move left
        if (!checkFullBoard(inputArray))
            return true;
        else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (inputArray[i][j] == inputArray[i][j + 1]) //Horizontal check
                        return true;
                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (inputArray[i][j] == inputArray[i + 1][j]) //Vertical check
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean checkValidMove(int[][] array1, int[][] array2) { //Method to check if a move is a valid move or not
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (array1[i][j] != array2[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getMax(int[][] inputArray) { //Method to get the highest score of the board
        int max = inputArray[0][0];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (inputArray[i][j] > max)
                    max = inputArray[i][j];
            }
        }
        return max;
    }

    public static void printArray(int[][] inputArray) { //Method to print the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%-6s", inputArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] generateRandomNumber(int[][] inputArray) { //Method to generate random 2 or 4 in random spots on the board
        int[] posStore = new int[16];
        int pos;
        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (inputArray[i][j] == 0) {
                    pos = i * 4 + j;
                    posStore[count] = pos; //Store the position of 0s on the board to a 1D array
                    count++;
                }
            }
        }

        int rand = new Random().nextInt(count);
        int value = posStore[rand];
        if (Math.random() >= 0.2) {
            inputArray[value / 4][value % 4] = 2; //Convert the value back to coordinates on the board and assign 2 or 4
        } else {
            inputArray[value / 4][value % 4] = 4;
        }
        return inputArray;
    }

    public static void concatDirection(int[][] inputArray, int dir) { //Method to move the numbers based on player's direction input
        if (dir == 1) { //Move left
            for (int i = 0; i < 4; i++) {
                ArrayList<Integer> store = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    if (inputArray[i][j] != 0)
                        store.add(inputArray[i][j]); //Store non-zero values into an arraylist
                }
                for (int j = 0; j < store.size(); j++) {
                    inputArray[i][j] = store.get(j); //Put all non-zero values to the left of the board
                }
                for (int j = store.size(); j < 4; j++) { //Assign the rest with 0s
                    inputArray[i][j] = 0;
                }
            }
        } else if (dir == 2) { //Move right
            for (int i = 0; i < 4; i++) {
                ArrayList<Integer> store = new ArrayList<>();
                for (int j = 3; j >= 0; j--) {
                    if (inputArray[i][j] != 0)
                        store.add(inputArray[i][j]);
                }
                for (int j = 3; j > 3 - store.size(); j--) {
                    inputArray[i][j] = store.get(3 - j);
                }
                for (int j = 3 - store.size(); j >= 0; j--) {
                    inputArray[i][j] = 0;
                }
            }
        } else if (dir == 3) { //Move up
            for (int i = 0; i < 4; i++) {
                ArrayList<Integer> store = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    if (inputArray[j][i] != 0)
                        store.add(inputArray[j][i]);
                }
                for (int j = 0; j < store.size(); j++) {
                    inputArray[j][i] = store.get(j);
                }
                for (int j = store.size(); j < 4; j++) {
                    inputArray[j][i] = 0;
                }
            }
        } else if (dir == 4) { //Move down
            for (int i = 0; i < 4; i++) {
                ArrayList<Integer> store = new ArrayList<>();
                for (int j = 3; j >= 0; j--) {
                    if (inputArray[j][i] != 0)
                        store.add(inputArray[j][i]);
                }
                for (int j = 3; j > 3 - store.size(); j--) {
                    inputArray[j][i] = store.get(3 - j);
                }
                for (int j = 3 - store.size(); j >= 0; j--) {
                    inputArray[j][i] = 0;
                }
            }
        }
    }

    public static int[][] moveNumber(int[][] inputArray, char dir) { //Method to add numbers based on player's direction input
        int[][] clone = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                clone[i][j] = inputArray[i][j]; //Store values of the board to a 2D array
            }
        }

        if (dir == 'a') { //Left addition
            concatDirection(clone, 1);
            for (int i = 0; i < 4; i++) {
                if (clone[i][0] == clone[i][1] && clone[i][2] == clone[i][3]) {
                    clone[i][0] += clone[i][1];
                    clone[i][2] += clone[i][3];
                    clone[i][1] = 0;
                    clone[i][3] = 0;
                } else if (clone[i][0] == clone[i][1]) {
                    clone[i][0] += clone[i][1];
                    clone[i][1] = 0;
                } else if (clone[i][1] == clone[i][2]) {
                    clone[i][1] += clone[i][2];
                    clone[i][2] = 0;
                } else if (clone[i][2] == clone[i][3]) {
                    clone[i][2] += clone[i][3];
                    clone[i][3] = 0;
                }
            }
            concatDirection(clone, 1);
        } else if (dir == 'd') { //Right addition
            concatDirection(clone, 2);
            for (int i = 0; i < 4; i++) {
                if (clone[i][3] == clone[i][2] && clone[i][1] == clone[i][0]) {
                    clone[i][3] += clone[i][2];
                    clone[i][1] += clone[i][0];
                    clone[i][2] = 0;
                    clone[i][0] = 0;
                } else if (clone[i][3] == clone[i][2]) {
                    clone[i][3] += clone[i][2];
                    clone[i][2] = 0;
                } else if (clone[i][2] == clone[i][1]) {
                    clone[i][2] += clone[i][1];
                    clone[i][1] = 0;
                } else if (clone[i][1] == clone[i][0]) {
                    clone[i][1] += clone[i][0];
                    clone[i][0] = 0;
                }
            }
            concatDirection(clone, 2);
        } else if (dir == 'w') { //Up addition
            concatDirection(clone, 3);
            for (int i = 0; i < 4; i++) {
                if (clone[0][i] == clone[1][i] && clone[2][i] == clone[3][i]) {
                    clone[0][i] += clone[1][i];
                    clone[2][i] += clone[3][i];
                    clone[1][i] = 0;
                    clone[3][i] = 0;
                } else if (clone[0][i] == clone[1][i]) {
                    clone[0][i] += clone[1][i];
                    clone[1][i] = 0;
                } else if (clone[1][i] == clone[2][i]) {
                    clone[1][i] += clone[2][i];
                    clone[2][i] = 0;
                } else if (clone[2][i] == clone[3][i]) {
                    clone[2][i] += clone[3][i];
                    clone[3][i] = 0;
                }
            }
            concatDirection(clone, 3);
        } else if (dir == 's') { //Down addition
            concatDirection(clone, 4);
            for (int i = 0; i < 4; i++) {
                if (clone[0][i] == clone[1][i] && clone[2][i] == clone[3][i]) {
                    clone[3][i] += clone[2][i];
                    clone[1][i] += clone[0][i];
                    clone[0][i] = 0;
                    clone[2][i] = 0;
                } else if (clone[3][i] == clone[2][i]) {
                    clone[3][i] += clone[2][i];
                    clone[2][i] = 0;
                } else if (clone[2][i] == clone[1][i]) {
                    clone[2][i] += clone[1][i];
                    clone[1][i] = 0;
                } else if (clone[1][i] == clone[0][i]) {
                    clone[1][i] += clone[0][i];
                    clone[0][i] = 0;
                }
            }
            concatDirection(clone, 4);
        }
        return clone;
    }

    public static void gameExecute() { //Game execution method
        Scanner s = new Scanner(System.in);
        int[][] defaultArray = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int validMoveCount = 0;

        generateRandomNumber(defaultArray);
        printArray(generateRandomNumber(defaultArray)); //Print the initial board
        while (!checkWin(defaultArray) && checkMovable(defaultArray)) {
            System.out.println("Enter key:");
            char move = s.next().charAt(0); //Scan for player's input
            if (move == 'q') { //Quit game
                System.out.println("Enter c to confirm quitting");
                move = s.next().charAt(0);
                if (move == 'c') //Confirm quit
                    break;
            } else if (move == 'r') { //Restart game
                System.out.println("Enter c to confirm restarting");
                move = s.next().charAt(0);
                if (move == 'c') //Confirm restart
                    gameExecute();
            } else if (move == 'a' || move == 'd' || move == 'w' || move == 's') { //Check for move keys
                for (int i = 0; i < 20; i++) {
                    System.out.println(); //Print multiple lines to clear screen
                }
                if (checkValidMove(defaultArray, moveNumber(defaultArray, move))) {
                    defaultArray = moveNumber(defaultArray, move);
                    printArray(generateRandomNumber(defaultArray)); //Print the new board with a number added
                    validMoveCount++;
                    System.out.println("High score: " + getMax(defaultArray)); //Print max number on the board
                    System.out.println("Total moves: " + validMoveCount); //Print total moves
                } else { //Check for other invalid keys
                    System.out.println("No change to the board - Invalid move");
                    printArray(defaultArray);
                }
            } else {
                System.out.println("Invalid key. Please enter a valid key");
            }
        }

        if (checkWin(defaultArray)) //End game print
            System.out.println("Congratulations! You have reached 2048");
        else
            System.out.println("Game Over!");
    }

    public static void main(String[] args) { //Main method to call gameExecute
        gameExecute();
    }
}