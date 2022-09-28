import java.awt.*;

import javax.swing.ImageIcon;

public class Skill {
	private Image skillImage;
	private snowball S;
	private int x, y;
	public Skill(String skill, int x, int y) {
		ImageIcon ic = new ImageIcon("images/"+skill);
		this.x=x;
		this.y=y;
		skillImage=ic.getImage();
	}
	public void Skill_snowball(int x, int y) {
		S = new snowball(x,y);
		Board.snowball_list.add(S);
	}
	public Image getImage() {
		return skillImage;
	}
}