//Brandon Selzer

package cpuscheduler.GUI;

import cpuscheduler.Processes.Process;
import cpuscheduler.Processes.ProcessList;
import cpuscheduler.Schedulers.PriorityScheduler;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Container holding components dealing with each processes's PID, burst/wait/turnaround times, and the average wait/turnaround time.
public class ProcessPanel extends JPanel
{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final PropertiesPanel propertiesPanel;
    private GanttPanel ganttPanel;
    private ProcessList processes;
    private ArrayList<JLabel> lblTitles, lblProcesses, lblWaitTimes, lblTurnaroundTimes, lblAvgTimes;
    private ArrayList<JTextField> txtPriorities, txtBurstTimes;
    
    //PropertiesPanel is required in the constructor since during initialization 10 processes are added.
    public ProcessPanel(PropertiesPanel propertiesPanel) 
    {
        this.propertiesPanel = propertiesPanel;
        processes = new ProcessList();
        
        initialize();
    }
    
    //GanttPanel is needed for removing components and revalidation.
    public void setGanttPanel(GanttPanel ganttPanel)
    {
        this.ganttPanel = ganttPanel;
    }
    
    private void initialize()
    {
        setLayout(new GridBagLayout());
        initializeComponents();
    }
    
    private void initializeComponents()
    {
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        lblTitles = new ArrayList();
        lblTitles.add(new JLabel("Processes"));
        lblTitles.add(new JLabel("Burst Times"));
        lblTitles.add(new JLabel("Wait Times"));
        lblTitles.add(new JLabel("TA Times"));
        lblTitles.add(new JLabel("Priorities"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 10);
        
        for(JLabel i: lblTitles)
        { 
            add(i, gbc);
            gbc.gridy++;
        }
        
        lblProcesses = new ArrayList();
        txtBurstTimes = new ArrayList();
        lblWaitTimes = new ArrayList();
        lblTurnaroundTimes = new ArrayList();
        txtPriorities = new ArrayList();
        lblAvgTimes = new ArrayList();
         
        //Due to the way gridbadlayout works, wording is shortened in order to have more scenarios where formatting isn't broken.
        lblAvgTimes.add(new JLabel("Avg wait: 0")); 
        lblAvgTimes.add(new JLabel("Avg TA:    0"));
        
        addProcesses(10);
    }
    
    //Invoked in order to redisplay the container's components in order to display changed values.
    //Since processes are added and removed, every component isn't removed and readded with the changed value.
    public void validateComponents()
    {
        setVisible(false);
        
        for(int i = 0; i < processes.getNumProcesses(); i++)
        {
            gbc.gridx = i + 2;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 10, 5);
            add(lblProcesses.get(i), gbc);
            
            gbc.gridy = 1;
            txtBurstTimes.get(i).setText(Integer.toString(processes.getListOfProcesses().get(i).getBurstTime()));
            add(txtBurstTimes.get(i), gbc);
            
            gbc.gridy = 2;
            lblWaitTimes.get(i).setText(Integer.toString(processes.getListOfProcesses().get(i).getWaitTime()));
            add(lblWaitTimes.get(i), gbc);
            
            gbc.gridy = 3;
            lblTurnaroundTimes.get(i).setText(Integer.toString(processes.getListOfProcesses().get(i).getTurnaroundTime()));
            add(lblTurnaroundTimes.get(i), gbc);
            
            gbc.gridy = 4;
            txtPriorities.get(i).setText(Integer.toString(processes.getListOfProcesses().get(i).getPriority()));
            add(txtPriorities.get(i), gbc);
            setPriorityVisibility();
        }//end for loop
        
        //Since 1 process is added during initialization, this becomes a necessary check.
        if(propertiesPanel.getScheduler() != null)
        {
            DecimalFormat round = new DecimalFormat("###.###");
            
            lblAvgTimes.get(0).setText("Avg wait: " + round.format(propertiesPanel.getScheduler().getAvgWaitTime()));
            lblAvgTimes.get(1).setText("Avg TA:    " + round.format(propertiesPanel.getScheduler().getAvgTurnaroundTime()));
            
            gbc.gridx = 0;
            gbc.gridy = 5;
            add(lblAvgTimes.get(0), gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 6;
            add(lblAvgTimes.get(1), gbc);
        }
        
        revalidate();
        repaint();
        setVisible(true);
    }//end validateComponents
    
    //Invoked when Set Random Burst Times is clicked in order to display the changed values.
    //validateComponents() isn't used because nothing else is recalculated, so everything else will display wrong values
    public void validateBurstTimes()
    {
        for(int i = 0; i < processes.getNumProcesses(); i++)
        {
            gbc.gridx = i + 2;
            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 10, 5);
            txtBurstTimes.get(i).setText(Integer.toString(processes.getListOfProcesses().get(i).getBurstTime()));
            add(txtBurstTimes.get(i), gbc);
        }
    }
    
    //Invoked when Set Random Priorities is clicked in order to display the changed values.
    //validateComponents() isn't used because nothing else is recalculated, so everything else will display wrong values
    public void validatePriorities()
    {
        for(int i = 0; i < processes.getNumProcesses(); i++)
        {
            gbc.gridx = i + 2;
            gbc.gridy = 4;
            gbc.insets = new Insets(0, 0, 10, 5);
            txtPriorities.get(i).setText(Integer.toString(processes.getListOfProcesses().get(i).getPriority()));
            add(txtPriorities.get(i), gbc);
            setPriorityVisibility();
        }
    }
    
    //Invoked when Clear Process Fields is clicked in order to clear the fields.
    public void clearComponents()
    {
        for(int i = 0; i < processes.getNumProcesses(); i++)
        {
            txtBurstTimes.get(i).setText("0");
            lblWaitTimes.get(i).setText("0");
            lblTurnaroundTimes.get(i).setText("0");
            txtPriorities.get(i).setText("0");
        }
        
        lblAvgTimes.get(0).setText("Avg wait: 0"); 
        lblAvgTimes.get(1).setText("Avg TA:    0");
    }
    
    //Invoked when parts of a process are manipulated, and so all components don't need to be cleared.
    public void clearWaitAndTurnaroundTimesComponents()
    {
        for(int i = 0; i < processes.getNumProcesses(); i++)
        {
            lblWaitTimes.get(i).setText("0");
            lblTurnaroundTimes.get(i).setText("0");
        }
        
        lblAvgTimes.get(0).setText("Avg wait: 0"); 
        lblAvgTimes.get(1).setText("Avg TA:    0");
    }
    
    //Invoked when Calculate is clicked in case the user makes manual changes that need to be saved.
    public boolean saveComponents()
    {
        for(int i = 0; i < processes.getNumProcesses(); i++)
        {
            int input; 
            
            try
            {
                input = Integer.parseInt(txtBurstTimes.get(i).getText());
                
                if(input < 0)
                {
                    JOptionPane.showMessageDialog(null, "Found a negative burst time. Burst times must be positive.");
                    return false;
                }
                
                processes.getListOfProcesses().get(i).setBurstTime(input);
            }//end try
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Invalid input found for a burst time.");
                return false;
            }//end catch

            //Since these are calculated as integers, this shouldn't ever be a problem.
            processes.getListOfProcesses().get(i).setWaitTime(Integer.parseInt(lblWaitTimes.get(i).getText())); 
            processes.getListOfProcesses().get(i).setTurnaroundTime(Integer.parseInt(lblTurnaroundTimes.get(i).getText()));
            
            try
            {
                //Since higher numbers are higher priorities, negatives are allowed.
                input = Integer.parseInt(txtPriorities.get(i).getText()); 
                processes.getListOfProcesses().get(i).setPriority(input);
            }//end try
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Invalid input found for a priority.");
                return false;
            }//end catch
        }//end for loop
        
        return true;
        
    }//end method
    
    //Adds processes to the ProcessList and displays them in the container with initial values of 0. 
    public void addProcesses(int add)
    {
        for(int i = 1; i <= add; i++)
        {
            Process process;

            process = new Process(processes.getNumProcesses() + 1);
            processes.addProcess(process);
            lblProcesses.add(new JLabel(Integer.toString(process.getPID())));
            
            txtBurstTimes.add(new JTextField("0")); //Initially 0. Should be manually changed by user or by RNG.
            txtBurstTimes.get(txtBurstTimes.size() - 1).setPreferredSize(new Dimension(26, 20));
            txtBurstTimes.get(txtBurstTimes.size() - 1).addFocusListener(new textFieldFocusAdapter());
            
            lblWaitTimes.add(new JLabel("0")); //Initially 0. Changes once the scheduling algorithm is performed.
            lblTurnaroundTimes.add(new JLabel("0")); //Initially 0. Changes once the scheduling algorithm is performed.
            
            txtPriorities.add(new JTextField("0")); //Initially 0. Should be manually changed by user or by RNG.
            txtPriorities.get(txtPriorities.size() - 1).setPreferredSize(new Dimension(26, 20));
            txtPriorities.get(txtPriorities.size() - 1).addFocusListener(new textFieldFocusAdapter());
        }//end loop
        
        validateComponents();
        clearWaitAndTurnaroundTimesComponents();
        
        if(ganttPanel != null) //Gantt panel initialization comes after the process panel.
        {
            ganttPanel.removeComponents();
        }
    }//end method
    
    //Removes processes from the ProcessList and from the container.
    public void removeProcesses(int remove)
    {   
        for(int i = 1; i <= remove; i++)
        {
            int numProcesses = processes.getNumProcesses();
            remove(lblProcesses.get(numProcesses - 1));
            remove(txtBurstTimes.get(numProcesses - 1));
            remove(lblWaitTimes.get(numProcesses - 1));
            remove(lblTurnaroundTimes.get(numProcesses - 1));
            remove(txtPriorities.get(numProcesses - 1));
            
            lblProcesses.remove(numProcesses - 1);
            txtBurstTimes.remove(numProcesses - 1);
            lblWaitTimes.remove(numProcesses - 1);
            lblTurnaroundTimes.remove(numProcesses - 1);
            txtPriorities.remove(numProcesses - 1);
            processes.removeLastProcess();
        }//end loop
        
        validateComponents();
        clearWaitAndTurnaroundTimesComponents();
        
        if(ganttPanel != null) //Gantt panel initialization comes after the process panel.
        {
            ganttPanel.removeComponents();
        }
    }//end method
    
    public ProcessList getProcessList()
    {
        return processes;
    }
    
    public void setProcessList(ProcessList list)
    {
        this.processes = list;
    }
    
    //Sets the visibility of the priority label/text fields depending on the scheduler.
    public void setPriorityVisibility()
    {
        boolean visibility;
        
        visibility = !(propertiesPanel.getScheduler() == null 
                || propertiesPanel.getScheduler().getClass() != PriorityScheduler.class);
        
        lblTitles.get(4).setVisible(visibility);
            
        for(JTextField i: txtPriorities)
        {
            i.setVisible(visibility);
        }  
    }
    
    //Used by JTextFields so that when it gains focus, the user can just start typing in order to remove the text.
    //This is done so that the user can easily replace text instead of hitting the Clear button if the user wanted to do so.
    private class textFieldFocusAdapter extends FocusAdapter
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            JTextField source = (JTextField)e.getSource();
            source.selectAll(); //Highlights all of the text in the JTextField
        }
    }
}//end class
