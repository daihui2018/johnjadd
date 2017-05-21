package com.Site.Dev.Virtual;

import com.Site.AbstractVar;
import com.Site.DevVirtual;
import com.Site.VarBoolean;
import com.Site.VarDouble;
import com.Site.AlarmJudger.EqualBool;
import com.Site.CalMethod.Max;
import com.Site.CalMethod.Or;

public class LS extends DevVirtual {

	@Override
	public void createVars() {
		AbstractVar var = new VarBoolean("leak", "leak alarm");
		addVar(var);
		var.setChildrenCalMethod(new Or());
		var.addJudger(new EqualBool(true));
		
		var = new VarDouble("dist", "leak distance");
		addVar(var);
		var.setChildrenCalMethod(new Max());
	}
}
