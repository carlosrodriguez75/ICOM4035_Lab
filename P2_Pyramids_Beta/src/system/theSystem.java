package system;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import classes.LinkedTree;
import classes.SLLQueue;
import exceptions.IllegalStateEXception;



public class theSystem {  

	private static SLLQueue<Job> jobQueue = new SLLQueue<Job>();
	private static SLLQueue<Job> jobOutputQueue = new SLLQueue<Job>();
	private static String outputName, assets, name ;
	private static int times =0;


	private static SLLQueue<Member> caseQueue = new SLLQueue<Member>();
	//public static LinkedTree<Member> testingCase = new LinkedTree<Member>();
	public static ArrayList<Member> theArrests = new ArrayList<Member>();
	public static ArrayList<Member> theArrestsFinal = new ArrayList<Member>();



	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {  
		fileData(jobQueue,"input.txt");
		fileData(jobOutputQueue,"input.txt");
		int jobSize = jobQueue.size();
		int counterJobs = 0;
		while(counterJobs!=jobSize){
		LinkedTree<Member> testingCase = new LinkedTree<Member>();
			counterJobs++;
			//Create a Queue with the child, asset and father information
			PQCase.fileData(caseQueue,jobQueue.first().getAsset()+" "+jobQueue.first().getName());
			//Create a tree with the Queue information
			PQCase.createTree(caseQueue,testingCase);
			times = Integer.parseInt(jobQueue.first().getAsset());
			theArrests = PQCase.findAssets(testingCase,jobQueue.first().getAsset());
			for(int i=0; i<times; i++){
				theArrestsFinal.add(theArrests.get(i));
			}
			System.out.println("Maximum seized assets: "+PQCase.calculateAssets(theArrestsFinal));
			int quantity = theArrests.size();
			int counter = 1,index=0;
			while(counter-1!= quantity/times){
				printResults(theArrests,index,counter);
				index=index+times;
				counter++;
			}
			System.out.println("");
			theArrests.clear();
			theArrestsFinal.clear();
			jobQueue.dequeue();
		}

	}

	private static void printResults(ArrayList<Member> theArrests2, int index,int counter) {
		StringBuilder sb = new StringBuilder();
		for(int i=index;i<index+times;i++){
			sb.append(theArrests2.get(i).getName());
			if(i+1!=index+times){
				sb.append(", ");
			}
			
		}
		System.out.println("List "+counter+": " + sb.toString()) ;
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
	public static SLLQueue<Job> fileData(SLLQueue<Job> jobQueue, String fileName ) throws FileNotFoundException{

		Path pathToFile = Paths.get(fileName);
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			while (line != null) { 	   
				String[] attributes = line.split(" ");
				Job job = createJob(attributes);
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

	private static Job createJob(String[] metadata) {
		assets = metadata[0];
		name = metadata[1];



		return new Job(assets,name); 

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












