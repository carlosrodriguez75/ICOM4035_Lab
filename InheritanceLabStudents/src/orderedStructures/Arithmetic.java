package orderedStructures;

public class Arithmetic extends Progression {
	private double commonDifference; 
	
	public Arithmetic(double firstValue, double commonDifference) { 
		super(firstValue); 
		this.commonDifference = commonDifference; 
	}
	
	@Override
	public double nextValue() {
		if(isCalled == false){
			 throw new IllegalStateException("firstValue method has not been previously executed");
		}
		current = current + commonDifference; 
		return current;
	}
	
	public String toString(){
		
		return "Arith (" + (int)this.firstValue() + ", " + (int) this.commonDifference + ")";
		
	}
	
	/**
	 * @param n
	 * returns the desired parameter n of the sequence
	 */
	public double getTerm(int n) {
	
		 double term = firstValue()+ commonDifference*(n-1);
		 return term;
	
}
	
	

}


