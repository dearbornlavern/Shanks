package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import sim.display3d.Display3D;
import sim.field.continuous.Continuous3D;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import sim.portrayal3d.network.SpatialNetwork3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayID;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

/**
 * @author a.carrera
 * 
 */
public abstract class Scenario3DPortrayal extends ScenarioPortrayal{

    private Continuous3D devices;
    private Network links;
    private SpatialNetwork3D deviceLinkNetwork;

    public HashMap<String, Display3D> displayList;
    public HashMap<String, JFrame> frameList;
    
    public static final String MAIN_DISPLAY_ID = "MainDisplay";

    /**
     * The constructor needs the size of the simulation
     * 
     * @param length
     * @param width
     * @param height
     * @throws DuplicatedPortrayalID 
     */
    public Scenario3DPortrayal(Scenario scenario, long width, long height, long length) throws DuplicatedPortrayalID {
        super(scenario);
        this.devices = new Continuous3D(5, width, height, length);
        this.links = new Network();
        this.deviceLinkNetwork = new SpatialNetwork3D(this.devices, this.links);
        this.displayList = new HashMap<String, Display3D>();
        this.frameList = new HashMap<String, JFrame>();
        ContinuousPortrayal3D devicesPortrayal = new ContinuousPortrayal3D();
        NetworkPortrayal3D linksPortrayal = new NetworkPortrayal3D();

        devicesPortrayal.setField(this.devices);
        linksPortrayal.setField(deviceLinkNetwork);

        this.addPortrayal(Scenario3DPortrayal.MAIN_DISPLAY_ID, ScenarioPortrayal.DEVICES_PORTRAYAL, devicesPortrayal);
        this.addPortrayal(Scenario3DPortrayal.MAIN_DISPLAY_ID, ScenarioPortrayal.LINKS_PORTRAYAL, linksPortrayal);

        this.placeElements();
    }

    /**
     * @return
     */
    public HashMap<String, Display3D> getDisplayList() {
        return displayList;
    }

    /**
     * @param displayList
     */
    public void setDisplayList(HashMap<String, Display3D> displayList) {
        this.displayList = displayList;
    }
    
    /**
     * @param displayID
     * @param display
     * @throws DuplictaedDisplayID 
     */
    public void addDisplay(String displayID, Display3D display) throws DuplictaedDisplayID {
        if (this.displayList.containsKey(displayID)) {
            throw new DuplictaedDisplayID(displayID);
        }
        this.displayList.put(displayID, display);
    }
    
    /**
     * @param displayID
     * @return
     */
    public Display3D getDisplay(String displayID) {
        return this.displayList.get(displayID);
    }
    
    /**
     * @param displayID
     */
    public void removeDisplay(String displayID) {
        this.displayList.remove(displayID);
    }

    /**
     * @return
     */
    public HashMap<String, JFrame> getFrameList() {
        return frameList;
    }

    /**
     * @param frameList
     */
    public void setFrameList(HashMap<String, JFrame> frameList) {
        this.frameList = frameList;
    }
    
    /**
     * @param frameID
     * @param frame
     */
    public void addFrame(String frameID, JFrame frame) {
        this.frameList.put(frameID, frame);
    }
    
    /**
     * @param frameID
     */
    public void removeFrame(String frameID) {
        this.frameList.remove(frameID);
    }
    
    /**
     * Draw all elements of the simulation
     */
    abstract public void placeElements();

    /**
     * To place a device in the simulation
     * 
     * @param d
     * @param x
     * @param y
     * @param z
     */
    public void situateDevice(Device d, double x, double y, double z) {
        devices.setObjectLocation(d, new Double3D(x, y, z));
    }

    /**
     * To draw a link
     * 
     * @param link
     */
    public void drawLink(Link link) {
        List<Device> linkedDevices = link.getLinkedDevices();
        for (int i = 0; i<linkedDevices.size(); i++) {
            Device from = linkedDevices.get(i);
            for (int j = i+1 ; j<linkedDevices.size(); j++) {
                Device to = linkedDevices.get(j);
                Edge e = new Edge(from, to, link);
                links.addEdge(e);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getElements()
     */
    public Continuous3D getPlacedDevices() {
        return this.devices;
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#getLinks()
     */
    public Network getLinks() {
        return this.links;
    }

}