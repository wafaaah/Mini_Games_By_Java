package games;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class NumberClickGame extends JFrame implements ActionListener {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private int currentNumber = 1;

    public NumberClickGame() {
        setTitle("Number Click Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        createButtons();
        randomizeButtonPositions();
    }

    private void createButtons() {
        for (int i = 1; i <= 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(this);
            button.setSize(60, 60);
            buttons.add(button);
            add(button);
        }
    }

    private void randomizeButtonPositions() {
        Random random = new Random();
        for (JButton button : buttons) {
            int x = random.nextInt(getWidth() - button.getWidth());
            int y = random.nextInt(getHeight() - button.getHeight());
            button.setLocation(x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int clickedNumber = Integer.parseInt(clickedButton.getText());
        if (clickedNumber == currentNumber) {
            clickedButton.setEnabled(false);
            currentNumber++;
            if (currentNumber > 10) {
                JOptionPane.showMessageDialog(this, "You clicked all numbers in order! Well done!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect! Try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberClickGame game = new NumberClickGame();
            game.setVisible(true);
        });
    }
}
