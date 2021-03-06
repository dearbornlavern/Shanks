/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
import es.upm.dit.gsi.shanks.agent.exception.DuplictaedDisplayIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplictedFrameIDException;

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
    
    private HashMap<String,Boolean> scaled;

    /**
     * The constructor needs the size of the simulation
     * 
     * @param length
     * @param width
     * @param height
     * @throws ShanksException 
     */
    public Scenario3DPortrayal(Scenario scenario, long width, long height, long length) throws ShanksException {
        super(scenario);
        this.scaled = new HashMap<String,Boolean>();
        this.devices = new Continuous3D(5, width, height, length);
        this.links = new Network();
        this.deviceLinkNetwork = new SpatialNetwork3D(this.devices, this.links);
        this.displayList = new HashMap<String, Display3D>();
        this.frameList = new HashMap<String, JFrame>();
        ContinuousPortrayal3D devicesPortrayal = new ContinuousPortrayal3D();
        NetworkPortrayal3D linksPortrayal = new NetworkPortrayal3D();

        devicesPortrayal.setField(this.devices);
        linksPortrayal.setField(this.deviceLinkNetwork);

        this.addPortrayal(Scenario3DPortrayal.MAIN_DISPLAY_ID, ScenarioPortrayal.DEVICES_PORTRAYAL, devicesPortrayal);
        this.addPortrayal(Scenario3DPortrayal.MAIN_DISPLAY_ID, ScenarioPortrayal.LINKS_PORTRAYAL, linksPortrayal);

        this.addPortrayals();
        this.placeElements();
    }

    /**
     * Add extra portrayals to one display of the simulation
     */
    abstract public void addPortrayals();

    /**
     * @return Map with key: Display name and value: Display3D object
     */
    public HashMap<String, Display3D> getDisplays() {
        return displayList;
    }

    /**
     * @param displays
     */
    public void setDisplayList(HashMap<String, Display3D> displays) {
        this.displayList = displays;
    }
    
    /**
     * @param displayID
     * @param display
     * @throws ShanksException 
     */
    public void addDisplay(String displayID, Display3D display) throws ShanksException {
        if (this.displayList.containsKey(displayID)) {
            throw new DuplictaedDisplayIDException(displayID);
        }
        this.displayList.put(displayID, display);
        this.scaled.put(displayID, false);
    }
    
    /**
     * @param displayID
     * @return Display3D object
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
     * @return Map with key: JFrame name and value: JFrame object
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
     * @throws DuplictedFrameIDException 
     */
    public void addFrame(String frameID, JFrame frame) throws ShanksException {
        if (this.frameList.containsKey(frameID)) {
            throw new DuplictedFrameIDException(frameID);
        } else {
            this.frameList.put(frameID, frame);            
        }
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

    /**
     * @param d
     */
    public void scaleDisplay(String displayID, double d) {
        if (this.scaled.containsKey(displayID) && !this.scaled.get(displayID)) {
            Display3D mainDisplay = this.getDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID);
            mainDisplay.scale(d);
            this.scaled.put(displayID, true);
        }
    }

    /**
     * @param d
     */
    public void rescaleDisplay(String displayID, double d) {
        if (this.scaled.containsKey(displayID)) {
            Display3D mainDisplay = this.getDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID);
            mainDisplay.scale(d);
            this.scaled.put(displayID, true);
        }
    }

}
