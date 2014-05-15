package Main;

public class HealthPack extends Item {
	private int damage, range;
	public HealthPack(String name, boolean questItem, int damage, int range) {
		super(name, questItem);	
		this.damage = damage;
		this.range = range;		// TODO Auto-generated constructor stub
	}
	public void heal(Matrix matrix, Player player) {
		player.health += damage;
	
}

	// TODO Auto-generated constructor stub


public int getDamage() {
	return damage;
}
}


