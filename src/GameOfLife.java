public class GameOfLife {
    private static final int GRID_SIZE = 10; // Size of the grid
    private boolean[][] grid; // 2D array to represent the grid

    public GameOfLife() {
        grid = new boolean[GRID_SIZE][GRID_SIZE]; // Initializing the grid
        initializeGrid(); // Method to set all elements in the grid to false
    }

    private void initializeGrid() {
        // Set all elements in the grid to false (dead)
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = false;
            }
        }

        // Set a specific cell as alive
        int activeRow = 1;
        int activeCol = 2;
        grid[activeRow][activeCol] = true;
    }

    public void startGame(int maxGenerations) {
        for (int generation = 1; generation <= maxGenerations; generation++) {
            // Update the grid for each generation
            boolean[][] updatedGrid = new boolean[GRID_SIZE][GRID_SIZE];

            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    // Apply the rules of Conway's Game of Life to determine the next state of the cell
                    boolean nextState = applyRules(row, col);

                    // Update the temporary grid with the next state
                    updatedGrid[row][col] = nextState;
                }
            }

            // Replace the original grid with the updated grid
            grid = updatedGrid;

            // Display the current state of the grid
            displayGrid(generation);
        }
    }

    private boolean applyRules(int row, int col) {
        int numAliveNeighbors = countAliveNeighbors(row, col);

        if (grid[row][col]) {
            // Cell is currently alive
            if (numAliveNeighbors < 2 || numAliveNeighbors > 3) {
                // Underpopulation or overpopulation, cell dies
                return false;
            } else {
                // Cell survives
                return true;
            }
        } else {
            // Cell is currently dead
            if (numAliveNeighbors == 3) {
                // Cell becomes alive due to reproduction
                return true;
            } else {
                // Cell remains dead
                return false;
            }
        }
    }

    private int countAliveNeighbors(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    // Skip the current cell
                    continue;
                }

                int neighborRow = row + i;
                int neighborCol = col + j;

                if (isValidCell(neighborRow, neighborCol) && grid[neighborRow][neighborCol]) {
                    // Neighbor cell is alive
                    count++;
                }
            }
        }

        return count;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
    }

    private void displayGrid(int generation) {
        System.out.println("Generation: " + generation);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col]) {
                    System.out.print("X "); // Print 'X' for alive cells
                } else {
                    System.out.print(". "); // Print '.' for dead cells
                }
            }
            System.out.println(); // Move to the next row
        }
    }

    public static void main(String[] args) {
        // Create an instance of GameOfLife and start the game
        GameOfLife game = new GameOfLife();
        game.startGame(10);
    }
}
