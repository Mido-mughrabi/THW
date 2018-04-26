package piclient;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import controller.PiController;
import model.Mission;
import model.Person;
import model.Estimated_time;
import model.Functions;
import model.Unit_types;

public class PiClientSocket extends WebSocketAdapter{
	
	private PiController controller;
	private Gson gson = new Gson();
	static private Mission mission = null;
	
	public void setController(PiController control) {
		this.controller = control;
	}
	
	@Override
	public void onWebSocketConnect(Session sess) {
		super.onWebSocketConnect(sess);
		controller.init();
		System.out.println("Socket connected : " + sess);
		//toDo: maybe want to tell the server that this client is a pi client
	}
	
	@Override
	public void onWebSocketText(String message) {
		super.onWebSocketText(message);
		System.out.println("Received TEXT message: " + message);
		JsonElement jelem = gson.fromJson(message, JsonElement.class);
		JsonObject json = jelem.getAsJsonObject();
		//toDo: what to do with received messages
		String action = json.get("action").getAsString();
		switch (action) {
		case "start mission":
			//toDo: start mission
			mission = gson.fromJson(json.get("mission"), Mission.class);
			controller.startMission(mission);
			break;
		case "end mission":
			//toDo: end mission, using the piController if needed
			controller.endMission();
			break;
		case "update arrival time":
				Person person = gson.fromJson(json.get("person"), Person.class);
				Estimated_time time = gson.fromJson(json.get("arrival time"), Estimated_time.class);
				controller.updateEngaged(person,time);
				break;
		default:
			break;
		}
	}
	
}
