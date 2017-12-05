package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import classes.AbstractPriorityQueue;
import classes.ArrayBasedStack;
import classes.LLStack;
import classes.LinkedPositionalList;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;
import interfaces.Entry;
import interfaces.PositionalList;

public class LCFS{


	
	LinkedPositionalList<Job> inputList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> processingList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobs = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvg = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompleted = new LinkedPositionalList<Job>();
	private static Job temp;
	
	private static double sum = 0;
	public static int currentSize = 0;

	public static int time = 0;
	private static int arrival, pid, burst, patience ;
	private static String cost;
	
	public LCFS(LinkedPositionalList<Job> inputList, LinkedPositionalList<Job> processingList, LinkedPositionalList<Job> terminatedJobs, LinkedPositionalList<Job> terminatedJobsAvg, LinkedPositionalList<Job> nonCompleted){
		this.inputList = inputList;
		this.processingList = processingList;
		this.terminatedJobs = terminatedJobs;
		this.terminatedJobsAvg = terminatedJobsAvg;
		this.nonCompleted = nonCompleted;
		
	}
	
	public static void processLCFS(LinkedPositionalList<Job> inputList) throws IllegalStateEXception{

		while(!inputList.isEmpty() || !processingList.isEmpty()){
			if(!processingList.isEmpty()){
				if(processingList.after(processingList.first()) != null && processingList.first().getElement().getArrival() == processingList.after(processingList.first()).getElement().getArrival() ){
					processingList.addLast(processingList.remove(processingList.first()));
					processingList.first().getElement().reduceBurst();
				}

				else
						processingList.first().getElement().reduceBurst();
			if( processingList.first().getElement().getBurst() == 0){ // remaining time of job is 0?
				temp = processingList.remove(processingList.first());
				temp.setDepartureTime(time);
				terminatedJobs.addLast(temp);
				terminatedJobsAvg.addLast(temp);
				if(processingList.size() !=0){
					while(processingList.size() != 0 && processingList.first().getElement().getPatience()<time ){
						nonCompleted.addLast(processingList.remove(processingList.first()));
					}
				}
			}

			}
			
			if(!inputList.isEmpty()){

				while(inputList.size() != 0 && time ==  inputList.first().getElement().getArrival()){
					if(processingList.size() != 0  &&  inputList.first().getElement().getArrival() > processingList.first().getElement().getArrival() ){
						processingList.addFirst(inputList.remove(inputList.first()));
						
					}
					else if(processingList.size() != 0 &&inputList.first().getElement().getArrival() < processingList.first().getElement().getArrival()){
						processingList.addLast(inputList.remove(inputList.first())) ;
					}
					
					else if(processingList.size() == 0){
						processingList.addFirst(inputList.remove(inputList.first()));
					}
					else processingList.addAfter(processingList.first(), (inputList.remove(inputList.first())));
				}
			}
			
			time++;

			
		}


	}
	
public static void fileData(String input, LinkedPositionalList<Job> inputList ) throws FileNotFoundException{
	
	@SuppressWarnings("resource")
	Scanner read = new Scanner(new File(input));	
	while (read.hasNext())		
	{
		String[] ar=read.nextLine().split(", ");
		arrival=Integer.parseInt(ar[0]);
		pid=Integer.parseInt(ar[1]); 
		burst = Integer.parseInt(ar[2]);
		cost = ar[3];
		patience = Integer.parseInt(ar[4]);
		temp = new Job(arrival, pid, burst, cost, patience);  		
		inputList.addLast(temp);

	}
}

/*
 * Will return the Average Time execution of the Queue
 */
public static double retAvgTime(LinkedPositionalList<Job> job) throws IllegalStateEXception{
	currentSize = job.size();
	while(!job.isEmpty()){
		sum = sum +  job.first().getElement().getTotalTime();
		job.remove(job.first());
	}
	return sum/currentSize;
}

/*
 * MAX PROFIT
 * Calculate the max profit earned of the terminated jobs
 */
public static double maxProfit(LinkedPositionalList<Job> job) throws IllegalStateEXception{
	currentSize=job.size();
	double sumProfit = 0;
	while(!job.isEmpty()){ 
		String tempCost = job.first().getElement().getCost();
		tempCost = tempCost.replace("$", "");
		sumProfit = sumProfit + Double.parseDouble(tempCost);
		job.remove(job.first());	
	}
	return sumProfit;
}
public static int nonCompleted(LinkedPositionalList<Job> job){
	int nonCompleted = 0;
	while(!job.isEmpty()){
		nonCompleted++;
		job.remove(job.first());
	}
	return nonCompleted;
}

}

