package system;

import classes.LinkedPositionalList;
import exceptions.IllegalStateEXception;
import interfaces.Position;

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
	public int getBurst(){
		return burstTime;
	}
	
	public int getArrival(){
		return arrivalTime;
	}
	
	public int getDeparture(){
		return departureTime;
	}
	
	public int getTotalTime(){
		return this.departureTime-this.arrivalTime;
	}
	public int getPatience(){
		return this.patience + this.getArrival();
	}
	public String getCost(){
		return this.cost;
	}
	
	
	/*
	 * Setters
	 */
	public void setBurst(int newValue){
		burstTime = newValue;
	}
	
	public void setDepartureTime(int time){
		this.departureTime = time;
	}
	public void setPatience(int newValue){
		this.patience = this.patience - 1;
	}
	
	/*
	 * Reducing
	 */
	
	public void reduceBurst(){
		burstTime--;
	}
	
	public void reducePatience(){
		patience--;
	}





	

	

	
	
	
	

}
