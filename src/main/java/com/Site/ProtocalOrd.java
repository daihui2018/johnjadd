package com.Site;

import java.util.Date;

public class ProtocalOrd {
	private String id;
	private String param;
	private long leftMs;
		
	private Date markedTime;
	
	public ProtocalOrd(String id, String param) {
		this(id, param, 0);
	}
	
	public ProtocalOrd(String id, String param, long leftMircroSeconds) {
		super();
		this.id = id;
		this.param = param;
		this.leftMs = leftMircroSeconds;
		this.markedTime = new Date(); 
	}
	
	public void refreshLeftSeconds() {
		Date now = new Date();
		this.leftMs -= (now.getTime()-this.markedTime.getTime());
		this.markedTime = now; 
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public long getLeftMs() {
		return leftMs;
	}

	public void setLeftMs(long leftMs) {
		this.leftMs = leftMs;
	}

	@Override
	public String toString() {
		return "ProtocalOrd [id=" + id + ", param=" + param + ", leftMs=" + leftMs + ", markedTime=" + markedTime + "]";
	}
}
