package com.Site.CalMethod;

import com.Site.AbstractCalMethod;
import com.Site.InterfaceBoolMarker;

public class Or extends AbstractCalMethod implements InterfaceBoolMarker {
	@Override
	/*public boolean commit(boolean[] vals, Boolean result) {
		if(vals.length==0) return false;
		
		result = vals[0];
		for (boolean val : vals) {
			if(val==true) {
				result = true;
				return true;
			}
		}
		return false;
	}*/
	public boolean commit(boolean[] vals) {
		if(vals.length==0) return false;
		
		for (boolean val : vals) {
			if(val==true) {
				return true;
			}
		}
		return false;
	}

}
