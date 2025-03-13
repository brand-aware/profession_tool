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
import java.io.IOException;

public class Properties {
	
	private String rootDir;
	private String company;
	private String logo;
	private String background;
	private String saveDir;
	private String userlist;
	private String userDir;
	
	public Properties(String root, String usrDir){
		rootDir = root;
		userDir = usrDir;
		company = rootDir + File.separator + "img" + File.separator + "company.png";
		logo = rootDir + File.separator + "img" + File.separator + "logo.png";
		background = rootDir + File.separator + "img" + File.separator + "background.png";
		userlist = rootDir + File.separator + "settings" + File.separator + "userlist.txt";
	
		String companyPath = userDir + File.separator + "AppData" + File.separator + "Local"
				+ File.separator + "brand-aware";
		File companyFolder = new File(companyPath);
		if(!companyFolder.exists()) {
			companyFolder.mkdir();
		}
		
		String productPath = companyPath + File.separator + "profession_tool";
		File productFolder = new File(productPath);
		if(!productFolder.exists()) {
			productFolder.mkdir();
		}
		
		String settingsPath = productPath + File.separator + "settings";
		File settingsFolder = new File(settingsPath);
		if(!settingsFolder.exists()) {
			settingsFolder.mkdir();
		}
		userlist = settingsPath + File.separator + "userlist.txt";
		File userFile = new File(userlist);
		if(!userFile.exists()) {
			try {
				userFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saveDir = productPath + File.separator + "save_files";
		File saveFolder = new File(saveDir);
		if(!saveFolder.exists()) {
			saveFolder.mkdir();
		}
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
	public String getSaveDir() {
		return saveDir;
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
