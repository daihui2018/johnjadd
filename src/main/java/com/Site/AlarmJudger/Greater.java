package com.Site.AlarmJudger;

import com.Site.AbstractAlarmJudger;
import com.Site.InterfaceDoubleMarker;

public class Greater  extends AbstractAlarmJudger implements InterfaceDoubleMarker  {
	double thresholdDou;
	
	public Greater(double threshold) {
		thresholdDou = threshold;
	}
	@Override
	public boolean judge(String val) {
		try {
			double v = Double.parseDouble(val);
			return judge(v);
        }catch(Exception e) {
        	System.out.println(e);
        }		
		return false;
	}
	@Override
	public String getThreshold() {
		return String.valueOf(thresholdDou);
	}
	@Override
	public void setThreshold(String threshold) {
		double th = Double.parseDouble(threshold);
		thresholdDou = th;		
	}
	@Override
	public void setThreshold(double val) {
		thresholdDou = val;
	}
	@Override
	public boolean judge(double val) {
		if(val>thresholdDou) {
			return true;
		}
		return false;
	}

}
