package orderedStructures;

public class Geometric extends Progression {

	private double commonFactor; 
	
	public Geometric(double firstValue, double commonFactor) { 
		super(firstValue); 
		this.commonFactor = commonFactor; 
		
	}
	
	@Override
	public double nextValue() {
		if(isCalled == false){
			 throw new IllegalStateException("firstValue method has not been previously executed");
		}
		current = current * commonFactor; 
		return current;
	
		
		
	}
	
	public String toString(){
		
		return "Geom (" + (int)this.firstValue() + ", " + (int) this.commonFactor + ")";
		
	}
	
	/**
	 * @param n
	 * returns the desired parameter n of te sequence
	 */
	public double getTerm(int n) { 
		
			double term = firstValue()*Math.pow(commonFactor,n-1);
			return term;
	}


}
