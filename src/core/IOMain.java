/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class IOMain extends CommonMain{
	
	protected void saveData() throws IOException{
		String path = properties.getSaveDir() + File.separator + displayData.getUsername() + ".txt";
		File file = new File(path);
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(OBJECTIVE_PATTERN + "\n");
		ArrayList<String> objectiveList = displayData.getObjectiveList();
		for(int x = 0; x < objectiveList.size(); x++){
			writer.write(objectiveList.get(x));
			writer.write("\n");
		}
		ArrayList<String> accomplishList = displayData.getAccomplishmentList();
		writer.write(ACCOMPLISH_PATTERN + "\n");
		for(int y = 0; y < accomplishList.size(); y++){
			writer.write(accomplishList.get(y));
			if(y+1 != accomplishList.size()){
				writer.write("\n");
			}
		}
		
		writer.close();
	}
	
	protected boolean updateUserlist(String username) throws IOException{
		String path = properties.getUserlist();
		String userlist = "";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while(reader.ready()){
			String line = reader.readLine();
			if(line.compareTo(username) == 0){
				reader.close();
				return false;
			}else if(line.compareTo(EMPTY_USER) != 0){
				userlist += line + "\n";
			}
		}
		userlist += username;
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write(userlist);
		writer.close();
		return true;
	}
	
	protected String[] loadUserList() throws IOException{
		String path = properties.getUserlist();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> userlistFile = new ArrayList<String>();
		while(reader.ready()){
			String line = reader.readLine();
			userlistFile.add(line);
		}
		String[] userlist = new String[userlistFile.size()];
		for(int x = 0; x < userlistFile.size(); x++){
			userlist[x] = userlistFile.get(x);
		}
		
		reader.close();
		return userlist;
	}
	
	protected boolean loadSaveFile(String username) throws Exception{
		displayData = new DisplayData(username);
		File saveFile = new File(properties.getSaveFile(username));
		if(saveFile.exists()){
			BufferedReader reader = new BufferedReader(new FileReader(saveFile));
			while(reader.ready()){
				String line = reader.readLine();
				if(line.compareTo(OBJECTIVE_PATTERN) == 0){
					objectiveFlag = true;
					accomplishFlag = false;
				}else if(line.compareTo(ACCOMPLISH_PATTERN) == 0){
					objectiveFlag = false;
					accomplishFlag = true;
				}else if(objectiveFlag){
					displayData.addObjective(line);
				}else if(accomplishFlag){
					displayData.addAccomplishment(line);
				}else{
					reader.close();
					throw new Exception("save file data is incorrect or corrupted...");
				}
			}
			
			reader.close();
			return true;
		}
		return false;
	}
	
	protected void loadNotes() throws IOException{
		File notesFile = new File(properties.getNotesFile(displayData.getUsername()));
		if(notesFile.exists()){
			BufferedReader reader = new BufferedReader(new FileReader(notesFile));
			while(reader.ready()){
				String line = reader.readLine();
				String[] data = line.split(SPLIT_PATTERN);
				if(data.length > 1){
					displayData.setNote(data[0], data[1]);
				}
			}
			reader.close();
		}
	}
	
	protected void saveNotes() throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getNotesFile(displayData.getUsername())));
		Iterator<String> keys = displayData.getNoteKeys();
		String buffer = "";
		boolean empty = true;
		while(keys.hasNext()){
			String key = keys.next();
			String description = displayData.getNote(key);
			buffer += key + SPLIT_PATTERN + description + "\n";
			empty = false;
		}
		if(!empty){
			buffer = buffer.substring(0, buffer.lastIndexOf("\n"));
		}
		writer.write(buffer);
		writer.close();
	}
	
	protected void deleteUser() throws IOException{
		String username = displayData.getUsername();
		BufferedReader reader = new BufferedReader(new FileReader(properties.getUserlist()));
		String buffer = "";
		boolean empty = true;
		while(reader.ready()){
			String line = reader.readLine();
			if(line.compareTo(username) != 0){
				buffer += line + "\n";
				empty = false;
			}
		}
		reader.close();
		if(empty){
			buffer += EMPTY_USER;
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getUserlist()));
		writer.write(buffer);
		writer.close();
		
		File saveFile = new File(properties.getSaveFile(username));
		saveFile.delete();
		File notesFile = new File(properties.getNotesFile(username));
		notesFile.delete();
		displayData = new DisplayData(EMPTY_USER);
		displayData.addObjective(EMPTY_OBJECTIVE);
		displayData.addAccomplishment(EMPTY_ACCOMPLISH);
	}

}
