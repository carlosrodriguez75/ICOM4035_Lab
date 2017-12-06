package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import classes.LinkedPositionalList;
import exceptions.IllegalStateEXception;
import interfaces.Position;

public class MPF {

	private static LinkedPositionalList<Job> inputList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> processingList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobs = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvg = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompleted = new LinkedPositionalList<Job>();
	private static Job temp;

	private static double sum = 0;
	public static int currentSize = 0;

	public static int time = 0;
	private static int arrival, pid, burst, patience ;
	private static int counterPatience = 0;
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
			while(!processingList.isEmpty()){
				executeProcessingList();

			}

			if(!inputList.isEmpty()){
				if(counterPatience == 0){
					while(inputList.size() != 0 && time ==  inputList.first().getElement().getArrival()){
						processingList.addFirst(inputList.remove(inputList.first()));
					}

				}

				while(inputList.size() != 0 && time ==  inputList.first().getElement().getArrival()){
					executeProcessingList();
					checkPatience();
					checkCost();
					processingList.addFirst(inputList.remove(inputList.first()));
				}
			}			
			time++;

		}
	}
	private static void executeProcessingList() throws IllegalStateEXception {
		while(!processingList.isEmpty()){
			counterPatience++;
			processingList.first().getElement().reduceBurst();
			if( processingList.first().getElement().getBurst() == 0){ // remaining time of job is 0?
				temp = processingList.remove(processingList.first());
				temp.setDepartureTime(counterPatience);
				terminatedJobs.addLast(temp);
				terminatedJobsAvg.addLast(temp);
				currentArrival = temp.getArrival();
			}
		}
		
	}

	private static void checkPatience() throws IllegalStateEXception {
		int counter = 0;
		while(counter != inputList.size()){
			counter++;
			if(inputList.first().getElement().getPatience()<counterPatience){
				nonCompleted.addLast(inputList.remove(inputList.first()));
			}
			else inputList.addLast(inputList.remove(inputList.first()));
		}

	}
	private static void checkCost() throws IllegalStateEXception {
		int counter = 0;
		Position<Job> temp = inputList.after(inputList.first());
		while(temp != null && counter != inputList.size()){
			counter++;
			String currentCost = inputList.first().getElement().getCost();
			String compareCost = temp.getElement().getCost();
			currentCost = currentCost.replace("$", "");
			compareCost = compareCost.replace("$","");

			if(Double.parseDouble(compareCost) > Double.parseDouble(currentCost) ){
				if(temp.getElement().getArrival() == time){
				inputList.addFirst(inputList.remove(temp));
				temp = inputList.after(inputList.first());
				}
				temp = inputList.after(inputList.after(inputList.first()));
			}

			else if(Double.parseDouble(compareCost) == Double.parseDouble(currentCost)){
				if(inputList.first().getElement().getArrival()>temp.getElement().getArrival()){
					inputList.addLast(inputList.remove(temp));
					temp = inputList.after(inputList.first());	
				}

				else
					inputList.addFirst(inputList.remove(temp));
				temp = inputList.after(inputList.first());
			}

			else{ 
				inputList.addLast(inputList.remove(temp));
				temp = inputList.after(inputList.first());

			}
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

