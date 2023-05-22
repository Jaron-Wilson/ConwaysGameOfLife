import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameOfLifeNoClickGUI extends JFrame {
    private static final int GRID_SIZE = 20;
    private boolean[][] grid;
    private JButton startButton;
    private JButton stopButton;
    private JSlider speedSlider;
    private Timer timer;

    public GameOfLifeNoClickGUI() {
        grid = new boolean[GRID_SIZE][GRID_SIZE];

        initializeGrid();

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.WHITE);
                cell.setOpaque(true);

                gridPanel.add(cell);
            }
        }

        JPanel controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        speedSlider = new JSlider(0, 1000, 500);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        speedSlider.addChangeListener(e -> {
            if (timer != null) {
                timer.setDelay(speedSlider.getValue());
            }
        });

        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(speedSlider);

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

        Random random = new Random();
        int count = 0;
        while (count < 5) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (!grid[row][col]) {
                grid[row][col] = true;
                count++;
            }
        }

        updateCellStates();
    }


    private void updateCellStates() {
        Container contentPane = getContentPane();
        if (contentPane.getComponentCount() > 0) {
            JPanel gridPanel = (JPanel) contentPane.getComponent(0);
            gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 1, 1)); // Add grid gaps

            gridPanel.removeAll(); // Clear existing cells and grid lines

            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    JPanel cell = new JPanel();
                    cell.setBackground(grid[i][j] ? Color.BLACK : Color.WHITE);
                    cell.setOpaque(true);

                    gridPanel.add(cell);

                    if (i != GRID_SIZE - 1) {
                        JPanel horizontalLine = new JPanel();
                        horizontalLine.setBackground(Color.BLACK);
                        gridPanel.add(horizontalLine);
                    }
                }

                if (i != GRID_SIZE - 1) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        JPanel verticalLine = new JPanel();
                        verticalLine.setBackground(Color.BLACK);
                        gridPanel.add(verticalLine);

                        if (j != GRID_SIZE - 1) {
                            JPanel emptyPanel = new JPanel();
                            emptyPanel.setBackground(Color.BLACK);
                            gridPanel.add(emptyPanel);
                        }
                    }
                }
            }

            gridPanel.revalidate();
            gridPanel.repaint();
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

        updateCellStates();
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

    private void startSimulation() {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(speedSlider.getValue(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextGeneration();
            }
        });
        timer.start();
    }

    private void stopSimulation() {
        if (timer != null) {
            timer.stop();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameOfLifeNoClickGUI::new);
    }
}
