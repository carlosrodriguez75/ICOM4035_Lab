package orderedStructures;

public class Fibonacci extends Progression {

	private double prev; 
	//static final double golden_ratio = (1.0 + Math.sqrt(5.0))/2.0;

	public Fibonacci() { 
		this(1); 
		prev = 0; 
	}
	
	

	private Fibonacci(double first) {
		super(first);
		
	}
	

	@Override
	public double nextValue() {
        // add the necessary code here
		//...
		double temp = prev;
		prev = current;
		return current += temp;
	}
	
	
	@Override	
	public double firstValue() { 
		double value = super.firstValue(); 
		prev = 0; 
		return value; 
	}
	//BONO 1 CLOSED-FORM
	/**
	 * @param n
	 * returns the desired parameter n of the sequence
	 */
	public double getTerm(int n) {
	
		 double term = (1/Math.sqrt(5))*((Math.pow((1+Math.sqrt(5))/(2),n))-(Math.pow((1-Math.sqrt(5))/(2),n)));
		 return term;
	
	}
	

	
	

}






