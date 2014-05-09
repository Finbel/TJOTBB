import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Disp {
	public static void main(String[] args) {
		Matrix matrix = new Matrix(10);
		matrix.createDoor(0, 2, true, null);

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
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			makeRoom(matrix);
			matrix.createDoor(0, 2, true, null);
			draw(matrix);
			Display.update();
			while (true) {
				Display.update();
				System.out.println(Mouse.getX() + " " + Mouse.getY()) ;
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					System.out.print("FILTH");
					Display.destroy();
					System.exit(0);
					break;
				}
				
			}

		}
	}

	public static void draw(Matrix matrix) {
		Node node;
		int size = matrix.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				node = matrix.getNode(i, j);
				if (node instanceof Wall) {
					glColor3f(0f, 0f, 0f);
					glRectf(i * 50, j * 50, i* 50 + 50, j * 50 + 50);
				}
				if (node instanceof Door) {
					glColor3f(1f, 0.35f, 1f);
					glRectf(i * 50, j * 50, i* 50 + 50, j * 50 + 50);
				}
				if (node instanceof Space) {
					glColor3f(0.5f, 0.5f, 0.5f);
					glRectf(i * 50, j * 50, i* 50 + 50, j * 50 + 50);
				}

			}

		}

	}
	
	public static void makeRoom(Matrix matrix) {
		int size = matrix.getSize();
		for(int i = 0; i<size; i++) {
			matrix.createWall(i, 0);
			matrix.createWall(i, size-1);
		}
		for(int i = 1; i<size-1; i++) {
			matrix.createWall(0, i);
			matrix.createWall(size-1, i);
		}
	}
}
