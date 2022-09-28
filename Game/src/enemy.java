import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
public class enemy {
	
	private int x;
	private int y;
	private int w;
	private int h;
	private Image image;
	private Rectangle rect;
	private int speed;
	private String enemy;
	public enemy(String enemy) {
	 
		ImageIcon ic = new ImageIcon("images/"+enemy+".png");
		this.enemy=enemy;
		image=ic.getImage();
		x=(int)(Math.random()*1180);
		speed=(int)(Math.random()*(Board.getLevel())+1);
		
		w=image.getWidth(null);
		h=image.getHeight(null);
		y=-h;
		rect=new Rectangle(x,y,w,h);
	
	
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
	public String getEnemy() {
		return enemy;
	}
	public void move() {
		y+=speed;
		rect.y=y;
	}
	
	public Rectangle getBounds() {
		
		if(enemy.equals("icewall")) {
			rect.x=x+30;
			rect.y=y+24;
			rect.width=w-60;
			rect.height=h-52;
		}
		else if(enemy.equals("mushroom")) {
			
		}
		else {
			rect.x=x+20;
			rect.y=y+22;
			rect.width=w-40;
			rect.height=h-44;
		}
		return rect;
	}
	
	
	
}