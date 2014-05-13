package Main;

public class Main {

	public static void main(String[] args) {
		
		/**
		 * Tests the matrix by creating a 3x3 matrix,
		 * turning some nodes into walls and doors 
		 * and printing it.
		 */
		Matrix matrix = new Matrix(3);
		
		
		matrix.createWall(0,0);
		matrix.createWall(0,2);
		matrix.createWall(2,0);
		matrix.createDoor(2,2,true,null);
		
		System.out.println(matrix);
		Disp disp = new Disp();
		disp.draw(matrix);
		while(true) {
			
		}
	}
	}