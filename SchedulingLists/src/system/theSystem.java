package system;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

import classes.ArrayQueue;
import classes.LLStack;
import classes.LinkedPositionalList;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;
import interfaces.Entry;
import interfaces.PositionalList;
import system.Job;

public class theSystem {  

	private static Job temp;	
	private static FCFS temp1Job;
	private static LCFS temp2Job;
	private static SJF temp3Job;
	private static MPF temp4Job;
	
	/* FCFS*/	
	private static SLLQueue<Job> terminatedJobsAvg = new SLLQueue<Job>();
	private static SLLQueue<Job> nonCompleted = new SLLQueue<Job>();
	private static SLLQueue<Job> processingQueue = new SLLQueue<Job>();
	private static SLLQueue<Job> terminatedJobs = new SLLQueue<Job>();
	/*LCFS*/
	private static LinkedPositionalList<Job> processingList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsLCFS = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvgLCFS = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompletedLCFS = new LinkedPositionalList<Job>();
	/*MPF*/
	private static LinkedPositionalList<Job> processingListSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvgSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompletedSJF = new LinkedPositionalList<Job>();
	/*MPF*/
	private static LinkedPositionalList<Job> processingListMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvgMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompletedMPF = new LinkedPositionalList<Job>();
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IllegalStateEXception  {  
		

		SLLQueue<Job> inputQueue = new SLLQueue<Job>();
		LinkedPositionalList<Job> inputList = new LinkedPositionalList<Job>();
		LinkedPositionalList<Job> inputListSJF = new LinkedPositionalList<Job>();
		LinkedPositionalList<Job> inputListMPF = new LinkedPositionalList<Job>();
		
		FCFS.fileData("Data.txt", inputQueue);
		LCFS.fileData("Data.txt", inputList);
		SJF.fileData("Data.txt", inputListSJF);
		MPF.fileData("Data.txt", inputListMPF);
		
		temp1Job = new FCFS(inputQueue,processingQueue,terminatedJobs,terminatedJobsAvg,nonCompleted);
		temp2Job = new LCFS(inputList,processingList,terminatedJobsLCFS,terminatedJobsAvgLCFS,nonCompletedLCFS);
		temp3Job = new SJF(inputListSJF,processingListSJF,terminatedJobsSJF,terminatedJobsAvgSJF,nonCompletedSJF);
		temp4Job = new MPF(inputListMPF,processingListMPF,terminatedJobsMPF,terminatedJobsAvgMPF,nonCompletedMPF);
		
		
		/*FCFS*/
		FCFS.processFCFS(inputQueue);
		System.out.println("Pat’s approach profit: $" + FCFS.maxProfit(terminatedJobs) );	//MAX PROFIT
		System.out.println("Pat’s approach number of disappointed customers: " + FCFS.nonCompleted(nonCompleted) );//DISSAPOINTED CUSTOMERS
		System.out.println("Average Time in Pat's approach: " + FCFS.retAvgTime(terminatedJobsAvg) );	//AVERAGE TIME
		/*LCFS*/
		LCFS.processLCFS(inputList);
		System.out.println("Mat’s approach profit: $" + LCFS.maxProfit(terminatedJobsLCFS) );	//MAX PROFIT
		System.out.println("Mat’s approach number of disappointed customers: " + LCFS.nonCompleted(nonCompletedLCFS) );//DISSAPOINTED CUSTOMERS
		System.out.println("Average Time in Mat's approach: " + LCFS.retAvgTime(terminatedJobsAvgLCFS) );
		/*MPF*/
		MPF.processMPF(inputListMPF);
		System.out.println("Max’s approach profit: $" + MPF.maxProfit(terminatedJobsMPF) );	//MAX PROFIT
		System.out.println("Max’s approach number of disappointed customers: " + MPF.nonCompleted(nonCompletedMPF) );//DISSAPOINTED CUSTOMERS
		System.out.println("Average Time in Max's approach: " + MPF.retAvgTime(terminatedJobsAvgMPF) );	//AVERAGE TIME
		/*SJF*/
		SJF.processSJF(inputListSJF);
		System.out.println("Pac’s approach profit: $" + SJF.maxProfit(terminatedJobsSJF) );	//MAX PROFIT
		System.out.println("Pac’s approach number of disappointed customers: " + SJF.nonCompleted(nonCompletedSJF) );//DISSAPOINTED CUSTOMERS
		System.out.println("Average Time in Max's approach: " + SJF.retAvgTime(terminatedJobsAvgSJF) );	//AVERAGE TIME
		
		//System.out.printf(format, args)
	}
	

	
}












