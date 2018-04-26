package model;

import java.util.Observable;

public class PiModel extends Observable{
	Mission mission = null;
	
	public void startMission(Mission mission)
	{
		this.mission = mission;
		
		Object[] msg = new Object[2];
		msg[0] = "start mission";
		msg[1] = mission;
		setChanged();
		notifyObservers(msg);
	}
	
	public void endMission()
	{
		this.mission = null;
		
		Object[] msg = new Object[1];
		msg[0] = "end mission";
		setChanged();
		notifyObservers(msg);;
	}
}
