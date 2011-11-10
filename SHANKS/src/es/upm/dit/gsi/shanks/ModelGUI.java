package es.upm.dit.gsi.shanks;

import sim.engine.*;
import sim.field.continuous.Continuous2D;
import sim.display.*;
import sim.portrayal.Inspector;
import sim.portrayal.SimpleInspector;
import sim.portrayal.grid.*;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import javax.swing.*;
import sim.display3d.*;
import sim.portrayal3d.*;
import sim.portrayal3d.simple.*;
import sim.portrayal3d.grid.*;
import sim.portrayal3d.grid.quad.*;


import java.awt.*;
import java.util.logging.Logger;

/**
 * ModelGUI class
 * 
 * This class create the graphic interface
 * 
 * @author Daniel Lara
 * @version 0.1 
 *
 */

//The GUI of the model
public class ModelGUI extends GUIState {
	
	public Logger log = Logger.getLogger("ModelGUI");
	
	public Display2D display;
	public JFrame frame;
	SparseGridPortrayal2D agents = new SparseGridPortrayal2D();
	NetworkPortrayal2D links = new NetworkPortrayal2D();
	SparseGridPortrayal2D problems = new SparseGridPortrayal2D();



	public static void main(String[] args) {
		ModelGUI modelgui = new ModelGUI();
		modelgui.createController();

	}
	
	public static Image loadImage(String img){ 
    return new ImageIcon(Model.class.getResource(img)).getImage(); 
    }


	public ModelGUI() {
		super(new Model(System.currentTimeMillis()));
		log.finer("-> Constructor ModelGUI");
	}

	public ModelGUI(SimState state) {
		super(state);
	}

	public static String getName() {
		return "SHANKS Console Control";
	}

	public static Object getInfo() {
		return "<H2>Simulation of Heterogeneal and Autonomus</H2>";
	}

	public Object getSimulationInspectedObject() {
		return state;
	}
	
	public class ScenarioChoice{
		int selected = 0;
		public Object domScenarios(){ 
			return new Object[] { "FTTH", "PRUEBA"}; 
		}
		public int getSelectScenario(){ 
			return selected;
		}
		public void setSelectscenario(int val){
			if(val == 0){
				selected = val;
				Model.setSelectScenario("FTTH");
			}
			else if (val==1){
				selected = val;
				Model.setSelectScenario("PRUEBA");
			}
            
        //reattach the portrayals
        display.detatchAll();
        display.attach(agents,"Devices");
        display.attach(links, "Links");

        // redisplay
        if (display!=null) display.repaint();
        }
	}
	public Inspector getInspector() {
		
		log.fine("-> Inspector");
		
		final Inspector originalInspector = super.getInspector();
		final SimpleInspector scenarioInspector = new SimpleInspector(new ScenarioChoice(),this);
		
		originalInspector.setVolatile(true);
		
		Inspector newInspector = new Inspector(){
			public void updateInspector(){
				originalInspector.updateInspector();
			}
		};
		
		newInspector.setVolatile(false);
		
		Box b = new Box(BoxLayout.X_AXIS) {
        public Insets getInsets(){ 
        	return new Insets(2,2,2,2); 
        	}  // in a bit
        };
        
        b.add(newInspector.makeUpdateButton());
        b.add(Box.createGlue());
        
        log.info("Before Box b2");
        
        Box b2 = new Box(BoxLayout.Y_AXIS);
        b2.add(b);
        b2.add(scenarioInspector);
        b2.add(Box.createGlue());
    
    // all one blob now.  We can add it at NORTH.

        newInspector.setLayout(new BorderLayout());
        newInspector.add(b2,BorderLayout.NORTH);
        newInspector.add(originalInspector,BorderLayout.CENTER);

        log.fine("Inspector ->");
        
        return originalInspector;
    }
	
	
	
	

	public void start() {
		super.start();
		setupPortrayals(); 
	}
	
	//Setup the Portrayals
	public void setupPortrayals(){
		Model model = (Model) state;
		agents.setField(Model.elements);
		links.setField(new SpatialNetwork2D(Model.elements, model.links1));
		links.setPortrayalForAll(new Links());
		display.reset();
		display.setBackdrop(Color.white);
		display.repaint();
	}
	
	//Controller that create the displays
	 public void init(Controller c){
		 super.init(c);
		 display = new Display2D(600, 600, this, 1);
		 display.setClipping(false);
		 frame = display.createFrame();
		 frame.setTitle("Agent Simulation");
		 c.registerFrame(frame);
		 frame.setVisible(true);
		 display.attach(links, "Links");
		 display.attach(agents, "Agens");
		 
     }
	 
	 
	 
	 public void quit(){
		 super.quit();
     
		 if (frame!=null){
			frame.dispose();
		 	frame = null; 
		 	display = null;
		 }
     }


}

		