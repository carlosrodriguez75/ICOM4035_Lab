package system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import classes.LinkedTree;
import classes.Node;
import classes.SLLQueue;



public class PQCase {

	private static String parent, name;
	private static Member temp;
	private static int asset;

	public static SLLQueue<Member> fileData(SLLQueue<Member> caseQueue, String fileName) {
		Path pathToFile = Paths.get(fileName);
		try (BufferedReader br = Files.newBufferedReader(pathToFile,StandardCharsets.US_ASCII)) {
			//read the first line from the text file 
			String line = br.readLine();
			// loop until all lines are read 
			while (line != null) { 
				// use string.split to load a string array with the values from 
				//each line of 
				//the file, using a comma as the delimiter		   
				String[] attributes = line.split("#");
				//Job job = createJob(attributes);
				name =attributes[0];
				asset = Integer.parseInt(attributes[1]);
				if(attributes.length==3){parent=attributes[2];}
				else{parent = null;}
				Member nuevo = new Member(name,asset,parent); 
				caseQueue.enqueue(nuevo) ;
				// read next line before looping
				//if end of file reached, line would be null
				line = br.readLine();
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return caseQueue;
	}
	public static LinkedTree<Member> createTree(SLLQueue<Member> caseQueue, LinkedTree<Member> testingCase) {
		testingCase.addRoot(caseQueue.dequeue());
		while(caseQueue.size() != 0){
			testingCase.addChild(caseQueue.first().getSponsor(),caseQueue.dequeue(),testingCase);
		}

		return testingCase;

	}
	public static <E> ArrayList<Member> findAssets(LinkedTree<Member> testingCase, String assets) throws CloneNotSupportedException {
		int arrests = Integer.parseInt(assets);
		LinkedTree<Member> cloneCase = testingCase.clone();
		int targetZeros = testingCase.size();
		int counter = 0, numberAssets = 0, tmpNumberAssets;
		ArrayList<Node<Member>> targets = cloneCase.root().getChildren();
		targets = getTargets(cloneCase,targets);
		ArrayList<Member> arrestsResult = new ArrayList<Member>();
		ArrayList<Member> tmpArrestsList = new ArrayList<Member>();
		while(counter != targetZeros){	
			if(counter == 0){
				counter++;
				tmpArrestsList =  testingCase.arrestOperation(arrests,testingCase.root()); 
				tmpNumberAssets = calculateAssets(tmpArrestsList);
				if(tmpNumberAssets > numberAssets){
					numberAssets = tmpNumberAssets;
					arrestsResult.clear();
					for(int pointer =0; pointer<tmpArrestsList.size();pointer++){
						arrestsResult.add(tmpArrestsList.get(pointer));
					}
					
				}
				else if(tmpNumberAssets == numberAssets){
					for(int j =0; j<tmpArrestsList.size();j++){
						arrestsResult.add(tmpArrestsList.get(j));
					}
				}
			}

			for(int i = 0; i<targets.size();i++){
				counter++;
				tmpArrestsList =  testingCase.arrestOperation(arrests,targets.get(i));  
				tmpNumberAssets = calculateAssets(tmpArrestsList);
				if(tmpNumberAssets > numberAssets){
					numberAssets = tmpNumberAssets;
					arrestsResult.clear();
					for(int pointer =0; pointer<tmpArrestsList.size();pointer++){
						arrestsResult.add(tmpArrestsList.get(i));
					}
					
				}
				else if(tmpNumberAssets == numberAssets){
					for(int j =0; j<tmpArrestsList.size();j++){
						arrestsResult.add(tmpArrestsList.get(j));
					}
				}
			}

		}
		return arrestsResult;

	}


	private static <E> ArrayList<Node<Member>> getTargets(LinkedTree<Member> testingCase, ArrayList<Node<Member>> targets) {
		// TODO Auto-generated method stub
		int targetZeros = testingCase.size();
		Iterable<Node<Member>> result = new ArrayList<Node<Member>> (); 
		//ArrayList<Node<Member>> targets = (ArrayList<Node<Member>>) testingCase.children(testingCase.root());
		for(int i =0; i<targetZeros-1; i++){
			if(testingCase.children(targets.get(i)) != null){
				//targets.add((Node<Member>) testingCase.children(targets.get(i)));
				result = testingCase.children(targets.get(i));
				Iterator<Node<Member>> memberIterator = result.iterator();
				while(memberIterator.hasNext()){
					targets.add(memberIterator.next());
				}
				
				
				
			}
			
		}
		
		return targets;
	}
	public static int calculateAssets(ArrayList<Member> tmpArrestsList) {
		int result = 0;
		for (int i=0; i< tmpArrestsList.size(); i++){
			result = result + tmpArrestsList.get(i).getAsset();
		}

		return result;

	}

}
