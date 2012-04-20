package es.upm.dit.gsi.shanks.model.event;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sim.engine.SimState;
import sim.engine.Steppable;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.networkelement.PeriodicNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.event.scenario.PeriodicScenarioEvent;
import es.upm.dit.gsi.shanks.model.event.scenario.ProbabilisticScenarioEvent;
import es.upm.dit.gsi.shanks.model.event.test.MyPeriodicNetElementEvent;
import es.upm.dit.gsi.shanks.model.event.test.MyPeriodicScenarioEvent;
import es.upm.dit.gsi.shanks.model.event.test.MyProbNetElementEvent;
import es.upm.dit.gsi.shanks.model.event.test.MyProbScenarioEvent;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class EventTest {
    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));

    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    
    @Test
    public void createPeriodicNE(){
        Steppable generator = new Steppable() {
         
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        PeriodicNetworkElementEvent pe = new MyPeriodicNetElementEvent(null, generator, 0);
        
   
        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(500, pe.getPeriod());
 
    }
    
    @Test
    public void createPeriodicNEAndLaunchIt() throws UnsupportedNetworkElementStatusException{
        Steppable generator = new Steppable() {
            
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        PeriodicNetworkElementEvent pe = new MyPeriodicNetElementEvent(null, generator, 0);
        

        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(500, pe.getPeriod());
        
        Device dev = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Assert.assertEquals("MyDevice", dev.getID());
        Assert.assertTrue(dev.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(dev.isGateway());
        
        pe.addAffectedElement(dev);
        pe.changeProperties();
        dev.checkStatus();
        
        Assert.assertEquals("MyDevice", dev.getID());
        Assert.assertTrue(dev.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertFalse(dev.getStatus().get(MyDevice.OK_STATUS));

    }
    
    @Test
    public void createProbNE(){
        Steppable generator = new Steppable() {
         
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        ProbabilisticNetworkElementEvent pe = new MyProbNetElementEvent(null, generator, 0);
        

        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(0.50, pe.getProb());
 
    }
    
    @Test
    public void createProbNEAndLaunchIt() throws UnsupportedNetworkElementStatusException{
        Steppable generator = new Steppable() {
            
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        ProbabilisticNetworkElementEvent pe = new MyProbNetElementEvent(null, generator, 0);
        

        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(0.50, pe.getProb());
 
        Device dev = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Assert.assertEquals("MyDevice", dev.getID());
        Assert.assertTrue(dev.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(dev.isGateway());
        
        pe.addAffectedElement(dev);
        pe.changeProperties();
        dev.checkStatus();
        
        Assert.assertEquals("MyDevice", dev.getID());
        Assert.assertTrue(dev.getStatus().get(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertFalse(dev.getStatus().get(MyDevice.OK_STATUS));
    }
    
    @Test
    public void createProbScenarioEvent(){
        Steppable generator = new Steppable() {
            
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        
        ProbabilisticScenarioEvent pe = new MyProbScenarioEvent(null, generator, 0);
        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(0.50, pe.getProb());
        
        
    }
    
    @Test
    public void createProbScenarioEventAndLaunchaIt() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException{
        Steppable generator = new Steppable() {
            
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        
        ProbabilisticScenarioEvent pe = new MyProbScenarioEvent(null, generator, 0);

        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(0.50, pe.getProb());
        
        Scenario scen = new MyScenario("MyScenario", MyScenario.SUNNY, null);
        Assert.assertEquals("MyScenario", scen.getID());
        Assert.assertEquals(MyScenario.SUNNY, scen.getCurrentStatus());
        Assert.assertNull(scen.getProperties());
        
        pe.addAffectedScenario(scen);
        pe.changeStatus();
        Assert.assertEquals(MyScenario.CLOUDY, scen.getCurrentStatus());
        
    }
    
    @Test
    public void createPeriodicScenarioEvent(){
        Steppable generator = new Steppable() {
            
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        
        PeriodicScenarioEvent pe = new MyPeriodicScenarioEvent(null, generator, 0);

        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(500, pe.getPeriod());
        
        
    }
    
    @Test
    public void createPeriodicScenarioEventAndLaunchaIt() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException{
        Steppable generator = new Steppable() {
            
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
           
                
            }
        };
        
        PeriodicScenarioEvent pe = new MyPeriodicScenarioEvent(null, generator, 0);

        Assert.assertEquals(generator, pe.getLauncher());
        Assert.assertEquals(500, pe.getPeriod());
        
        Scenario scen = new MyScenario("MyScenario", MyScenario.SUNNY, null);
        Assert.assertEquals("MyScenario", scen.getID());
        Assert.assertEquals(MyScenario.SUNNY, scen.getCurrentStatus());
        Assert.assertNull(scen.getProperties());
        
        pe.addAffectedScenario(scen);
        pe.changeStatus();
        Assert.assertEquals(MyScenario.CLOUDY, scen.getCurrentStatus());
        
    }
    
   
}