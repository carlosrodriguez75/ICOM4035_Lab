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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.SLLQueue;
import exceptions.IllegalStateEXception;

public class FCFS {

	private static SLLQueue<Job> terminatedJobsAvg = new SLLQueue<Job>();
	private static SLLQueue<Job> nonCompleted = new SLLQueue<Job>();
	private static SLLQueue<Job> processingQueue = new SLLQueue<Job>();
	private static SLLQueue<Job> terminatedJobs = new SLLQueue<Job>();
	private static SLLQueue<Job> inputQueue = new SLLQueue<Job>();

	private static double sum = 0, currentSize = 0;
	private static Job temp;
	//public static int time = 0;
	private static int arrival, pid, burst, patience ;
	private static String cost;
	private static int counterPatience = 0;

	public FCFS(SLLQueue<Job> inputQueue, SLLQueue<Job> processingQueue, SLLQueue<Job> terminatedJobs, SLLQueue<Job> terminatedJobsAvg, SLLQueue<Job> nonCompleted){
		this.inputQueue = inputQueue;
		this.processingQueue = processingQueue;
		this.terminatedJobs = terminatedJobs;
		this.terminatedJobsAvg = terminatedJobsAvg;
		this.nonCompleted = nonCompleted;

	}

	public static void processFCFS(SLLQueue<Job> inputQueue){
		int time = 0;
		while(!inputQueue.isEmpty() || !processingQueue.isEmpty()){
			if(!processingQueue.isEmpty()){
				processingQueue.first().reduceBurst();
				if(processingQueue.first().getBurst() == 0){ //remaining time of job is 0?
					temp = processingQueue.dequeue();		 
					temp.setDepartureTime(time);//set departure time to t
					terminatedJobs.enqueue(temp);//remove from processing and add to terminated
					terminatedJobsAvg.enqueue(temp);
					if(!processingQueue.isEmpty())
						while(processingQueue.first() != null &&  processingQueue.first().getPatience()<time){
							nonCompleted.enqueue(processingQueue.dequeue());					
						}
				}
			}

			if(!inputQueue.isEmpty()){
				while(inputQueue.first()!= null && time == inputQueue.first().getArrival() ){
					processingQueue.enqueue(inputQueue.dequeue());//remove job from inputQueue and place at processingQueue
				}
			}

			time++;
		}
	}
	
	

	public static SLLQueue<Job> fileData(SLLQueue<Job> inputQueue, String fileName ) throws FileNotFoundException{

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
				inputQueue.enqueue(job);
				// read next line before looping
				//if end of file reached, line would be null
				line = br.readLine();
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return inputQueue;
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
	public static double retAvgTime(SLLQueue<Job> job){
		currentSize = job.size();
		while(!job.isEmpty()){
			sum = sum + job.first().getTotalTime();
			job.dequeue();
		}
		return sum/currentSize;
	}

	/*
	 * MAX PROFIT
	 * Calculate the max profit earned of the terminated jobs
	 */
	public static double maxProfit(SLLQueue<Job> job){
		currentSize=job.size();
		DecimalFormat df2 = new DecimalFormat("##.##");
		double sumProfit = 0;
		while(!job.isEmpty()){ 
			String tempCost = job.first().getCost();
			tempCost = tempCost.replace("$", "");
			sumProfit = sumProfit + Double.parseDouble(tempCost);
			job.dequeue();	
		}
		return Double.valueOf(df2.format(sumProfit));
	}
	public static int nonCompleted(SLLQueue<Job> job){
		int nonCompleted = 0;
		while(!job.isEmpty()){
			nonCompleted++;
			job.dequeue();
		}
		return nonCompleted;
	}

}
