package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Mission {
	private Timestamp alarmTime;
	//toDo: gson is dump AF, you can just send String String maps, it might be a clever way to do it but this bug killed every hope I had
	private HashMap<String, String> engaged = new HashMap<>() ;
	//toDo: ask what should a mission include
	
	public Mission(Timestamp alarmTime)
	{
		this.alarmTime = alarmTime;
	}

	
	public HashMap<String, String> getEngaged()
	{
		return engaged;
	}
	
	public void addEngaged(Person person)
	{
		if(person == null) return;
		engaged.put(person.toString(), Estimated_time.UNKNOWN.toString());
	}


	public void updateEngaged(Person person, Estimated_time time) {
		System.out.println(time.toString());
		engaged.put(person.toString(), time.toString());
	}
	
}
