package client;

import java.io.IOException;
import java.time.LocalDateTime;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.IO;
import model.Mission;
import model.Person;
import model.Estimated_time;
import model.Functions;
import model.Unit_types;

public class ClientSocket extends WebSocketAdapter{
	
	private Gson gson = new Gson();
	String token = IO.getToken();
	
	@Override
	public void onWebSocketConnect(Session sess) {
		super.onWebSocketConnect(sess);
		System.out.println("Socket connected : " + sess);
		//toDo: maybe want to call some init functions here
		////////////////////////////////////////////////////////// testArea, please delete
		JsonObject json = new JsonObject();
		json.addProperty("token", token);
		json.addProperty("type", "client");
		json.addProperty("action", "start mission");
		//json.addProperty("action", "update arrival time");
		Mission m = new Mission(LocalDateTime.now());
		//Person p = new Person("0156", "lala",Functions.SKILLED_WORKER, Unit_types.OV_STAB);
		//Person p2 = new Person("123", "lalasda",Functions.SKILLED_WORKER, Unit_types.OV_STAB);
		//m.addEngaged(p);
		//m.addEngaged(p2);
		//json.add("person", gson.toJsonTree(p));
		json.add("mission", gson.toJsonTree(m));
		json.add("arrival time", gson.toJsonTree(Estimated_time.FIVE_MINS));
		try {
			getSession().getRemote().sendString(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////////////////////
	}
	
	@Override
	public void onWebSocketText(String message) {
		super.onWebSocketText(message);
		System.out.println("Received TEXT message: " + message);
		JsonElement jelem = gson.fromJson(message, JsonElement.class);
		JsonObject json = jelem.getAsJsonObject();
		//toDo: what to do with received messages
	}
	
}
