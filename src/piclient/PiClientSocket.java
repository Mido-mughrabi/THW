package piclient;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import controller.PiController;
import io.IO;
import model.Mission;
import model.Person;
import model.Estimated_time;
import model.Functions;
import model.Unit_types;

public class PiClientSocket extends WebSocketAdapter{
	
	private PiController controller;
	private Gson gson = new Gson();
	static private Mission mission = null;
	String token = IO.getToken();
	
	public void setController(PiController control) {
		this.controller = control;
	}
	
	@Override
	public void onWebSocketConnect(Session sess) {
		super.onWebSocketConnect(sess);
		controller.init();
		System.out.println("Socket connected : " + sess);
		
		JsonObject json = new JsonObject();
		json.addProperty("action", "auth");
		json.addProperty("token", token);
		json.addProperty("type", "pi");
		try {
			getSession().getRemote().sendString(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onWebSocketText(String message) {
		super.onWebSocketText(message);
		System.out.println("Received TEXT message: " + message);
		JsonElement jelem = gson.fromJson(message, JsonElement.class);
		JsonObject json = jelem.getAsJsonObject();

		String action = json.get("action").getAsString();
		switch (action) {
		case "start mission":
			mission = gson.fromJson(json.get("mission"), Mission.class);
			controller.startMission(mission);
			break;
		case "end mission":
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
