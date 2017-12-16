package system;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import classes.LinkedPositionalList;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;
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
		int jobSize = jobQueue.size()-1;
		int counterJobs = 0;
		while(counterJobs!=jobSize){
		counterJobs++;
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
	    s="Maximum number of customers served possible: " +  maxCustomers+ "\n";
		byte data2[] = s.getBytes();
		dataOutputFile(data2);

		/*FCFS*/
		FCFS.processFCFS(inputQueue);
		s= "Pat’s approach profit: $" + FCFS.maxProfit(terminatedJobs) + "\n";
		byte data3[] = s.getBytes();
		dataOutputFile(data3);
		
		s = "Pat’s approach number of disappointed customers: " + FCFS.nonCompleted(nonCompleted) + "\n";
		byte data4[] = s.getBytes();
		dataOutputFile(data4);
		/*LCFS*/
		LCFS.processLCFS(inputList);
		s="Mat’s approach profit: $"+ LCFS.maxProfit(terminatedJobsLCFS) + "\n";
		byte data5[] = s.getBytes();
		dataOutputFile(data5);		
		s="Mat’s approach number of disappointed customers: " + LCFS.nonCompleted(nonCompletedLCFS) +"\n";
		byte data6[] = s.getBytes();
		dataOutputFile(data6);

		/*MPF*/
		MPF.processMPF(inputListMPF);
		s="Max’s approach profit: $"+ MPF.maxProfit(terminatedJobsMPF) +"\n";
		byte data7[] = s.getBytes();
		dataOutputFile(data7);

		
		s="Max’s approach number of disappointed customers: " + MPF.nonCompleted(nonCompletedMPF) + "\n";
		byte data8[] = s.getBytes();
		dataOutputFile(data8);

		
		/*SJF*/
		SJF.processSJF(inputListSJF);
		s="Pac’s approach profit: $"+ SJF.maxProfit(terminatedJobsSJF)+"\n";
		byte data9[] = s.getBytes();
		dataOutputFile(data9);
		
		
		s="Pac’s approach number of disappointed customers: " + SJF.nonCompleted(nonCompletedSJF) + "\n";
		byte data10[] = s.getBytes();
		dataOutputFile(data10);

		jobQueue.dequeue();	
		  

	}
		
		System.exit(0);
	}
	
	
	/*
	 * Create the .out File in the Project Path
	 */
	private static void dataOutputFile(byte data[]) {
		 Path p = Paths.get("./"+outputName+".out");

		    try (OutputStream out = new BufferedOutputStream(
		      Files.newOutputStream(p, CREATE, APPEND))) {
		      out.write(data, 0, data.length);
		    } catch (IOException x) {
		      System.err.println(x);
		    }
		
	}


/*
 * Will read the specified .txt file in the Project Path
 */
	public static SLLQueue<String> fileData(SLLQueue<String> jobQueue, String fileName ) throws FileNotFoundException{

		Path pathToFile = Paths.get(fileName);
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
	
	/**
	 * 
	 * @param metadata: receive a new .csv file
	 * @return new Job for Queue of type String
	 */
	
	private static String createJob(String[] metadata) {
		 String newJob = metadata[0];

		 return newJob; 

	}
	/**
	 * Used to create the name of the new .out File
	 * @param str receive a new string 
	 * @return new string eliminating the Last char
	 */
	private static String removeLastChar(String str) {
	    return str.substring(0, str.length() - 1);
	}
	
	
	

	
}












