package Main;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class Disp {
	private static UnicodeFont font;
	private HashMap<String, String> myBody;
	private int resolution;
	
	private static Matrix matrix;
	private static Player player;
	private static LinkedList<Mob> mobs = new LinkedList<Mob>();
	private static HashMap<String, Item> items = new HashMap<String, Item>();
	private static final int RESOLUTION = 800;
	private static HashMap<String, String> hud = new HashMap<String, String>();

	public static void main(String[] args) {
		makeLevel1();

		try {
			Display.setDisplayMode(new DisplayMode(RESOLUTION, RESOLUTION));
			Display.create();
			Display.sync(30);
			Display.setTitle("TJOTBB");
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, RESOLUTION, RESOLUTION, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		setUpFonts();
		while (!Display.isCloseRequested()) {
			int turns = 0;
			glClear(GL_COLOR_BUFFER_BIT);
			Display.update();
			draw();
			while (true) {
				draw();
				Display.update();
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
					
					if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
						if (!Keyboard.getEventKeyState()) {
							if (player.hasItem(items.get("zombieslayer"))) {
								Random random = new Random();
								if (random.nextInt(2) == 1) {
									mobsMove(3);
								}
								Character character = ((ZombieSlayer)items.get("zombieslayer")).shoot(matrix, player, nodeSize(), Mouse.getX(), RESOLUTION -  Mouse.getY());
								if (character != null && character.isAlive()) {
									player.hit(character);
									System.out.println(player.damage + " damage inflicted upon " + character + "(health = " + character.getHealth() + ")");
									if (!character.isAlive()) {
										Square square = (Square)matrix.getNode(character.getX(), character.getY());
										square.removeCharacter();
										square.addDeadCharacter(character);
									}
								}
								turns++;
							}
						}
					}
					
					if (Keyboard.getEventKey() == Keyboard.KEY_LCONTROL) {
						if (!Keyboard.getEventKeyState()) {
							if(player.tryOpen(matrix)) {
								turns++;
							}
						}
					}

					if (Keyboard.getEventKey() == Keyboard.KEY_W) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX(),
									player.getY() - 1);
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_S) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX(),
									player.getY() + 1);
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_A) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX() - 1,
									player.getY());
							if (neighbour instanceof Square) {
								player.move(matrix, (Square) neighbour);
								turns++;
							}
						}
					}
					
					if (Keyboard.getEventKey() == Keyboard.KEY_D) {
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
						mobsMove(3);
						turns = 0;
					}
					if (!player.isAlive) {
						 System.out.println("DEAD AND ROTTEN FILTH");
						 System.exit(0);
					}
				}
			}
		}
	}
	
	public static void mobsMove(int k) {
		 for (Mob mob : mobs) {
			 for (int i = 0; i < k; i++) {
				 mob.act(matrix, player);
				 draw();
				 Display.update();
				 //System.out.println("HP: " + player.getHealth());
				 //System.out.println("Is the filth alive? " + player.isAlive());
			 }
		}
	}
	

	public static void draw() {
		int nodeSize = nodeSize();
		int size = matrix.getSize();

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
					if (square.getDeadCharacter() != null) {
						glColor3f(1f, 0f, 0f);
						glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
					}
					if (square.getCharacter() != null) {
						if (square.getCharacter() instanceof Player) {
							glColor3f(0.3f, 0.5f, 0.1f);
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
						} else if (square.getCharacter() instanceof Mob) {
							Random r1 = new Random();
							glColor3f(r1.nextFloat(), r1.nextFloat(), r1.nextFloat());
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
						}
					} else if (square.getItem() != null) {
						if (square.getItem() instanceof Key) {
							glColor3f(1f, 1f, 0f);
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
						}
						else if (square.getItem() instanceof ZombieSlayer) {
							glColor3f(0.5f, 0.3f, 0.8f);
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
						}
					}
				}
			}
		}
		
		//HP
		updateHud();
		drawingwithpen(200, 200, hud);
		//INVENTORY
		//TimerDemo.drawingwithpen(RESOLUTION, 200,200, "THE SKY IS THE LIMIT");
		//TimerDemo.drawingwithpen(RESOLUTION, 200,200, "THE SKY IS THE LIMIT");
		//ammo
		//wTimerDemo.drawingwithpen(RESOLUTION, 200,200, "THE SKY IS THE LIMIT");
		
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
		addZombieSlayer("zombieslayer", true, 2, 3);
		
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

		
		addMob("Zombie1", 100, 1, 23, 17);
		addMob("Zombie2", 100, 100, 5, 23);

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
	
	public static void addZombieSlayer(String name, boolean questItem, int x, int y) {
		ZombieSlayer item = new ZombieSlayer(name, questItem, 20, 150);
		items.put(item.getName(), item);
		((Square) matrix.getNode(x, y)).addItem(item);
	}

	public static void addPlayer(String name, int health, int damage, int x,int y) {
		player = new Player(name, health, damage, x, y);
		((Square) matrix.getNode(x, y)).addCharacter(player);
		updateHud();
	}
	
	public static void updateHud() {
		hud.put("hp", "HP: " + player.getHealth());
		hud.put("damage", "Damage: " + player.getDamage());
		hud.put("inventory", "Inventory: " + player.getInventoryString());
	}
	
	public static int nodeSize() {
		int size = matrix.getSize();
		return RESOLUTION/size;
	}



		private static void setUpFonts() {
			java.awt.Font awtFont = new java.awt.Font("ComicSans",
					java.awt.Font.PLAIN, 14);
			font = new UnicodeFont(awtFont);
			font.getEffects().add(new ColorEffect(java.awt.Color.white));
			font.addAsciiGlyphs();

			try {
				font.loadGlyphs();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		public static void drawingwithpen(int whatX, int whatY, HashMap<String, String> myBody) {
			int i = 0;
			for(String string : myBody.values()) {
				font.drawString(whatX, whatY + i, string);
				i += 15;
			}
			glDisable(GL_TEXTURE_2D);

			Display.update();
		

	}
}

