package com.Site;

import java.util.ArrayList;
import jssc.SerialPort;
import jssc.SerialPortException;

public class ChannCom extends AbstractChann {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ChannCom.class);
	
	private SerialPort sp = null;
	private ChannComParam comParam = null;
	
	//running param
	private long delayToCheckComingData = 10;
	
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
		
		setDelayToCheckComingData(comParam.getDelayToCheckComingData());
	}

	protected long getDelayToCheckComingData() {
		return delayToCheckComingData;
	}

	protected void setDelayToCheckComingData(long delayToCheckComingData) {
		this.delayToCheckComingData = delayToCheckComingData;
	}

	@Override
	public boolean open() {
		if(comParam==null) {
			return false;
		}
		
		System.out.println("channel is trying to open: " + comParam );
		if(isOpened()) {
			try {
				sp.closePort();
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
		sp = null;
		System.out.println("try to create port name = " + comParam.getPortName());
		sp = new SerialPort(comParam.getPortName());
		System.out.println("port = " + comParam.getPortName() + "created!");
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
	}

	@Override
	public byte[] read() {	
		byte[] ret = null;
		ArrayList<Byte> bydata = new ArrayList<Byte>();
		if(isOpened()) {
			try {
				//return sp.readBytes();
				byte[] bybuf = null;
				while((bybuf = sp.readBytes())!=null) {
					for(byte by : bybuf) {
						bydata.add(by);
					}					
					Thread.sleep(getDelayToCheckComingData());					
				}
				
				int datalen = bydata.size();
				if(datalen>0) {
					ret = new byte[bydata.size()];
					for (int index=0; index<ret.length; index++) {
						ret[index] = bydata.get(index);
					}
				}				
			} catch (SerialPortException e) {
				e.printStackTrace();
				return null;
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ret;
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
