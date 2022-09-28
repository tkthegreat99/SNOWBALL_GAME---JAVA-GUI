import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Board extends JPanel implements ActionListener{

	private Timer timer;
	private character nunu;
	private Background inGameBack;
	private Dimension d;
	private JLabel score=new JLabel();
	private Mushroom m;
	private static int level=1;
	private JButton help;
	private Image snowball;
	private Skill skill;
	private Skill skill2;
	int init=100;
	int t=0;
	int Hscore=0;
	private static boolean skill_status=false;
	private String id=null;
	static ArrayList snowball_list=new ArrayList();
	private snowball S;
	ArrayList mushroom_list=new ArrayList();
	Bgm B=new Bgm();
	public static int getLevel() {
		return level;
	}
	public Board() {//패널 생성자
		setLayout(null);
		
		B.playBgm(new File("sounds/26_Nunu and Willump, the Boy and his Yeti- Trailer.wav"), 1.0f, false);
		setBackground(new Color(19,60,106,59));
		initBoard();
		initVariables();
	}
	
	private void initVariables() {
		d=new Dimension(400,400);
		timer=new Timer(10,this);
		timer.start();
	}
	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		
		inGameBack=new Background();
		nunu=new character();
		skill=new Skill("skill_Consume.png", nunu.getX(), nunu.getY());
		skill2=new Skill("skill_Snowball.png", nunu.getX(), nunu.getY());
	}
	
	class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key =e.getExtendedKeyCode();
			if(key=='s'||key=='S') {
				
				Main.setInGame();
				B.stopBgm();
				B.playBgm(new File("sounds/runbgm.wav"), 1.0f, true);
				sound("summoner's");
				
			}
			
			else if(key=='E'||key=='e') {
				skill_status=false;
				if(nunu.getMP()>=20) {
				sound("eskill");
				skill2.Skill_snowball(nunu.getX()+45, nunu.getY());
				nunu.setMP(20);
				}
			}
			else if(key=='q'||key=='Q') {
				if(nunu.getMP()>=50) {
				skill_status=true;
				}
				else {
					skill_status=false;
				}

			}
			else {
				nunu.keyPressed(e);
			}
			
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			skill_status=false;
			int key =e.getExtendedKeyCode();
			if(key=='h'||key=='H') {

				JFrame help=new JFrame();
				help.setTitle("Help");
				help.setSize(750,800 );
				help.setResizable(false);
				help.setVisible(true);
				help.setLocationRelativeTo(null);
				
				
			
				ImageIcon ic=new ImageIcon("images/help.png");
				
				JPanel p= new JPanel();
				JLabel l=new JLabel("", ic, JLabel.CENTER);
				
				p.add(l);
				help.add(p);
				}
			else if(key=='r'||key=='R') {
				if(Main.getInGame()) {
				B.stopBgm();
				reStart();
				nunu.setRe();
				}
			}
			else if(key=='q'||key=='Q') {
				skill_status=false;
			}
				nunu.keyReleased(e);
			
		}
	}
	public void initmushroom() {
		if(t%init==0) {
		int R=(int)(Math.random()*3)+1;
		if(R==1) {
			m = new Mushroom("minion", 20);
			mushroom_list.add(m);
		}
		else if(R==2) {
			m = new Mushroom("icewall", 10);
			mushroom_list.add(m);
	
		}
		else if(R==3) {
			m = new Mushroom("mushroom", 20);
			mushroom_list.add(m);
	
		}
		}
		
	}
	public void actionPerformed(ActionEvent e) {
	
		if(Main.getInGame()) {
		t+=1;
		if(t%(init*7)==0) {
			level+=1;
			init-=3;
		}
		collisions();
		repaint();
		nunu.move();//누누 조작 함수
		nunu.setMP();//누누 마나 재생
		nunu.setscore();
		nunu.setsnowball();
		inGameBack.setY();
		initmushroom();
		move();
		moveSnowball();
		if(nunu.getHP()<=0) {
			
			
			sound("killed");
			timer.stop();
			B.stopBgm();
			//String nickname=JOptionPane.showInputDialog();
			id=JOptionPane.showInputDialog("ID 입력(8자 이내)");
			if(id!=null) {
				ConnectMysql sql=new ConnectMysql();
				sql.connect();	
				sql.update("INSERT INTO nunu_rank(id,Score) VALUES(\""+id+"\",'"+nunu.getScore()+"');");
				
			String[] buttons= {"다시하기","끝내기","랭크확인"};
			int result=JOptionPane.showOptionDialog(this, "NUNU가 당했습니다", "GAME OVER!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");
 
			if(result==JOptionPane.YES_OPTION) {//계속하면 
				reStart();				
				}
			else if(result==JOptionPane.NO_OPTION) {//끝내기
				System.exit(0);//종료
			}
			else {//취소하면 그대로
				JFrame tier=new JFrame();
				tier.setTitle("데굴데굴 눈덩이!!!");
				tier.setSize(500,1000);
				tier.setResizable(false);
				tier.setVisible(true);
				tier.setLocationRelativeTo(null);
				String t="";
				
				JPanel p= new JPanel();
				p.setLayout(null);
				p.setBackground(Color.white);
				
				JLabel st=new JLabel("당신의 점수 : "+nunu.getScore());
				st.setBounds(150,130, 300, 100);
				p.add(st);
				int b=7000,s=10000,g=13000,pl=15000;
				if(nunu.getScore()<7000) {
					t="Bronze";
					JLabel st2=new JLabel("다음 등급까지  "+(b-nunu.getScore())+"점 남았습니다.");
					st2.setBounds(150,150,300,100);
					p.add(st2);
				}
				else if(nunu.getScore()<10000) {
					t="Silver";
					JLabel st2=new JLabel("다음 등급까지  "+(s-nunu.getScore())+"점 남았습니다.");
					st2.setBounds(150,150,300,100);
					p.add(st2);
				}
				else if(nunu.getScore()<13000) {
					t="Gold";
					JLabel st2=new JLabel("다음 등급까지  "+(g-nunu.getScore())+"점 남았습니다.");
					st2.setBounds(150,150,300,100);
					p.add(st2);
				}
				else if(nunu.getScore()<15000) {
					t="Platinum";
					JLabel st2=new JLabel("다음 등급까지  "+(pl-nunu.getScore())+"점 남았습니다.");
					st2.setBounds(150,150,300,100);
					p.add(st2);
				}
				else if(nunu.getScore()>15000) {
					t="Diamond";
					JLabel st2=new JLabel("축하합니다. 최고 등급입니다.");
					st2.setBounds(150,150,300,100);
					p.add(st2);
				}
				
				ImageIcon ic=new ImageIcon("images/"+t+".png");
				
				
				
				
				
				JLabel l=new JLabel("", ic, JLabel.CENTER);
				l.setBounds(200, 50, 100, 100);
				
				JLabel l1 = new JLabel("브론즈(7000점 미만)");
				JLabel l2 = new JLabel("실버(7000점 이상 10000점 미만)");
				JLabel l3 = new JLabel("골드(10000점 이상 13000점 미만)");
				JLabel l4 = new JLabel("플레티넘(13000점 이상 15000점 미만)");
				JLabel l5 = new JLabel("다이아(15000점 이상)");
				
				ImageIcon Bronze = new ImageIcon("images/Bronze.png");
				ImageIcon Silver = new ImageIcon("images/Silver.png");
				ImageIcon Gold = new ImageIcon("images/Gold.png");
				ImageIcon Platinum = new ImageIcon("images/Platinum.png");
				ImageIcon Diamond= new ImageIcon("images/Diamond.png");
				
				JLabel B=new JLabel(Bronze);
				JLabel S=new JLabel(Silver);
				JLabel G=new JLabel(Gold);
				JLabel P=new JLabel(Platinum);
				JLabel D=new JLabel(Diamond);
				
				
				B.setBounds(100,200,100,100); 
				S.setBounds(100,250,100,100);
				G.setBounds(100,300,100,100);
				P.setBounds(100,350,100,100);
				D.setBounds(100,400,100,100);
				
				l1.setBounds(190, 250, 300, 20);
				l2.setBounds(190, 300, 300, 20);
				l3.setBounds(190, 350, 300, 20);
				l4.setBounds(190, 400, 300, 20);
				l5.setBounds(190, 450, 300, 20);
				
				ArrayList rank_list=new ArrayList();
				Label Test;
				try {
					sql.rs=sql.stmt.executeQuery("SELECT @rownum:=@rownum+1  rnum, nunu_rank.*FROM (SELECT *FROM nunu_rank ORDER BY Score DESC) nunu_rank, (SELECT @ROWNUM := 0) R");
					while(sql.rs.next()){
						if(id.equals(sql.rs.getString("id")))
						rank_list.add(new Label("나의 순위"+sql.rs.getInt("rnum")+"    "+sql.rs.getString("id")+"    "+sql.rs.getString("Score")));
					}
				}
				catch(Exception e2) {
					System.out.println("getString error:"+e2);
				}
				try {
					sql.rs=sql.stmt.executeQuery("SELECT @rownum:=@rownum+1  rnum, nunu_rank.*FROM (SELECT *FROM nunu_rank ORDER BY Score DESC) nunu_rank, (SELECT @ROWNUM := 0) R LIMIT 0,10");
					while(sql.rs.next()){
						rank_list.add(new Label(sql.rs.getInt("rnum")+"    "+sql.rs.getString("id")+"    "+sql.rs.getString("Score")));
					}
				}
				catch(Exception e2) {
					System.out.println("getString error:"+e2);
				}
				
				for(int i=0;i<rank_list.size();i++) {
					Test=(Label)(rank_list.get(i));
					Test.setFont(new Font("verdana",Font.BOLD,30));
					Test.setBounds(100,500+i*40,400,50);
					p.add(Test);
				}
				
				
				
				p.add(B);
				p.add(S);
				p.add(G);
				p.add(P);
				p.add(D);
				
				p.add(l1);
				p.add(l2);
				p.add(l3);
				p.add(l4);
				p.add(l5);
				
				JLabel r=new JLabel("다시하려면 R");
				r.setBounds(200, 500, 100, 30);
				p.add(r);
				p.add(l);
				tier.add(p);
			}
			}
			}
		}
	
		
	}
	public void move() {
		for(int i=0;i<mushroom_list.size();i++) {
			m = (Mushroom)(mushroom_list.get(i));
			m.move();
			if(m.getY()>1280) {
				mushroom_list.remove(i);
			}
		}
	}
	public void moveSnowball() {
		for(int j=0;j<snowball_list.size();j++) {
			S=(snowball)(snowball_list.get(j));
			S.move();
			if(S.getY()<-200) {
				snowball_list.remove(j);
			}
			}
	}
	public void collisions() {
		for(int i=0;i<mushroom_list.size();i++) {
			m = (Mushroom)(mushroom_list.get(i));
			Rectangle r1=m.getBounds();
			for(int j=0;j<snowball_list.size();j++) {
				S=(snowball)(snowball_list.get(j));
				Rectangle r2=S.getBounds();
			if(r1.intersects(r2)) {
				
				mushroom_list.remove(i);
				snowball_list.remove(j);
				nunu.setScore();
				B.playSound(new File("sounds/snowball_collision.wav"), 1.0f, false);
			}
			}
		}

		for(int i=0;i<mushroom_list.size();i++) {
			m = (Mushroom)(mushroom_list.get(i));
			Rectangle r1=m.getBounds();
			
			if(r1.intersects(nunu.getBounds())) {
				if(skill_status==false) {
				nunu.getDamage(m.getDamage());
				sound(m.getEnemy());
				B.playSound(new File("sounds/"+m.getEnemy()+".wav"), 2.0f, false);
				
				mushroom_list.remove(i);
				nunu.setScore();
				}
				else {
					System.out.println("버억");
					mushroom_list.remove(i);
					nunu.recover_consume();
					nunu.setMP(50);
					B.playSound(new File("sounds/qskill.wav"), 1.0f, false);
					nunu.setscore();
					skill_status=false;
				}
			}
		}
	}
	public void sound(String enemy) {
		try {
			AudioInputStream ais=AudioSystem.getAudioInputStream(new File("sounds/"+enemy+".wav"));
			
			Clip clip=AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
		}
		catch(Exception ex) {
			
		}
	}
	public void reStart() {
		id=null;
		timer.start();
		t=0;
		level=1;
		init=100;
		for(int i=0;i<mushroom_list.size();i++) {
			m = (Mushroom)(mushroom_list.get(i));
				mushroom_list.remove(i);
			}
		nunu.setRe();
		B.playBgm(new File("sounds/runbgm.wav"), 1.0f, true);
		
		
	}
	private void showIntroScreen(Graphics2D g2d) {//인트로 화면
		Image back=new ImageIcon("images/introImage-1.jpg").getImage();
		g2d.drawImage(back, 0, 120, 1280, 720, null);
		
		//g2d.setColor(new Color(0,32,48));
		//g2d.fillRect(480,400,400,100);
		g2d.setColor(Color.white); 
		g2d.drawRect(595,420,100,100);
		
		String s="Press                 to start";
		Font small=new Font("Helvetica",Font.BOLD,40);
		FontMetrics metr=this.getFontMetrics(small);
		
		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s,445,485);
		g2d.setFont(new Font("Serif", Font.BOLD,50));
		g2d.drawString("S", 631, 485);
		String S="Press \'h\' to HELP";
		g2d.setFont(small);
		g2d.setColor(new Color(107,252,255,255));
		g2d.drawString(S, 495, 900);
		
		g2d.setFont(new Font("Serif", Font.BOLD,80));
		g2d.drawString("BIGGEST SNOWBALL EVER!", 70, 85);
			
	}
	private void doDrawNunu(Graphics g) {//누누 캐릭터 그리는 함수
		Graphics2D g2d=(Graphics2D) g;
		Font small=new Font("Verdana", Font.BOLD, 30);
		g2d.setColor(Color.white);
		g2d.setFont(small);
		String s="SCORE : "+nunu.getScore();
		String S="LEVEL : "+level;
		String H;
		if(nunu.getScore()<Hscore) {
			H="BEST : "+Hscore;
		}
		else {
			Hscore=nunu.getScore();
			H="BEST : "+nunu.getScore();
		}
		
		g2d.drawString(H, 30, 40);
		g2d.drawString(s, 560, 40);
		g2d.drawString(S, 30, 70);
		g2d.setColor(Color.white);
		g2d.fillOval(nunu.getX()-((int)nunu.getSnowball()/2-100), nunu.getY()-(int)nunu.getSnowball()/2-((int)nunu.getSnowball()/2-100)+20, (int)nunu.getSnowball(), (int)nunu.getSnowball());
		
		g2d.drawImage(nunu.getImage(),nunu.getX(), nunu.getY(), 200,200, this);
		
		
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Verdana", Font.PLAIN, 20));
		g2d.drawString("Nunu", nunu.getX()+95,nunu.getY()-25);
		
		g2d.setColor(Color.orange);
		g2d.drawString("Lv "+level, nunu.getX()+35,nunu.getY()-25);
		
		g2d.setColor(Color.green);
		g2d.fillRect(nunu.getX()+45, nunu.getY()-20, nunu.getHP(),10);
		g2d.setColor(Color.black);
		g2d.drawRect(nunu.getX()+45, nunu.getY()-20, 100,10);
	
		g2d.setColor(Color.blue);
		g2d.fillRect(nunu.getX()+45, nunu.getY()-10, (int)nunu.getMP(),10);
		g2d.setColor(Color.black);
		g2d.drawRect(nunu.getX()+45, nunu.getY()-10, 100,10);
		
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setFont(new Font("Verdana", Font.BOLD, 20));
		if(nunu.getMP()>=50) {
			g2d.drawImage(skill.getImage(), 40, 140, 100,100, this);
			g2d.drawString("Q : 50", 50,130);
		}
		if(nunu.getMP()>=20) {
			g2d.drawImage(skill2.getImage(), 160, 140, 100,100, this);
			g2d.drawString("E : 20", 170,130);
		}
		
		g2d.setColor(new Color(67,228,27));
		g2d.fillRect(300, 850, nunu.getHP()*7,20);
		g2d.setColor(Color.black);
		g2d.drawRect(300, 850, 700,20);
		
		g2d.setColor(new Color(10,34,250));
		g2d.fillRect(300, 885, (int)nunu.getMP()*7,20);
		g2d.setColor(Color.black);
		g2d.drawRect(300, 885, 700,20);
		
		g2d.setColor(Color.white);
		g2d.drawString("HP "+nunu.getHP(), 300,850);
		g2d.drawString("MP "+(int)nunu.getMP(), 300,885);
		
	}
	public void doDrawBackground(Graphics g2d) {
		g2d.drawImage(inGameBack.getImage(), 0, inGameBack.getY1(), 1280,inGameBack.getHeigh(), this);
		g2d.drawImage(inGameBack.getImage(), 0, inGameBack.getY2(), 1280, inGameBack.getHeigh(), this);
	}
	
	public void doDrawEnemy(Graphics g2d) {
		for(int i=0;i<mushroom_list.size();i++) {
			m = (Mushroom)(mushroom_list.get(i));
			g2d.drawImage(m.getImage(), m.getX(), m.getY(), m.getWidth(), m.getHeigh(), this);
		
		}
		
	}
	public void doDrawSnowball(Graphics g2d) {
		for(int j=0;j<snowball_list.size();j++) {
			S=(snowball)(snowball_list.get(j));
			g2d.drawImage(S.getImage(), S.getX(), S.getY(), 100, 100, this);
			
			}
	}
	
	@Override
	public void paintComponent(Graphics g) {//자신 그림
		super.paintComponent(g);
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
	}
	private void doDrawing(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		if(!Main.getInGame()) {//!인게임
			showIntroScreen(g2d);//인트로화면 
			
		}
		else {//인게임
			
			doDrawBackground(g2d);
			doDrawEnemy(g2d);
			doDrawNunu(g2d);//누누 출력
			doDrawSnowball(g2d);
		}
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}
	private class MoveAdapter extends KeyAdapter{
		public void keyReleased(KeyEvent e) {
			nunu.keyReleased(e);
		}
		public void keyPressed(KeyEvent e) {
			nunu.keyPressed(e);
		}
	}
	
	
	

	
}