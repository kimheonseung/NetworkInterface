package com.devheon.modules.networkinterface;

public class Main {
	
	public static void main(String[] args) {
		
		NIUtils niUtils = new NIUtils();
        niUtils.printNetworkInterfaces();

        if(args != null && args.length > 0) {
            final String interfaceName = args[0];
            System.out.println(niUtils.getHostAddressByInterfaceName(interfaceName));
        }
	}
}
