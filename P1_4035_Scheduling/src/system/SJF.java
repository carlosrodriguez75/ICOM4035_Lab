package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;


import classes.LLStack;
import classes.LinkedPositionalList;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;
import interfaces.Position;

/**
 * Class used to creates the process of scheduling Shortest Job First
 * Max approach
 * @author CarlosAlberto
 *
 */
public class SJF {

	private static LinkedPositionalList<Job> inputList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> processingList = new LinkedPositionalList<Job>();
	//private static LinkedPositionalList<Job> preProcessingList = new LinkedPositionalList<Job>();
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
	public static int verifyIndex = 0;
	private static boolean verify = false;


	public SJF(LinkedPositionalList<Job> inputList, LinkedPositionalList<Job> processingList, LinkedPositionalList<Job> terminatedJobs, LinkedPositionalList<Job> terminatedJobsAvg, LinkedPositionalList<Job> nonCompleted){
		this.inputList = inputList;
		this.processingList = processingList;
		this.terminatedJobs = terminatedJobs;
		this.terminatedJobsAvg = terminatedJobsAvg;
		this.nonCompleted = nonCompleted;

	}
	/**
	 * process a Linked Positional List to schedule the jobs by Shortest Job First
	 * @param inputList: new List with all the Jobs to be executed Shortest Job First
	 */
	public static void processSJF(LinkedPositionalList<Job> inputList) throws IllegalArgumentException, IllegalStateEXception{
		while(!inputList.isEmpty() || !processingList.isEmpty()){
			if(!processingList.isEmpty()){
				checkBurst();	
				checkPatience();
				while(verify == true){
					checkBurst();
					checkPatience();
				}
				
				if(processingList.first() != null){
				executeProcessingList();}
	
			}
			if(!inputList.isEmpty()){
				while(time != counterPatience && counterPatience>time){
					while(inputList.size() != 0 && time == inputList.first().getElement().getArrival()){
						processingList.addFirst(inputList.remove(inputList.first()));
						while(inputList.first()!=null && inputList.first().getElement().getArrival() == time){
							checkIndex(); //Verify if exist other with the same arrival
						}
						
						if(time!=counterPatience){
							time++;
						}
					}
					if(time!=counterPatience){
						time++;
					}
				}
					
					while(inputList.size() != 0 && time == inputList.first().getElement().getArrival()){
						processingList.addFirst(inputList.remove(inputList.first()));}
					
					
				}
				time++;	
			}
			
			time = 0;
			counterPatience = 0;
		}
	/**
	 * Verify if the list contain another job with the same arrival
	 * @throws IllegalStateEXception
	 */
		private static void checkIndex() throws IllegalStateEXception {
			if(inputList.first()!=null)
			 verifyIndex = inputList.first().getElement().getArrival();
			if(verifyIndex == time){
				processingList.addFirst(inputList.remove(inputList.first()));
			}
			
		}
		/**
		 * Put First in the processingList the Job with the biggest Arrival
		 * @throws IllegalStateEXception
		 */
	private static void checkArrival() throws IllegalStateEXception {
		int counter = 0;
		verify = false;
		while(counter != processingList.size() && processingList.first() != null){
			Position<Job> temp = processingList.after(processingList.first());
			counter++;
			if(temp!=null){
			if(processingList.first().getElement().getArrival()<temp.getElement().getArrival()){
				processingList.addFirst(processingList.remove(temp));
			}
			else
			processingList.addLast(processingList.remove(temp));
			
		}
		}
		
		
	}
	/**
	 * Verify the level patience of the Job which will be processed
	 * @throws IllegalStateEXception
	 */
	private static void checkPatience() throws IllegalStateEXception {
		//Verificar que la paciencia del first no se haya vencido
		if(processingList.first() != null){
			int temp = processingList.first().getElement().getPatience();
			if(counterPatience>temp){
				nonCompleted.addLast(processingList.remove(processingList.first()));
				verify = true;
		}
		}
		
	}
	/**
	 * Execute the Job until its preparation Time is 0
	 * 
	 * @throws IllegalStateEXception
	 */
	
	private static void executeProcessingList() throws IllegalStateEXception {
		
		if(counterPatience ==0){
			counterPatience = time -1;
		}
		while(processingList.first()!=null && processingList.first().getElement().getBurst() != 0 ){
			counterPatience++;
			processingList.first().getElement().reduceBurst();
		}
			if( processingList.first().getElement().getBurst() == 0){ // remaining time of job is 0?
				temp = processingList.remove(processingList.first());
				temp.setDepartureTime(counterPatience);
				terminatedJobs.addLast(temp);
				terminatedJobsAvg.addLast(temp);
			}
		}
	/**
	 * Will specify the first in the processingList which will be 
	 * the Job with the Shortest preparation Time
	 * @throws IllegalArgumentException
	 * @throws IllegalStateEXception
	 */

	private static  void checkBurst() throws IllegalArgumentException, IllegalStateEXception {
		int counter = 0;
		verify = false;
			while(counter != processingList.size()){
				Position<Job> temp = processingList.after(processingList.first());
				if(temp!=null){
					if(processingList.first().getElement().getBurst() > temp.getElement().getBurst()){
					processingList.addFirst(processingList.remove(temp));
					//temp = preProcessingList.after(preProcessingList.first());
					}
					
					else if(processingList.first().getElement().getBurst() == temp.getElement().getBurst()){
						if(processingList.first().getElement().getArrival()<temp.getElement().getArrival()){
							processingList.addLast(processingList.remove(temp));
						}
						else if(processingList.first().getElement().getArrival()>temp.getElement().getArrival()){
							processingList.addFirst(processingList.remove(temp));
						}
					}
					else
						processingList.addLast(processingList.remove(temp));
				//temp = preProcessingList.after(preProcessingList.first());
			}	
			counter ++;}		
	}
	
	/**
	 * Will read the specified .csv file and store the vlaues in a Queue
	 * @param inputList used to store the values 
	 * @param fileName string acquired from the .txt file 
	 * @return new List with all the Jobs to process
	 * @throws FileNotFoundException
	 */
public static LinkedPositionalList<Job>  fileData(LinkedPositionalList<Job> inputList, String fileName ) throws FileNotFoundException{

		Path pathToFile = Paths.get(fileName);
		//		 // create an instance of BufferedReader
		//		// using try with resource, Java 7 feature to close resources
		try (BufferedReader br = Files.newBufferedReader(pathToFile,
				StandardCharsets.US_ASCII)) {

			//read the first line from the text file 
			String line = br.readLine();
			//			// loop until all lines are read 
			while (line != null) { 
				// use string.split to load a string array with the values from 
				//each line of 
				//the file, using a comma as the delimiter		   
				String[] attributes = line.split(",");
				Job job = createJob(attributes);
				// adding book into ArrayList
				inputList.addLast(job);
				// read next line before looping
				//if end of file reached, line would be null
				line = br.readLine();
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return inputList;
	}

/**
 * Will create a new Object of type Job with arrival, pid, burst, cost and patience of the Job
 * @param metadata attributes of the specified Job
 * @return
 */
	private static Job createJob(String[] metadata) {
		 arrival =Integer.parseInt(metadata[0]);
		 pid = Integer.parseInt(metadata[1]);
		 burst=Integer.parseInt(metadata[2]);
		 cost = metadata[3];
		 patience = Integer.parseInt(metadata[4]);
		 
		// create and return book of this metadata
		 return new Job(arrival, pid, burst, cost, patience); 

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
		DecimalFormat df2 = new DecimalFormat("##.##");
		while(!job.isEmpty()){ 
			String tempCost = job.first().getElement().getCost();
			tempCost = tempCost.replace("$", "");
			sumProfit = sumProfit + Double.parseDouble(tempCost);
			job.remove(job.first());	
		}
		return Double.valueOf(df2.format(sumProfit));
	}
	
	/**
	 * Used to calculate how many have not been completed from the List.
	 * @param job
	 * @return number of non completed Jobs of the List
	 */
	public static int nonCompleted(LinkedPositionalList<Job> job){
		int nonCompleted = 0;
		while(!job.isEmpty()){
			nonCompleted++;
			job.remove(job.first());
		}
		return nonCompleted;
	}

}





