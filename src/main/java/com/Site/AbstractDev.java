package com.Site;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDev {
	private String id = "newID";
	private String name = "newDev";
	private Map<String, AbstractVar> vars= new HashMap<String, AbstractVar>();
	private int refreshInterval = 1000;
	
	public abstract void createVars();
	
	public void init() {
		createVars();
	}	
	public void refresh() {
		for (AbstractVar var : vars.values()) {
			 var.walkJudger();
		}
		System.out.println("[" + this.getId() + "] is refreshed!");
	}	
	public void addVar(AbstractVar var) {
		if(var!=null) {
			if(!vars.containsKey(var.getId())) {
				vars.put(var.getId(), var);
			}
		}
	}
	public AbstractVar getVar(String id) {
		if(vars.containsKey(id)) {
			return vars.get(id);
		}
		return null;
	}
	public void setVaule(String varId, double val) {
		AbstractVar var = getVar(varId);
		if(var!=null) {
			var.setValue(val);
		}
	}
	public void setVaule(String varId, boolean val) {	
		AbstractVar var = getVar(varId);
		if(var!=null) {
			var.setValue(val);
		}
	}
	public void setVaule(String varId, String val) {	
		AbstractVar var = getVar(varId);
		if(var!=null) {
			var.setValue(val);
		}
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
	public Map<String, AbstractVar> getVars() {
		return vars;
	}
	public void setVars(Map<String, AbstractVar> vars) {
		this.vars = vars;
	}
	
	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	@Override
	public String toString() {
		return "Dev [id=" + id + ", name=" + name + ", vars=" + vars  + "]";
	}
}
