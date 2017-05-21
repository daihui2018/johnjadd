package com.Site.Dev.Real;

import com.Site.DevReal;
import com.Site.Protocal;
import com.Site.VarDouble;

public class WSD_TH11 extends DevReal {
	@Override
	public void decode(Protocal pro) {
		/*if(pro.getId().equalsIgnoreCase("poll1")) {
			this.addOrd("ord1", "33");
		}else if(pro.getId().equalsIgnoreCase("ord1")) {
			this.addOrd("ord2", "33");
		}
		getVar("temp").setValue(23.4);
		getVar("humi").setValue(55.56);
		System.out.println(getVars());*/
	}

	@Override
	public void createVars() {
		addVar(new VarDouble("temp", "Temperature"));
		addVar(new VarDouble("humi", "Humility"));	
	}

	@Override
	public void createPros() {
		setDataFormat("HEX");
		setDataEnd("");
		
		String data = "01040000000271CB";
		addPro("poll1",  data);
	}

	@Override
	public String createOrds(String id, String param) {
		String ord = null;
		if(id.equalsIgnoreCase("ord1")) {
			ord = "01060000" + param;
		}else if(id.equalsIgnoreCase("ord2")) {
			ord = "01070000" + param;
		}
		return ord;
	}
	

}
