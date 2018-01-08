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

	private static SLLQueue<String> jobQueue = new SLLQueue<String>();
	private static SLLQueue<String> jobOutputQueue = new SLLQueue<String>();
	private static String outputName, assets, name ;
	
 
	private static SLLQueue<Member> caseQueue = new SLLQueue<Member>();
	public static LinkedTree<Member> testingCase = new LinkedTree<Member>();
	public static ArrayList<Member> theArrests = new ArrayList<Member>();
	public static ArrayList<Member> theArrestsFinal = new ArrayList<Member>();
	
	
	
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {  
		fileData(jobQueue,"input.txt");
		fileData(jobOutputQueue,"input.txt");
		int jobSize = jobQueue.size();
		int counterJobs = 0;
		while(counterJobs!=jobSize){
		counterJobs++;
		//Create a Queue with the child, asset and father information
		PQCase.fileData(caseQueue,jobQueue.first());
		//Create a tree with the Queue information
		PQCase.createTree(caseQueue,testingCase);
		theArrests = PQCase.findAssets(testingCase,assets);
		for(int i=0; i<4; i++){
			theArrestsFinal.add(theArrests.get(i));
		}
		System.out.println("Maximum seized assets: "+PQCase.calculateAssets(theArrestsFinal));
		int quantity = theArrests.size();
		int counter = 0;
		while(counter!= quantity/4){
			for(int pointer=0; pointer<quantity; pointer++){
				System.out.print("List "+counter+":" + theArrests.get(pointer).getName() + ", ");
			}
			
		}
		
		
		
		
		
		}


		  

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
				String[] attributes = line.split(" ");
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
		  assets = metadata[0];
		  name = metadata[1];
		 
		 

		 return assets + " " + name; 

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












