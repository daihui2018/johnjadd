package com.Site.CalMethod;

import com.Site.AbstractCalMethod;
import com.Site.InterfaceDoubleMarker;

public class Max  extends AbstractCalMethod implements InterfaceDoubleMarker {
	@Override
	/*public boolean commit(double[] vals, Double result) {
		if(vals.length==0) return false;
		result = vals[0];
		for (double d : vals) {
			if(result < d) {
				result = d;
			}
		}
		return true;
	}*/
	public double commit(double[] vals) {
		if(vals.length==0) return 0;
		double result = vals[0];
		for (double d : vals) {
			if(result < d) {
				result = d;
			}
		}
		return result;
	}

}
