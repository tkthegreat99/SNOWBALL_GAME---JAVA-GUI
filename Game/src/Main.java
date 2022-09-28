import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
public class Main extends JFrame{
	private static boolean inGame=false;
	public Main() {
		
		initUI();
	}
	public static boolean getInGame() {
		return inGame;
	}
	public static void setInGame() {
		inGame=true;
	}
	private void initUI() {
		setLayout(null);
		
		Board board=new Board();
		board.setBounds(0,0,1280,1000);
		add(board);
		
		
		setTitle("누누 공굴리기");
		setSize(1280,1000);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
		
	public static void main(String[] args) throws Exception {
		
		Main A=new Main();
		
		A.setVisible(true);
	}

}