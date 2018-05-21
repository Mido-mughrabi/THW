package server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.IO;
import model.Estimated_time;
import model.Mission;
import model.Person;
import model.ServerModel;

public class ServerSocket extends WebSocketAdapter{
	
	private static Set<Session> clientSessions = new HashSet<>();
	private static Set<Session> piSessions = new HashSet<>();
	private static Set<Session> androidSession = new HashSet<>();
	private static ServerModel ServerModel = new ServerModel();
	private static String token = IO.getToken();
	
	private Gson gson = new Gson();
	private Session session;
	private Mission mission;
	
	@Override
	public void onWebSocketConnect(Session sess) {
		super.onWebSocketConnect(sess);
		System.out.println(token);
		System.out.println("Socket Connected: " + sess);
		this.session = sess;
	}
	
	@Override
	public void onWebSocketText(String message) {
		super.onWebSocketText(message);
		System.out.println("recieved message :" + message);
		
		JsonElement jelem = gson.fromJson(message, JsonElement.class);
		JsonObject json = jelem.getAsJsonObject();
		//what to do on each action, may need ToDos in case of more actions
		String action = json.get("action").getAsString();
		Person person = null;
		switch (action) {
		case "add person":
			if(checkToken(json.get("token").getAsString()))
			{
				System.out.println(json.get("person"));
				person = gson.fromJson(json.get("person"), Person.class);
				ServerModel.addPerson(person);
				sendAllInSessionGroup(clientSessions,message);
			}
			break;
		case "delete person":
			if(checkToken(json.get("token").getAsString()))
			{
				person = gson.fromJson(json.get("person"), Person.class);
				ServerModel.deletePerson(person);
				sendAllInSessionGroup(clientSessions,message);
			}
			break;
		case "update person":
			if(checkToken(json.get("token").getAsString()))
			{
				person = gson.fromJson(json.get("person"), Person.class);
				ServerModel.updatePerson(person);
				sendAllInSessionGroup(clientSessions,message);
			}
			break;
		case "start mission":
			if(checkToken(json.get("token").getAsString()))
			{
				mission = gson.fromJson(json.get("mission"), Mission.class);
				for(Person p : ServerModel.getPersons())
				{
					System.out.println(p);
					mission.addEngaged(p);
				}
				ServerModel.startMission(mission);
				json.remove("mission");
				json.add("mission",  gson.toJsonTree(mission));
				sendAll(json.toString());		
			}
			break;
		case "end mission":
			if(checkToken(json.get("token").getAsString()))
			{
				ServerModel.endMission();
				sendAll(message);
			}
			break;
		case "update arrival time":
			if(checkToken(json.get("token").getAsString()))
			{
				person = gson.fromJson(json.get("person"), Person.class);
				Estimated_time time = gson.fromJson("arrival time", Estimated_time.class);
				if(mission != null)
				{
					mission.updateEngaged(person,time);
				}
				sendAllInSessionGroup(piSessions,message);
			}
			break;
		case "auth":
			String clientToken = json.get("token").getAsString();
			String clientType = json.get("type").getAsString();
			if(token.equals(clientToken))
			{
				if(clientType.equals("pi"))
				{
					piSessions.add(session);
				}
				else if(clientType.equals("android"))
				{
					androidSession.add(session);	
				}
				else if(clientType.equals("client"))
				{
					clientSessions.add(session);
				}
			}
			break;
		default:
			System.out.println("unkown command: " + action);
			break;
		}
	}
	
	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		super.onWebSocketClose(statusCode, reason);
		System.out.println("Socket Closed: [" + statusCode + "] " + reason);
		piSessions.remove(session);
		androidSession.remove(session);
		clientSessions.remove(session);
	}
	
	private boolean checkToken(String token) {
		if (token.equals(ServerSocket.token))
		{
			return true;
		}
		
		try {
			session.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private void sendAll(String message){
		sendAllInSessionGroup(clientSessions,message);
		sendAllInSessionGroup(piSessions,message);
		sendAllInSessionGroup(androidSession,message);
	}

	private void sendAllInSessionGroup(Set<Session> group,String message)
	{
		for (Session session : group) {
			try {
				if(session != this.session){
					session.getRemote().sendString(message);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
