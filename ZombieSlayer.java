package Main;

import java.util.ArrayList;

public class ZombieSlayer extends Item {
	private int damage, range;
	public ZombieSlayer(String name, boolean questItem, int damage, int range) {
		super(name, questItem);
		this.damage = damage;
		this.range = range;
	}
	
	public int getDamage() {
		return damage;
	}

	public Character shoot(Matrix matrix, Player player, int ns, int shootX, int shootY) {
		int nx = Math.round(shootX/ns);
		int ny = Math.round(shootY/ns);
		Node node = matrix.getNode(nx, ny);
		System.out.println("testing node at " + "(" + node.getX() + ":" + node.getY() + ")");
		if (!(node instanceof Square)) {
			return null;
		}
		double length = Math.sqrt((nx - player.getX())*(nx - player.getX()) + (ny - player.getY())*(ny - player.getY()));
		System.out.println("length: " + (int)length + "(player's position: " + player.getX() + ":" + player.getY() + ") " + ", range: " + range);
		if ((int)length <= range) {
			System.out.println("shot at node at " + "(" + node.getX() + ":" + node.getY() + ") was within range!");
			if (validBulletPath(matrix, (Square)matrix.getNode(player.getX(), player.getY()), (Square)node, 10)) {
				System.out.println("BULLETPATH VALID");
				Character target = ((Square)node).getCharacter();
				if (target != null) {
					if(target instanceof Mob) {
						System.out.println("HIT");
						return target;
					}
				}
			}
		}
		return null;
	}
	
	private boolean validBulletPath(Matrix matrix, Square location, Square target, int distance) {
		ArrayList<Node> shortestRoute = matrix.shortestRoute(location, target, distance, true);
		if (shortestRoute == null) {
			return false;
		}
		for (Node node : shortestRoute) {
			if (!(node instanceof Square)) {
				return false;
			} else if (node instanceof Door) {
				if (!((Door)node).isOpen()) {
					return false;
				}
			}
		}
		return true;
	}
}