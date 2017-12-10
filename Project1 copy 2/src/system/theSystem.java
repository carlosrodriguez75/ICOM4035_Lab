package system;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

import classes.ArrayQueue;
import classes.LinkedPositionalList;
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
	private static SLLQueue<Job> inputQueue = new SLLQueue<Job>();
	private static SLLQueue<Job> terminatedJobsAvg = new SLLQueue<Job>();
	private static SLLQueue<Job> nonCompleted = new SLLQueue<Job>();
	private static SLLQueue<Job> processingQueue = new SLLQueue<Job>();
	private static SLLQueue<Job> terminatedJobs = new SLLQueue<Job>();
	/*LCFS*/
	private static LinkedPositionalList<Job> inputList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> processingList = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsLCFS = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvgLCFS = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompletedLCFS = new LinkedPositionalList<Job>();
	/*SJF*/
	private static LinkedPositionalList<Job> inputListSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> processingListSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvgSJF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompletedSJF = new LinkedPositionalList<Job>();
	/*MPF*/
	private static LinkedPositionalList<Job> inputListMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> processingListMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> terminatedJobsAvgMPF = new LinkedPositionalList<Job>();
	private static LinkedPositionalList<Job> nonCompletedMPF = new LinkedPositionalList<Job>();
	
	private static SLLQueue<Job> tempQueue1 = new SLLQueue<Job>();
	private static SLLQueue<Job> tempQueue2 = new SLLQueue<Job>();
	private static SLLQueue<String> jobQueue = new SLLQueue<String>();
	private static SLLQueue<String> jobOutputQueue = new SLLQueue<String>();
	private static String outputName ;
	
	
	public static void main(String[] args) throws IllegalStateEXception, IOException  {  
		
		fileData(jobQueue,"input.txt");
		fileData(jobOutputQueue,"input.txt");
		
		while(jobQueue.first() != " " && !jobQueue.isEmpty()){
		
		FCFS.fileData(inputQueue,jobQueue.first());
		FCFS.fileData(tempQueue1, jobQueue.first());
		FCFS.fileData(tempQueue2, jobQueue.first());
		LCFS.fileData(inputList, jobQueue.first());
		SJF.fileData(inputListSJF, jobQueue.first());
		MPF.fileData(inputListMPF, jobQueue.first());
		

		
		double maxProfit = Job.countMaxProfit(tempQueue1);
		int maxCustomers = Job.countCustomers(tempQueue2);
		
		temp1Job = new FCFS(inputQueue,processingQueue,terminatedJobs,terminatedJobsAvg,nonCompleted);
		temp2Job = new LCFS(inputList,processingList,terminatedJobsLCFS,terminatedJobsAvgLCFS,nonCompletedLCFS);
		temp3Job = new SJF(inputListSJF,processingListSJF,terminatedJobsSJF,terminatedJobsAvgSJF,nonCompletedSJF);
		temp4Job = new MPF(inputListMPF,processingListMPF,terminatedJobsMPF,terminatedJobsAvgMPF,nonCompletedMPF);

		
		outputName = jobOutputQueue.dequeue();
		outputName = removeLastChar(outputName);
		outputName = removeLastChar(outputName);
		outputName = removeLastChar(outputName);
		outputName = removeLastChar(outputName);
		
	    String s = null ;

		/*Basic Information*/
	    s = "Maximum profit possible: $"+maxProfit +"\n";
	    byte data[] = s.getBytes(); 
	    dataOutputFile(data);
	    System.out.format("Maximum profit possible: $%.2f%n", maxProfit );
	    
	    s="Maximum number of customers served possible: " +  maxCustomers+ "\n";
		byte data2[] = s.getBytes();
		dataOutputFile(data2);
		System.out.println("Maximum number of customers served possible: " +  maxCustomers);
		
		
		/*FCFS*/
		FCFS.processFCFS(inputQueue);
		s= "Pat’s approach profit: $" + FCFS.maxProfit(terminatedJobs) + "\n";
		byte data3[] = s.getBytes();
		dataOutputFile(data3);
		System.out.format("Pat’s approach profit: $%.2f%n", FCFS.maxProfit(terminatedJobs) );	//MAX PROFIT
		
		s = "Pat’s approach number of disappointed customers: " + FCFS.nonCompleted(nonCompleted) + "\n";
		byte data4[] = s.getBytes();
		dataOutputFile(data4);
		System.out.println("Pat’s approach number of disappointed customers: " + FCFS.nonCompleted(nonCompleted) );//DISSAPOINTED CUSTOMERS
	

		/*LCFS*/
		LCFS.processLCFS(inputList);
		s="Mat’s approach profit: $"+ LCFS.maxProfit(terminatedJobsLCFS) + "\n";
		byte data5[] = s.getBytes();
		dataOutputFile(data5);
		System.out.format("Mat’s approach profit: $%.2f%n", LCFS.maxProfit(terminatedJobsLCFS) );	//MAX PROFIT
		
		s="Mat’s approach number of disappointed customers: " + LCFS.nonCompleted(nonCompletedLCFS) +"\n";
		byte data6[] = s.getBytes();
		dataOutputFile(data6);
		System.out.println("Mat’s approach number of disappointed customers: " + LCFS.nonCompleted(nonCompletedLCFS) );//DISSAPOINTED CUSTOMERS

		
		/*MPF*/
		MPF.processMPF(inputListMPF);
		s="Max’s approach profit: $"+ MPF.maxProfit(terminatedJobsMPF) +"\n";
		byte data7[] = s.getBytes();
		dataOutputFile(data7);
		System.out.format("Max’s approach profit: $%.2f%n", MPF.maxProfit(terminatedJobsMPF) );	//MAX PROFIT
		
		s="Max’s approach number of disappointed customers: " + MPF.nonCompleted(nonCompletedMPF) + "\n";
		byte data8[] = s.getBytes();
		dataOutputFile(data8);
		System.out.println("Max’s approach number of disappointed customers: " + MPF.nonCompleted(nonCompletedMPF) );//DISSAPOINTED CUSTOMERS
		
		/*SJF*/
		SJF.processSJF(inputListSJF);
		s="Pac’s approach profit: $"+ SJF.maxProfit(terminatedJobsSJF)+"\n";
		byte data9[] = s.getBytes();
		dataOutputFile(data9);
		System.out.format("Pac’s approach profit: $%.2f%n", SJF.maxProfit(terminatedJobsSJF) );	//MAX PROFIT
		
		s="Pac’s approach number of disappointed customers: " + SJF.nonCompleted(nonCompletedSJF) + "\n";
		byte data10[] = s.getBytes();
		dataOutputFile(data10);
		System.out.println("Pac’s approach number of disappointed customers: " + SJF.nonCompleted(nonCompletedSJF) );//DISSAPOINTED CUSTOMERS
			
		
		
		jobQueue.dequeue();	
		  

	}
	}
	
	
	
	private static void dataOutputFile(byte data[]) {
		 Path p = Paths.get("./"+outputName+".txt");

		    try (OutputStream out = new BufferedOutputStream(
		      Files.newOutputStream(p, CREATE, APPEND))) {
		      out.write(data, 0, data.length);
		    } catch (IOException x) {
		      System.err.println(x);
		    }
		
	}



	public static SLLQueue<String> fileData(SLLQueue<String> jobQueue, String fileName ) throws FileNotFoundException{

		Path pathToFile = Paths.get(fileName);
		try (BufferedReader br = Files.newBufferedReader(pathToFile,
				StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			while (line != null) { 	   
				String[] attributes = line.split(",");
				String job = createJob(attributes);
				jobQueue.enqueue(job);
				line = br.readLine();
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return jobQueue;
	}
	
	private static String createJob(String[] metadata) {
		 String newJob = metadata[0];

		 return newJob; 

	}
	
	private static String removeLastChar(String str) {
	    return str.substring(0, str.length() - 1);
	}
	
	
	

	
}












