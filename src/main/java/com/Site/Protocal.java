package com.Site;

import java.util.ArrayList;
import java.util.Arrays;

public class Protocal {
	private String id;	
	private byte[] sendData;
	private byte[] recvData;
	private DevReal dev;

	public Protocal(DevReal dev, String id) {
		this.id = id;
		this.dev = dev;
	}

	public Protocal(DevReal dev, String id, String sendData) {
		this(dev, id);
		setSendData(sendData);
	}
	public byte[] getSendData() {
		return sendData;
	}
	public void setSendData(String strData) {
		if(dev==null) return;
		
		String dataFormat = dev.getDataFormat();
		String dataEnd = dev.getDataEnd();
		
		if(dataEnd.equalsIgnoreCase("cr")){
			strData = strData + (char)0x0D;
		}else if(dataEnd.equalsIgnoreCase("lf")){
			strData = strData + (char)0x0A;
		}else if(dataEnd.equalsIgnoreCase("cr+lf")){
			strData = strData + (char)0x0D + (char)0x0A;
		}else if(dataEnd.equalsIgnoreCase("lf+cr")){
			strData = strData + (char)0x0A + (char)0x0D;
		}
		
		ArrayList<Byte> data = Math.str2Bytes(strData, dataFormat);
		sendData = new byte[data.size()];
		for(int i=0; i<data.size(); i++) {
			sendData[i] = data.get(i);
		}
	}
	public String getRecvData(String dataFormat) {
		return Math.bytes2Str(recvData, dataFormat);
	}
	public void setRecvData(byte[] data) {
		recvData = null;	//这里会内存泄漏吗？
		if(data==null) return;
		if(data.length==0) return;
		
		recvData = new byte[data.length];
		for(int i=0; i<data.length; i++) {
			recvData[i] = data[i];
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getRecvData() {
		return recvData;
	}
	public void setSendData(byte[] sendData) {
		this.sendData = sendData;
	}
	@Override
	public String toString() {
		return "Protocal [id=" + id + ", sendData=" + Arrays.toString(sendData) + ", recvData="
				+ Arrays.toString(recvData) + "]";
	}
}
