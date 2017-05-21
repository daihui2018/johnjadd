package com.Site;

public abstract class AbstractChannParam {
	public abstract String getType();
	
	public String getName() {return getType();};
	
	public boolean isInTheSameChann(AbstractChannParam otherParam) {
		return (getName().equalsIgnoreCase(otherParam.getName()));
	};
}
