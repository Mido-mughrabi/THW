package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import controller.PiController;
import model.Mission;
import model.PiModel;
import piclient.PiClientSocket;
import view.PiView;

import org.eclipse.jetty.websocket.api.Session;

//toDo this client and pi client can extend a client class
public class StartClient {
	final static String HOST = "localhost";
	final static int PORT = 3000;
	
	public static void main(String[] args)
	{	
		String URIString = "ws://" + HOST + ":" + PORT + "/servlets";
		URI uri = URI.create(URIString);
		WebSocketClient client = new WebSocketClient();
		
		try
		{
			client.start();
			ClientSocket clientSocket = new ClientSocket();
			Future<Session> fut = client.connect(clientSocket, uri);
			
			//setting view
			JFrame frame = new JFrame();
			frame.setSize(200,300);
			frame.setLayout(new GridLayout(2, 1));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JButton startMissionBtn = new JButton("Start Mission");
			JButton endMissionBtn = new JButton("End Mission");
			frame.add(startMissionBtn);
			frame.add(endMissionBtn);
			frame.setVisible(true);
			
			//adding controls
			startMissionBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Gson gson = new Gson();
					JsonObject json = new JsonObject();
					json.addProperty("token", clientSocket.token);
					json.addProperty("type", "client");
					json.addProperty("action", "start mission");
					Mission m = new Mission(LocalDateTime.now());
					json.add("mission", gson.toJsonTree(m));
					try {
						clientSocket.getSession().getRemote().sendString(json.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			endMissionBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					JsonObject json = new JsonObject();
					json.addProperty("token", clientSocket.token);
					json.addProperty("action", "end mission");
					try {
						clientSocket.getSession().getRemote().sendString(json.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			
		}
		catch (Throwable t)
		{
			t.printStackTrace(System.err);
		}
	}
}
