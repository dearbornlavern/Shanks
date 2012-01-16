package es.upm.dit.gsi.shanks.model.failure;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * DeviceErrors class
 * 
 * Make the possible erros of the devices
 * 
 * @author Daniel Lara
 * @author a.carrera
 * @version 0.1.1
 * 
 */

public abstract class Failure extends SimplePortrayal2D {
    
    private Logger logger = Logger.getLogger(Failure.class.getName());

    /** DeviceErrors parametres */
    private static final long serialVersionUID = -5684572432145540188L;
    
    private String id;
    private boolean active;

    private HashMap<NetworkElement,String> affectedElements;
    private HashMap<NetworkElement,String> oldStatesOfAffectedElements;
    private List<Class<? extends NetworkElement>> possibleAffectedElements;

    /**
     * Constructor of the class
     * 
     * @param id
     */
    public Failure(String id) {
        this.id = id;
        this.affectedElements = new HashMap<NetworkElement, String>();
        this.possibleAffectedElements = new ArrayList<Class<? extends NetworkElement>>();
        this.active = false;
    }


    /**
     * @return the id
     */
    public String getID() {
        return id;
    }

    /**
     * Used to activate a failure. All elements will be set with the affected status.
     * 
     */
    public void activateFailure() {
        if (!this.active) {
            Set<? extends NetworkElement> elements = this.affectedElements.keySet();
            for (NetworkElement element : elements) {
                try {
                    String oldStatus = element.getCurrentStatus();
                    this.oldStatesOfAffectedElements.put(element, oldStatus);
                    element.setCurrentStatus(affectedElements.get(element));
                } catch (UnsupportedNetworkElementStatusException e) {
                    logger.severe("Exception setting status: " + affectedElements.get(element) + " in element " + element.getID() + ". Exception: " + e.getMessage());
                }
            }
            this.active = true;   
        } else {
            logger.info("Failure " + this.getID() + " is already active.");
        }
    }

    /**
     * Used to deactivate a failure. All current affected elements will restore the old status.
     * 
     */
    public void deactivateFailure() {
        if (this.active) {
            Set<? extends NetworkElement> elements = this.affectedElements.keySet();
            for (NetworkElement element : elements) {
                try {
                    String oldStatus = this.oldStatesOfAffectedElements.get(element);
                    element.setCurrentStatus(oldStatus);
                } catch (UnsupportedNetworkElementStatusException e) {
                    logger.severe("Exception setting status: " + affectedElements.get(element) + " in element " + element.getID() + ". Exception: " + e.getMessage());
                }
                this.oldStatesOfAffectedElements.remove(element);
            }
            this.active = false;            
        } else {
            logger.info("Failure " + this.getID() + " is not active.");
        }
    }

    /**
     * @return
     */
    public boolean getStatus() {
        return active;
    }

    /**
     * @return the currentAffectedElements if the failure is active, null if not
     */
    public Set<NetworkElement> getCurrentAffectedElements() {
        if (this.active) {
            return affectedElements.keySet();   
        } else {
            return null;
        }
    }
    
    /**
     * @param element The element that will be affected by this failure when the failure will be active
     * @param affectedStatus The element status that will be set when the failure will be active
     * @return true if the status is possible, false if not
     */
    public boolean addAffectedElement (NetworkElement element, String affectedStatus) {
        Class<? extends NetworkElement> c = element.getClass();
        if (this.possibleAffectedElements.contains(c) && element.getPossibleStates().contains(affectedStatus)) {
            this.affectedElements.put(element, affectedStatus);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Remove this element, but not modify the status. When the failure will be deactive, this removed element will keep the actual status
     * 
     * @param element
     */
    public void removeAffectedElement (NetworkElement element) {
        this.affectedElements.remove(element);
    }

    /**
     * @return the possibleAffectedElements
     */
    public List<Class<? extends NetworkElement>> getPossibleAffectedElements() {
        return possibleAffectedElements;
    }
    
    /**
     * @param c
     */
    public void addPossibleAffectedElements (Class<? extends NetworkElement> c) {
        this.possibleAffectedElements.add(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.portrayal.SimplePortrayal2D#draw(java.lang.Object,
     * java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        final double width = 20;
        final double height = 20;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Problem generated ----> "
                + this.getClass().getName(), x - 3, y);
    }

}