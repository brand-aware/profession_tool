/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.io.File;

public class Properties {
	
	private String rootDir;
	private String company;
	private String logo;
	private String background;
	private String saveDir;
	private String userlist;
	
	public Properties(String root){
		rootDir = root;
		company = rootDir + File.separator + "img" + File.separator + "company.png";
		logo = rootDir + File.separator + "img" + File.separator + "logo.png";
		background = rootDir + File.separator + "img" + File.separator + "background.png";
		saveDir = rootDir + File.separator + "save_files";
		userlist = rootDir + File.separator + "settings" + File.separator + "userlist.txt";
	}
	
	public String getRootDir(){
		return rootDir;
	}
	public String getCompany(){
		return company;
	}
	public String getLogo(){
		return logo;
	}
	public String getBackground(){
		return background;
	}
	public String getSaveFile(String username){
		return saveDir + File.separator + username + ".txt";
	}
	public String getNotesFile(String username){
		return saveDir + File.separator + username + "_notes.txt";
	}
	public String getUserlist(){
		return userlist;
	}
}
