import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TicTacToe {
    JFrame frame = new JFrame("Tic Tac Toe");
    JLabel textLabel = new JLabel("Click Start to Play!", JLabel.CENTER);
    JLabel timerLabel = new JLabel("Time: 0s", JLabel.CENTER);
    JPanel textPanel = new JPanel(new GridLayout(3, 1));
    JPanel boardPanel = new JPanel(new GridLayout(3, 3));
    JPanel buttonPanel = new JPanel();
    JButton[][] board = new JButton[3][3];
    JButton startButton = new JButton("Start");
    JButton restartButton = new JButton("Restart");
    String currentPlayer = "X";
    boolean gameOver = false;
    int turns = 0, elapsedTime = 0;
    Timer timer;

    TicTacToe() {
        frame.setSize(600, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setBackground(Color.RED);
        textLabel.setForeground(Color.ORANGE);
        textLabel.setOpaque(true);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        timerLabel.setBackground(Color.BLACK);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setOpaque(true);

        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setEnabled(false); // Disabled initially

        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);

        textPanel.add(textLabel);
        textPanel.add(timerLabel);
        textPanel.add(buttonPanel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setBackground(Color.RED);
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setBackground(Color.ORANGE);
                tile.setFocusable(false);
                tile.addActionListener(e -> makeMove(tile));
                tile.setEnabled(false); // Initially disabled
                board[r][c] = tile;
                boardPanel.add(tile);
            }
        }

        // Add action listeners for buttons
        startButton.addActionListener(e -> startGame());
        restartButton.addActionListener(e -> resetGame());

        frame.setVisible(true);
    }

    void startGame() {
        gameOver = false;
        turns = 0;
        elapsedTime = 0;
        timerLabel.setText("Time: 0s");
        textLabel.setText(currentPlayer + "'s Turn");
        startButton.setEnabled(false); // Disable Start button
        restartButton.setEnabled(true); // Enable Restart button
        enableBoard(true);
    }

    void makeMove(JButton tile) {
        if (gameOver || !tile.getText().isEmpty()) return;
        if (turns == 0) startTimer();
        tile.setText(currentPlayer);
        turns++;
        if (checkWinner()) {
            textLabel.setText(currentPlayer + " Wins!");
            stopTimer();
            gameOver = true;
            enableBoard(false); // Disable the board
        } else if (turns == 9) {
            textLabel.setText("Draw!");
            stopTimer();
            gameOver = true;
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            textLabel.setText(currentPlayer + "'s Turn");
        }
    }

    boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (checkLine(board[i][0], board[i][1], board[i][2]) || checkLine(board[0][i], board[1][i], board[2][i]))
                return true;
        }
        return checkLine(board[0][0], board[1][1], board[2][2]) || checkLine(board[0][2], board[1][1], board[2][0]);
    }

    boolean checkLine(JButton a, JButton b, JButton c) {
        if (!a.getText().isEmpty() && a.getText().equals(b.getText()) && b.getText().equals(c.getText())) {
            highlightWinner(a, b, c);
            return true;
        }
        return false;
    }

    void highlightWinner(JButton... tiles) {
        for (JButton tile : tiles) tile.setBackground(Color.GREEN);
    }

    void startTimer() {
        timer = new Timer(1000, e -> timerLabel.setText("Time: " + (++elapsedTime) + "s"));
        timer.start();
    }

    void stopTimer() {
        if (timer != null) timer.stop();
    }

    void resetGame() {
        stopTimer();
        gameOver = false;
        turns = 0;
        elapsedTime = 0;
        timerLabel.setText("Time: 0s");
        textLabel.setText("Click Start to Play!");
        currentPlayer = "X";
        enableBoard(false);
        startButton.setEnabled(true); // Enable Start button
        restartButton.setEnabled(false); // Disable Restart button

        // Reset the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.ORANGE);
            }
        }
    }

    void enableBoard(boolean enable) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setEnabled(enable);
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}