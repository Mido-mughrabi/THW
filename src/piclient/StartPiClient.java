package piclient;

import controller.PiController;
import model.PiModel;
import view.PiView;


public class StartPiClient {
	final static String HOST = "localhost";
	final static int PORT = 3000;
	
	public static void main(String[] args)
	{
		PiModel model = new PiModel();
		PiView view = new PiView();
		PiClientSocket piClientSockt = new PiClientSocket();
		PiController controller = new PiController(model,view,piClientSockt,HOST,PORT);
	}
}