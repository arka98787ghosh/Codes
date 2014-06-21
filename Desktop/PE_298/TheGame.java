import java.util.Random;

public class TheGame {

	int memoryr[] = new int[5];		//robin's memory
	
	/*index is used to put the next number in robin's memory, it decrements from 4 to 0 and the back
	 * to 4 again simulating robin's deletion condition i.e deleting the number that has been in the
	 * memory longest
	 */
	
	int index = 4;	
	
	int memoryl[] = new int[5];		//larry's memory
	/*
	 * this array stores the turns since an element has been called.
	 * 0-no of turns since 1 has been called
	 * 1-no of turns since 2 has been called
	 * ..
	 * ..
	 * this array keeps track of last time an element is called. this is 
	 * done by incrementing all the elements of the array in each turn save the element being called.
	 * when an element is called its index is set to 0. that way the index having the highest value 
	 * hasn't been called longest.
	 */
	
	int numbers[] = new int[10];
	//used to assign first five elements to larry's memory
	int five=0;
	
	int scorel=0;		//larry's score
	
	int scorer=0;		//robin's score

	
	//generate's a number
	
	public int generate() {
		Random r = new Random();
		int num = r.nextInt(10) + 1;
		for (int i = 0; i < numbers.length; i++) {
			if (num == (i + 1))
				numbers[i] = 0;
			else
				numbers[i]++;
		}
		return num;
	}
	
	//updates robin's memory

	public void updater(int n) {
		boolean found = false;				//keeps track of whether the new number is already in the memory or not

		// checks if number already exists in the memory

		for (int i = 0; i < memoryr.length; i++) {
			if (memoryr[i] == n) {
				found = true;
				scorer++;
				break;
			}
		}

		if (!(found)) {
			memoryr[index] = n;
			index--;
		}

		if (index < 0)
			index = 4;

		/*for (int i = 0; i < memoryr.length; i++) {
			System.out.print(memoryr[i] + " ");
		}*/
	}

	//update's larry's memory
	
	public void updatel(int n){
		
		boolean found=false;			//keeps track of whether the new number is already in the memory or not
		
		int lastcalled=0;		// stores the highest value of the index
		
		int index=0;		// stores the index of the value called earliest
		
		// updating memory for the first five turns
		
		if(five<5){
			for(int i=0;i<(five+1);i++){		//checks if the number is already present
				if(memoryl[i]==n){
					found=true;
					scorel++;
					break;
				}
			}
			if(!found)
				memoryl[five++]=n;
		}else{			// updating memory after first five turns
			for(int i=0;i<memoryl.length;i++){		//checks if the number is already present
				if(memoryl[i]==n){
					scorel++;
					found=true;
				}
				if(numbers[memoryl[i]-1]>lastcalled){	//finds the number last called
					lastcalled=numbers[memoryl[i]-1];
					index=i;
				}
			}
			if(!found)
				memoryl[index]=n;
		}
		/*for (int i = 0; i < memoryl.length; i++) {
			System.out.print(memoryl[i] + " ");
		}*/
	}
	
	
	//returns absolute score after 50 turns
	public int absoluteScore(){
		return Math.abs(scorer-scorel);
	}
	
	//return result of one game
	public int result() {
		TheGame tg = new TheGame();
		int num=0;
		for (int i = 0; i < 50; i++) {
			num=tg.generate();
			tg.updatel(num);
			tg.updater(num);
		}
		return tg.absoluteScore();
	}
}
