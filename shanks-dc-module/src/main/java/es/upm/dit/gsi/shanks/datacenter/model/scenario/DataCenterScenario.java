package es.upm.dit.gsi.shanks.datacenter.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Router;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Server;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal.DataCenterScenario2DPortrayal;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal.DataCenterScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.EthernetLink;

public class DataCenterScenario extends Scenario {

	// TODO make that the status has influence.
	public static final String CLOUDY = "CLOUDY";
    public static final String SUNNY = "SUNNY";
	public static final String STATUS_NORMAL = "Normal";
	public static final String STATUS_UNDER_ATTACK = "UnderAttack";

	public DataCenterScenario(String id, String initialState,
			Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException {
		super(id, initialState, properties);
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new DataCenterScenario2DPortrayal(this, 100, 150);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new DataCenterScenario3DPortrayal(this, 100, 100, 100);
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(DataCenterScenario.STATUS_UNDER_ATTACK);
		this.addPossibleStatus(DataCenterScenario.STATUS_NORMAL);

	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		ArrayList<EthernetLink> links = new ArrayList<EthernetLink>();
		
		Router intranetRouter = new Router(Values.DATA_CENTER_ROUTER_ID, this);
		addNetworkElement(intranetRouter);
		
		Router webProxy = new Router(Values.WEB_PROXY_ID, this);
		links.add(new EthernetLink(Values.ETHERNET_ID+"0", Values.ETHERNET_LENGHT));
		webProxy.connectToDeviceWithLink(intranetRouter, links.get(links.size()-1));
		addNetworkElement(webProxy);

		// IT Crow terminals.
		
		for (int i = 0; i < Values.NUMBER_OF_ITCROW; i++) {
			Computer computer = (new Computer(Values.COMPUTER_ID + i));
			links.add(new EthernetLink(Values.ETHERNET_ID+i+10, Values.ETHERNET_LENGHT));
			computer.connectToDeviceWithLink(webProxy, links.get(i+1));
			addNetworkElement(computer);
		}

		
		// Servers
		Server ldap = new Server(Values.LDAP_SERVER_ID);
		links.add(new EthernetLink(Values.ETHERNET_ID+"1", Values.ETHERNET_LENGHT));
		ldap.connectToDeviceWithLink(webProxy, links.get(links.size()-1));
		addNetworkElement(ldap);
		
		Server webApp = new Server(Values.WEB_APP_ID);
		links.add(new EthernetLink(Values.ETHERNET_ID+"2", Values.ETHERNET_LENGHT));
		webApp.connectToDeviceWithLink(intranetRouter, links.get(links.size()-1));
		addNetworkElement(webApp);
		
//		Server external = new Server(Values.EXTERNAL_SERVER_ID);
//		links.add(new EthernetLink(Values.ETHERNET_ID+links.size(), Values.ETHERNET_LENGHT));
//		external.connectToDeviceWithLink(intranetRouter, links.get(links.size()-1));
//		addNetworkElement(external);
		
		Server bbdd = new Server(Values.BBDD_SERVER_ID);
		links.add(new EthernetLink(Values.ETHERNET_ID+"3", Values.ETHERNET_LENGHT));
		bbdd.connectToDeviceWithLink(webApp, links.get(links.size()-1));
		addNetworkElement(bbdd);
		
		Server bbddReplica = new Server(Values.BBDD_REPLICA_ID);
		links.add(new EthernetLink(Values.ETHERNET_ID+"4", Values.ETHERNET_LENGHT));
		bbddReplica.connectToDeviceWithLink(bbdd, links.get(links.size()-1));
		addNetworkElement(bbddReplica);

		//Adding links
		for(EthernetLink link: links){
				this.addNetworkElement(link);
		}

	}

	@Override
	public void addPossibleFailures() {
		
//		HashMap<String, NetworkElement> addedElements = this.getCurrentElements();
//		Set<NetworkElement> routerSet = new HashSet<NetworkElement>();
//		List<Set<NetworkElement>> computers = new ArrayList<Set<NetworkElement>>();
//		List<Set<NetworkElement>> servers = new ArrayList<Set<NetworkElement>>();
//		
//		for(String eName: addedElements.keySet()){
//			Set<NetworkElement> computerSet = new HashSet<NetworkElement>();
//			Set<NetworkElement> serverSet = new HashSet<NetworkElement>();
//			NetworkElement e = addedElements.get(eName);
//			if(!(e instanceof Link)){
//				routerSet.add(e);
//				if((e instanceof Computer)) {
//					if((e instanceof Server)){
//						serverSet.add(e);
//						servers.add(serverSet);
//					} else {
//						computerSet.add(e);
//						computers.add(computerSet);
//					}
//				}
//			}
//		}
//		this.addPossibleFailure(RouterFailure.class, routerSet);
//		this.addPossibleFailure(ComputerFailure.class, computers);
//		this.addPossibleFailure(ServerFailure.class, servers);
		
	}

	@Override
	public void addPossibleEvents() {
		// TODO Auto-generated method stub
	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws UnsupportedScenarioStatusException {
		
		HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
	    return penalties;
	}

}
