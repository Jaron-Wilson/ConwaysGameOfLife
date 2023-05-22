import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLifeGUI extends JFrame {
    private static final int GRID_SIZE = 10;
    private boolean[][] grid;
    private JButton[][] cells;
    private JSlider slider;
    private Timer timer;

    public GameOfLifeGUI() {
        grid = new boolean[GRID_SIZE][GRID_SIZE];
        cells = new JButton[GRID_SIZE][GRID_SIZE];

        initializeGrid();

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton cell = new JButton();
                cell.setBackground(Color.WHITE);
                cell.setOpaque(true);
                cell.setBorderPainted(false);

                final int row = i;
                final int col = j;
                cell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        grid[row][col] = !grid[row][col];
                        updateCellState(row, col);
                    }
                });

                gridPanel.add(cell);
                cells[i][j] = cell;
            }
        }

        JPanel controlPanel = new JPanel(new FlowLayout());
        slider = new JSlider(0, 1000, 500);
        slider.addChangeListener(e -> {
            if (timer != null) {
                timer.setDelay(slider.getValue());
            }
        });

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            if (timer != null) {
                timer.stop();
            }
            timer = new Timer(slider.getValue(), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nextGeneration();
                }
            });
            timer.start();
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            if (timer != null) {
                timer.stop();
            }
        });

        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(slider);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game of Life");
        pack();
        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = false;
            }
        }
    }

    private void updateCellState(int row, int col) {
        JButton cell = cells[row][col];
        if (grid[row][col]) {
            cell.setBackground(Color.BLACK);
        } else {
            cell.setBackground(Color.WHITE);
        }
    }

    private void nextGeneration() {
        boolean[][] updatedGrid = new boolean[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                boolean nextState = applyRules(row, col);
                updatedGrid[row][col] = nextState;
            }
        }

        grid = updatedGrid;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                updateCellState(i, j);
            }
        }
    }

    private boolean applyRules(int row, int col) {
        int numAliveNeighbors = countAliveNeighbors(row, col);

        if (grid[row][col]) {
            if (numAliveNeighbors < 2 || numAliveNeighbors > 3) {
                return false;
            } else {
                return true;
            }
        } else {
            if (numAliveNeighbors == 3) {
                return true;
            } else {
                return false;
            }
        }
    }

    private int countAliveNeighbors(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int neighborRow = row + i;
                int neighborCol = col + j;
                if (isValidCell(neighborRow, neighborCol) && grid[neighborRow][neighborCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameOfLifeGUI();
            }
        });
    }
}
