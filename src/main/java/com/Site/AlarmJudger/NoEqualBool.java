package com.Site.AlarmJudger;

import com.Site.InterfaceBoolMarker;

public class NoEqualBool extends EqualBool implements InterfaceBoolMarker {
	public NoEqualBool(boolean threshold) {
		super(threshold);
	}
	@Override
	public boolean judge(boolean val) {
		if(val!=thresholdBool)
			return true;
		return false;
	}

}
