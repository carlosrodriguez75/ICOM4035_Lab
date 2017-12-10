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

import classes.AbstractPriorityQueue;
import classes.ArrayBasedStack;
import classes.LLStack;
import classes.LinkedPositionalList;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;
import interfaces.Entry;
import interfaces.Position;
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
	public static int counterPatience = 0;
	private static int arrival, pid, burst, patience ;
	private static String cost;
	private static boolean verify = false;
	
	public LCFS(LinkedPositionalList<Job> inputList, LinkedPositionalList<Job> processingList, LinkedPositionalList<Job> terminatedJobs, LinkedPositionalList<Job> terminatedJobsAvg, LinkedPositionalList<Job> nonCompleted){
		this.inputList = inputList;
		this.processingList = processingList;
		this.terminatedJobs = terminatedJobs;
		this.terminatedJobsAvg = terminatedJobsAvg;
		this.nonCompleted = nonCompleted;
		
	}
	
	public static void processLCFS(LinkedPositionalList<Job> inputList) throws IllegalArgumentException, IllegalStateEXception{
		while(!inputList.isEmpty() || !processingList.isEmpty()){
			if(!processingList.isEmpty()){
				checkArrival();	
				checkPatience();
				while(verify == true){
					checkArrival();
					checkPatience();
				}
				
				if(processingList.first() != null){
				executeProcessingList();}
	
			}
			if(!inputList.isEmpty()){
				if(time != counterPatience && counterPatience>time){
				while(inputList.size() != 0 && time == inputList.first().getElement().getArrival()){
					processingList.addFirst(inputList.remove(inputList.first()));
					if(time!=counterPatience){
						time++;
					}
				}
			}
				else
				while(inputList.size() != 0 && time == inputList.first().getElement().getArrival()){
					processingList.addFirst(inputList.remove(inputList.first()));}
				
				
			}
			time++;
			//counterPatience++;	
		}
		
		time = 0;
		counterPatience = 0;
	}
	
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

	private static void checkPatience() throws IllegalStateEXception {
		//Verificar que la paciencia del first no se haya vencido
		if(processingList.first() != null && processingList.first().getElement().getPatience()<counterPatience){
			nonCompleted.addLast(processingList.remove(processingList.first()));
			verify = true;
		}
		
	}
	
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
public static int nonCompleted(LinkedPositionalList<Job> job){
	int nonCompleted = 0;
	while(!job.isEmpty()){
		nonCompleted++;
		job.remove(job.first());
	}
	return nonCompleted;
}

}
