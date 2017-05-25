package com.Site;

import java.lang.reflect.Field;
import java.util.ArrayList;
import com.Site.Dev.Virtual.LS;
import com.Site.Dev.Virtual.Site;
import com.Site.Dev.Virtual.WSD;

import jssc.SerialPort;

public class App 
{
	public static void main( String[] args )
    {
		try {
			Class<?> clazz = Class.forName("com.Site.DevReal");
						
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				System.out.println(field.getName());
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		//System.out.println(Loader.getResource(null));
        ///dev/ttyS0 /dev/ttyS1 9600 8 S1 N
        ///dev/pts/3 /dev/pts/3       
        //********************************************Create realDevs*********************************
        String portName1 = "/dev/ttyS6";
        String portName2 = "/dev/ttyS3";
        String portName3 = "/dev/ttyS5";
        int interval = 2000;
        
        ArrayList <DevReal> rdevs = new ArrayList<DevReal>();
        DevReal dev = null;
        Class<?> devClass = null;
        try {
			devClass = Class.forName("com.Site.Dev.Real.WSD_TH11");
			dev = (DevReal)(devClass.newInstance());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        if(dev!=null) {
	        dev.setId("wsd01");
	        dev.setName("temp 01");
	        dev.setChannParam(new ChannComParam(portName1, SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();         
        }
        rdevs.add(dev);
        DevReal wsd01 = dev;
        
        try {
			devClass = Class.forName("com.Site.Dev.Real.WSD_TH11");
			dev = (DevReal)(devClass.newInstance());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        if(dev!=null) {
	        dev.setId("wsd02");
	        dev.setName("temp 02");
	        dev.setChannParam(new ChannComParam(portName2, SerialPort.BAUDRATE_1200, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();     
        }
        rdevs.add(dev);
        DevReal wsd02 =dev;
        
        try {
			devClass = Class.forName("com.Site.Dev.Real.WSD_TH11");
			dev = (DevReal)(devClass.newInstance());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

        if(dev!=null) {
	        dev.setId("wsd03");
	        dev.setName("temp 03");
	        dev.setChannParam(new ChannComParam(portName3, SerialPort.BAUDRATE_4800, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();    
        }
        rdevs.add(dev);
        DevReal wsd03 = dev;
        
        try {
			devClass = Class.forName("com.Site.Dev.Real.MK_8053");
			dev = (DevReal)(devClass.newInstance());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

        if(dev!=null) {
	        dev.setId("mk01");
	        dev.setName("mk 01");
	        dev.setChannParam(new ChannComParam(portName2, SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();    
        }
        rdevs.add(dev);
        DevReal mk01 = dev;
        
        //********************************************Create virtualDevs*********************************
       /* WSD wsd = new WSD();
        wsd.setId("virtual WSD");
        wsd.setName("temperature");
        wsd.init();
        wsd.getVar("temp").addChild(wsd01.getVar("temp"));
        wsd.getVar("humi").addChild(wsd01.getVar("humi1"));
        wsd.refresh();
        System.out.println(wsd);
        
        LS ls = new LS();
        ls.setId("virtual LS1");
        ls.setName("water leak detector 1");
        ls.init();
        ls.getVar("leak").addChild(mk01.getVar("DI05"));
        ls.refresh();
        System.out.println(ls);
        
        LS ls1 = new LS();
        ls1.setId("virtual LS2");
        ls1.setName("water leak detector 2");
        ls1.init();
        ls1.getVar("leak").addChild(mk01.getVar("DI06"));
        ls1.refresh();
        System.out.println(ls1);
        
        Site site = new Site();
        site.setId("site");
        site.setName("Virtual Site");
        site.init();
        site.addChild(wsd);
        site.addChild(ls);
        site.addChild(ls1);
        */
        //***************************************************Create and run Threads
        //add real devs
        ArrayList<ChannThread> ths = CreateChannThreads(rdevs);
        //add virtual devs
        /*ChannThread vth = new ChannThread();
        vth.addDev(site);
        ths.add(vth);*/
        for (ChannThread th : ths) {
        	Thread thread = new Thread(th);
        	thread.start();
        }
        
        while(true) 
        {
	        try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        // System.out.println("MainThread is asking ChannThread to stop!~~~~~~~~~~~~~~");
        //ChannThread.setStop(true);
    }
	
	static private ArrayList<ChannThread> CreateChannThreads(ArrayList<DevReal> rdevs) {
		ArrayList<ChannThread> ths = new ArrayList<ChannThread>();
		AbstractChann currChann = null;
	    for (DevReal dev : rdevs) {
        	currChann = null;
        	//try to find the same chann from current th
        	ChannThread rightth = null;
        	for (ChannThread th : ths) {
				if(dev.getChannParam().isInTheSameChann(th.getChann().getParam())) {
					currChann = th.getChann();
					rightth = th;
					break;
				}
			}
        	
        	if(currChann==null) {
        		currChann = ChannFactory.createChann(dev.getChannParam());
        		rightth = new ChannThread();
        		ths.add(rightth);
        	}
        	
        	if(rightth!=null) {
        		dev.setChann(currChann);
        		rightth.addDev(dev);
        	}        	
		}
		
		return ths;		
	}
	
	
}
