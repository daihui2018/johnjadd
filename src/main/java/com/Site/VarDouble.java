package com.Site;

import java.util.Date;

public class VarDouble extends AbstractVar {
	private double val;
	private double lastVal;
	private double filterVal;
	
	public VarDouble() {
		this("newVarDou", "newVarDou");
	}
	public VarDouble(String id, String name) {
		super(id, name);
		val = 0;
		lastVal = 0;
		filterVal = 1;
	}	
	@Override
	public double getDouValue() {
		return val;
	}	
	@Override
	public boolean getBoolValue() {
		return (val!=0);
	}	
	@Override
	public void setValue(String value) {
		value = value.trim();
		try {
			this.val = Double.valueOf(value);
		}catch(NumberFormatException e) {
			System.out.println(e);
			setValue( Boolean.valueOf(value));
		}
	}
	@Override
	public void setValue(double value) {
		this.val = value;
		this.setValueUpdatedTime(new Date());
		if(isValueChanged()==true) {
			this.setValueChangedTime(new Date());
			this.lastVal = this.val;			
		}
	}
	@Override
	public void setValue(boolean value) {
		if(value==false) {
			setValue(0f);
		}else {
			setValue(1f);
		}		
	}	
	@Override
	public String getValue() {		
		return String.valueOf(val);
	}
	@Override
	public boolean isJudgerMatch(AbstractAlarmJudger aj) {
		return (aj instanceof InterfaceDoubleMarker);
	}
	@Override
	public void walkJudger() {
		for (AbstractAlarmJudger aj : judgers) {
			if(aj!=null) {
				aj.judge(val);
			}
		}
	}
	@Override
	public void calValueFromChildren() {
		if(this.getChildrenCalMethod()==null) return;
		
		if(children.size()==0) return;
		double vals[] = new double[children.size()];
		for(int index = 0; index < children.size(); index++) {
			vals[index] = children.get(index).getDouValue();
		}
	
		this.val = this.getChildrenCalMethod().commit(vals);		
	}	
	@Override
	public boolean isValueChanged() {
		return (Math.abs(this.lastVal-this.val)>this.filterVal);
	}
	public double getLastVal() {
		return lastVal;
	}
	public void setLastVal(double lastVal) {
		this.lastVal = lastVal;
	}
	public double getFilterVal() {
		return filterVal;
	}
	public void setFilterVal(double filterVal) {
		this.filterVal = Math.abs(filterVal);
	}
	@Override
	public boolean isCalMethodMatch(AbstractCalMethod cm) {
		return(cm instanceof InterfaceDoubleMarker);
	}
}
