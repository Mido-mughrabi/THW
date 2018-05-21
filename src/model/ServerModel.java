package model;

import java.util.HashSet;
import java.util.Set;

import io.IO;

public class ServerModel {
	//toDo: hashset can have null element (but only one), be careful !
	//Tipp: if the project gets bigger, you can use a Map for efficiency, where key is the call number 
	Set<Person> persons = IO.readPersons();
	//toDo: if more than one mission can take place in one time, replace with Set of missions
	Mission mission = null;
	
	public void addPerson(Person person)
	{
		persons.add(person);
		IO.writePersons(persons);
	}
	
	public void deletePerson(Person person)
	{
		persons.remove(person);
		IO.writePersons(persons);
	}
	
	public void updatePerson(Person person)
	{
		//remove then add to achieve an update, consider changing if the data structure is to be changed
		persons.remove(person);
		persons.add(person);
		IO.writePersons(persons);
	}
	
	public void startMission(Mission mission)
	{
		this.mission = mission;
	}
	
	//toDo: in case than more than one mission, this function should know which one to end
	public void endMission()
	{
		this.mission = null;
	}
	
	public Set<Person> getPersons()
	{
		return persons;
	}
}
