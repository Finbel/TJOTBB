package Main;

import static org.lwjgl.opengl.GL11.*;

import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Disp {
	public static void main(String[] args) {
		int turns = 0;
		Matrix matrix = new Matrix(10);
		matrix.createWall(3, 4);
		Player player = new Player("player", 100, 50, 4, 5);
		((Square)matrix.getNode(4, 5)).addCharacter(player);
		
		LinkedList<Character> mobs = new LinkedList<Character>();
		Mob mob1 = new Mob("Zombie", 100, 25, 8, 8);
		mobs.add(mob1);
		((Square)matrix.getNode(8, 8)).addCharacter(mob1);

		System.out.println(matrix);
		try {
			Display.setDisplayMode(new DisplayMode(500, 500));
			Display.create();
			Display.setTitle("TJOTBB");
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, 500, 500, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		makeRoom(matrix);
		matrix.createDoor(0, 2, true, null);
		matrix.createDoor(9, 6, true, null);
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			Display.update();
			draw(matrix);
			while (true) {
				draw(matrix);
				Display.update();
				// System.out.println(Mouse.getX() + " " + Mouse.getY()) ;
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					System.out.print("FILTH");
					Display.destroy();
					System.exit(0);
					break;
				}
				Display.update();
				while (turns != 2) {
				draw(matrix);	
				Display.update();
				while (Keyboard.next()) {
					if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
						System.out.print("FILTH");
						Display.destroy();
						System.exit(0);
						break;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX(), player.getY() - 1);
							if (neighbour instanceof Square) {
								player.move(matrix, (Square)neighbour);
								turns++;
							}
						}
						
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX(), player.getY() + 1);
							if (neighbour instanceof Square) {
								player.move(matrix, (Square)neighbour);
								turns++;
							}
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX() -1, player.getY());
							if (neighbour instanceof Square) {
								player.move(matrix, (Square)neighbour);
								turns++;
							}
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						if (!Keyboard.getEventKeyState()) {
							Node neighbour = matrix.getNode(player.getX()+1, player.getY());
							if (neighbour instanceof Square) {
								player.move(matrix, (Square)neighbour);
								turns++;
							}
						}
					}
					if(turns >= 2) {
						for (int i = 0; i < 3; i++) {
							mob1.act(matrix, player);
							draw(matrix);
							Display.update();
							System.out.println("HP: " + player.getHealth());
							System.out.println("Is the filth alive? " + player.isAlive());
						}
						if (!player.isAlive) {
							System.out.println("DEAD AND ROTTEN FILTH");
							System.exit(0);
						}
						turns = 0;
						break;
					}
				}
				}
			}
		}
	}

	public static void draw(Matrix matrix) {
		int size = matrix.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Node node = matrix.getNode(i, j);
				if (node instanceof Wall) {
					glColor3f(0f, 0f, 0f);
					glRectf(i * 50, j * 50, i * 50 + 50, j * 50 + 50);
				}
				if (node instanceof Door) {
					glColor3f(1f, 0.35f, 1f);
					glRectf(i * 50, j * 50, i * 50 + 50, j * 50 + 50);
				}
				if (node instanceof Space) {
					glColor3f(0.5f, 0.5f, 0.5f);
					glRectf(i * 50, j * 50, i * 50 + 50, j * 50 + 50);
				}
				if (node instanceof Square) {
					Square square = (Square)node;
					if (square.getCharacter() != null) {
						if(square.getCharacter() instanceof Player) {
						glColor3f(0.3f, 0.5f, 0.1f);
						glRectf(i * 50, j * 50, i * 50 + 50, j * 50 + 50);
						}

						if(square.getCharacter() instanceof Mob) {
						Random r1 = new Random();
						glColor3f(r1.nextFloat(), r1.nextFloat(), r1.nextFloat());
						glRectf(i * 50, j * 50, i * 50 + 50, j * 50 + 50);
						}
					}
				}
			}
		}
	}

	public static void makeRoom(Matrix matrix) {
		int size = matrix.getSize();
		for (int i = 0; i < size; i++) {
			matrix.createWall(i, 0);
			matrix.createWall(i, size - 1);
		}
		for (int i = 1; i < size - 1; i++) {
			matrix.createWall(0, i);
			matrix.createWall(size - 1, i);
		}
	}

}
