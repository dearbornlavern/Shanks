package es.upm.dit.gsi.shanks.model.element.link.portrayal;

import javax.media.j3d.TransformGroup;

import sim.portrayal3d.network.SimpleEdgePortrayal3D;

/**
 * Links class
 * 
 * This class draw the connections between the devices
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */
public abstract class Link3DPortrayal extends SimpleEdgePortrayal3D {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3575152597887827354L;

    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {
        return super.getModel(object, model);
        //TODO check this
    }
}
