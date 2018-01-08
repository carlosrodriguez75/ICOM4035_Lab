package system;

import java.util.ArrayList;

import classes.LinkedTree;
import classes.Node;

public class Member {
	
	private int asset;
	private String name, sponsor, mentor;
	private ArrayList<Member> childrens;
	
	public Member(String name, int asset, String sponsor){
		this.asset = asset;
		this.name = name;
		this.sponsor = sponsor;
	}
	
	public int getAsset(){
		return this.asset;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSponsor(){
		//for each buscando String
//		return new Member(this.name, this.asset, this.parent);
		return this.sponsor;
	}
	
	public boolean equals(Member m){
		if(this.getName().equals(m.getName()))
			return true;
		
		return false;
	}

	public void setMentor(String mentor) {
		this.mentor = mentor;
		
	}

}




