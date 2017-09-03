package TesterClasses;
//Implement a second tester: ProgressionTester2 – the tester 
//should be able to declare a variable of type Progression and 
//output the sum of the first n terms for any value of n that is greater than zero.  

import orderedStructures.Arithmetic;
import orderedStructures.Fibonacci;
import orderedStructures.Geometric;
import orderedStructures.Progression;

public class ProgressionTester2 {

	public static void main(String[] args) { 
		Progression p = new Arithmetic(3, 2); 
	//	p.nextValue();
		// outputs the sum of first 5 terms in p
		printSumOfTerms(p, 5); 

		p = new Geometric(3, 2); 
		printSumOfTerms(p, 5); 

		p = new Fibonacci(); 
		printGolden(p,20);
	} 

	private static void printGolden(Progression p, int n) {
		double golden = p.firstValue();
		if(n>0){
			for(int i=1; i<=n; i++){
				golden = p.nextValue()/p.getTerm(i);
				System.out.println("Golden Ratio: " + golden);
			}
			
		}
		
	}

	/** Prints the sum of the first terms in a 
	    	      progression. 
			@param p the progression
			@param n the number of terms to consider
	 **/ 
	private static void printSumOfTerms(Progression p, int n) 
	{ 		
		// pre: n is valid
		//... add code to compute, and assign to sum, the sum
		// of the first n terms in p
		
		double sum = p.firstValue();
		if(n>0){
			for(int i=1; i<=n-1; i++){
				sum += p.nextValue();
			}
		    
		System.out.println("Sum of first " + n + " terms in " 
				+ p + " is: " + sum ); 
	}
		
		
	


}
}
