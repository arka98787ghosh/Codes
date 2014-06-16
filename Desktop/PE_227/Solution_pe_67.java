import java.io.File;
import java.io.FileNotFoundException;
import java.math.*;
import java.util.Scanner;


public class Solution_pe_67 {
	
	int array[][]=new int[100][100]; // creating a 2-d array to store the triangle(I am wasting a lot of space here, 
					 //couldn't find a way to dynamically allocate elements to array)
	int count=array.length-2;
	
	int arr[]=new int[100]; 		//array to store all the elements of the bottom-most row 
	
	
	// this method copies the elements of the text file into an array
	
	public void copy() throws FileNotFoundException{
		int rows=100,columns=0;
		File file=new File("triangle.txt");
		Scanner input=new Scanner(file);
		for(int i=0;i<rows;i++){
			for(int j=0;j<=columns;j++){
				array[i][j]=input.nextInt();
				//System.out.print(array[i][j]+" ");
			}
			columns++;
			System.out.println();
		}
		for(int i=0;i<array[count+1].length;i++){
			arr[i]=array[count+1][i];
		}
	}
	
	
	/*The algorithm follows a bottom up approach working its way from last row to upwards.
	 * what the following method does is to compare consecutive elements in a particular row
	 * and replace each element with the maximum among the two. then it adds the modified 
	 * row with the above row. that way we get maximum sum path(sub-max path) from bottom 
	 * till that row. this is continued till it reaches the top of the triangle.
	 */
	
	
	public int actual(){
		for(int k=0;k<array.length-1;k++){
			//comparing consecutive elements and picking the max
			for (int i=0;i<arr.length-1;i++){
				arr[i]=Math.max(arr[i], arr[i+1]);
			}
			//adding the sub-max path with the upper array
			for (int i=0;i<array[count].length;i++){
				arr[i]=arr[i]+array[count][i];
			}
			count--;
		}
		return arr[0];
	}
	
	
	
	public static void main(String args[]) throws FileNotFoundException {
		Solution_pe_67 p=new Solution_pe_67();
		p.copy();
		System.out.println(p.actual());
	}
	
	

}

