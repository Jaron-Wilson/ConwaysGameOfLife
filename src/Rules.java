public class Rules {
    public boolean applyRules(Grid grid, int row, int col) {
        int numAliveNeighbors = countAliveNeighbors(grid, row, col);

        if (grid.isValidCell(row, col)) {
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

    private int countAliveNeighbors(Grid grid, int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    // Skip the current cell
                    continue;
                }

                int neighborRow = row + i;
                int neighborCol = col + j;

                if (grid.isValidCell(neighborRow, neighborCol) && grid.getCellState(neighborRow, neighborCol)) {
                    // Neighbor cell is alive
                    count++;
                }
            }
        }

        return count;
    }
}
