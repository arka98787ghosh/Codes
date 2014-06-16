import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

//Please go through the text file "STEPS" before reading the code.
public class Move {

	int x = 0, y = 0;

	// this method traverses 15258789=(5^12-1)/2^4 steps in the D24 path

	public void traverse() throws FileNotFoundException,
			UnsupportedEncodingException {
		Dss d = new Dss();
		Generate gen = new Generate();
		String Path = d.generate();
		long l = 0, count = 0;
		int direction = 1;
		for (int i = 0; i < Path.length(); i++) {
			if (Path.charAt((int) l) == 'L') {
				direction += 1;
				if (direction >= 4) {
					direction -= 4;
				}
			} else if (Path.charAt((int) l) == 'R') {
				direction -= 1;
				if (direction <= -1) {
					direction += 4;
				}
			} else if (Path.charAt((int) l) == 'F') {
				if (direction == 0) {
					x += 1;
				} else if (direction == 2) {
					x -= 1;
				} else if (direction == 1) {
					y += 1;
				} else if (direction == 3) {
					y -= 1;
				}
				count++;
				if (count == 15258789) {
					break;
				}
			}
			l++;
		}
		System.out.println(x + " " + y + " " + direction);
	}

	// this method manipulates the above result according to the method
	// described in the text file.
	public void manipulate() {
		int xf = x, yf = y;
		int xi = 0, yi = 0;
		
		// manipulating for 2^4
		
		for (int i = 1; i <= 4; i++) {
			xi = xf;
			yi = yf;
			xf = xi + yi;
			yf = yi - xi;
		}
		
		//taking a step downwards
		
		yf = yf - 1;
		System.out.println(xf + " " + yf);
		xi = 0;
		yi = 0;
		
		// manipulating for 2^12
		
		for (int i = 1; i <= 12; i++) {
			xi = xf;
			yi = yf;
			xf = xi + yi;
			yf = yi - xi;
		}
		System.out.println(xf + " " + yf);
	}

	public static void main(String args[]) throws FileNotFoundException,
			UnsupportedEncodingException {
		Move m = new Move();
		m.traverse();
		m.manipulate();
	}
}
