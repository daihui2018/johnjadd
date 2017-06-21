package com.Site;

import java.util.ArrayList;

public abstract class DevVirtual extends AbstractDev {
	private ArrayList<DevVirtual> children = new ArrayList<DevVirtual>();
	//public abstract void createVars();
		
	public DevVirtual() {
		super();
		setRefreshInterval(5000);
	}
		
	@Override
	public void refresh() {
		for(AbstractDev dev : children){
			dev.refresh();
		}
		
		try {
			Thread.sleep(this.getRefreshInterval());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (AbstractVar var : getVars().values()) {
			 var.calValueFromChildren();
		}
		
		super.refresh();
	}
	
	public void addChild(DevVirtual dev) {
		if(dev==null) return;
		if(children.contains(this)) return;
		
		children.add(dev);
		//dev.setParent(this);
	}
	public void removeChild(DevVirtual dev) {
		if(dev==null) return;
		if(!children.contains(this)) return;
		
		children.remove(dev);
		//dev.setParent(null);
	}	

	public ArrayList<DevVirtual> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<DevVirtual> children) {
		this.children = children;
	}

}
