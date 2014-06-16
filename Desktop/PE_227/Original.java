public class Original {
	int noofplayers = 100;
	
	// the players who have the die initially(these also keep record of the current die position)
	
	int index1 = 1;
	int index2 = (1 + (noofplayers / 2));
	
	// the number of turns the game lasts
	
	int count = 0;
	
	// the die value for the two players after each roll
	
	int player1;
	int player2;
	
	// this method counts the number of turns the game lasts

	public int one_game() {
		RollingDie rd = new RollingDie();
		index1 = 1;
		index2 = (1 + (noofplayers / 2));
		boolean flag = true;
		count = 0;
		while (flag) {
			player1 = rd.roll();		// player1's value
			player2 = rd.roll();		// player2's value
			
			// update according to player1's value
			switch (player1) {        
			case 1:
				index1--;
				break;
			case 6:
				index1++;
				break;
			default:
				break;
			}
			
			// update according to player2's value
			switch (player2) {
			case 1:
				index2--;
				break;
			case 6:
				index2++;
				break;
			default:
				break;
			}

			if (index1 == 0)
				index1 = noofplayers;
			else if (index1 == (noofplayers + 1))
				index1 = 1;
			if (index2 == 0)
				index2 = noofplayers;
			else if (index2 == (noofplayers + 1))
				index2 = 1;

			count++;    // updating the no of turns.

			if (index1 == index2)		//break the loop if both die comes to the same player.
				flag = false;

		}
		return count;
	}

	public static void main(String args[]) {
		Original o = new Original();
		
		/*
		 *  this part calculates the expected value of turns the game would last
		 *  by repeating it several times. the more number of iterations performed 
		 *  the less variation in the values but then it becomes too slow for more than 100000
		 */
		int i = 0;
		double sum = 0;
		double expected_value = 0;
		while (i < 100000) {
			i++;
			sum += o.one_game();
		}
		expected_value = sum / i;
		System.out.println(expected_value);
	}
}