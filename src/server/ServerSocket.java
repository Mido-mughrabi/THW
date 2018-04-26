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
import model.Mission;
import model.Person;
import model.ServerModel;

public class ServerSocket extends WebSocketAdapter{
	
	private static Set<Session> sessions = new HashSet<>();
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
		//toDo: compare client's token with server's token
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
			person = gson.fromJson(json.get("person"), Person.class);
			ServerModel.addPerson(person);
			break;
		case "delete person":
			person = gson.fromJson(json.get("person"), Person.class);
			ServerModel.deletePerson(person);
			break;
		case "update person":
			person = gson.fromJson(json.get("person"), Person.class);
			ServerModel.updatePerson(person);
			break;
		case "start mission":
			mission = gson.fromJson(json.get("mission"), Mission.class);
			ServerModel.startMission(mission);
			break;
		case "end mission":
			ServerModel.endMission();
			break;
		case "auth":
			//toDo: auth between server and client
			String clientToken = json.get("token").getAsString();
			break;
		//toDo: case update estimated time, in this case you can send the person and a JSON Property estimated time
		default:
			System.out.println("unkown command: " + action);
			break;
		}
		sendAll(message);
	}
	
	private void sendAll(String message){
		for (Session session : sessions) {
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
