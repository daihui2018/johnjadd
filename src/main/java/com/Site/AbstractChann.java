package com.Site;

public abstract class AbstractChann{
	public abstract boolean open();
	public abstract void close();	
	
	public abstract void write(byte[] data);
	public abstract byte[] read();
	
	public abstract void setParam(AbstractChannParam param);
	public abstract AbstractChannParam getParam();
	public abstract boolean isOpened();

}
