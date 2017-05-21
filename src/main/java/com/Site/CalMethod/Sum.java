package com.Site.CalMethod;

import com.Site.AbstractCalMethod;
import com.Site.InterfaceBoolMarker;
import com.Site.InterfaceDoubleMarker;

public class Sum extends AbstractCalMethod implements InterfaceDoubleMarker, InterfaceBoolMarker{
	@Override
	/*public boolean commit(double[] vals, Double result) {
		if(vals.length==0) return false;
		result = 0.0;
		for (double d : vals) {
			result += d;
		}
		return true;
	}*/

	public double commit(double[] vals) {
		if(vals.length==0) return 0;
		double result = 0;
		for (double d : vals) {
			result += d;
		}
		return result;
	}
}
