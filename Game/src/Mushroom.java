public class Mushroom extends enemy {
	private int damage;
	public Mushroom(String enemy, int damage) {
		super(enemy);
		this.damage=damage;
	}
	public int getDamage() {
		return damage;
	}
	

}