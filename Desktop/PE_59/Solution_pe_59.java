import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Solution_pe_59 {

	double count = 1201.0;
	int noofelements = (int) Math.ceil(count / 3);
	int elements[][] = new int[3][noofelements];
	int All[] = new int[52]; // stores the ASCII values of [A-Z,a-z]
	int a_z[] = new int[26]; // stores the ASCII values of [a-z]
	int sets[][] = new int[26][52]; // stores the xored possibilities of all the
									// lower case alphabets with [A-Z,a-z]
									// 0th row-[a^[A-Z,a-z]];1st row-[b^[A-Z,a-z]];.........so
									// on

	/*
	 * reading the file and storing every fourth element in one array. this way
	 * all the numbers corresponding to one particular key alphabet is in one
	 * place
	 */

	public void read() throws FileNotFoundException {
		String file = "Cipher1.txt";
		FileReader fr = new FileReader(file);
		Scanner in = new Scanner(fr);
		StringTokenizer line = new StringTokenizer(in.nextLine());
		int rows = 0;
		int columns = 0;
		while (line.hasMoreTokens()) {
			elements[rows][columns] = Integer.parseInt(line.nextToken(","));
			if (rows == 2) {
				rows = 0;
				columns++;
			} else {
				rows++;
			}
		}
		in.close();
	}

	/*
	 * creating the array of all the possible xor values between lower case
	 * alphabets[a-z] and all other alphabets[a-z,A-Z]
	 */

	public void creating_arrays() {

		int A = 65;
		int a = 97;
		// assigning ASCII values [A-Z,a-z]
		for (int i = 0; i < All.length; i++) {
			if (i < 26) {
				All[i] = A;
				A++;
			} else {
				All[i] = a;
				a++;
			}
		}
		a = 97;

		// assigning ASCII values [a-z]
		for (int i = 0; i < a_z.length; i++) {
			a_z[i] = a;
			a++;
		}

		// calculating and assigning those values

		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < All.length; j++) {
				sets[i][j] = (a_z[i] ^ All[j]);
			}
		}
	}

	// creating the array of alphabets and their respective occurrences;try to
	// modify this

	int alphapos[][] = new int[3][26]; // this array stores the number of
										// matches
										// found in the xored value
										// possibilities sets
										// and the values in the file

	// this method checks for the occurrences of each alphabet and stores them
	// in a 3*26 array
	// [0,0]no. of matches of a in 1st key position
	// [0,1]no. of matches of b in 1st key position
	// [1,0]no. of matches of a in 2nd key position etc.

	public void checkMaxOccurences() {
		int flag = 0;
		for (int ar = 0; ar < alphapos[0].length; ar++) {
			for (int b = 0; b < alphapos.length; b++) {
				flag = 0;
				for (int i = 0; i < sets[ar].length; i++) {
					for (int j = 0; j < elements[b].length; j++) {
						if (sets[ar][i] == elements[b][j]) {
							flag++;
							break;
						}
					}
				}
				if (b == 3) {
					continue;
				}
				alphapos[b][ar] = flag;
			}
		}
	}

	// retrieving the key by searching for the maximum occurring xored value
	int index[] = new int[3];

	public void key() {
		int value;
		for (int i = 0; i < alphapos.length; i++) {
			value = 0;
			for (int j = 0; j < alphapos[i].length; j++) {
				if (alphapos[i][j] > value) {
					value = alphapos[i][j];
					index[i] = j;
				}
			}
		}
		System.out.println("The key is " + (char) (index[0] + 97) + " "+ (char) (index[1] + 97) + " " + (char) (index[2] + 97));

	}

	// finding the sum
	
	public void sum() {
		int newelements[][] = new int[elements.length][elements[1].length];
		int key[] = { (index[0] + 97), (index[1] + 97), (index[2] + 97) };
		int sum = 0;
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[i].length; j++) {
				newelements[i][j] = (elements[i][j] ^ key[i]);
				sum += newelements[i][j];
			}
		}
		sum -= (int) 'o'; // compensating for the last two zeroes.
		sum -= (int) 'd';
		System.out.println(sum);
	}

	public static void main(String args[]) throws FileNotFoundException {
		Solution_pe_59 frt=new Solution_pe_59();
		frt.read();
		frt.creating_arrays();
		frt.checkMaxOccurences();
		frt.key();
		frt.sum();
	}

}