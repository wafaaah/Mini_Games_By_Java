import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.*;

public class WordMatchGame {
    public static void main(String[] args) {
        // Create the main window
        JFrame frame = new JFrame("Word Match Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Game variables
        final int[] score = {0}; // To track the score
        final int[] currentWordIndex = {0}; // To track the current word index

        // Words and meanings
        HashMap<String, String> wordMeaningMap = new HashMap<>();
        wordMeaningMap.put("Drip", "Fashionable");
        wordMeaningMap.put("Sus", "Suspicious");
        wordMeaningMap.put("Lit", "Exciting");
        wordMeaningMap.put("Ghost", "To ignore someone");
        wordMeaningMap.put("FOMO", "Fear of missing out");
        wordMeaningMap.put("Cap", "A lie");

        ArrayList<String> words = new ArrayList<>(wordMeaningMap.keySet());
        ArrayList<String> meanings = new ArrayList<>(wordMeaningMap.values());

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // "Word Match Game" tab
        JPanel introPanel = new JPanel(new GridLayout(1, 1));
        introPanel.setBackground(new Color(255, 192, 203)); // Pink background
        JLabel introLabel = new JLabel("Word Match Game", SwingConstants.CENTER);
        introLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 48)); // Bubbly font
        introLabel.setForeground(new Color(255, 105, 180)); // Hot pink
        introPanel.add(introLabel);
        tabbedPane.addTab("Home", introPanel);

        // "Start Game" tab
        JPanel startPanel = new JPanel(new GridLayout(1, 1));
        startPanel.setBackground(new Color(255, 228, 225)); // Light pink background
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        startButton.setBackground(new Color(255, 182, 193));
        startButton.setForeground(Color.BLACK);
        startButton.addActionListener(e -> {
            // Shuffle the words for each game
            Collections.shuffle(words);
            currentWordIndex[0] = 0;
            score[0] = 0;
            showGamePanel(tabbedPane, wordMeaningMap, words, meanings, currentWordIndex, score);
        });
        startPanel.add(startButton);
        tabbedPane.addTab("Start Game", startPanel);

        // "Game" tab - Placeholder to dynamically show words
        JPanel gamePanel = new JPanel(new GridLayout(1, 1));
        tabbedPane.addTab("Game", gamePanel);

        // "Dashboard" tab
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 1));
        dashboardPanel.setBackground(new Color(255, 228, 225)); // Light pink background
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        resultLabel.setForeground(new Color(255, 105, 180));
        dashboardPanel.add(resultLabel);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        playAgainButton.setBackground(new Color(255, 182, 193));
        playAgainButton.setForeground(Color.BLACK);
        playAgainButton.addActionListener(e -> tabbedPane.setSelectedIndex(1)); // Back to "Start Game"
        dashboardPanel.add(playAgainButton);

        tabbedPane.addTab("Dashboard", dashboardPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static void showGamePanel(JTabbedPane tabbedPane, HashMap<String, String> wordMeaningMap, ArrayList<String> words, ArrayList<String> meanings, int[] currentWordIndex, int[] score) {
        JPanel gamePanel = new JPanel(new GridLayout(4, 1, 10, 10));
        gamePanel.setBackground(new Color(255, 228, 225)); // Light pink background

        // Current word
        String currentWord = words.get(currentWordIndex[0]);
        JLabel wordLabel = new JLabel("Select the meaning of: " + currentWord, SwingConstants.CENTER);
        wordLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        wordLabel.setForeground(new Color(255, 105, 180));
        gamePanel.add(wordLabel);

        // Generate choices
        String correctMeaning = wordMeaningMap.get(currentWord);
        ArrayList<String> choices = new ArrayList<>(meanings);
        choices.remove(correctMeaning);
        Collections.shuffle(choices);
        choices = new ArrayList<>(choices.subList(0, 2));
        choices.add(correctMeaning);
        Collections.shuffle(choices);

        for (String choice : choices) {
            JButton choiceButton = new JButton(choice);
            choiceButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            choiceButton.setBackground(new Color(255, 240, 245)); // Light pink
            choiceButton.setForeground(Color.BLACK);
            choiceButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2)); // Hot pink border
            choiceButton.addActionListener(e -> {
                if (choice.equals(correctMeaning)) {
                    JOptionPane.showMessageDialog(tabbedPane, "Correct!");
                    score[0]++;
                } else {
                    JOptionPane.showMessageDialog(tabbedPane, "Incorrect!");
                }
                currentWordIndex[0]++;
                if (currentWordIndex[0] < words.size()) {
                    showGamePanel(tabbedPane, wordMeaningMap, words, meanings, currentWordIndex, score);
                } else {
                    showDashboard(tabbedPane, score[0], words.size());
                }
            });
            gamePanel.add(choiceButton);
        }

        tabbedPane.setComponentAt(2, gamePanel);
        tabbedPane.setSelectedIndex(2); // Move to the "Game" tab
    }

    private static void showDashboard(JTabbedPane tabbedPane, int score, int totalWords) {
        JPanel dashboardPanel = (JPanel) tabbedPane.getComponentAt(3);
        JLabel resultLabel = (JLabel) dashboardPanel.getComponent(0);
        resultLabel.setText("Your score: " + score + "/" + totalWords);
        tabbedPane.setSelectedIndex(3); // Move to the "Dashboard" tab
    }
}