package org;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TOOLS {
	
	public String getAppName() {
		Context initialContext;
		String myApplicationName = "";
		try {
			initialContext = new InitialContext();
			myApplicationName = (String) initialContext.lookup("java:app/AppName");
System.out.println("init_PAIEMENT1 : ApplicationName="+myApplicationName);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myApplicationName;
	}

	public String getModuleName() {
		Context initialContext;
		String myModuleName = "";
		try {
			initialContext = new InitialContext();
			myModuleName = (String) initialContext.lookup("java:module/ModuleName");
System.out.println("init_PAIEMENT1 : ModuleName="+myModuleName);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myModuleName;
	}

}
