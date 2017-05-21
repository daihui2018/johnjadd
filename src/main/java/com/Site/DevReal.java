package com.Site;

import java.util.ArrayList;

public abstract class DevReal extends AbstractDev {	
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DevReal.class);
	
	private ArrayList<Protocal> pros = new ArrayList<Protocal>();
	private ArrayList<ProtocalOrd> ords = new ArrayList<ProtocalOrd>();
	
	private AbstractChann chann;
	private AbstractChannParam channParam;
	
	private String dataFormat = "ASCII";
	private String dataEnd = "cr";
	
	public abstract void decode(Protocal pro);
	
	public abstract void createVars();
	public abstract void createPros();
	
	public void init() {
		super.init();
		createPros();
	}
	public String createOrds(String id, String param) {
		return null;
	}	
	public DevReal() {
		this(null,null);	
	}
	public DevReal(AbstractChann chann, AbstractChannParam channParam) {
		super();
		this.chann = chann;
		this.channParam = channParam;
	}
	@Override
	public void refresh() {
		for (Protocal pro : pros) {
			commitOrd();
			commitPro(pro);
		}
		super.refresh();
	}
	
	private void commitPro(Protocal pro) {
		if(pro==null) return;
		
		poll(pro);
		try {
			Thread.sleep(getRefreshInterval());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fill(pro);
		
		System.out.println("pro =" + pro);
		
		try {
			logger.info("Start to decode pro: " + pro.getId());
			decode(pro);
		}catch(Exception e) {
			logger.error("fail to decode pro: " + this.getId() + "." + pro.getId() + " err: " + e.getMessage());
			System.out.println(e + " in " + this.getId() + " func: decode");
		}
	}
	private void commitOrd() {
		String strOrd = "";
		Protocal pro = null;
		ProtocalOrd readyOrd = getReadyOrd();
		
		if(readyOrd!=null) {
			strOrd = createOrds(readyOrd.getId(), readyOrd.getParam());
			pro = new Protocal(this, readyOrd.getId(), strOrd);
			commitPro(pro);
			ords.remove(readyOrd);
		
			commitOrd();
		}
	}	
	private void poll(Protocal pro) {
		AbstractChann chann = getChann();
		if(chann==null) return;
		if(pro==null) return;
		byte[] data = pro.getSendData();
		chann.setParam(getChannParam());
		chann.write(data);;
	}
	private void fill(Protocal pro) {
		AbstractChann chann = getChann();
		if(chann==null) return;
		if(pro==null) return;
		pro.setRecvData(chann.read());
	}	
	private ProtocalOrd getReadyOrd() {		
		for(ProtocalOrd ord : ords) {
			if(ord==null) continue;
			ord.refreshLeftSeconds();
			if(ord.getLeftMs()<=0) {
				return ord;
			}
		}	
		return null;
	}
	public void addPro(Protocal pro) {
		if(pro!=null)
			pros.add(pro);
	}
	public void addPro(String id, String sendData) {
		addPro(new Protocal(this, id, sendData));
	}
	public ArrayList<Protocal> getPros() {
		return pros;
	}
	public void setPros(ArrayList<Protocal> pros) {
		this.pros = pros;
	}
	
	public void addOrd(ProtocalOrd ord) {
		if(ord!=null)
			ords.add(ord);
	}
	public void addOrd(String id, String param, long leftMicroSeconds) {
		addOrd(new ProtocalOrd(id, param, leftMicroSeconds));
	}
	public void addOrd(String id, String param) {
		addOrd(new ProtocalOrd(id, param));
	}
	public ArrayList<ProtocalOrd> getOrds() {
		return ords;
	}
	public void setOrds(ArrayList<ProtocalOrd> ords) {
		this.ords = ords;
	}
	public AbstractChann getChann() {
		return chann;
	}
	public void setChann(AbstractChann chann) {
		this.chann = chann;
	}
	
	public AbstractChannParam getChannParam() {
		return channParam;
	}
	public void setChannParam(AbstractChannParam channParam) {
		this.channParam = channParam;
	}	
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		if(dataFormat.equalsIgnoreCase("ascii")) {
			this.dataFormat = "ASCII";
		}else {
			this.dataFormat = "HEX";
		}
	}
	public String getDataEnd() {
		return dataEnd;
	}
	public void setDataEnd(String dataEnd) {
		this.dataEnd = dataEnd;		
	}
}
