import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
//This is just my "Drawing Board" for this assignment. For Test purposes only!
//Keeping since I have a lot of comments on here to help me explain what I coded and what it means in the scope of the program
public class TTTFrame extends JFrame {
    //2 Panels
    private JPanel titlePanel;
    private JPanel boardPanel;
    private JPanel mainPanel;

    //Button
    private JButton tileButton;

    //Label
    JLabel titleLabel;

    //2D Array of Buttons
    private JButton[][] board;

    //Height and width of the board game.
    int boardHeight = 650;
    int boardWidth = 600;
    //Other variables
    String currentPlayer = "X";
    boolean isWin = false;
    int moveCounter = 0;

    public TTTFrame() {
        //Main panel in the Frame
        mainPanel = new JPanel(new BorderLayout());
        //Creating and adding Title Panel to the Main Panel
        generateTitlePanel();
        mainPanel.add(titlePanel,BorderLayout.NORTH); // Setting the location of the Title Panel since we have a
                                                //Layout manager (BorderLayout) in the main panel.

        //Creating and adding the Board Panel to the Main Panel
        generateBoardPanel();
        mainPanel.add(boardPanel, BorderLayout.CENTER);


        this.add(mainPanel); //Make sure to add Main panel to the frame, otherwise none of the components
        this.setSize(boardWidth,boardHeight);
        this.setTitle("Tic Tac Toe Game");
        this.setLocationRelativeTo(null); //Centers my frame to the middle of the computer screen
        this.setResizable(false); //This sets the frame to not be resized.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Will need to change later to close on dialog Pane.
    }

    public void generateTitlePanel(){
        titlePanel = new JPanel();
        titleLabel = new JLabel("Tic-Tac-Toe Game");
        titlePanel.setFont(new Font("Ariel", Font.BOLD, 150 ));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setOpaque(true);// This will change make sure the color changes happen.

        titlePanel.add(titleLabel);
    }


    public void generateBoardPanel(){
        boardPanel = new JPanel();
        boardPanel.setLayout( new GridLayout(3, 3));//Setting the layout for my buttons
        board = new JButton[3][3];// made a 2D array that has 3 rows and 3 columns
        //Here we are going to run through all of the items in the Array.
        for (int r = 0; r < 3; r++){ //Numbers in Programming start at Zero.
            for (int c = 0; c < 3; c++){
                board[r][c] = new TicTicToeButton(r, c); // This makes all the indexes in the array anonymous instances of the TicTacToeButton Class (which is a JButton)
                boardPanel.add(board[r][c]);//Adding all the buttons in the 2D Array. We can add the entire array since we have assigned the array to the anonymous TicTacToeButton class we created

                board[r][c].setFont(new Font("Ariel", Font.BOLD,120));
                board[r][c].setFocusable(false);

                //Here we will give the buttons an actionListener using a Lambda expression
                board[r][c].addActionListener((ActionEvent ae) -> {
                    JButton clickedButton = (JButton) ae.getSource(); // Here we are assigning the button to "clickedButton" and using getSource() method allows me to identify which specific button I clicked
                    if (clickedButton.getText().isEmpty()){ //If the button has an empty String, then we can select it to change the text of button
                        clickedButton.setText(currentPlayer);
                        moveCounter++;
                        if (moveCounter > 4){
                            isWin = checkWin(clickedButton.getText());// Checking to see if there is a winner

                        }
                        if (!isWin){ // If there isn't a winner yet, we are going to keep alternating players and check for ties too
                            if (isTie()) {// Checking for ties
                                JOptionPane.showMessageDialog(null, "The game is a tie!");
                                int result = JOptionPane.showConfirmDialog(null, "Do you want to play again?");
                                if (result == JOptionPane.YES_OPTION) {
                                    resetGame();
                                } else {
                                    System.exit(0);
                                }
                            }else {
                                if (currentPlayer.equals("X")) { // Alternates players.
                                    currentPlayer = "O";
                                } else {
                                    currentPlayer = "X";
                                }
                            }// End of if else that alternates players
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Invalid move! Please Try again.");
                        int result = JOptionPane.OK_OPTION;
                    }
                });
            }

        }
    }

    /**
     * This method checks for all possible wins of the current play
     * @param currentPlayer - Uses the current player's text (X or O) to find possible wins
     * @return - returns a boolean value to see if we do have a winner or not.
     */
    public boolean checkWin(String currentPlayer) {
        if (isColWin(currentPlayer) || isRowWin(currentPlayer) || isDiagonalWin(currentPlayer)) {
            int result = JOptionPane.showConfirmDialog(null, "Player " + currentPlayer + " won. Do you want to play again?");
            if (result == JOptionPane.YES_OPTION) {
                // Reset the game board
                resetGame();
            } else{
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    /**
     * This method is one way the current player can win.(Horizontal Win)
     * @param currentPlayer - Uses the current player's text (X or O) to find row win
     * @return - returns a boolean value if the current player won by row win
     */
    private boolean isRowWin(String currentPlayer){
        for (int r = 0; r < 3 ;r++){
            if (board[r][0].getText().equals(currentPlayer) && board[r][1].getText().equals(currentPlayer) && board[r][2].getText().equals(currentPlayer)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method is one way the current player can win. (Vertical Win)
     * @param currentPlayer -Uses the current player's text (X or O) to find column win
     * @return -returns a boolean value if the current player won by column win
     */
    private boolean isColWin(String currentPlayer){
        for (int c = 0; c < 3; c++){
            if (board[0][c].getText().equals(currentPlayer) && board[1][c].getText().equals(currentPlayer) && board[2][c].getText().equals(currentPlayer)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method is one way the current player can win. (Diagonal Win)
     * @param currentPlayer -Uses the current player's text (X or O) to find diagonal win
     * @return -returns a boolean value if the current player won by diagonal win
     */
    private boolean isDiagonalWin(String currentPlayer){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                if (board[0][0].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[2][2].getText().equals(currentPlayer)
                        || board[0][2].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[2][0].getText().equals(currentPlayer)){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * This method looks for ties, but checking if all of the buttons have text in them or if there is a win
     * @return - returns a boolean value if the game ended up in a tie.
     */
    private boolean isTie() {
        // First, check if there's a winner
        if (isWin) {
            return false;  // If there's a winner, it's not a tie
        }

        // Check if all spaces on the board are filled
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().isEmpty()) {
                    return false;  // If any space is empty, it's not a tie
                }
            }
        }
        // If we've reached this point, all spaces are filled and there's no winner
        return true;
    }

    /**
     * This method sets all of the buttons in the array to an empty String text
     * It also resets the current player to X.
     * Resets the isWin Value back to false since it's a new game
     */
    private void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
            }
        }
        currentPlayer = "X";
        isWin = false;
    }



}