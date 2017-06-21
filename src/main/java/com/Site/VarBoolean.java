package com.Site;

import java.util.Date;

public class VarBoolean extends AbstractVar {
    private boolean val;
    private boolean lastVal;
	
	public VarBoolean() {
		this("newVarB", "newVarB");
	}
	public VarBoolean(String id, String name) {
		super(id, name);
		this.val = false;
		this.lastVal = false;
	}
	@Override
	public boolean getBoolValue() {
		return val;
	}
	@Override
	public double getDouValue() {
		if(val==false) {
			return 0;
		}else {		
			return 1;
		}
	}
	@Override
	public String getValue() {
		return String.valueOf(val);
	}
	@Override
	public void setValue(boolean value) {
		this.val = value;
		this.setValueUpdatedTime(new Date());

		if(isValueChanged()==true) {
			this.setValueChangedTime(new Date());
			this.lastVal = this.val;
		}		
	}
	@Override
	public void setValue(String value) {
		value = value.trim();
		try {
			setValue(Double.valueOf(value));
		}catch(NumberFormatException e) {
			setValue(Boolean.valueOf(value));
		}
	}	
	@Override
	public void setValue(double value) {
		if(value==0) {
			setValue(false);
		}else {
			setValue(true);
		}
	}
	@Override
	public void walkJudger() {
		for (AbstractAlarmJudger aj : judgers) {
			aj.judge(val);
		}
	}
	@Override
	public void calValueFromChildren() {
		if(this.getChildrenCalMethod()==null) return;
		if(children.size()==0) return;
		
		boolean vals[] = new boolean[children.size()];
		for(int index = 0; index < children.size(); index++) {
			vals[index] = children.get(index).getBoolValue();
		}
		
		this.val = this.getChildrenCalMethod().commit(vals);
	}
	@Override
	public boolean isCalMethodMatch(AbstractCalMethod cm) {
		return (cm instanceof InterfaceBoolMarker);
	}
	@Override
	public boolean isJudgerMatch(AbstractAlarmJudger aj) {
		return (aj instanceof InterfaceBoolMarker);
	}
	@Override
	public boolean isValueChanged() {
		return (lastVal == val);
	}
}
