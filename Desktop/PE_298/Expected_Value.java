
public class Expected_Value {
	public static void main(String args[]){
		TheGame tg1=new TheGame();
		double sum=0;
		int noofiterations=10000000;
		
		//finding the expected value by repeating the game several times.
		// the code gives an answer upto 7 decimal places in about 54 seconds
		//but for 8 decimal places as asked in the original problem this is not optimal 
		// and is too slow. this gets close to the original answer but as we are finding an
		//expected value so the answer changes on each execution but the variaiton is less
		
		for(int i=0;i<noofiterations;i++){ 
			sum+=tg1.result();
		}
		System.out.println((sum/noofiterations));
	}
}