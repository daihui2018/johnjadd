package com.Site.Dev.Real;

import com.Site.AbstractVar;
import com.Site.DevReal;
import com.Site.Protocal;
import com.Site.MyMath;;

public class MK_8053 extends DevReal {

	@Override
	public void decode(Protocal pro){
		byte[] recv = pro.getRecvData();
		if(recv==null || recv.length!=8) return;
		
		if(pro.getId().equalsIgnoreCase("8053_poll1")) {			
			char chrs[] = new char[4];
			for(int index=0; index<4; index++){
				chrs[index] = (char) recv[index+1];
			}
			
			int data = MyMath.atoi(chrs);
			for(int index=0; index<16; index++){
				AbstractVar var = this.getVar("DI" + index);
				if(var==null) continue;
				
				if(((data&(1<<index))>>index)==0){
					var.setValue(true);
				}else{
					var.setValue(false);
				}
				
				//System.out.println("dev[" + this.getId() + "]'s " + var.getName() + "=" + var.getValue());
			}
		}
	}
	
	@Override
	public void createPros() {
		setDataFormat("ASCII");
		setDataEnd("cr");
		
		String data = "$016";
		int addr = Integer.parseInt(this.getParam().trim());
		if((addr>0) && (addr<=255)) {
			data = "$" + MyMath.byte2Hex((byte)addr) + "6";
			addPro("8053_poll1", data);
		}
	}
	
	public String createOrds(String id, String param) {
		String ord = "";
		
		return ord;
		
	}
}
