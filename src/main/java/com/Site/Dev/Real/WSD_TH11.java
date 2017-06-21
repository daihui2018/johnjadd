package com.Site.Dev.Real;

import com.Site.DevReal;
import com.Site.MyMath;
import com.Site.Protocal;

public class WSD_TH11 extends DevReal {
	@Override
	public void decode(Protocal pro) {
		if(pro.getId().equalsIgnoreCase("poll1")) {
			byte[] data = pro.getRecvData();
			int len = data.length;

			if(len>0) {
				byte[] crc = MyMath.checkSumCRC16(data, 0, len-2);

				byte[] temp = {0,0};
				double val = 0.0;
				if(crc[0]==data[len-2] && crc[1]==data[len-1]) {
					
					temp[0] = data[3];
					temp[1] = data[4];
					val = MyMath.atoi(MyMath.bytes2Hex(temp))/10.0;
					getVar("0").setValue(val);
					
					temp[0] = data[5];
					temp[1] = data[6];
					val = MyMath.atoi(MyMath.bytes2Hex(temp))/10.0;
					getVar("1").setValue(val);
					
					//System.out.println("dev[" + this.getId() + "]'s temperature = " + this.getVar("0").getValue() + " humility = " + this.getVar("1").getValue());
				}
				
			}
			
		}else if(pro.getId().equalsIgnoreCase("ord1")) {
			this.addOrd("ord2", "33");
		}
	}

	@Override
	public void createPros() {
		setDataFormat("HEX");
		setDataEnd("");
		
		byte[] data = {0x01,0x04,0x00,0x00,0x00,0x02};
		
		int addr = Integer.parseInt(this.getParam().trim());
		if((addr>0) && (addr<=255)) {
			data[0] = (byte)addr;
			byte[] crc = MyMath.checkSumCRC16(data);
			data = (byte[])MyMath.resizeArray(data, data.length+2);
			data[6] = crc[0];
			data[7] = crc[1];
			addPro("poll1",  data);
		}		
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
