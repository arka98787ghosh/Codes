import java.util.Random;

public class RollingDie {
	public int roll(){
			Random r=new Random();
			int value=r.nextInt(6)+1;
			return value;		
	}
}
