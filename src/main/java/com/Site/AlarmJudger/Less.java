package com.Site.AlarmJudger;

import com.Site.InterfaceDoubleMarker;

public class Less extends Greater implements InterfaceDoubleMarker { 
	public Less(double threshold) {
		super(threshold);
	}

	@Override
	public boolean judge(double val) {
		if(val<thresholdDou) {
			return true;
		}
		return false;
	}

}
