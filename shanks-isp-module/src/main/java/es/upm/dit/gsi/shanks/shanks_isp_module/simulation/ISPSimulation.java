package es.upm.dit.gsi.shanks.shanks_isp_module.simulation;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import sim.engine.Schedule;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.chart.chartPainter;
import es.upm.dit.gsi.shanks.workerroom.agent.RepairWireAgent;

public class ISPSimulation extends ShanksSimulation{

	private static final long serialVersionUID = 1778288778609950190L;
    private Properties configuration;

    public static final String CONFIGURATION = "Configuration";
	
	public ISPSimulation(long seed, Class<? extends Scenario> scenarioClass,
			String scenarioID, String initialState, Properties properties,
			Properties configPropertiesISPSimulation)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException, DuplicatedAgentIDException,
			DuplicatedActionIDException {
		super(seed, scenarioClass, scenarioID, initialState, properties);
		this.configuration = configPropertiesISPSimulation;
	}


    @Override
    public void registerShanksAgents() throws DuplicatedAgentIDException,
            DuplicatedActionIDException {
    	RepairWireAgent worker = new RepairWireAgent("Repair Wire Worker");
    	this.registerShanksAgent(worker);
      }
    
    public void addSteppables() {
		schedule.scheduleRepeating(Schedule.EPOCH, 3, new chartPainter(), 50);
	}
    
    
}
