package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Person;

public class IO {
	
	static String path = "./persons.json";
	static Gson gson = new Gson();
	
	public static HashSet<Person> readPersons()
	{
		File personsFile = new File(path);
		HashSet<Person> persons = null;
		String personsJson = "";
		try {
			Scanner sc = new Scanner(personsFile);
			persons =  new HashSet<>();
			while(sc.hasNext())
			{
				personsJson += sc.next();
			}
			persons = gson.fromJson(personsJson, new TypeToken<HashSet<Person>>() {}.getType());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return persons;
	}
	
	public static void writePersons(Set<Person> persons)
	{
		String personsJson = gson.toJson(persons);
		try {
			PrintWriter pw = new PrintWriter(path);
			pw.print(personsJson);
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static String getToken() {
		Properties properties = new Properties();
		InputStream input = IO.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(input != null)
			{
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return properties.getProperty("token");
	}
}
