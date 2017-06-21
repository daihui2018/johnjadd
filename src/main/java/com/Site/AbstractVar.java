package com.Site;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public abstract class AbstractVar {
	private String id;
	private String name;
	private AbstractDev dev;
	protected ArrayList<AbstractAlarmJudger> judgers = new ArrayList<AbstractAlarmJudger>();
	protected transient ArrayList<AbstractVar> children = new ArrayList<AbstractVar>();
	
	public abstract String getValue();
	public abstract boolean getBoolValue();
	public abstract double getDouValue();
	
	public abstract void setValue(double value);
	public abstract void setValue(boolean value);	
	public abstract void setValue(String value);
	
	public abstract boolean isValueChanged();
	
	private Date valueUpdatedTime;
	private Date valueChangedTime;
	//private boolean valueChanged;
	//private boolean available;
	private AbstractCalMethod childrenCalMethod;
	public abstract void calValueFromChildren();
	public abstract boolean isCalMethodMatch(AbstractCalMethod cm);
	
	public abstract boolean isJudgerMatch(AbstractAlarmJudger aj);
	public abstract void walkJudger();
	
	
	public AbstractVar() {
		this("newID", "newVar");
	}	
	public AbstractVar(String id, String name) {
		super();
		this.setId(id);
		this.setName(name);
		this.setValueUpdatedTime(new Date());
		this.setValueChangedTime(new Date());	
	}
	public void addChild(AbstractVar var) {
		if(var!=null) {
			children.add(var);
		}
	}
	public AbstractCalMethod getChildrenCalMethod() {
		return childrenCalMethod;
	}
	public void setChildrenCalMethod(AbstractCalMethod childrenCalMethod) {
		if(isCalMethodMatch(childrenCalMethod))
			this.childrenCalMethod = childrenCalMethod;
	}
	
	public void addJudger(AbstractAlarmJudger aj) {
		if(isJudgerMatch(aj)) 
			judgers.add(aj);
	}
	
	public String getFullId() {
		String fid = this.id;
				
		if(this.dev!=null) {
			fid = this.dev.getId() + "_" + fid;
		}
		return fid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public ArrayList<AbstractAlarmJudger> getJuders() {
		return judgers;
	}
	public void setJuders(ArrayList<AbstractAlarmJudger> judgers) {
		this.judgers = judgers;
	}	
	public Date getValueChangedTime() {
		return valueChangedTime;
	}
	protected void setValueChangedTime(Date valueChangedTime) {
		this.valueChangedTime = valueChangedTime;
	}
	public Date getValueUpdatedTime() {
		return valueUpdatedTime;
	}
	public void setValueUpdatedTime(Date valueUpdatedTime) {
		this.valueUpdatedTime = valueUpdatedTime;
		
		try {
			DataSender.sendDevByPut(this);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public AbstractDev getDev() {
		return dev;
	}
	public void setDev(AbstractDev dev) {
		this.dev = dev;
	}
	@Override
	public String toString() {
		return "AbstractVar [id=" + id + ", name=" + name + ", getValue()=" + getValue() + "]";
	}
	

}