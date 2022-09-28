import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
public class character extends JPanel{
	private int mx;
	private int my;
	private int x=550;
	private int y=700;
	private int w;
	private int h;
	private int HP=100;
	private double MP=0;
	private double snowball=200;
	private int score=0;
	private Image image;
	private Rectangle nunu;
	public character() {
		ImageIcon ic = new ImageIcon("images/NUNU.png");
		image=ic.getImage();
		
		w=image.getWidth(null);
		h=image.getHeight(null);
	
		
	}
	public void recover_consume() {
		HP+=30;
		if(HP>100) {
			HP=100;
		}
	}
	
	public void setScore() {
		score+=100;
	}
	public void setRe() {
		MP=0;
		HP=100;
		snowball=200;
		score=0;
		x=500;
		y=700;
		mx=0;
		my=0;
	}
	public void setMP() {
		if(MP<100&&Main.getInGame()) {
		MP+=0.1;
		}
	}
	public void setMP(int skill) {
		MP-=skill;
		if(MP<0) {
			MP=0;
		}
	}
	public void setscore(){
		if(Main.getInGame()) {
			score++;
			}
	}
	public void setsnowball(){
		if(Main.getInGame()) {
			snowball+=0.03;
			}
	}
	public int getHP() {
		return HP;
	}
	public double getMP() {
		return MP;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return w;
	}
	public int getHeigh() {
		return h;
	}
	public Image getImage() {
		return image;
	}
	public int getScore() {
		return score;
	}
	public double getSnowball() {
		return snowball;
	}
	
	public void move() {
		if(getBounds().x>0&&getBounds().x+getBounds().width<1280) {
			x+=mx;
		}
		if(getBounds().y>0&&getBounds().y+getBounds().height<1000) {
			y+=my;
		}
		if(getBounds().x<0) {
			getDamage(10);
			x=50;
		}
		else if(getBounds().x+getBounds().width>1280) {
			getDamage(10);
			x=1211-(int)snowball;
		}
		
		if(getBounds().y+getBounds().height>1000) {
			getDamage(10);
			y=700;
		}
		else if(getBounds().y<0) {
			getDamage(10);
			y=50;
		}
	}
	public void getDamage(int damage) {
		HP-=damage;
	}
	public Rectangle getBounds() {
		return new Rectangle(x-((int)(snowball/2-100)), y-(int)(snowball/2)-((int)(snowball/2-100))+20, (int)snowball, (int)(snowball)+64);
	}
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_LEFT) {
				mx=-6;
				}
			
		if(key==KeyEvent.VK_RIGHT) {
				mx=6;
			
		}
		if(key==KeyEvent.VK_UP) {
				my=-6;
		}
		if(key==KeyEvent.VK_DOWN) {
				my=6;
		}
		
	}
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_LEFT) {
			mx=0;
		}
		if(key==KeyEvent.VK_RIGHT) {
			mx=0;
		}
		if(key==KeyEvent.VK_UP) {
			my=0;
		}
		if(key==KeyEvent.VK_DOWN) {
			my=0;
		}
	}
	
}