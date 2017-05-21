package com.Site.AlarmJudger;

import com.Site.AbstractAlarmJudger;
import com.Site.InterfaceBoolMarker;

public class EqualBool extends AbstractAlarmJudger implements InterfaceBoolMarker {
	boolean thresholdBool;
	
	public EqualBool(boolean threshold) {
		thresholdBool = threshold;
	}
	@Override
	public boolean judge(String val) {
		boolean v = Boolean.parseBoolean(val);
		return judge(v);
	}		
	@Override
	public String getThreshold() {
		return String.valueOf(thresholdBool);
	}	
	@Override
	public void setThreshold(boolean val) {
		thresholdBool = val;
	}
	@Override
	public void setThreshold(String threshold) {
		boolean th = Boolean.parseBoolean(threshold);
		thresholdBool = th;
	}
	@Override
	public boolean judge(boolean val) {
		if(val==thresholdBool) {
			return true;
		}
		return false;
	}

}
