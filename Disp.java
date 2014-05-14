package Main;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Disp {
	private static Matrix matrix;
	private static Player player;
	private static LinkedList<Mob> mobs = new LinkedList<Mob>();
	private static HashMap<String, Item> items = new HashMap<String, Item>();
	private static final int RESOLUTION = 500;

	public static void main(String[] args) {
		makeLevel1();

		try {
			Display.setDisplayMode(new DisplayMode(RESOLUTION, RESOLUTION));
			Display.create();
			Display.setTitle("TJOTBB");
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, RESOLUTION, RESOLUTION, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		while (!Display.isCloseRequested()) {
			int turns = 0;
			glClear(GL_COLOR_BUFFER_BIT);
			Display.update();
			draw();
			while (true) {
				draw();
				Display.update();
				// System.out.println(Mouse.getX() + " " + Mouse.getY()) ;
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					System.out.print("FILTH");
					Display.destroy();
					System.exit(0);
					break;
				}
				while (Keyboard.next()) {
					draw();
					Display.update();
					if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
						System.out.print("FILTH");
						Display.destroy();
						System.exit(0);
						break;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX(),
									player.getY() - 1);
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}

					}
					if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX(),
									player.getY() + 1);
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX() - 1,
									player.getY());
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX() + 1,
									player.getY());
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}
					}
					
					if (turns >= 2) {
						 for (Mob mob : mobs) {
							 for (int i = 0; i < 3; i++) {
								 turns = 0;
								 mob.act(matrix, player);
								 draw();
								 Display.update();
								 System.out.println("HP: " + player.getHealth());
								 System.out.println("Is the filth alive? " + player.isAlive());
							 }
							 if (!player.isAlive) {
								 System.out.println("DEAD AND ROTTEN FILTH");
								 System.exit(0);
							 }
						}
					}
				}
			}
		}
	}

	public static void draw() {
		int size = matrix.getSize();
		int nodeSize = RESOLUTION / size;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Node node = matrix.getNode(i, j);
				if (node instanceof Wall) {
					glColor3f(0f, 0f, 0f);
					glRectf(i * nodeSize, j * nodeSize,
							i * nodeSize + nodeSize, j * nodeSize + nodeSize);
				}
				if (node instanceof Door) {
					if (((Door)node).isOpen()) {
						glColor3f(1f, 1f, 1f);
						glRectf(i * nodeSize, j * nodeSize,
								i * nodeSize + nodeSize, j * nodeSize + nodeSize);
					} else {
						glColor3f(0.1f, 0.1f, 0.1f);
						glRectf(i * nodeSize, j * nodeSize,
								i * nodeSize + nodeSize, j * nodeSize + nodeSize);
					}
				}
				if (node instanceof Space) {
					glColor3f(0.5f, 0.5f, 0.5f);
					glRectf(i * nodeSize, j * nodeSize,
							i * nodeSize + nodeSize, j * nodeSize + nodeSize);
				}
				if (node instanceof Square) {
					Square square = (Square) node;
					if (square.getCharacter() != null) {
						if (square.getCharacter() instanceof Player) {
							glColor3f(0.3f, 0.5f, 0.1f);
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize
									+ nodeSize, j * nodeSize + nodeSize);
						} else if (square.getCharacter() instanceof Mob) {
							Random r1 = new Random();
							glColor3f(r1.nextFloat(), r1.nextFloat(),
									r1.nextFloat());
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize
									+ nodeSize, j * nodeSize + nodeSize);
						}
					} else if (square.getItem() != null) {
						if (square.getItem() instanceof Key) {
							glColor3f(1f, 1f, 0f);
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize
									+ nodeSize, j * nodeSize + nodeSize);
						}
					}
				}
			}
		}
	}
	
	public static void initializeLevel(int size) {
		// creates borders.
		matrix = new Matrix(size);
		for (int i = 0; i < size; i++) {
			matrix.createWall(i, 0);
			matrix.createWall(i, size - 1);
		}
		for (int i = 1; i < size - 1; i++) {
			matrix.createWall(0, i);
			matrix.createWall(size - 1, i);
		}
	}

	public static void makeLevel1() {
		int size = 25;
		initializeLevel(size);
		
		addKey("key1", true, 13, 12);
		
		matrix.createWall(10, 1);
		matrix.createWall(10, 2);
		matrix.createWall(10, 3);
		matrix.createWall(10, 4);
		matrix.createWall(10, 5);
		matrix.createWall(11, 5);
		matrix.createDoor(12, 5, false, items.get("key1"));
		matrix.createWall(13, 5);
		matrix.createWall(14, 5);
		matrix.createWall(15, 5);
		matrix.createWall(15, 4);
		matrix.createWall(15, 3);
		matrix.createWall(15, 2);
		matrix.createWall(15, 1);

		
		addMob("Zombie1", 100, 25, 23, 17);
		addMob("Zombie2", 100, 25, 5, 23);

		addPlayer("Filth", 100, 25, 1, 1);
	}

	public static void addMob(String name, int health, int damage, int x, int y) {
		Mob mob = new Mob(name, health, damage, x, y, 5);
		mobs.add(mob);
		((Square) matrix.getNode(x, y)).addCharacter(mob);
	}
	
	public static void addKey(String name, boolean questItem, int x, int y) {
		Key item = new Key(name, questItem);
		items.put(item.getName(), item);
		((Square) matrix.getNode(x, y)).addItem(item);
	}

	public static void addPlayer(String name, int health, int damage, int x,int y) {
		player = new Player(name, health, damage, x, y);
		((Square) matrix.getNode(x, y)).addCharacter(player);
	}
}
