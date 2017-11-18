package tictactoewindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToeWindow extends JFrame {
  JPanel mainPanel;
  // Boolean to determine if user is X.
  boolean userIsX = true;
  
  /// Booleans to hold difficulty setting.
  boolean easy = false;
  boolean normal = true;
  boolean hard = false;
  // Variables to keep track of wins, losses, and draws.
  int moveCounter = 0;
  
  JButton [] buttons = new JButton[9];
  
  // Declare array gameBoard to store game data and set all values to 0.
  // Value is 0 if the space is empty, 1 if there is an X in the space, 2 if there is an O.
  int [] gameBoard = {0,0,0,0,0,0,0,0,0};
  // Declare the objects to construct the menu bar.
  private JMenuBar menuBar;
  private JMenu fileMenu;
  
  private JMenu difficultyMenu;
  private JMenuItem newMenu;
  
  private JRadioButtonMenuItem difficultyEasy;
  private JRadioButtonMenuItem difficultyNormal;
  private JRadioButtonMenuItem difficultyHard;
  
  /*
   * Constructor for the window.
   */
  
  
  public TicTacToeWindow() {
    setTitle("Tic Tac Toe");
    setSize(640,480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    buildPanel();
    add(mainPanel);
    buildMenuBar();
    
    setVisible(true);
  }
  /*
   * The buildMenuBar method builds the menu bar.
   */
  private void buildMenuBar()
    
    
  {
    //Create the menu bar.
    menuBar = new JMenuBar();
    
    //Create the file and help menus.
    buildFileMenu();
    buildDifficultyMenu();
    // buildHelpMenu();
    
    //Add the file and help menus to the menu bar.
    //menuBar.add(fileMenu);
    menuBar.add(difficultyMenu);
    // menuBar.add(helpMenu);
    
    //Set the window's menu bar.
    setJMenuBar(menuBar);
  }
  private void buildFileMenu()
    
    
  {
    
    //Create a New menu item.
    //newMenu = new JMenuItem("New");
    //newMenu.setMnemonic(KeyEvent.VK_N);
    //newMenu.addActionListener(new NewButtonListener());
    
    //Create a JMenu object for the File menu.
    //fileMenu = new JMenu("new");
    //fileMenu.setMnemonic(KeyEvent.VK_F);
   // fileMenu.setMnemonic(KeyEvent.VK_N);
    //fileMenu.addActionListener(new NewButtonListener());
    //Add the items to the menu.
    //fileMenu.add(newMenu);
    // fileMenu.add(exitMenu);
  }

  private void buildDifficultyMenu(){
      
    //Create an Exit menu item.
    difficultyEasy = new JRadioButtonMenuItem("Easy");
    difficultyEasy.addActionListener(new DifficultyListener());
    difficultyNormal = new JRadioButtonMenuItem("Normal", true);
    difficultyNormal.addActionListener(new DifficultyListener());
    difficultyHard = new JRadioButtonMenuItem("Hard");
    difficultyHard.addActionListener(new DifficultyListener());
    
    //Create a JMenu object for the Help menu.
    difficultyMenu = new JMenu("Difficulty");
    
    //Add the difficulty buttons to a group to make them mutually exclusive.
    ButtonGroup difficultyGroup = new ButtonGroup( );
    difficultyGroup.add(difficultyEasy);
    difficultyGroup.add(difficultyNormal);
    difficultyGroup.add(difficultyHard);
    
    //Add the items to the menu.
    difficultyMenu.add(difficultyEasy);
    difficultyMenu.add(difficultyNormal);
    difficultyMenu.add(difficultyHard);
  }
  
  
  public static void main(String [] args) {
    TicTacToeWindow frame = new TicTacToeWindow();
  }
  
  /**
   * buildPanel creates the main panel which includes two subpanels. 
   *
   */
  
  
  private void buildPanel() {
    mainPanel = new JPanel();
    //mainPanel.setBackground(Color.white);
    mainPanel.setLayout(new BorderLayout());
    
    JPanel gamePanel = new JPanel();
    //gamePanel.setBackground(Color.white);
    gamePanel.setBorder(BorderFactory.createTitledBorder("Game Board"));
    gamePanel.setLayout(new GridLayout(3,3));
    
    
    for (int count = 0; count < 9; count++) {
      buttons[count] = new JButton();
      buttons[count].setBorder(BorderFactory.createLineBorder(Color.white));
      buttons[count].addActionListener(new ButtonListener());
      buttons[count].setBackground(Color.gray);
      buttons[count].setActionCommand("" + count);
      gamePanel.add(buttons[count]);
      buttons[count].setFont(buttons[count].getFont().deriveFont(90.0f ));
      
    }
    
    JPanel controls = new JPanel();
   // controls.setLayout(new BorderLayout());
   // controls.setBorder(BorderFactory.createTitledBorder("Control"));
    
    JButton newGame = new JButton("New Game");
    controls.add(newGame, BorderLayout.NORTH);
    newGame.addActionListener(new NewButtonListener());
    
    JPanel informationPanel = new JPanel();
    informationPanel.setLayout(new GridLayout(4,1,5,5));
    
    JLabel spacer = new JLabel(" ");
        
    clearGameBoard();
    
    ButtonGroup group = new ButtonGroup();

    
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new GridLayout(3,1));
    informationPanel.add(radioPanel);
                        
    mainPanel.add(gamePanel, BorderLayout.CENTER);
    mainPanel.add(controls, BorderLayout.EAST);
    
  }
  
  /*
   * Activates all of the AI components. 
   */ 
  
  
  private void ActivateAI() {
    
    // Check for a draw game.
    if (!checkForDraw()) { 
      // Tell the user if there is a winner.
      checkForWinner();
      
      // If there is not a winner after the last turn, proceed with AI.
            
      if (Winner() == 0){
        
        // If the AI is able to win, it does so. If not, it attempts to block the user
        // from winning.
                
        if (!goForWin()){
                    
          if (!BlockUserFromWinning()){
            
            // Choose a random move if all else fails.
            ChooseRandomMove();
          }
        }
        
        // If there is a winner after the AI moves, inform the user.
        checkForWinner();
        
        // Check for a draw game.
        checkForDraw();
      }
    }
  }
  
  /*
   * Check to see if there is a winner after each turn.
   * An X win returns a 1, a O win returns a 2, and no win returns a 0. 
   */
  
  
  private int Winner(){
    
    // Check for a win horizontally.
    
    
    for (int k = 1; k < 3; k++){
      
      
      for (int i = 0; i < 7; i+=3){
        if(gameBoard[i] == k && gameBoard[i + 1] == k && gameBoard[i + 2] == k)
          return k;
      }
    }
    
    // Check for a win vertically.
    
    
    for (int k = 1; k < 3; k++){
      
      
      for (int i = 0; i < 3; i++){
        if(gameBoard[i] == k && gameBoard[i + 3] == k && gameBoard[i + 6] == k)
          return k;
      }
    }
    // Check for a win diagonally, top left to bottom right.
    
    
    for (int i = 1; i < 3; i++){
      
      
      if(gameBoard[0] == i && gameBoard[4] == i && gameBoard[8] == i){
        return i;
      }
    }
    
    // Check for win diagonally, top right to bottom left.
    
    
    for (int i = 1; i < 3; i++){
      
      
      if(gameBoard[2] == i && gameBoard[4] == i && gameBoard[6] == i){
        return i;
      }
    }
    return 0;
  }
  /*
   * Check to see if there is a winner after each turn.
   * An X win returns a 1, a O win returns a 2, and no win returns a 0. 
   */
  
  
  private boolean checkForDraw(){
    
    
    if (isBoardFull() && Winner() == 0) {
      JOptionPane.showMessageDialog(null,"GAME DRAW");
//      if (twoPlayerMode)
//        twoPlayerDraws++;
//      else
//        draws++;
      //updateRecord();
      return true;
    }else {
      return false;
    }
    
  }
  

                                                                            
  /*
   * Check to see if either the user or the CPU has won the game. 
   */
    
  private void checkForWinner(){
    
    
    if (Winner() == 1){
      JOptionPane.showMessageDialog(null,"X WINS");
//      if (userIsX)
//        wins++;
//      if (!userIsX && !twoPlayerMode)
//        losses++;
//      if (twoPlayerMode)
//        xWins++;
      //updateRecord();
    }
    
    
    if (Winner() == 2){
      JOptionPane.showMessageDialog(null,"O WINS");
//      if (userIsX || twoPlayerMode)
//        losses++;
//      if (!userIsX && !twoPlayerMode)
//        wins++;
//      if (twoPlayerMode)
//        oWins++;
      //updateRecord();
    }
  }
  /*
   * Attempt to win the game if possible.
   */
  
  
  private boolean goForWin(){
    int CPUValue;
    int bestMove = -1;
    
    // Find out if the CPU is X or O. 2 for O, 1 for X.
    if (userIsX)
      CPUValue = 2;
    else
      CPUValue = 1;
    
    // Try to win horizontally.
    bestMove = checkHorizontal(CPUValue);
    
    // Try to win vertically.
    if (bestMove == -1)
      bestMove = checkVertical(CPUValue);
    
    // Try to win diagonally.
    if (bestMove == -1)
      bestMove = checkDiagonal(CPUValue);
    
    
    
    if (bestMove > -1 && bestMove < 9){
      CPUMove(bestMove);
      return true;
    }else {
      return false;
    }
  }
  
  
  /*
   * Block the users win attempt if possible.
   */
  private boolean BlockUserFromWinning(){
    int userValue;
    int bestMove = -1;
    
    if (userIsX)
      userValue = 1;
    else
      userValue = 2;
    
    bestMove = checkHorizontal(userValue);
    
    // Block commonly used tic tac toe moves if the difficulty is hard.
      
    if (hard && foresight(userValue) > 0){
      if (bestMove == -1)
        bestMove = foresight(userValue);
    }
    
    // Block the user's vertical move.
    if (bestMove == -1)
      bestMove = checkVertical(userValue);
    
    // Block the user's diagonal move.
    if (bestMove == -1)
      bestMove = checkDiagonal(userValue);
    
    // If difficulty is normal or hard, prioritize the remaining spaces.
        
    if (normal || hard){
      if (bestMove == -1 && gameBoard[4]==0 && userIsX)
        bestMove = 4;
      
      if (bestMove == -1)
        bestMove = ChooseRandomCorner();
    }
    
    
    // If bestMove falls between the valid numbers 0 and 8 instruct the CPU to physically
    // make the move and return true.
    
    
    if (bestMove > -1 && bestMove < 9){
      CPUMove(bestMove);
      return true;
    }else {
      return false;
    }
  }
  
  /*
   * Choose a random open place to move if all else fails.
   */
  private void ChooseRandomMove(){
    Random rndGenerator = new Random();
    int rndNum = rndGenerator.nextInt(8);
    
    if (!isBoardFull()){   
      CPUMove (rndNum);
    }
  }
  
  /*
   * Check to see if all the spaces on the board are full.
   */  
  private boolean isBoardFull(){
    
    
    for (int i = 0; i < 9; i++){
            
      if (gameBoard[i] == 0){
        return false;
      }
    }
    return true;
  }
  
  /*
   * Analyze the horizontal values to help determine the best available move.
   * Checks for critical moves: winning moves or moves essential for preventing defeat.
   */
  
  
  private int checkHorizontal(int value){
    // Incrementing by 3 each time ensures the values are on the same row on the game board.
        
    for (int k = 0; k < 7; k+=3){
      
      // Check each member of the current row and return a value if a critical move is detected.
            
      if(gameBoard[k] == value && gameBoard[k + 1] == value && gameBoard[k + 2] == 0){
        return (k + 2);
      }
            
      if(gameBoard[k] == value && gameBoard[k + 2] == value && gameBoard[k + 1] == 0){
        return (k + 1);
      }
           
      if(gameBoard[k + 1] == value && gameBoard[k + 2] == value && gameBoard[k] == 0){
        return (k);
      }
    }
    
    // Return -1 if no critical move is found.
    return -1;
  }
  
  /*
   * Analyze the vertical values to help determine the best available move.
   * Checks for critical moves: winning moves or moves essential for preventing defeat.
   */
  
  private int checkVertical(int value){
    
    
    for (int i = 0; i < 3; i++){
      
      // Check each member of the current column to detect if there is a critical move.
            
      if(gameBoard[i] == value && gameBoard[i + 3] == value && gameBoard[i + 6] == 0){
        return i + 6;
      }
            
      if(gameBoard[i] == value && gameBoard[i + 6] == value && gameBoard[i + 3] == 0){
        return i + 3;
      }
            
      if(gameBoard[i + 3] == value && gameBoard[i + 6] == value && gameBoard[i] == 0){
        return i;
      }
    }
    
    // Return -1 if no critical move is found.
    return -1;
  }
  
  /*
   * Analyze the diagonal values to help determine the best available move.
   * Checks for critical moves: winning moves or moves essential for preventing defeat.
   */
  
  
  private int checkDiagonal(int value){
    
    // Determine if there are any critical moves diagonally.
       
    if(gameBoard[0] == value && gameBoard[4] == value && gameBoard[8] == 0){
      return 8;
    }
        
    if(gameBoard[0] == value && gameBoard[8] == value && gameBoard[4] == 0){
      return 4;
    }
        
    if(gameBoard[4] == value && gameBoard[8] == value && gameBoard[0] == 0){
      return 0;
    } 
        
    if(gameBoard[2] == value && gameBoard[4] == value && gameBoard[6] == 0){
      return 6;
    }
        
    if(gameBoard[2] == value && gameBoard[6] == value && gameBoard[4] == 0){
      return 4;
    }
       
    if(gameBoard[6] == value && gameBoard[4] == value && gameBoard[2] == 0){
      return 2;
    }
    
    //return -1 if no critical move is found.
    return -1;
  }
  
  /*
   * Recognize commonly used tic tac toe strategies; for use with hard difficulty. 
   */
  
  
  private int foresight(int value){
        
    if(gameBoard[0] == value && gameBoard[8] == value && gameBoard[3] == 0){
      return 3;
    }
        
    if(gameBoard[2] == value && gameBoard[6] == value && gameBoard[5] == 0){
      return 5;
    }
    return -1;
  }
  
  /*
   * Choose a random corner; for use with normal and hard difficulties.
   */  
  private int ChooseRandomCorner(){
    Random rndGenerator = new Random();
    int rndNum = rndGenerator.nextInt(3);
        
    switch (rndNum){
      case 0:
        if (gameBoard[0]==0)
        return 0;
        if (gameBoard[2]==0)
          return 2;
        if (gameBoard[6]==0)
          return 6;
        if (gameBoard[8]==0)
          return 8;
        break;
        
      case 1:
        if (gameBoard[4]==0 && !userIsX)
        return 4;
        if (gameBoard[2]==0)
          return 2;
        if (gameBoard[6]==0)
          return 6;
        if (gameBoard[8]==0)
          return 8;
        if (gameBoard[0]==0)
          return 0;
        break;
        
      case 2:
        if (gameBoard[6]==0)
        return 6;
        if (gameBoard[8]==0)
          return 8;
        if (gameBoard[0]==0)
          return 0;
        if (gameBoard[2]==0)
          return 2;
        break;
        
      case 3:
        if (gameBoard[8]==0)
        return 8;
        if (gameBoard[0]==0)
          return 0;
        if (gameBoard[2]==0)
          return 2;
        if (gameBoard[6]==0)
          return 6;
        break;
    }
    
    return -1;
  }
  
  /*
   * The computer opponent physically makes its move.
   */
  
    private void CPUMove(int ButtonNumber){
    
    
    if (userIsX){
      buttons[ButtonNumber].setText("O");
      gameBoard[ButtonNumber] = 2;
    }
    
    
    else {
      buttons[ButtonNumber].setText("X");
      gameBoard[ButtonNumber] = 1;
    }
  }
    
  /*
   * Handles the event that the user presses in one of the Tic Tac Toe squares. 
   */
    private class ButtonListener implements ActionListener {
    
    
    public void actionPerformed(ActionEvent event) {
      String numberPressed = event.getActionCommand();
      JButton theButton = (JButton) event.getSource();
      
      
      if (theButton.getText() == "") {
                
        if (userIsX) {
          theButton.setText ("X");
          gameBoard[Integer.parseInt(numberPressed)] = 1;
          ActivateAI();
        }
        
        
//        if (!userIsX && !twoPlayerMode) {
//          theButton.setText ("O");
//          gameBoard[Integer.parseInt(numberPressed)] = 2;
//          ActivateAI();
//        }
        
        
//        if (twoPlayerMode) {
//          if (moveCounter == 0 || moveCounter == 2 || moveCounter == 4
//                
//                
//                || moveCounter == 6) {
//            gameBoard[Integer.parseInt(numberPressed)] = 1;
//            theButton.setText ("X");
//          }
//          
//          
//          else {
//            gameBoard[Integer.parseInt(numberPressed)] = 2;
//            theButton.setText ("O");
//          }
//          checkForWinner();
//          checkForDraw();
//          moveCounter++;
//        }
      }
    }
  }
  
  /*
   * Handles the event that the user presses the Exit button.
   */
  
  
  private class ExitButtonListener implements ActionListener {
    
    
    
    public void actionPerformed(ActionEvent event){
      System.exit(0);
    }
  }
  /*
   * Handles the event that the user presses the New Game button.
   */
  
  
  private class NewButtonListener implements ActionListener {
    
    
    
    public void actionPerformed(ActionEvent event){
      moveCounter = 0;
      
      
      for (int count = 0; count < 9; count++) {
        buttons[count].setText("");
        gameBoard[count] = 0;
      }
      
      
//      if (userIsX == false && twoPlayerMode != true){
//        ActivateAI();
//      }
    }
  }
  /*
   * Clears the game board.
   */
  
  
  private void clearGameBoard() {
    moveCounter = 0;
    
    
    for (int count = 0; count < 9; count++) {
      buttons[count].setText("");
      gameBoard[count] = 0;
    }
    
  } 
  /*
   * Handles the event that the user presses the About menu button.
   */
  
  
  private class AboutButtonListener implements ActionListener {
    
    
    
    public void actionPerformed(ActionEvent event){
      JOptionPane.showMessageDialog(null, "Tic Tac Toe","About", getDefaultCloseOperation());
    }
  } 
  /*
   * Handles the event that the user plays X or user plays O as indicated by
   * radio buttons. 
   */
  
  
  private class RadioListener implements ActionListener {
    
    
    public void actionPerformed(ActionEvent event) {
      JRadioButton theButton = (JRadioButton) event.getSource();
      
      
      if (theButton.getText().equals("User Plays X")) {
        userIsX = true;
        //twoPlayerMode = false;
      }
      
      
      if (theButton.getText().equals("User Plays O")){
        userIsX = false;
        //twoPlayerMode = false;
      }
      
      
      if (theButton.getText().equals("Two Player Mode")) {
        userIsX = false;
        //twoPlayerMode = true;
      }
      //updateRecord();
      clearGameBoard();
    }
  }
  
  /*
   * Handles the event that the user changes the difficulty.
   */
  
  
  private class DifficultyListener implements ActionListener {
    
    
    public void actionPerformed(ActionEvent event) {
      JRadioButtonMenuItem theButton = (JRadioButtonMenuItem) event.getSource();
      
      
      if (theButton.getText().equals("Easy")) {
        easy = true;
        normal = false;
        hard = false;
      }
      
      
      if (theButton.getText().equals("Normal")) {
        easy = false;
        normal = true;
        hard = false;;
      }
      
      
      if (theButton.getText().equals("Hard")) {
        easy = false;
        normal = false;
        hard = true;
      }
    }
  }
}