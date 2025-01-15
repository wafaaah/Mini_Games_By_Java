import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MemoryMatchGame extends JFrame {
    private JPanel introPanel, menuPanel, gamePanel, scorePanel;
    private JLabel scoreLabel;
    private JButton[] buttons;
    private String[] icons;
    private boolean[] revealed;
    private int score, clicks, firstIndex, secondIndex;
    private int[] pairFlips; // Tracks flip counts for each pair
    private int pairsMatched;

    public MemoryMatchGame() {
        setTitle("Memory Match Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Initialize Panels
        introPanel = new JPanel();
        menuPanel = new JPanel();
        gamePanel = new JPanel();
        scorePanel = new JPanel();

        createIntroPanel();
        createMenuPanel();
        createGamePanel();
        createScorePanel();

        add(introPanel, "IntroPanel");
        add(menuPanel, "MenuPanel");
        add(gamePanel, "GamePanel");
        add(scorePanel, "ScorePanel");

        showIntroPanel();
    }

    private void createIntroPanel() {
        introPanel.setLayout(new BorderLayout());
        introPanel.setBackground(new Color(204, 153, 102));

        JLabel title = new JLabel("Memory Match Game", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        title.setForeground(new Color(102, 51, 0));
        introPanel.add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Bradley Hand", Font.PLAIN, 20));
        startButton.setBackground(new Color(255, 200, 128));
        startButton.setForeground(new Color(102, 51, 0));
        startButton.addActionListener(e -> showMenuPanel());
        introPanel.add(startButton, BorderLayout.SOUTH);
    }

    private void createMenuPanel() {
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBackground(new Color(139, 69, 19));

        JLabel menuLabel = new JLabel("Ready to Play?", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 30));
        menuLabel.setForeground(Color.WHITE);
        menuPanel.add(menuLabel, BorderLayout.CENTER);

        JButton letsPlayButton = new JButton("Let's Play");
        letsPlayButton.setFont(new Font("Brush Script MT", Font.ITALIC, 22));
        letsPlayButton.setBackground(new Color(205, 133, 63));
        letsPlayButton.setForeground(Color.WHITE);
        letsPlayButton.addActionListener(e -> startGame());
        menuPanel.add(letsPlayButton, BorderLayout.SOUTH);
    }

    private void createGamePanel() {
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(255, 239, 204));

        JPanel gridPanel = new JPanel(new GridLayout(3, 4));
        gamePanel.add(gridPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0/120", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(102, 51, 0));
        gamePanel.add(scoreLabel, BorderLayout.NORTH);

        buttons = new JButton[12];
        icons = new String[]{"Ruby", "Ruby", "Java", "Java", "C++", "C++", "Python", "Python", "HTML", "HTML", "CSS", "CSS"};
        revealed = new boolean[12];
        score = 0;
        clicks = 0;
        firstIndex = -1;
        secondIndex = -1;
        pairFlips = new int[6]; // 6 pairs
        pairsMatched = 0;

        shuffleArray(icons);

        for (int i = 0; i < 12; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Courier New", Font.PLAIN, 22));
            buttons[i].setBackground(new Color(222, 184, 135));
            buttons[i].setForeground(new Color(102, 51, 0));
            final int index = i;
            buttons[i].addActionListener(e -> handleButtonClick(index));
            gridPanel.add(buttons[i]);
        }
    }

    private void createScorePanel() {
        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBackground(new Color(255, 228, 196));

        JLabel finalScoreLabel = new JLabel("Game Over! Your Score: 0", SwingConstants.CENTER);
        finalScoreLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        finalScoreLabel.setForeground(new Color(139, 69, 19));
        scorePanel.add(finalScoreLabel, BorderLayout.CENTER);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        playAgainButton.setBackground(new Color(255, 140, 0));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.addActionListener(e -> restartGame());
        scorePanel.add(playAgainButton, BorderLayout.SOUTH);
    }

    private void showIntroPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "IntroPanel");
    }

    private void showMenuPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "MenuPanel");
    }

    private void startGame() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "GamePanel");
    }

    private void restartGame() {
        for (int i = 0; i < 12; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            revealed[i] = false;
        }
        shuffleArray(icons);
        score = 0;
        clicks = 0;
        firstIndex = -1;
        secondIndex = -1;
        pairFlips = new int[6]; // Reset flip counts
        pairsMatched = 0;
        scoreLabel.setText("Score: 0/120");

        showMenuPanel();
    }

    private void handleButtonClick(int index) {
        if (revealed[index] || clicks == 2) return;

        buttons[index].setText(icons[index]); // Show the word
        if (clicks == 0) {
            firstIndex = index;
            clicks++;
        } else if (clicks == 1) {
            secondIndex = index;
            clicks++;
            new javax.swing.Timer(1000, e -> checkMatch()).start(); // Delay checkMatch to show the second click
        }
    }

    private void checkMatch() {
        if (icons[firstIndex].equals(icons[secondIndex])) {
            revealed[firstIndex] = true;
            revealed[secondIndex] = true;

            int pairIndex = firstIndex / 2;
            pairFlips[pairIndex]++;

            if (pairFlips[pairIndex] == 1) score += 20;
            else if (pairFlips[pairIndex] == 2) score += 10;
            else if (pairFlips[pairIndex] == 3) score += 5;

            pairsMatched++;
        } else {
            buttons[firstIndex].setText("");
            buttons[secondIndex].setText("");
        }

        firstIndex = -1;
        secondIndex = -1;
        clicks = 0;

        scoreLabel.setText("Score: " + score + "/120");

        if (isGameOver()) showScorePanel();
    }

    private boolean isGameOver() {
        for (int flip : pairFlips) {
            if (flip > 3) return true; // Game ends if a pair exceeds 6 flips
        }
        return pairsMatched == 6;
    }

    private void showScorePanel() {
        JLabel finalScoreLabel = (JLabel) scorePanel.getComponent(0);
        finalScoreLabel.setText("Game Over! Your Score: " + score);
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "ScorePanel");
    }

    private void shuffleArray(String[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemoryMatchGame game = new MemoryMatchGame();
            game.setVisible(true);
        });
    }
}
