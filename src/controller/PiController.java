package controller;

import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import model.Estimated_time;
import model.Mission;
import model.Person;
import model.PiModel;
import piclient.PiClientSocket;
import view.PiView;

public class PiController {
	private PiModel model;
	PiView view;
	PiClientSocket clientSocket;
	
	public PiController(PiModel model, PiView view, PiClientSocket clientSocket, String host, int port) {
		this.model = model;
		this.view = view;
		this.clientSocket = clientSocket;
		this.clientSocket.setController(this);
		
		URI uri = URI.create("ws://" + host + ":" + port +"/servlets");
		System.out.println(uri);
		WebSocketClient client = new WebSocketClient();
		
		try {
			client.start();
			Future<Session> fut = client.connect(this.clientSocket, uri);
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			System.exit(1);
		}
		
	}

	public void init() {
		//tipp ToDo: use PiclientSocket to tell the server that a new piclient has connected
		model.addObserver(view);
	}

	public void startMission(Mission mission) {
		model.startMission(mission);
	}

	public void endMission() {
		model.endMission();
	}

	public void updateMission(Mission mission) {
		
		
	}

	public void updateEngaged(Person person, Estimated_time time) {
		// TODO Auto-generated method stub
		model.updatePersonArrivalTime(person,time);
	}
	
	

}
