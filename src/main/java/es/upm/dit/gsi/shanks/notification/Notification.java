/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.notification;


/**
 * From time to time the user may want to use data generated by the simulation,
 * to analyze and take conclusions.
 * A notification is a trace left by interactions of agents, elements and the 
 * environment. This trace can be consulted on simulation time or at the end 
 * of it. 
 * 
 * There is two types of notifications in SHANKS. Both of them extends from this
 * interface. A raw notification is defined by 3 parameters.
 *    
 * - An unique identification id
 * - An integer that indicates the step number when the event was trigger.
 * - A source, that indicates the element of the simulation that is responsible
 * for this notification.         
 * 
 * @author darofar
 *
 */
public abstract class Notification {

    /**
     * Notification ID generated by NotificationManager when the notification
     * is created. 
     */
    private String id;
    
    /**
     * Step number in which the interaction occurs. 
     */
    private long when = -1;
    
    /**
     * Interaction source object.   
     */
    private Object source;

    /**
     * @param id
     * @param when
     * @param source
     */
    public Notification(String id, long when, Object source) {
        this.id = id;
        this.when = when;
        this.source = source;

    }

    /**
     * @return the Notification ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return the source object that originates the notification. 
     */
    public Object getSource() {
        return source;
    }

    /**
     * @return the step number in which the notification was created.
     */
    public long getWhen() {
        return when;
    }
}
