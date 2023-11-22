import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ConnectFour extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	MenuBar menuBar;
	Menu menu;
	MenuItem startItem;
	MenuItem quitItem;
	JButton dropC1, dropC2, dropC3, dropC4, dropC5, dropC6, dropC7;
	int[][] array;
	boolean gameOver = false;
	boolean newGame;
	public static final int empty = 0;
	public static final int yellow = 1;
	public static final int red = 2;
	int current = red;
	//Win messages are created here to prevent JOptionPane from opening repeatedly.
	JOptionPane redWin = new JOptionPane("RED WINS!");
	JOptionPane yelWin = new JOptionPane("YELLOW WINS!");
	JDialog rWIN = redWin.createDialog("RED, WINNER IS YOU!");
	JDialog yWIN = yelWin.createDialog("YELLOW, WINNER IS YOU!");
	
	
	
	public ConnectFour(){
		super("Connect Four!");
		setSize(1024,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
			menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		menu = new Menu("Game");
		menuBar.add(menu);
	
		startItem = new MenuItem("New Game");
		menu.add(startItem);
		startItem.addActionListener(this);
		
		quitItem = new MenuItem("Quit");
		menu.add(quitItem);
		quitItem.addActionListener(this);
		
	
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new GridLayout(1,7));
		
		//Buttons associated with each column
		dropC1 = new JButton("Drop in column 1!");
		dropC1.addActionListener(this);
		buttonPanel.add(dropC1);
		//
		dropC2 = new JButton("Drop in column 2!");
		dropC2.addActionListener(this);
		buttonPanel.add(dropC2);
		//
		dropC3 = new JButton("Drop in column 3!");
		dropC3.addActionListener(this);
		buttonPanel.add(dropC3);
		//
		dropC4 = new JButton("Drop in column 4!");
		dropC4.addActionListener(this);
		buttonPanel.add(dropC4);
		//
		dropC5 = new JButton("Drop in column 5!");
		dropC5.addActionListener(this);
		buttonPanel.add(dropC5);
		//
		dropC6 = new JButton("Drop in column 6!");
		dropC6.addActionListener(this);
		buttonPanel.add(dropC6);
		//
		dropC7 = new JButton("Drop in column 7!");
		dropC7.addActionListener(this);
		buttonPanel.add(dropC7);
		
		//Adds button panel to the pane
		add(buttonPanel, BorderLayout.NORTH);
		
		//Loads the game by reseting the array to all blank cells
		loadGame();
		
	}
	private void loadGame() {
		array = new int[6][7];
		for(int r=0; r<6; r++){
			for(int c=0; c<7; c++){
				array[r][c] = 0;
			}
		}
		newGame = false;
		
	}
	public static void main(String arg[]){
		ConnectFour newGame = new ConnectFour();
		newGame.setVisible(true);
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dropC1){
			insert(1);
		}else if (e.getSource() == dropC2){
			insert(2);
		}else if (e.getSource() == dropC3){
			insert(3);
		}else if (e.getSource() == dropC4){
			insert(4);
		}else if (e.getSource() == dropC5){
			insert(5);
		}else if (e.getSource() == dropC6){
			insert(6);
		}else if (e.getSource() == dropC7){
			insert(7);
		}else if (e.getSource() == quitItem){
			System.exit(0);
		}else if (e.getSource() == startItem){
			gameOver = false;
			loadGame();
			repaint();
		}
	}
	
	public void paint(Graphics g){
		//Paints the blue playing board
		g.setColor(Color.BLUE);
		g.fillRect(110,50,100+100*7,100+100*6);
		//Paints circles on the board depending on if the cell is full or not
		for (int r=0; r<6; r++){
			for(int c=0; c<7; c++){
				if(array[r][c]==empty){
					g.setColor(Color.WHITE);
				}if (array[r][c]==yellow){
					g.setColor(Color.YELLOW);
				}if (array[r][c]==red){
					g.setColor(Color.RED);
				}
				g.fillOval(160+100*c, 100+100*r, 100, 100);
			}
		}
		//Since the window is repainted often, this will consistently check for the win condition
		checkWin();
	}
	
	public void insert(int n){
		//Won't allow the user to place disks if the game is over
		if(gameOver) return;
		newGame = true;
		
		int r;
		n--;
		for(r=0; r<6; r++){
			if (array[r][n]>0) break;
		}
		if(r>0){
			array[--r][n] = current;
			if(current == yellow){
				current = red;
			}else{
				current = yellow;
			}
			repaint();
		}
	}
	public void checkWin(){
		//Checks win for each case
		//Checks for horizontal win
		for(int r=0; r<6; r++){
			for(int c=0;c<4;c++){
				int temp = array[r][c];
				if(temp > 0 && temp == array[r][c+1] && temp == array[r][c+2] && temp == array[r][c+3]){
					showWinrar(array[r][c]);
				}
			}
		}
		//Checks for vertical win
		for(int c=0; c<7; c++){
			for(int r=0;r<3;r++){
				int temp = array[r][c];
				if(temp > 0 && temp == array[r+1][c] && temp == array[r+2][c] && temp == array[r+3][c]){
					showWinrar(array[r][c]);
				}
			}
		}
		//Checks for diagonal win
		for(int r=0; r<3; r++){
			for(int c=0;c<4;c++){
				int temp = array[r][c];
				if(temp > 0 && temp == array[r+1][c+1] && temp == array[r+2][c+2] && temp == array[r+3][c+3]){
					showWinrar(array[r][c]);
				}
			}
		}
		//Checks for backwards diagonal win
		for(int r=5; r>=3; r--){
			for(int c=0;c<4;c++){
				int temp = array[r][c];
				if(temp > 0 && temp == array[r-1][c+1] && temp == array[r-2][c+2] && temp == array[r-3][c+3]){
					showWinrar(array[r][c]);
				}
			}
		}
	}
	//Outputs winner and sets gameOver to true
	public void showWinrar(int n){
		if (n == yellow){
			yWIN.setVisible(true);	
		}else if(n == red){
			rWIN.setVisible(true);
		}
		gameOver = true;
	}
	
}
