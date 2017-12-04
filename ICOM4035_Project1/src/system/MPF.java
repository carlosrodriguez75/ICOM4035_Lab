package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import classes.LinkedPositionalList;
import exceptions.IllegalStateEXception;
import interfaces.Position;

public class MPF {

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

	private static int currentArrival = 0;



	public MPF(LinkedPositionalList<Job> inputList, LinkedPositionalList<Job> processingList, LinkedPositionalList<Job> terminatedJobs, LinkedPositionalList<Job> terminatedJobsAvg, LinkedPositionalList<Job> nonCompleted){
		this.inputList = inputList;
		this.processingList = processingList;
		this.terminatedJobs = terminatedJobs;
		this.terminatedJobsAvg = terminatedJobsAvg;
		this.nonCompleted = nonCompleted;

	}
	
	public static void processMPF(LinkedPositionalList<Job> inputList) throws IllegalStateEXception{
		while(!inputList.isEmpty() || !processingList.isEmpty()){
			if(!processingList.isEmpty()){
				processingList.addFirst(processingList.remove(checkCost(processingList.first())));
				System.out.println(processingList.first().getElement().getBurst() + " Burst");
				System.out.println(processingList.first().getElement().getArrival() + " Arrival");
				System.out.println(processingList.first().getElement().getPatience() + " Patience");			
				processingList.first().getElement().reduceBurst();
				if( processingList.first().getElement().getBurst() == 0){ // remaining time of job is 0?
					temp = processingList.remove(processingList.first());
					temp.setDepartureTime(time);
					terminatedJobs.addLast(temp);
					terminatedJobsAvg.addLast(temp);
					currentArrival = temp.getArrival();
//					if(processingList.size() !=0){
//						while(processingList.size() != 0 && processingList.first().getElement().getPatience()<time ){
//							nonCompleted.addLast(processingList.remove(processingList.first()));
//						}
//					}
				}

			}

			if(!inputList.isEmpty()){

				while(inputList.size() != 0 && time ==  inputList.first().getElement().getArrival()){
						processingList.addFirst(inputList.remove(inputList.first()));
				}
			}			
			time++;

		}
	}
	
	private static Position<Job> checkCost(Position<Job> first) throws IllegalArgumentException, IllegalStateEXception{
		//Position<Job> temp = processingList.after(processingList.first());
		int counter = 0;
		int counterPatience = 0;
		while(counterPatience != processingList.size()){
			counterPatience++;
			processingList.first().getElement().setPatience(1);
			if(processingList.first().getElement().getPatience()<=0){
				nonCompleted.addLast(processingList.remove(processingList.first()));
			}
			processingList.addLast(processingList.remove(processingList.first()));
			
		}
		
		Position<Job> temp = processingList.after(processingList.first());
		if(temp != null){
			while(counter != processingList.size()){
				counter++;
				String currentCost = processingList.first().getElement().getCost();
				String compareCost = temp.getElement().getCost();
				currentCost = currentCost.replace("$", "");
				compareCost = compareCost.replace("$","");
				
				if(Double.parseDouble(compareCost) > Double.parseDouble(currentCost) ){
					processingList.addFirst(processingList.remove(temp));
					temp = processingList.after(processingList.first());
				}
				
				else if(Double.parseDouble(compareCost) == Double.parseDouble(currentCost)){
					if(processingList.first().getElement().getArrival()>temp.getElement().getArrival()){
						processingList.addLast(processingList.remove(temp));
						temp = processingList.after(processingList.first());
					}
					processingList.addFirst(processingList.remove(temp));
					temp = processingList.after(processingList.first());
					
				}
				processingList.addLast(processingList.remove(temp));
				temp = processingList.after(processingList.first());
			}
			return processingList.first();
		}
		 return processingList.first();
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

