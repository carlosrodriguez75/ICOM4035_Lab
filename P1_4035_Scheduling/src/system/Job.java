package system;

import java.text.DecimalFormat;

import classes.LinkedPositionalList;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;
import interfaces.Position;
/**
 * Created to manage each Job created 
 * The Job can be created in a Queue, List, Stack
 * Contain all the data of a Job
 * Arrival Time, Pid, Burst or Preparation Time, Patience and Departure Time
 * Also will contain getters and setter for the data of each Job
 * @author CarlosAlberto
 *
 * @param <E>
 */
public class Job<E> {
	
	

	private int arrivalTime, pid, burstTime, patience,departureTime;
	private String cost;
	
	
	//arrival,id,burstTime,Cost,patience
	
	public Job(int arrival, int pid, int burst, String cost, int patience){
	this.arrivalTime = arrival;
	this.pid = pid;
	this.burstTime = burst;
	this.cost=cost;
	this.patience=patience;		
	}
	
	/*
	 * Getters
	 */	
	/**
	 * 
	 * @return Burst time of the Job
	 */
	public int getBurst(){
		return burstTime;
	}
	/**
	 * 
	 * @return Arrival time of the Job
	 */
	public int getArrival(){
		return arrivalTime;
	}
	/**
	 * 
	 * @return Departure time of the Job 
	 */
	public int getDeparture(){
		return departureTime;
	}
	/**
	 * 
	 * @return Execution time of the Job
	 */
	public int getTotalTime(){
		return this.departureTime-this.arrivalTime;
	}
	/**
	 * 
	 * @return Patience of the Job
	 */
	public int getPatience(){
		return this.patience + this.getArrival();
	}
	/**
	 * 
	 * @return Cost of the current Job
	 */
	public String getCost(){
		return this.cost;
	}
	
	
	/*
	 * Setters
	 */
	/**
	 * Set a new burst value for a Job 
	 * @param newValue
	 */
	public void setBurst(int newValue){
		burstTime = newValue;
	}
	/**
	 * Set the departure time for a finished job
	 * @param time
	 */
	public void setDepartureTime(int time){
		this.departureTime = time;
	}
	/**
	 * Set a new level of patience for a Job
	 * @param newValue
	 */
	public void setPatience(int newValue){
		this.patience = this.patience - 1;
	}
	
	/*
	 * Reducing
	 */
	/**
	 * Will reduce the Burst or Preparation Time 
	 * Used after a complete turn
	 */
	public void reduceBurst(){
		burstTime--;
	}
	/**
	 * Reduce the Patience of the specified Job
	 */
	public void reducePatience(){
		patience--;
	}

	/**
	 * Count the Maximum profit of a Queue of jobs
	 * @param tempQueue1
	 * @return
	 */
	public static double countMaxProfit(SLLQueue<Job> tempQueue1) {
		int currentSize = tempQueue1.size();
		DecimalFormat df2 = new DecimalFormat("##.##");
		double sumProfit = 0;
		while(!tempQueue1.isEmpty()){ 
			String tempCost = tempQueue1.first().getCost();
			tempCost = tempCost.replace("$", "");
			sumProfit = sumProfit + Double.parseDouble(tempCost);
			tempQueue1.dequeue();
		}
		
		
		return Double.valueOf(df2.format(sumProfit));
	}
 /**
  * Count the total of Customers in a Queue of Jobs
  * @param tempQueue2
  * @return
  */
	public static int countCustomers(SLLQueue<Job> tempQueue2) {
	
		int currentSize = tempQueue2.size();
		int customers = 0;
		while(!tempQueue2.isEmpty()){
			tempQueue2.dequeue();
			customers++;
		}
		return customers;
	}





	

	

	
	
	
	

}
