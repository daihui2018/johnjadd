package com.Site;

import jssc.SerialPort;

public class ChannComParam extends AbstractChannParam {
	private String portName;
	private int baudRate;
	private int parity;
	private int dataBits;
	private int stopBits;
	private long delayToCheckComingData;
	

	public ChannComParam() {
		super();		
	}
	
	public ChannComParam(String portName) {
		this(portName, SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1);
	}

	public ChannComParam(String portName, int baudRate, int parity, int dataBits, int stopBits) {
		super();
		
		this.portName = portName;
		this.baudRate = baudRate;
		this.parity = parity;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		
		calDelayToCheckComingData();
	}

	private long calDelayToCheckComingData() {
		return 10;
	}
	@Override
	public String toString() {
		return "ChannComParam [port=" + portName + ", baudRate=" + baudRate + ", parity=" + parity + ", dataBits="
				+ dataBits + ", stopBits=" + stopBits + "]";
	}

	@Override
	public String getType() {
		return "com";
	}
	@Override
	public String getName() {
		return super.getName() + portName;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}


	public int getDataBits() {
		return dataBits;
	}

	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	public int getStopBits() {
		return stopBits;
	}

	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}

	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}
	
	protected long getDelayToCheckComingData() {
		return delayToCheckComingData;
	}
}
