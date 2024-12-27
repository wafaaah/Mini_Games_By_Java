import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WordMatchGameDashboard {
    public static void main(String[] args) {
        // Create a window
        JFrame frame = new JFrame("Word Match Game");
        frame.setSize(700, 600);  // Increased window size for better space
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        // Dashboard panel (shown after game ends)
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(2, 1));
        JLabel dashboardLabel = new JLabel("", SwingConstants.CENTER);
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));  // Set font size and bold for "Play Again"
        playAgainButton.setPreferredSize(new Dimension(200, 50)); // Set a fixed size for play again button
        dashboardPanel.add(dashboardLabel);
        dashboardPanel.add(playAgainButton);

        // Words and meanings
        HashMap<String, String> wordMeaningMap = new HashMap<>();
        wordMeaningMap.put("Apple", "A fruit");
        wordMeaningMap.put("Car", "A vehicle");
        wordMeaningMap.put("Book", "A source of knowledge");
        wordMeaningMap.put("Dog", "A domestic animal");
        wordMeaningMap.put("Ocean", "A large body of water");
        wordMeaningMap.put("Sun", " star ");

        ArrayList<String> words = new ArrayList<>(wordMeaningMap.keySet());
        ArrayList<String> meanings = new ArrayList<>(wordMeaningMap.values());

        // Game variables
        final int[] score = {0}; // To track the score
        final int[] totalAttempts = {0}; // To track the number of matches

        // Start the game
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size and bold for "Start Game"
        startButton.setPreferredSize(new Dimension(300, 100)); // Set a larger size for start button
        frame.add(startButton, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            // Reset variables
            score[0] = 0;
            totalAttempts[0] = 0;

            // Shuffle meanings
            Collections.shuffle(meanings);

            // Prepare panels
            mainPanel.removeAll();

            JPanel wordPanel = new JPanel();
            wordPanel.setLayout(new GridLayout(words.size(), 1, 10, 10)); // Added gap between buttons
            JLabel selectedWordLabel = new JLabel("Selected Word: None", SwingConstants.CENTER);
            selectedWordLabel.setFont(new Font("Arial", Font.PLAIN, 20));  // Make the font readable

            JPanel meaningPanel = new JPanel();
            meaningPanel.setLayout(new GridLayout(meanings.size(), 1, 10, 10)); // Added gap between buttons

            // Variables to store selected word
            final String[] selectedWord = {null};

            // Add words to the word panel
            for (String word : words) {
                JButton wordButton = new JButton(word);
                wordButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font of word buttons
                wordButton.setBackground(Color.CYAN); // Set background color to make buttons stand out
                wordButton.setForeground(Color.BLACK); // Set text color to black for visibility
                wordButton.setPreferredSize(new Dimension(250, 50)); // Set fixed size for word buttons
                wordButton.setFocusPainted(false); // Remove button border when clicked
                wordButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Add border to buttons
                wordButton.addActionListener(ev -> {
                    selectedWord[0] = word;
                    selectedWordLabel.setText("Selected Word: " + word);
                });
                wordPanel.add(wordButton);
            }

            // Add meanings to the meaning panel
            for (String meaning : meanings) {
                JButton meaningButton = new JButton(meaning);
                meaningButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font of meaning buttons
                meaningButton.setBackground(Color.GREEN); // Set background color to differentiate
                meaningButton.setForeground(Color.BLACK); // Set text color to black for visibility
                meaningButton.setPreferredSize(new Dimension(250, 50)); // Set fixed size for meaning buttons
                meaningButton.setFocusPainted(false); // Remove button border when clicked
                meaningButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Add border to buttons
                meaningButton.addActionListener(ev -> {
                    if (selectedWord[0] == null) {
                        JOptionPane.showMessageDialog(frame, "Please select a word first!");
                        return;
                    }
                    totalAttempts[0]++;
                    if (wordMeaningMap.get(selectedWord[0]).equals(meaning)) {
                        JOptionPane.showMessageDialog(frame, "Correct!");
                        score[0]++;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect!");
                    }
                    selectedWord[0] = null; // Reset selected word
                    selectedWordLabel.setText("Selected Word: None");
                    if (totalAttempts[0] == words.size()) {
                        showDashboard(frame, mainPanel, dashboardPanel, dashboardLabel, score[0], words.size());
                    }
                });
                meaningPanel.add(meaningButton);
            }

            // Add panels to the main panel
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(selectedWordLabel, BorderLayout.NORTH);
            mainPanel.add(wordPanel, BorderLayout.WEST);
            mainPanel.add(meaningPanel, BorderLayout.EAST);

            // Refresh UI
            frame.remove(startButton);
            frame.add(mainPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        // Play again button action
        playAgainButton.addActionListener(e -> {
            frame.remove(dashboardPanel);
            frame.add(startButton, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        frame.setVisible(true);
    }

    private static void showDashboard(JFrame frame, JPanel mainPanel, JPanel dashboardPanel, JLabel dashboardLabel, int score, int totalWords) {
        // Calculate performance
        String summary = "<html>Your Score: " + score + "/" + totalWords + "<br>";
        if (score == totalWords) {
            summary += "Excellent job! You matched all words correctly.";
        } else if (score > totalWords / 2) {
            summary += "Good effort! Keep practicing.";
        } else {
            summary += "Try again! Practice makes perfect.";
        }
        summary += "</html>";

        // Update dashboard
        dashboardLabel.setText(summary);

        // Show dashboard
        frame.remove(mainPanel);
        frame.add(dashboardPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
