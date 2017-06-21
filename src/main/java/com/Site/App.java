package com.Site;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import jssc.SerialNativeInterface;
import jssc.SerialPort;

public class App 
{
	static ArrayList<PortDef> ports = new ArrayList<PortDef>();
	
	
	private static final String libVersion = "2.8"; //jSSC-2.8.0 Release from 24.01.2014
    private static final String libMinorSuffix = "0"; //since 0.9.0
	private static boolean isLibFolderExist(String libFolderPath) {
        boolean returnValue = false;
        File folder = new File(libFolderPath);
        if(folder.exists() && folder.isDirectory()){
            returnValue = true;
        }
        return returnValue;
    }

	 private static boolean isLibFileExist(String libFilePath) {
	        boolean returnValue = false;
	        File folder = new File(libFilePath);
	        if(folder.exists() && folder.isFile()){
	            returnValue = true;
	        }
	        return returnValue;
	    }

	    /**
	     * Extract lib to lib folder
	     *
	     * @param libFilePath
	     * @param osName
	     * @param libName
	     *
	     * @since 0.8
	     */
	    private static boolean extractLib(String libFilePath, String osName, String libName) {
	        boolean returnValue = false;
	        File libFile = new File(libFilePath);
	        InputStream input = null;
	        FileOutputStream output = null;
	        input = SerialNativeInterface.class.getResourceAsStream("/libs/" + osName + "/" + libName);
	        if(input != null){
	            int read;
	            byte[] buffer = new byte[4096];
	            try {
	                output = new FileOutputStream(libFilePath);
	                while((read = input.read(buffer)) != -1){
	                    output.write(buffer, 0, read);
	                }
	                output.close();
	                input.close();
	                returnValue = true;
	            }
	            catch (Exception ex) {
	                try {
	                    output.close();
	                    if(libFile.exists()){
	                        libFile.delete();
	                    }
	                }
	                catch (Exception ex_out) {
	                    //Do nothing
	                }
	                try {
	                    input.close();
	                }
	                catch (Exception ex_in) {
	                    //Do nothing
	                }
	            }
	        }
	        return returnValue;
	    }
	    
	    public static String getLibraryVersion() {
	        return libVersion + "." + libMinorSuffix;
	    }

	    public static String getLibraryBaseVersion() {
	        return libVersion;
	    }


	public static void main( String[] args )
    {
		/*String osName = System.getProperty("os.name");
        String architecture = System.getProperty("os.arch");
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String tmpFolder = System.getProperty("java.io.tmpdir");

        //since 2.3.0 ->
        String libRootFolder = new File(userHome).canWrite() ? userHome : tmpFolder;
        //<- since 2.3.0

        String javaLibPath = System.getProperty("java.library.path");//since 2.1.0
        
        */
		final String libVersion = "2.8"; //jSSC-2.8.0 Release from 24.01.2014
	    final String libMinorSuffix = "0"; //since 0.9.0

	    final int OS_LINUX = 0;
	    final int OS_WINDOWS = 1;
	    final int OS_SOLARIS = 2;//since 0.9.0
	    final int OS_MAC_OS_X = 3;//since 0.9.0
		int osType = -1;
		
        String libFolderPath;
        String libName;

        String osName = System.getProperty("os.name");
        String architecture = System.getProperty("os.arch");
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String tmpFolder = System.getProperty("java.io.tmpdir");

        //since 2.3.0 ->
        String libRootFolder = new File(userHome).canWrite() ? userHome : tmpFolder;
        //<- since 2.3.0

        String javaLibPath = System.getProperty("java.library.path");//since 2.1.0

        System.out.println("osName = " + osName);
        System.out.println("architecture = " + architecture);
        System.out.println("userHome = " + userHome);
        System.out.println("fileSeparator = " + fileSeparator);
        System.out.println("tmpFolder = " + tmpFolder);
        System.out.println("libRootFolder = " + libRootFolder);
        System.out.println("javaLibPath = " + javaLibPath);
        System.out.println("*************************************************");
        
        if(osName.equals("Linux")){
            osName = "linux";
            osType = OS_LINUX;
        }
        else if(osName.startsWith("Win")){
            osName = "windows";
            osType = OS_WINDOWS;
        }//since 0.9.0 ->
        else if(osName.equals("SunOS")){
            osName = "solaris";
            osType = OS_SOLARIS;
        }
        else if(osName.equals("Mac OS X") || osName.equals("Darwin")){//os.name "Darwin" since 2.6.0
            osName = "mac_os_x";
            osType = OS_MAC_OS_X;
        }//<- since 0.9.0

        if(architecture.equals("i386") || architecture.equals("i686")){
            architecture = "x86";
        }
        else if(architecture.equals("amd64") || architecture.equals("universal")){//os.arch "universal" since 2.6.0
            architecture = "x86_64";
        }
        else if(architecture.equals("arm")) {//since 2.1.0
            String floatStr = "sf";
            if(javaLibPath.toLowerCase().contains("gnueabihf") || javaLibPath.toLowerCase().contains("armhf")){
                floatStr = "hf";
            }
            else {
                try {
                    Process readelfProcess =  Runtime.getRuntime().exec("readelf -A /proc/self/exe");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(readelfProcess.getInputStream()));
                    String buffer = "";
                    while((buffer = reader.readLine()) != null && !buffer.isEmpty()){
                        if(buffer.toLowerCase().contains("Tag_ABI_VFP_args".toLowerCase())){
                            floatStr = "hf";
                            break;
                        }
                    }
                    reader.close();
                }
                catch (Exception ex) {
                    //Do nothing
                }
            }
            //architecture = "arm" + floatStr;
            architecture = "arm" + "hf";
        }
        
        libFolderPath = libRootFolder + fileSeparator + ".jssc" + fileSeparator + osName;
        libName = "jSSC-" + libVersion + "_" + architecture;
        libName = System.mapLibraryName(libName);
        
        System.out.println("osName = " + osName);
        System.out.println("architecture = " + architecture);
        System.out.println("userHome = " + userHome);
        System.out.println("fileSeparator = " + fileSeparator);
        System.out.println("tmpFolder = " + tmpFolder);
        System.out.println("libRootFolder = " + libRootFolder);
        System.out.println("javaLibPath = " + javaLibPath);
        System.out.println("libFolderPath = " + libFolderPath);
        System.out.println("libName = " + libName);
        
        if(libName.endsWith(".dylib")){//Since 2.1.0 MacOSX 10.8 fix
            libName = libName.replace(".dylib", ".jnilib");
        }

        boolean loadLib = false;

        if(isLibFolderExist(libFolderPath)){
            if(isLibFileExist(libFolderPath + fileSeparator + libName)){
                loadLib = true;
            }
            else {
                if(extractLib((libFolderPath + fileSeparator + libName), osName, libName)){
                    loadLib = true;
                }
            }
        }
        else {
            if(new File(libFolderPath).mkdirs()){
                if(extractLib((libFolderPath + fileSeparator + libName), osName, libName)){
                    loadLib = true;
                }
            }
        }

        if (loadLib) {
        	System.out.println("loading Lib... from " + libFolderPath + fileSeparator + libName);
            System.load(libFolderPath + fileSeparator + libName);
            System.out.println("loadLib ok!");
            String versionBase = getLibraryBaseVersion();
        }
        
        
        
        byte[] data = {0x01,0x04,0x00,0x00,0x00,0x02};
        byte[] crc = MyMath.checkSumCRC16(data);
		
		try {
			Class<?> clazz = Class.forName("com.Site.DevReal");
						
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				System.out.println(field.getName());
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	
		loadPortConfig("/home/ports.xml");
        //********************************************Create realDevs*********************************
        int interval = 1000;
        //测试CRC
        ArrayList <DevReal> rdevs = new ArrayList<DevReal>();
        DevReal dev = null;
        byte[] a = {1,2,3};
        a = (byte[])MyMath.resizeArray(a, 5);
        
        /*dev = RealDevFactory.createDev("WSD_TH11");
        if(dev!=null) {
	        dev.setId("wsd01");
	        dev.setName("temp 01");
	        dev.setParam("1");
	        dev.setChannParam(new ChannComParam(getPortID("com2"), SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();   
	        dev.setSuperFastMode(false);
        }
        rdevs.add(dev);
        DevReal wsd01 = dev;

        dev = RealDevFactory.createDev("WSD_TH11");
        if(dev!=null) {
	        dev.setId("wsd02");
	        dev.setName("temp 02");
	        dev.setParam("3");
	        dev.setChannParam(new ChannComParam(getPortID("com4"), SerialPort.BAUDRATE_1200, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();   
	        dev.setSuperFastMode(false);
        }
        rdevs.add(dev);
        DevReal wsd02 =dev;
*/
        dev = RealDevFactory.createDev("WSD_TH11");
        if(dev!=null) {
	        dev.setId("wsd03");
	        dev.setName("temp 03");
	        dev.setParam("2");
	        dev.setChannParam(new ChannComParam(getPortID("com1"), SerialPort.BAUDRATE_4800, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();    
	        dev.setSuperFastMode(false);
        }

        rdevs.add(dev);
        DevReal wsd03 = dev;
        
        dev = RealDevFactory.createDev("MK_8053");
        if(dev!=null) {
	        dev.setId("mk01");
	        dev.setName("mk 01");
	        dev.setParam("001");
	        dev.setChannParam(new ChannComParam(getPortID("com4"), SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();    
	        dev.setSuperFastMode(false);
        }
        rdevs.add(dev);
        DevReal mk01 = dev;
        
        System.out.println("Devs created successfully!");
        
        /*dev = RealDevFactory.createDev("SMS_SIMCOM");
        if(dev!=null) {
	        dev.setId("sms01");
	        dev.setName("sms 01");
	        dev.setChannParam(new ChannComParam(getPortID("modem"), SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1));
	        dev.setRefreshInterval(interval);
	        dev.init();    
        }
        rdevs.add(dev);
        DevReal sms01 = dev;
        */
        //********************************************Create virtualDevs*********************************
       /* 
        WSD wsd = new WSD();
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
        ArrayList<ChannThread> ths = CreateChannThreads(rdevs);
        System.out.println("ths created successfully!");
        
        for (ChannThread th : ths) {
        	Thread thread = new Thread(th);
        	thread.start();
        }
        
        System.out.println("ths are running!");
        
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
	
	static private void loadPortConfig(String xmlFileName) {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(xmlFileName);

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("serialPort");
		
			PortDef port = null;
			for (int index = 0; index < list.size(); index++) {
				Element node = (Element) list.get(index);
		   
				port = new PortDef();
				ports.add(port);
		   
				port.setId(node.getChildText("id"));
				port.setName(node.getChildText("name"));
				port.setRemark(node.getChildText("remark"));
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		
		System.out.println(ports);
	}	
	
	static private String getPortID(String portName) {
		String id = null;
		for (PortDef port : ports) {
			if(port.getName().equalsIgnoreCase(portName.trim())) {
				id = port.getId();
			}
		}
		return id;
	}
}
