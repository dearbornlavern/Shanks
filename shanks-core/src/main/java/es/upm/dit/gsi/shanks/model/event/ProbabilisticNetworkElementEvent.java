package es.upm.dit.gsi.shanks.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import sim.engine.Steppable;

public abstract class ProbabilisticNetworkElementEvent extends ProbabilisticEvent{
    
    private List<NetworkElement> affectedElements;
    private HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> possibleAffected;

    private HashMap<String, Object> properties;
    private HashMap<String, Object> status;

    public ProbabilisticNetworkElementEvent(String name, Steppable generator,
            double prob) {
        super(name, generator, prob);
        
        this.affectedElements = new ArrayList<NetworkElement>();
        this.possibleAffected = new HashMap<Class<? extends NetworkElement>, HashMap<String,Object>>();
        this.properties = new HashMap<String, Object>();
        this.status = new HashMap<String, Object>();
        
        this.addPossibleAffected();
    }

    public abstract void addPossibleAffected();
        

    @Override
    public void changeProperties() throws UnsupportedNetworkElementStatusException {
        List<? extends NetworkElement> elements = this.affectedElements;
        for(NetworkElement el : elements){
            for(Class<?> c : possibleAffected.keySet()){
                if(c.equals(el.getClass())){                        
                    for(String s : possibleAffected.get(c).keySet()){
                        if(el.getProperties().containsKey(s)){
                            el.updatePropertyTo(s, possibleAffected.get(c).get(s));
                        }
                    }
                }
            }   
        }
        
    }

    public void changeStatus() throws UnsupportedNetworkElementStatusException {
        List<? extends NetworkElement> elements = this.affectedElements;
        for(NetworkElement el : elements){
            for(Class<?> c : possibleAffected.keySet()){
                if(c.equals(el.getClass())){                        
                    for(String s : possibleAffected.get(c).keySet()){
                        if(el.getStatus().containsKey(s)){
                            el.updateStatusTo(s, (Boolean) possibleAffected.get(c).get(s));
                        }
                    }
                }   
            }
        }
        
    }

    public abstract void interactWithNE();
    
    /**
     * @return the currentAffectedElements if the failure is active, null if not
     */
    public List<NetworkElement> getCurrentAffectedElements() {
            return affectedElements;
    }

    
    
    public void addAffectedElement(NetworkElement el){
        affectedElements.add(el);
    }
    
    public void addAffectedScenario(Scenario scen){
        
    }
    /**
     * Remove this element, but not modify the status. When the failure will be
     * deactive, this removed element will keep the actual status
     * 
     * @param element
     */
    public void removeAffectedElement(NetworkElement element) {
        this.affectedElements.remove(element);
    }

    /**
     * @return the possibleAffectedElements
     */
    public HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> getPossibleAffectedElements() {
        return possibleAffected;
    }

    /**
     * @param c
     */
    public void addPossibleAffectedProperties(Class<? extends NetworkElement> c, String property, Object value) {
        this.properties.put(property, value);
        this.possibleAffected.put(c, properties);
    }
    
    public void addPossibleAffectedStatus(Class<? extends NetworkElement> c, String state, Boolean value){
        this.status.put(state, value);
        this.possibleAffected.put(c, status);
    }

 
    /**
     * @param elementClass
     */
    public void removePossibleAffectedElements(Class<? extends NetworkElement> elementClass) {
        if (this.possibleAffected.containsKey(elementClass)) {
            this.possibleAffected.remove(elementClass);
        }
    }
    
    
    public List<?> getAffected() {
        return this.getCurrentAffectedElements();
    }
        

}
