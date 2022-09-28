import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class Background extends JPanel{
	int Y1;
	int Y2;
	private int h;
	private int w;
	private Image image;
	public Background() {
		ImageIcon ic = new ImageIcon("images/background.jpg");
		image=ic.getImage();
		h=image.getHeight(null);
		w=image.getWidth(null);
		
		
		Y1=0;
		Y2=Y1-h+5;
	}
	public void setY() {
		Y1+=5;
		Y2+=5;
		
		if(getY1()>=(image.getHeight(null))) {
			setY1();
		}
		if(getY2()>=(image.getHeight(null))) {
			setY2();
		}
		
	}
	public Image getImage() {
		return image;
	}
	public int getY1() {
		return Y1;
	}
	public int getY2() {
		return Y2;
	}
	public void setY1() {
		Y1=-h+10;
	}
	public void setY2() {
		Y2=-h+10;
	}
	public int getWidth() {
		return w;
	}
	public int getHeigh() {
		return h;
	}
	
}