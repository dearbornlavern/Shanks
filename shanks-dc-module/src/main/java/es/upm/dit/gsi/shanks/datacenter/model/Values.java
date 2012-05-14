package es.upm.dit.gsi.shanks.datacenter.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values {
	

	
	
	public Values(ShanksSimulation sim) {
		super(sim);
	}
	// Failures probability
	public static final double SERVER_FAILURE_PROB = 0.001;
	
	// Network Elements names. 
	public static final String COMPUTER_ID = "ITCrowComputer#";
	public static final String DATA_CENTER_ROUTER_ID = "DataCenterRouter#";
	public static final String SERVER_ID = "Server#";
	public static final String LDAP_SERVER_ID = "LDAPServer";
	public static final String EXTERNAL_SERVER_ID = "ExternalServer";
	public static final String BBDD_SERVER_ID = "BBDDServer";
	public static final String BBDD_REPLICA_ID = "BBDDReplica";
	public static final String ETHERNET_ID = "Ethernet#";
	public static final String WEB_APP_ID= "Web App Server";
	public static final String WEB_PROXY_ID="WebProxy";
	
	// Scenario Values. 
	public static final double ETHERNET_LENGHT = 2.5;
	public static final int WIFI_CHANNELS = 64;
	public static final int NUMBER_OF_ITCROW = 3;
	

	//Services values.
	public static final String SERVICE_NOT_FOUND = "ServiceNotFound";
	public static final Object ACTION_NOT_AVAILABLE = "NotAvailableAction";

	// SysAdmin values
	public static final String SYSADMIN_BAYESIAN_NETWORK_PATH = "/shanks-dc-module/src/main/java/es/upm/dit/gsi/shanks/datacenter/agent/SysAdmin.net";
	
	public static final String SYSADMIN_REPAIR_NODENAME = "Reparar";
	public static final String SYSADMIN_SERVER_LOAD_NODENAME = "Server_load";
	public static final String SYSADMIN_ROUTER_LOAD_NODENAME = "Router_load";
	public static final String SYSADMIN_LOG_NODENAME = "log";
	public static final String SYSADMIN_ACTION_NODENAME = "Action";
	
	public static final String SYSADMIN_LOG_OK = "OK";
	public static final String SYSADMIN_LOG_WEIRD = "Weird";
	public static final String SYSADMIN_LOG_NOK = "NOK";
	
	public static final String SYSADMIN_ACTION_REPAIR = "Repair";
	public static final String SYSADMIN_ACTION_MANTEINANCE = "Manteinance";
	public static final String SYSADMIN_ACTION_PATCH = "Patch";
	public static final String SYSADMIN_ACTION_LOOKOUT = "Look_out"; 

}
