/**
 * The first GUI that user sees is this
 */
package neuron;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import neuron.Neuron.State;

/**
 * @author Soumya Batra
 *
 */
@SuppressWarnings("serial")
public class GUI extends JPanel {
	
	private static JFrame frame;
	private int nNeurons = 50;
	private JLabel noOfNeurons;
	private static String text = "Enter minimum number of neurons in the network(>=50)";
	private JFormattedTextField textField;
	private NumberFormat intFormat;
	private JButton enterButton;

	//Creates GUI that prompts user to enter number of neurons in the network and an 
	//Enter button to make the change happen
	public GUI() {
        
		super(new BorderLayout());		
		intFormat = NumberFormat.getIntegerInstance();

		noOfNeurons = new JLabel(text);
		
		textField = new JFormattedTextField(intFormat);
		textField.setValue(new Integer(nNeurons));
		textField.setColumns(5);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		
		noOfNeurons.setLabelFor(textField);
		
		enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	        	   Network network = new Network(Integer.parseInt(textField.getValue().toString()));
	        	   Neuron source = network.getSource();
	        	   LinkedHashMap<Integer,LinkedHashMap<Integer,State>> hm = network.getState(0);
	        	   showNetwork(network,source,hm);
	          }
	       });
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(noOfNeurons);

        //Layout the text field in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(textField);
        
        JPanel buttonPane = new JPanel(new GridLayout(0,1));
        buttonPane.add(enterButton);

        
        //Put all the panels in the main panel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);  
        add(buttonPane,BorderLayout.AFTER_LAST_LINE);
	    }
	    

	//Create the GUI and show it
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Neural Network");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new GUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //Called after Enter button is pressed. The existing frame is disposed and a new frame
    //is created with the resultant Neural Net
    private static void showNetwork(Network network, Neuron source, LinkedHashMap<Integer,LinkedHashMap<Integer,State>> hm)
    {
    	
    	frame.dispose();
    	
    	//Create and set up the window.
        frame = new JFrame("Neural Network");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new NetworkVisualizer(network,source, hm));

        //Display the window.
        frame.pack();
        frame.setVisible(true); 
    	
    }
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
    

}
