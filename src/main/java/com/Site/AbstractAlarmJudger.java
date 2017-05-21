package com.Site;

public abstract class AbstractAlarmJudger {
	public abstract String getThreshold(); 
	
	public abstract void setThreshold(String threshold);
	public void setThreshold(double val) {};
	public void setThreshold(boolean val) {};
	
	public abstract boolean judge(String val);
	public boolean judge(double val) {return false;};
	public boolean judge(boolean val) {return false;};
}
