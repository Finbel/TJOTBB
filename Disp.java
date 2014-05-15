package Main;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
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
	private static int nextLevel = 1;
	private static LinkedList<String> lL = new LinkedList<String>();
	private static Matrix matrix;
	private static Player player;
	private static LinkedList<Mob> mobs = new LinkedList<Mob>();
	private static HashMap<String, Item> items = new HashMap<String, Item>();
	private static final int RESOLUTION = 500;
	private static HashMap<String, String> hud = new HashMap<String, String>();

	public static void main(String[] args) {
		makeLevel1();

		try {
			Display.setDisplayMode(new DisplayMode(RESOLUTION+300, RESOLUTION));
			Display.create();
			Display.setTitle("TJOTBB");
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, RESOLUTION + 300, RESOLUTION, 0, 1, -1);
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
										HashMap<String, Item> loot = character.removeInventory();
										for (Item item : loot.values()) {
											square.addItem(item);
										}
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
					
					// LEVEL ADVANCEMENT
					if(matrix.getNode(player.getX(), player.getY()) instanceof VictoryBox) {
						nextLevel++;
						makeLevel(nextLevel);
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
	
	private static void makeLevel(int level) {
		if (level == 2) {
			makeLevel2();
		}
		if (level == 3) {
			makeLevel3();
		}
	}

	public static void mobsMove(int k) {
		 for (Mob mob : mobs) {
			 for (int i = 0; i < k; i++) {
				 mob.act(matrix, player);
				 draw();
				 Display.update();
			 }
		 }
	}
	

	public static void draw() {
		int nodeSize = nodeSize();
		int size = matrix.getSize();
		glColor3f(0f, 0f, 0f);
		glRectf(RESOLUTION, 0, RESOLUTION + 300, RESOLUTION);
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
				if (node instanceof VictoryBox) {
					glColor3f(0.2f, 0.5f, 0.9f);
					glRectf(i * nodeSize, j * nodeSize,
							i * nodeSize + nodeSize, j * nodeSize + nodeSize);
				}
				
				if (node instanceof Square) {
					Square square = (Square) node;
					Character character = square.getCharacter();
					if (character != null) {
						if (character instanceof Player) {
							glColor3f(0.3f, 0.5f, 0.1f);
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
						} else if (character instanceof Mob) {
							Random r1 = new Random();
							glColor3f(r1.nextFloat(), r1.nextFloat(), r1.nextFloat());
							glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
						} 
					} else {
						HashMap<String, Item> items = square.getItems();
						if (items != null) {
							for (Item item : items.values()) {
								if (item instanceof Key) {
									glColor3f(1f, 1f, 0f);
									glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
									break;
								}
								if (item instanceof ZombieSlayer) {
									glColor3f(0.5f, 0.3f, 0.8f);
									glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
								}
							}
						}
						else {
							if (square.getDeadCharacter() != null) {
								glColor3f(1f, 0f, 0f);
								glRectf(i * nodeSize, j * nodeSize, i * nodeSize + nodeSize, j * nodeSize + nodeSize);
							}
						}
					}
				}
			}
		}
		
		updateHud();
		drawingwithpen(775, 50, hud);
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
	
	public static void addZombieSlayer(String name, boolean questItem, int damage, int range, int x, int y) {
		ZombieSlayer item = new ZombieSlayer(name, questItem, damage, range);
		items.put(item.getName(), item);
		((Square) matrix.getNode(x, y)).addItem(item);
	}

	public static void addPlayer(int x, int y) {
		player = new Player("player", 100, 0, x, y);
		((Square) matrix.getNode(x, y)).addCharacter(player);
		updateHud();
	}
	
	public static void updateHud() {
		hud.put("hp", "HP: " + player.getHealth());
		hud.put("inventory", "Inventory: " + player.getInventoryString());
		hud.put("damage", "Damage: " + player.getDamage());
	}
	
	public static int nodeSize() {
		int size = matrix.getSize();
		return RESOLUTION/size;
	}
	
	//------------------------------------------------TEXTPRINTING

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
		font.drawString(whatX, whatY + i, myBody.get("hp"));
		i += 15;
		font.drawString(whatX, whatY + i, myBody.get("damage"));
		i += 15;
		font.drawString(whatX, whatY + i, myBody.get("inventory"));
		i += 100;
		while(!lL.isEmpty()) {
			font.drawString(whatX, whatY + i, lL.pop());
			i += 15;
		}
			
		glDisable(GL_TEXTURE_2D);

		Display.update();
	}
	
	// --------------------------------------------------LEVEL CREATION
	
	public static void initializeLevel(int size, int spawnX, int spawnY) {
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
		mobs.clear();
		addPlayer(spawnX, spawnY);
	}

	// NOT USED - ONLY FOR TESTING.
	public static void makeLevel0() {
		int size = 25;
		initializeLevel(size, 1, 1);
		
		addKey("key1", true, 13, 12);
		addZombieSlayer("zombieslayer", true, 100, 20, 2, 3);
		
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
		matrix.createVictoryBox(11, 11);

		addMob("Zombie1", 100, 1, 23, 17);
		addMob("Zombie2", 100, 1, 5, 23);
	}
	
	public static void makeLevel1() {
		int size = 25;
		initializeLevel(size, 1, 1);

		addKey("key1", true, 22, 3);
		
		matrix.rowWall(10, 2, 24);
		matrix.rowWall(6, 0, 3);
		matrix.rowWall(6, 5, 8);
		matrix.rowWall(6, 10, 13);
		matrix.rowWall(6, 15, 18);
		matrix.rowWall(6, 20, 22);
		matrix.columnWall(5, 0, 6);
		matrix.columnWall(10, 0, 6);
		matrix.columnWall(15, 0, 6);
		matrix.columnWall(24, 0, 6);
		matrix.columnWall(20, 0, 6);

		matrix.createDoor(1, 10, false, items.get("key1"));
		
		addMob("Zombie2", 100, 15, 7, 3);
		addMob("Zombie3", 100, 15, 12, 2);
		addMob("Zombie3", 100, 15, 18, 4);
		
		matrix.rowWall(11, 5, 24);
		matrix.rowWall(12, 5, 24);
		matrix.rowWall(13, 5, 24);
		addZombieSlayer("zombieslayer", true, 50, 20, 3, 2);
		
		for(int i = 14; i<25; i++){
			matrix.rowWall(i, 0, 24);
		}
		
		matrix.createVictoryBox(2, 11);
	}
	
	
	public static void makeLevel2() {
		int size = 25;
		initializeLevel(size, 22, 12);
		
		//First for loop
		for(int i = 1; i<5; i++){
			matrix.rowWall(i, 4, 12);
			matrix.rowWall(i, 16, 19);
		}
		
		matrix.rowWall(4, 1, 19);
		
		//Second for loop
		for(int i = 4; i<9; i++){
			matrix.columnWall(i, 4, 7);
			matrix.columnWall(i, 14, 23);
		}
		
		matrix.rowWall(8, 4, 19);
		matrix.columnWall(16, 5, 7);
		
		//Third for loop
		for(int i = 21; i < 24; i++){
			matrix.columnWall(i, 1, 10);
			matrix.columnWall(i, 14, 23);
		}
		
		matrix.columnWall(20, 1, 23);
		matrix.rowWall(21, 1, 3);
		matrix.rowWall(16, 1, 3);
		
		for(int i = 15; i < 23; i+=3){
			matrix.rowWall(i, 11, 12);
			matrix.rowWall(i, 15, 16);
		}
		
		matrix.rowWall(12, 12, 15);
		
		matrix.createDoor(20, 12, true, null);
		
		matrix.createDoor(18, 8, true, null);
		matrix.createDoor(16, 6, true, null);
		matrix.createDoor(13, 4, true, null);
		matrix.createDoor(2, 16, true, null);
		matrix.createDoor(2, 21, true, null);
		
		addZombieSlayer("zombieslayer", true, 25, 20, 15, 1);
		addKey("key1", true, 2, 23);
		matrix.createDoor(2, 4, false, items.get("key1"));
		
		addMob("Zombie1", 100, 15, 11, 16);
		addMob("Zombie2", 100, 15, 15, 16);
		addMob("Zombie3", 100, 15, 11, 19);
		addMob("Zombie4", 100, 15, 15, 19);
		addMob("Zombie5", 100, 15, 11, 22);
		addMob("Zombie6", 100, 15, 15, 22);
		
		addMob("The Janitor", 100, 20, 10, 6);
		addMob("The Dead Cop", 200, 20, 2, 18);
	}
	
	public static void makeLevel3() {
		int size = 25;
		initializeLevel(size, 2, 22);
		
		matrix.rowWall(2, 8, 15);
		matrix.createWall(11, 3);
		matrix.rowWall(4, 8, 15);
		matrix.rowWall(4, 3, 4);
		matrix.rowWall(5, 3, 8);
		matrix.rowWall(6, 3, 8);
		matrix.rowWall(5, 15, 16);
		matrix.rowWall(6, 15, 16);
		matrix.createWall(17, 6);
		matrix.createWall(16, 7);
		matrix.rowWall(7, 8, 15);
		matrix.rowWall(9, 8, 14);
		matrix.createWall(11, 8);
		
		matrix.rowWall(20, 0, 5);
		matrix.columnWall(5, 20, 23);
		
		matrix.createDoor(5, 22, true, null);
		matrix.createDoor(14, 7, true, null);
		
		addMob("Zombie1", 100, 25, 13, 10);
		addMob("Zombie2", 100, 25, 12, 22);
		addMob("Zombie3", 100, 25, 11, 20);
		addMob("Zombie4", 100, 25, 18, 3);
		addMob("Zombie5", 100, 25, 9, 22);
		addMob("Zombie6", 100, 25, 19, 15);
		addMob("Zombie7", 100, 25, 4, 15);
		addMob("zombie8", 100, 25, 7, 10);
		addMob("zombie9", 100, 25, 10, 15);

		addZombieSlayer("zombieslayer", true, 25, 20, 3, 21);
	}
}