package com.Site;

public class ChannFactory {
	public static AbstractChann createChann(AbstractChannParam param) {
		if(param instanceof ChannComParam) {
			return new ChannCom((ChannComParam)param);
		}
		return null;
	};
}
