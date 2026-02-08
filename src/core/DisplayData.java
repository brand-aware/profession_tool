/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class DisplayData extends ConfigGeneral{
	
	private String username;
	private ArrayList<String> objectiveList;
	private ArrayList<String> accomplishmentList;
	private Map<String, String> notes;
	private Collection<String> checks;
	
	public DisplayData(String user){
		username = user;
		objectiveList = new ArrayList<String>();
		accomplishmentList = new ArrayList<String>();
		notes = new TreeMap<String, String>();
		checks = new TreeSet<String>();
	}
	
	public boolean addObjective(String item){
		if(objectiveEmpty()){
			objectiveList.remove(0);
		}
		if(!isFound(item)){
			objectiveList.add(item);
			checks.add(item);
			return true;
		}
		return false;
	}
	
	public boolean objectiveEmpty(){
		if(objectiveList.size() == 1){
			if(objectiveList.get(0).compareTo(EMPTY_OBJECTIVE) == 0){
				return true;
			}
		}
		return false;
	}
	
	public boolean isFound(String item){
		return checks.contains(item);
	}
	
	public boolean addAccomplishment(String item){
		if(accomplishmentEmpty()){
			accomplishmentList.remove(0);
		}
		if(!isFound(item)){
			accomplishmentList.add(item);
			checks.add(item);
			return true;
		}
		return false;
	}
	
	public boolean accomplishmentEmpty(){
		if(accomplishmentList.size() == 1){
			if(accomplishmentList.get(0).compareTo(EMPTY_ACCOMPLISH) == 0){
				return true;
			}
		}
		return false;
	}
	
	public void removeObjective(int index){
		String item = objectiveList.get(index);
		objectiveList.remove(index);
		checks.remove(item);
		if(objectiveList.size() == 0){
			objectiveList.add(EMPTY_OBJECTIVE);
		}
	}

	public void removeObjective(String item){
		objectiveList.remove(item);
		checks.remove(item);
		if(objectiveList.size() == 0){
			objectiveList.add(EMPTY_OBJECTIVE);
		}
	}
	
	public void removeAccomplishment(int index){
		String item = accomplishmentList.get(index);
		accomplishmentList.remove(index);
		checks.remove(item);
		if(accomplishmentList.size() == 0){
			accomplishmentList.add(EMPTY_ACCOMPLISH);
		}
	}
	
	public void removeAccomplishment(String item){
		accomplishmentList.remove(item);
		checks.remove(item);
		if(accomplishmentList.size() == 0){
			accomplishmentList.add(EMPTY_ACCOMPLISH);
		}
	}
	
	public ArrayList<String> getObjectiveList(){
		return objectiveList;
	}
	
	public String getObjective(int index){
		if(index < objectiveList.size()){
			return objectiveList.get(index);
		}
		return "";
	}
	
	public ArrayList<String> getAccomplishmentList(){
		return accomplishmentList;
	}
	
	public String getAccomplishment(int index){
		if(index < accomplishmentList.size()){
			return accomplishmentList.get(index);
		}
		return "";
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setNote(String item, String description){
		notes.put(item, description);
	}
	
	public String getNote(String item){
		return notes.get(item);
	}
	
	public void deleteNote(String item){
		notes.remove(item);
	}
	
	public Iterator<String> getNoteKeys(){
		return notes.keySet().iterator();
	}
}
