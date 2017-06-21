package com.Site;

import java.util.ArrayList;

public class ChannThread implements Runnable {
	private ArrayList<AbstractDev> devs = new ArrayList<AbstractDev>();
	static private boolean stop;
	
	public void run(){
		System.out.println("chanthread start to run");
		if(getChann()!=null) {
			System.out.println("chan in chanthread is " + getChann());
			getChann().open();
			System.out.println("chan in chanthread is opened");
		}
		
		while(!stop) {	
			for (AbstractDev dev : devs) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dev.refresh();
			}
			//loop done
		}
		
		if(getChann()!=null) {
			getChann().close();
		}
	}
	
	public void addDev(AbstractDev dev) {
		devs.add(dev);
	}
	public ArrayList<AbstractDev> getDevs() {
		return devs;
	}
	public void setDevs(ArrayList<AbstractDev> devs) {
		this.devs = devs;
	}
	public AbstractChann getChann() {
		AbstractChann chann = null;
		if(devs.size()>0) {
			if(devs.get(0) instanceof DevReal) {
				chann =((DevReal) devs.get(0)).getChann();
			}
		}
		return chann;
	}
	public static boolean isStop() {
		return stop;
	}
	public static void setStop(boolean stop) {
		ChannThread.stop = stop;
	}
	
}
