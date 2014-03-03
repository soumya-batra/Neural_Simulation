/**
 * Visualizes a Neural Network in GUI
 */
package neuron;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import neuron.Neuron.State;

/**
 * @author Soumya Batra
 *
 */
@SuppressWarnings("serial")
public class NetworkVisualizer extends JPanel {
	
	private LinkedHashMap<Integer,LinkedHashMap<Integer,State>> h;
	private JTable table;
	private JScrollPane scroller;
	private String[] columns = {"Element","Current Potential","Current Stage"};
	private int timeStep = 1;
	private JLabel timeStepLabel;
	private static String text = "Enter time step after which change is to be observed";
	private JFormattedTextField textField;
	private NumberFormat intFormat;
	private JButton enterButton;
	
	public class graphics extends JComponent{
		Neuron source;
		graphics(Neuron s) {
            setPreferredSize(new Dimension(500, 500));
            source = s;
        }
		
		//Paints Neurons and their links on GUI
	    public void paintComponent(Graphics g){
	    	
	    	int x1 = 100, y1 = 100, x2 = 0, y2 = 0;
	    	
	    super.paintComponents(g);

	    g.fillOval(x1,y1, 10, 10);
	    LinkedHashMap<Integer,State> h1 = new LinkedHashMap<Integer,State>();
	    h1.put(source.getId()+1, source.s);
	    
	    g.drawString(((Integer)(source.getId()+1)).toString(), x1-10, y1+15);
	    
	    paintNeurons(g,x1,y1,x2,y2,source.getLinks());

	    }
	    
	    public void paintNeurons(Graphics g,int x1, int y1 ,int x2, int y2, ArrayList<Neuron> n)
	    {
	    	
	    	x2 = x1 + 25;    	
		    for(Neuron n1:n)
		    {
		    y2 = 100 + (n1.getId()-2)*25;
		    g.fillOval(x2,y2, 10, 10);
		    
		    
		    g.drawString(((Integer)(n1.getId()+1)).toString(), x2-10, y2+15);
		    g.drawLine(x1+5, y1+5, x2+5, y2+5);
		    
		    if(n1.getNoOfSynapses() > 0)
		    {
		    	paintNeurons(g,x2,y2,0,0,n1.getLinks());
		    }
		    } 
		    
	    }
	    
	    
	}

	
	//Creates a GUI with visualized Neural net, table showing states of all Neurons at a time
	//step, a text field for user to enter the time step and an Enter button to make the change
	public NetworkVisualizer(final Network network, final Neuron source, LinkedHashMap<Integer,LinkedHashMap<Integer,State>> hm)
	{
		super(new BorderLayout());
		
		h = hm;
		
		intFormat = NumberFormat.getIntegerInstance();

		timeStepLabel = new JLabel(text);
		
		textField = new JFormattedTextField(intFormat);
		textField.setValue(new Integer(timeStep));
		textField.setColumns(5);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		
		timeStepLabel.setLabelFor(textField);
		
		enterButton = new JButton("Enter");
		
		
		graphics gObj = new graphics(source);
		
		String[][] arrMap = convertHmToArray(h);
		
		table = new JTable(arrMap, columns);
		
		JPanel jp = new JPanel(new GridLayout(1,2));
		jp.add(gObj); 
		
		JPanel labelPane = new JPanel(new FlowLayout());
		labelPane.add(timeStepLabel);
        labelPane.add(textField);
        labelPane.add(enterButton);

        scroller = new JScrollPane(table);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(jp,BorderLayout.LINE_START);
        add(scroller,BorderLayout.CENTER);
        add(labelPane, BorderLayout.PAGE_END);
        
		enterButton.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	        	  h = network.getState(((Number)textField.getValue()).intValue());
	        	  String[][] arrMap = convertHmToArray(h);
	      		
	            
	      		table = new JTable(arrMap, columns);
	      		
	      		remove(scroller);
	      		scroller = new JScrollPane(table);
	            add(scroller,BorderLayout.CENTER);
	      		revalidate();
	          }
	       });

	}

//Converts and returns a 2D array from a LinkedHashMap of Network's Neurons' depths, ids 
//and states
	private String[][] convertHmToArray(
			LinkedHashMap<Integer, LinkedHashMap<Integer, State>> hm) {
		
		//Array size set to maximum possible number of rows
		String map[][] = new String[hm.size()*5][3];
		int index = 0;
		for(Map.Entry<Integer,LinkedHashMap<Integer,State>> entry : hm.entrySet()){
			LinkedHashMap<Integer,State> temp = entry.getValue();
			for(Map.Entry<Integer,State> t : temp.entrySet()){
				State s = t.getValue();
				String str = "( " + entry.getKey() + ", ";
				str = str + t.getKey() + " )";
				map[index][0] = str;
				map[index][1] = ((Integer)s.pot).toString();
				map[index][2] = s.stage;
				index++;
			
			}
		}
		return map;
		
		
	}

}
