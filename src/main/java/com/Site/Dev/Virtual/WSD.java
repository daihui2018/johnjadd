package com.Site.Dev.Virtual;

import com.Site.AbstractVar;
import com.Site.DevVirtual;
import com.Site.VarDouble;
import com.Site.AlarmJudger.*;
import com.Site.CalMethod.*;

public class WSD extends DevVirtual {
	@Override
	public void createVars() {
		AbstractVar var = new VarDouble("temp", "Temperature");
		addVar(var);
		var.setChildrenCalMethod(new Max());
		var.addJudger(new Greater(30.0));
		var.addJudger(new Less(10.0));		
		
		var = new VarDouble("humi", "Humility");	
		addVar(var);
		var.setChildrenCalMethod(new Max());
		var.addJudger(new Greater(80.0));
		var.addJudger(new Less(10.0));		
	}
}
