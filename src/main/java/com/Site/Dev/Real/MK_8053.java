package com.Site.Dev.Real;

import com.Site.AbstractVar;
import com.Site.DevReal;
import com.Site.Protocal;
import com.Site.VarBoolean;
import com.Site.Math;;

public class MK_8053 extends DevReal {

	@Override
	public void decode(Protocal pro) {
		byte[] recv = pro.getRecvData();
		if(recv==null || recv.length!=8) return;
		
		if(pro.getId().equalsIgnoreCase("8053_poll1")) {			
			char chrs[] = new char[4];
			for(int index=0; index<4; index++){
				chrs[index] = (char) recv[index+1];
			}
			
			int data = Math.atoi(chrs);
			for(int index=0; index<16; index++){
				AbstractVar var = this.getVar("DI" + index);
				if(var==null) continue;
				
				if(((data&(1<<index))>>index)==0){
					var.setValue(true);
				}else{
					var.setValue(false);
				}
				
				System.out.println(var.getName() + "=" + var.getValue());
			}
		}
	}
	
	@Override
	public void createVars() {
		addVar(new VarBoolean("DI0", "DI00 closed"));
		addVar(new VarBoolean("DI1", "DI01 closed"));
		addVar(new VarBoolean("DI2", "DI02 closed"));
		addVar(new VarBoolean("DI3", "DI03 closed"));
		addVar(new VarBoolean("DI4", "DI04 closed"));
		addVar(new VarBoolean("DI5", "DI05 closed"));
		addVar(new VarBoolean("DI6", "DI06 closed"));
		addVar(new VarBoolean("DI7", "DI07 closed"));
		addVar(new VarBoolean("DI8", "DI08 closed"));
		addVar(new VarBoolean("DI9", "DI09 closed"));
		addVar(new VarBoolean("DI10", "DI10 closed"));
		addVar(new VarBoolean("DI11", "DI11 closed"));
		addVar(new VarBoolean("DI12", "DI12 closed"));
		addVar(new VarBoolean("DI13", "DI13 closed"));
		addVar(new VarBoolean("DI14", "DI14 closed"));
		addVar(new VarBoolean("DI15", "DI15 closed"));		
	}

	@Override
	public void createPros() {
		setDataFormat("ASCII");
		setDataEnd("cr");
		
		addPro("8053_poll1", "$016");
	}
	
	public String createOrds(String id, String param) {
		String ord = "";
		
		return ord;
		
	}

}
