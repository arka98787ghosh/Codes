//creates the Di string for upto D25;after that it exceed the capacity of stringbuilder and 
//stringbuffer and it becomes too slow to use string

public class Dss {
	public static void main(String args[]) {
		
	}
	public String generate(){
		StringBuffer D0 = new StringBuffer("Fa");
		StringBuffer a = new StringBuffer("aRbFR");
		StringBuffer b = new StringBuffer("LFaLb");
		StringBuffer Di = D0;
		for (int i = 1; i <= 24; i++) { // have to go upto 42
			char[] charray = Di.toString().toCharArray();
			Di= new StringBuffer("");
			for (int j = 0; j < charray.length; j++) {
				if (charray[j] == 'a') {
					Di.append(a);
				} else if (charray[j] == 'b') {
					Di.append(b);
				} else {
					Di.append(charray[j]);
				}
			}
		}
		return Di.toString();
	}
}
