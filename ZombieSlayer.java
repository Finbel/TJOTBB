package Main;

import java.util.ArrayList;

/**
 * this is my weapon this is my gun.....
 * this is a weapon class
 * Creates a weapon with the damage and
 * range as parameters.
 * 
 * @param damage
 * @param range How many Nodes its shot can reach (utilizes
 * diagonal pathfinding ("bulletPath")).
 */
public class ZombieSlayer extends Item {
	private int damage, range;
	public ZombieSlayer(String name, boolean questItem, int damage, int range) {
		super(name, questItem);
		this.damage = damage;
		this.range = range;
	}
	
	/**
	 * returns the damage the weapon does
	 * @return
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * through the use of x and y cordinates finds the
	 * node which your mouse is aiming at
	 * then looks if have the range, as well as
	 * makes sure there is no wall
	 * because guns can't shoot through walls
	 * @param matrix
	 * @param player
	 * @param ns
	 * @param shootX
	 * @param shootY
	 * @return
	 */
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
	
	/**
	 * checks that you are not trying to shoot through walls
	 * @param matrix
	 * @param location
	 * @param target
	 * @param distance
	 * @return
	 */
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