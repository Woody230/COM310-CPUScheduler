//Brandon Selzer

package cpuscheduler.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Container holding the Gantt chart.
public class GanttPanel extends JPanel
{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private PropertiesPanel propertiesPanel;
    private ArrayList<JLabel> lblOrder; //List of processes in the Gantt chart's rectangles.
    private ArrayList<JLabel> lblTimes; //List of times under the Gantt chart's processes.
    
    public GanttPanel()
    {
        initialize();
    }
    
    public void setPropertiesPanel(PropertiesPanel propertiesPanel)
    {
        this.propertiesPanel = propertiesPanel;
    }
    
    private void initialize()
    {
        setLayout(new GridBagLayout());
        
        initializeComponents();
    }
    
    private void initializeComponents()
    {
        lblOrder = new ArrayList();
        lblTimes = new ArrayList();
    }
    
    //Invoked in order to remake the container's components in order to display changed values using the method setComponents().
    //Since everything needs to be recalculated it scraps the whole display and readds it with the changed values.
    public void validateComponents()
    {
        setVisible(false);
        removeAll(); //If previous components aren't removed, they will overlap with the newly added components.
        setComponents();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(lblTimes.get(0), gbc);
        
        //Add each label in lblOrder and lblTimes to the container.
        for(int i = 0; i < propertiesPanel.getScheduler().getProcessOrder().size(); i++)
        {
            gbc.gridx = i;
            gbc.gridy = 0;
            add(lblOrder.get(i), gbc);
            
            gbc.gridx = i + 1;
            gbc.gridy = 1;
            add(lblTimes.get(i + 1), gbc);
        }      
        
        revalidate();
        repaint();
        setVisible(true);
    }
    
    //Invoked by validateComponents in order to display changed values.
    private void setComponents()
    {
        initializeComponents(); //Reset lblOrder and lblTimes
        
        lblTimes.add(new JLabel(Integer.toString(propertiesPanel.getScheduler().getProcessTimes().get(0))));
        
        for(int i = 0; i < propertiesPanel.getScheduler().getProcessOrder().size(); i++)
        {
            lblOrder.add(new JLabel());
            lblOrder.get(i).setText("P" + Integer.toString(propertiesPanel.getScheduler().getProcessOrder().get(i)));
            lblOrder.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK)); //The rectangle representing each process.
            lblOrder.get(i).setPreferredSize(new Dimension(50, 20));
           
            lblTimes.add(new JLabel());
            lblTimes.get(i + 1).setText(Integer.toString(propertiesPanel.getScheduler().getProcessTimes().get(i + 1)));
        }
    }
    
    //Invoked when Clear Process Fields is clicked in order to remove the Gantt chart.
    public void removeComponents()
    {
        removeAll();
        revalidate();
        repaint();
    }
}
