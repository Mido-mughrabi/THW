package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class StartServer {
	public static final int SERVER_PORT = 3000;
	
	public static void main(String[] args) throws Exception{
		Server server = new Server(SERVER_PORT);
		ServletContextHandler ctx = new ServletContextHandler();
		ctx.setContextPath("/");
		ctx.addServlet(SocketServlet.class, "/servlets");
		
		server.setHandler(ctx);
		server.start();
		server.join();
	}

	public static class SocketServlet extends WebSocketServlet
	{
		@Override
		public void configure(WebSocketServletFactory factory) {
			factory.register(ServerSocket.class);
		}
	}
}
