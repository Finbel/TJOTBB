package Main;

public class ZombieSlayer extends Item {
	private int damage, range;
	public ZombieSlayer(String name, boolean questItem, int damage, int range) {
		super(name, questItem);
		this.damage = damage;
		this.range = range;
		// TODO Auto-generated constructor stub
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Character shoot(Matrix matrix, Player player, int ns, int shootX, int shootY) {
		int nx = shootX/ns;
		int ny = shootY/ns;
		Node node = matrix.getNode(nx, ny);
		if (!(node instanceof Square)) {
			return null;
		}
		double length = Math.sqrt((shootX - player.getX())*(shootX - player.getX()) + (shootY - player.getY())*(shootY - player.getY()));
		if (((int)length) / ns <= range) {
			Mob mob = (Mob)((Square)node).getCharacter();
			if(mob != null) {
				return mob;
			}
		}
		return null;
	}
}
