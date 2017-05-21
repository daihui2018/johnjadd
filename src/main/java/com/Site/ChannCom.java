package com.Site;

import jssc.SerialPort;
import jssc.SerialPortException;

public class ChannCom extends AbstractChann {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ChannCom.class);
	
	private SerialPort sp = null;
	private ChannComParam comParam = null;
	
	public ChannCom(ChannComParam param) {
		setParam(param);
	}
    
	@Override
	public void setParam(AbstractChannParam param) {
		if(!(param instanceof ChannComParam)) return;
		
		boolean needRefreshParam = false;
		if(comParam==null) {
			comParam = (ChannComParam)param;
			needRefreshParam = true;
		}else {
			if(comParam.isInTheSameChann(param)) {				
				comParam = (ChannComParam)param;
				needRefreshParam = true;
			}
		}
		
		if(needRefreshParam && isOpened()) {
			try {
				sp.setParams(comParam.getBaudRate(), comParam.getDataBits(), comParam.getStopBits(), comParam.getParity());
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean open() {
		if(comParam==null) {
			return false;
		}
		
		if(isOpened()) {
			try {
				sp.closePort();
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
		sp = null;
		
		sp = new SerialPort(comParam.getPortName());//("/dev/pts/3");
		
		try {
			sp.openPort();
		} catch (SerialPortException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		if(isOpened())
			System.out.println("channel opened: " + comParam );
		
		return isOpened();
	}

	@Override
	public void close() {
		if(isOpened()) {
			try {
				sp.closePort();
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
			System.out.println("channel closed: " + this.getParam() );
		}
	}

	@Override
	public void write(byte[] data){
		if(isOpened()) {
			try {
				sp.writeBytes(data);
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(getParam().getName() + " write: " + data);
	}

	@Override
	public byte[] read() {
		if(isOpened()) {
			try {
				return sp.readBytes();
			} catch (SerialPortException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
		//System.out.println(getParam().getName() + "is reading");
	}

	public boolean isOpened() {
		if(sp!=null) {
			return sp.isOpened();
		}
		return false;
	}

	@Override
	public AbstractChannParam getParam() {
		return comParam;
	}

}
