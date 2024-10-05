import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WordleGUI extends JFrame {
    // Predefined list of words to guess
    private static final String[] WORD_LIST = {"hello", "world", "apple", "robot", "smart", "track", "horse", "light", "brick", "jumps"};
    private static String targetWord;
    private static final int MAX_ATTEMPTS = 6;
    private int attempt = 0;

    // UI components
    private JTextField guessField;
    private JPanel feedbackPanel;
    private JLabel attemptsLabel;

    public WordleGUI() {
        // Set a random word to guess
        setRandomTargetWord();

        // Setup JFrame
        setTitle("Wordle");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        guessField = new JTextField(5);
        JButton submitButton = new JButton("Submit");

        inputPanel.add(new JLabel("Enter 5-letter word:"));
        inputPanel.add(guessField);
        inputPanel.add(submitButton);

        // Attempts label
        attemptsLabel = new JLabel("Attempts: 0/" + MAX_ATTEMPTS);
        inputPanel.add(attemptsLabel);

        add(inputPanel, BorderLayout.NORTH);

        // Feedback panel
        feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new GridLayout(MAX_ATTEMPTS, 1));
        add(feedbackPanel, BorderLayout.CENTER);

        // Action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guess = guessField.getText().toLowerCase();
                if (guess.length() == 5) {
                    attempt++;
                    giveFeedback(guess);
                    attemptsLabel.setText("Attempts: " + attempt + "/" + MAX_ATTEMPTS);

                    if (guess.equals(targetWord)) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You've guessed the word!");
                        resetGame();
                    } else if (attempt == MAX_ATTEMPTS) {
                        JOptionPane.showMessageDialog(null, "Game Over! The word was: " + targetWord.toUpperCase());
                        resetGame();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a 5-letter word.");
                }
            }
        });
    }

    // Method to give feedback with color-coded output
    private void giveFeedback(String guess) {
        JPanel row = new JPanel();
        row.setLayout(new FlowLayout());

        for (int i = 0; i < 5; i++) {
            JLabel letterLabel = new JLabel(Character.toString(guess.charAt(i)).toUpperCase());
            letterLabel.setOpaque(true);
            letterLabel.setFont(new Font("Arial", Font.BOLD, 20));
            letterLabel.setPreferredSize(new Dimension(50, 50));
            letterLabel.setHorizontalAlignment(JLabel.CENTER); // Center horizontally
            letterLabel.setVerticalAlignment(JLabel.CENTER);   // Center vertically
            letterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            // Set background color based on letter correctness
            if (guess.charAt(i) == targetWord.charAt(i)) {
                letterLabel.setBackground(Color.GREEN); // Correct letter and position
            } else if (targetWord.contains(String.valueOf(guess.charAt(i)))) {
                letterLabel.setBackground(Color.YELLOW); // Correct letter, wrong position
            } else {
                letterLabel.setBackground(Color.LIGHT_GRAY); // Incorrect letter
            }

            row.add(letterLabel);
        }

        feedbackPanel.add(row);
        feedbackPanel.revalidate();
    }

    // Method to randomly pick a target word from the list
    private void setRandomTargetWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(WORD_LIST.length);
        targetWord = WORD_LIST[randomIndex];
    }

    // Method to reset the game after winning or losing
    private void resetGame() {
        attempt = 0;
        feedbackPanel.removeAll();
        feedbackPanel.revalidate();
        feedbackPanel.repaint();
        attemptsLabel.setText("Attempts: 0/" + MAX_ATTEMPTS);
        setRandomTargetWord(); // Pick a new random word for the next game
    }

    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordleGUI().setVisible(true);
            }
        });
    }
}