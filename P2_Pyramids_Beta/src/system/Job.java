package system;

public class Job {

	private String assets,name;
	
	public Job(String assets, String name){
		this.assets=assets;
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getAsset(){
		return this.assets;
	}
}
