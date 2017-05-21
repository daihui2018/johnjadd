package com.Site;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Math {
	public static String byte2Hex(byte data){
		String ret = "";
		
		String str = "";
		int intdata = data;
		if(intdata<0){//byte: -127~+127 转变为0~255
			intdata = intdata + 256;
		}
		while(intdata>0){
			switch(intdata%16){
				case 0: str = "0"; break;
				case 1: str = "1"; break;
				case 2: str = "2"; break;
				case 3: str = "3"; break;
				case 4: str = "4"; break;
				case 5: str = "5"; break;
				case 6: str = "6"; break;
				case 7: str = "7"; break;
				case 8: str = "8"; break;
				case 9: str = "9"; break;
				case 10: str = "A"; break;
				case 11: str = "B"; break;
				case 12: str = "C"; break;
				case 13: str = "D"; break;
				case 14: str = "E"; break;
				case 15: str = "F"; break;
			}
			ret = str + ret;
			intdata = intdata/16;
		}
		
		int cnt = ret.length();
		if(cnt==0){
			ret = "00";
		}else if(cnt==1){
			ret = 0 + ret;
		}
		return ret;
	}
	
	public static String bytes2Hex(byte[] data){
		String ret = "";
		for(int index=0; index<data.length; index++){
			ret = ret + byte2Hex(data[index]);
		}
		return ret;
	}
	
	public static String byte2Asc(byte data){
		String ret = (char)data + "";
		return ret;
	}
	
	public static String bytes2Asc(byte[] data){
		String ret = "";
		for(int index=0; index<data.length; index++){
			ret = ret + byte2Asc(data[index]);
		}
		return ret;
	}
	
	public static String bytes2Str(byte[] data, String dataFormat) {
		String ret = "";
		if(dataFormat.equalsIgnoreCase("hex")){
			ret = bytes2Hex(data);
		}else if(dataFormat.equalsIgnoreCase("ascii")){			
			ret = Math.bytes2Asc(data);
		}
		return ret;
	}
	
	public static ArrayList<Byte> hex2Bytes(String strdata){
		ArrayList<Byte> data = new ArrayList<Byte>();
		for(int index=0; index<(strdata.length())/2; index++){
			String tempdata = strdata.substring(index*2, index*2+2);
			data.add(new Byte((byte)Integer.parseInt(tempdata, 16)));
		}
		return data;
	}
	
	public static ArrayList<Byte> asc2Bytes(String strdata){
		ArrayList<Byte> data = new ArrayList<Byte>();
		for(int index=0; index<strdata.length(); index++){
			data.add(new Byte((byte)strdata.charAt(index)));
		}
		return data;
	}
	
	public static ArrayList<Byte> str2Bytes(String strData, String dataFormat){
		ArrayList<Byte> data = new ArrayList<Byte>();
		if(dataFormat.equalsIgnoreCase("hex")){
			data = hex2Bytes(strData);
		}else if(dataFormat.equalsIgnoreCase("ascii")){			
			data = asc2Bytes(strData);
		}
		return data;
	}
	
	public static int atoi(String val){
		char[] chrs = val.toCharArray();
		return atoi(chrs);
	}
	
	public static int atoi(char[] chrs){
		int ret = 0;
		int temp = 0;
		if(chrs.length <=8){
			for(int index=0; index<chrs.length; index++){
				temp = chrs[index];
				if(temp>='0' && temp<='9'){
					temp = temp - '0';
				}else if(temp>='A' && temp<='F'){
					temp = temp - 'A' + 10;
				}else{
					temp = 0;
					break; 
				}				   
		        ret = (ret<<4) + temp;
			}
		}
		
		return ret;
	}

	public static boolean isInteger(String str) {  
		Pattern pattern = Pattern.compile("^[+\\-]?\\d+$");
		Matcher matcher = pattern.matcher(str.trim());
		return matcher.matches();
    }

    public static boolean isNumeric(String str) {
    	Pattern pattern = Pattern.compile("^[+\\-]?((\\d*\\.\\d+)|(\\d+))$");
    	Matcher matcher = pattern.matcher(str.trim());
    	return matcher.matches();
    }
    
    public static String getUUID(){  
    	String ret = UUID.randomUUID().toString();//获取随机唯一标识符  
    	//去掉标识符中的"-"  
    	//ret = ret.substring(0, 8)+ret.substring(9,13)+ret.substring(14,18)+ret.substring(19,23)+ret.substring(24);  
    	ret = ret.replaceAll("-", "");
    	return ret;  
    }  
    
    public static String replaceWordInExpr(String expr, String org, String target){
    	String ret = expr;
    	Pattern pattern = Pattern.compile("(?<![a-zA-Z0-9_])"+ org +"(?![a-zA-Z0-9_])",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
    	Matcher matcher = pattern.matcher(expr);
    	ret = matcher.replaceAll(target);
    	return ret;
    }
}
