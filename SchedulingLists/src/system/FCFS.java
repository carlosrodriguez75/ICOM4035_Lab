package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import classes.SLLQueue;

public class FCFS {
	
	private static SLLQueue<Job> terminatedJobsAvg = new SLLQueue<Job>();
	private static SLLQueue<Job> nonCompleted = new SLLQueue<Job>();
	private static SLLQueue<Job> processingQueue = new SLLQueue<Job>();
	private static SLLQueue<Job> terminatedJobs = new SLLQueue<Job>();
	private static SLLQueue<Job> inputQueue = new SLLQueue<Job>();
	
	private static double sum = 0, currentSize = 0;
	private static Job temp;
	public static int time = 0;
	private static int arrival, pid, burst, patience ;
	private static String cost;
	
	public FCFS(SLLQueue<Job> inputQueue, SLLQueue<Job> processingQueue, SLLQueue<Job> terminatedJobs, SLLQueue<Job> terminatedJobsAvg, SLLQueue<Job> nonCompleted){
		this.inputQueue = inputQueue;
		this.processingQueue = processingQueue;
		this.terminatedJobs = terminatedJobs;
		this.terminatedJobsAvg = terminatedJobsAvg;
		this.nonCompleted = nonCompleted;
		
	}
	
	public static void processFCFS(SLLQueue<Job> inputQueue){
		while(!inputQueue.isEmpty() || !processingQueue.isEmpty()){
			if(!processingQueue.isEmpty()){
				processingQueue.first().reduceBurst();
				if(processingQueue.first().getBurst() == 0){ //remaining time of job is 0?
					temp = processingQueue.dequeue();		 
					temp.setDepartureTime(time);//set departure time to t
					terminatedJobs.enqueue(temp);//remove from processing and add to terminated
					terminatedJobsAvg.enqueue(temp);
					if(!processingQueue.isEmpty())
						while(processingQueue.first().getPatience()<time){
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
	
	public static void fileData(String input, SLLQueue<Job> inputQueue ) throws FileNotFoundException{
		
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
			inputQueue.enqueue(temp);

		}
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
		double sumProfit = 0;
		while(!job.isEmpty()){ 
			String tempCost = job.first().getCost();
			tempCost = tempCost.replace("$", "");
			sumProfit = sumProfit + Double.parseDouble(tempCost);
			job.dequeue();	
		}
		return sumProfit;
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
