package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Mission;
import model.Person;
import model.PiModel;

public class PiView extends JFrame implements Observer{

	private JPanel contentPane;
	
	public PiView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.add(new JLabel("Warte auf Einsatz"));
		setSize(100,600);
		setVisible(true);
	}
	
	public void resetView()
	{
		contentPane.removeAll();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof PiModel && arg instanceof Object[]) 
		{
			PiModel model = (PiModel) o;
			Object[] msg = (Object[]) arg;
			String change = (String) msg[0];
			switch (change) {
			case "start mission": 
			case "update mission":
				Mission mission = (Mission) msg[1];
				contentPane.removeAll();
				JLabel label = new JLabel("mission arrived !");
				contentPane.add(label);
				for(String p : mission.getEngaged().keySet())
				{
					JLabel personLabel = new JLabel(p + ":" + mission.getEngaged().get(p));
					contentPane.add(personLabel);
				}
				break;
			case "end mission":
				resetView();
				break;
			default:
				break;
			}
		}
		revalidate();
		repaint();
	}
}
