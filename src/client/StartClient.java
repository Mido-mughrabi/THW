package client;

import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.client.WebSocketClient;
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
		}
		catch (Throwable t)
		{
			t.printStackTrace(System.err);
		}
	}
}
