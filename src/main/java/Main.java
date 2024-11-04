import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Main extends JFrame implements ActionListener {

    JButton newGameButton = new JButton();
    JButton winGameButton = new JButton();
    JButton emptySpace;
    JPanel topPanel = new JPanel();
    JPanel gamePanel;
    JPanel mainPanel;
    JLabel textYouWon;
    List<JButton> buttons = new ArrayList<>();
    List<String> sortedButtons = new ArrayList<>();

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        newGameButton = new JButton("Nytt Spel");
        winGameButton = new JButton("Snabbseger");
        topPanel = new JPanel();
        gamePanel = new JPanel();
        mainPanel = new JPanel();
        emptySpace = new JButton(" ");
        emptySpace.setEnabled(false);

        addTopPanel();

        gamePanel.setLayout(new GridLayout(4, 4));
        gamePanel.setBackground(Color.WHITE);

        initializeBoard();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        winGameButton.addActionListener(this);
        newGameButton.addActionListener(this);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addTopPanel() {
        topPanel.setLayout(new GridLayout(1, 2));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(winGameButton);
        topPanel.add(newGameButton);
    }

    private void initializeBoard() {
        sortedButtons.clear();
        clearMainPanel();
        addButtonList();
        Collections.shuffle(buttons);
        buttons.add(emptySpace);
        addSortedButtonList();
        addButtonsToGamePanel();
    }

    private void initializeSortedBoard() {
        clearMainPanel();
        addButtonList();
        buttons.add(emptySpace);
        addButtonsToGamePanel();
    }

    private void clearMainPanel() {
        buttons.clear();
        gamePanel.removeAll();
    }

    private void addButtonList() {
        for (int i = 1; i < 16; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(this);
            buttons.add(button);
        }
    }

    private void addSortedButtonList() {
        for (int i = 1; i < 16; i++) {
            sortedButtons.add(String.valueOf(i));
        }
        sortedButtons.add(" ");
    }

    private void addButtonsToGamePanel() {
        gamePanel.removeAll();
        for (JButton button : buttons) {
            gamePanel.add(button);
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            addNewMainPanel();
            return;
        }

        if (e.getSource() == winGameButton) {
            initializeSortedBoard();
            return;
        }

        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = buttons.indexOf(clickedButton);
        int emptyButtonIndex = buttons.indexOf(emptySpace);

        if (gameCondition(clickedIndex, emptyButtonIndex)) {
            Collections.swap(buttons, clickedIndex, emptyButtonIndex);
            addButtonsToGamePanel();

            if (checkWin()) {
                showYouWonMessage();
            }
        }
    }

    private void addNewMainPanel() {
        mainPanel.removeAll();
        topPanel.removeAll();
        initializeBoard();
        addTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);
        pack();
        revalidate();
        repaint();
    }

    private void showYouWonMessage() {
            mainPanel.removeAll();
            textYouWon = new JLabel("Grattis, du vann!", JLabel.CENTER);
            mainPanel.add(textYouWon, BorderLayout.NORTH);
            mainPanel.add(newGameButton, BorderLayout.SOUTH);
            pack();

            mainPanel.revalidate();
            mainPanel.repaint();
    }

    private boolean checkWin() {
        List<String> currentOrder = new ArrayList<>();

        for (JButton button : buttons) {
            currentOrder.add(button.getText());
        }

        return currentOrder.equals(sortedButtons);
    }

    private boolean gameCondition(int clickedIndex, int emptyButtonIndex) {
        return (clickedIndex + 1 == emptyButtonIndex && clickedIndex % 4 != 3) ||
                (clickedIndex - 1 == emptyButtonIndex && clickedIndex % 4 != 0) ||
                (clickedIndex - 4 == emptyButtonIndex) ||
                (clickedIndex + 4 == emptyButtonIndex);
    }

}
