package es.upm.dit.gsi.shanks.model.element.link;

import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;

/**
 * Link class
 * 
 * This class represents the links than connect the different devices
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public class Link extends NetworkElement {
    private String id;
    private int status;
    private int type;
    private List<Device> linkedDeviceList;

    public Link(String id, int status, int type) {
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public String getID() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public List<Device> getDevices() {
        return linkedDeviceList;
    }

}