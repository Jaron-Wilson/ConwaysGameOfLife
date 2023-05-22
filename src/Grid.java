public class Grid {
    private boolean[][] grid; // 2D array to represent the grid
    private int gridSize; // Size of the grid

    public Grid(int size) {
        gridSize = size;
        grid = new boolean[gridSize][gridSize];
        initializeGrid();
    }

    void initializeGrid() {
        // Set all elements in the grid to false (dead)
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = false;
            }
        }
    }

    public void setCellState(int row, int col, boolean state) {
        if (isValidCell(row, col)) {
            grid[row][col] = state;
        }
    }

    public boolean getCellState(int row, int col) {
        if (isValidCell(row, col)) {
            return grid[row][col];
        }
        return false; // Invalid cell, return false (dead)
    }

    public void setGrid(boolean[][] newGrid) {
        if (newGrid.length == gridSize && newGrid[0].length == gridSize) {
            grid = newGrid;
        }
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    public void display() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col]) {
                    System.out.print("X "); // Print 'X' for alive cells
                } else {
                    System.out.print(". "); // Print '.' for dead cells
                }
            }
            System.out.println(); // Move to the next row
        }
    }
}
