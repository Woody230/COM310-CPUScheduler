//Brandon Selzer

package cpuscheduler.GUI;

import cpuscheduler.Schedulers.FCFSScheduler;
import cpuscheduler.Schedulers.PriorityScheduler;
import cpuscheduler.Schedulers.RRScheduler;
import cpuscheduler.Schedulers.Scheduler;
import cpuscheduler.Schedulers.SJFScheduler;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Container holding components dealing with setting the scheduler, setting the number of processes, setting the quantum,
//clearing fields, randomly setting the burst times, and randomly setting the priorities.
public class PropertiesPanel extends JPanel
{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private ProcessPanel processPanel;
    private GanttPanel ganttPanel;
    private Scheduler scheduler;
    private JComboBox cboScheduler;
    private JButton btnRandomBurstTimes, btnRandomPriorities, btnClear, btnCalculate;
    private JLabel lblNumProcesses, lblQuantum, lblScheduler;
    private JTextField txtQuantum, txtNumProcesses;
    
    public PropertiesPanel()
    { 
        initialize();
    }
    
    //ProcessPanel is needed to manage the # of processes, display the priority JTextFields,
    //set the processList for the scheduler, etc.
    public void setProcessPanel(ProcessPanel processPanel)
    {
        this.processPanel = processPanel;
    }
    
    //GanttPanel is needed to remove components or revalidate.
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
       
        lblScheduler = new JLabel();
        lblScheduler.setText("Scheduling algorithm:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(lblScheduler, gbc);
        
        cboScheduler = new JComboBox();
        cboScheduler.addItem("");
        cboScheduler.addItem("First Come First Serve (FCFS)");
        cboScheduler.addItem("Shortest Job First (SJF)");
        cboScheduler.addItem("Round Robin (RR)");
        cboScheduler.addItem("Priority");
        cboScheduler.addActionListener(new ActionListener()
        {
            //Set the scheduler based on the combo box choice and sets the visibility of certain components based on the scheduler.
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(cboScheduler.getSelectedItem().equals(cboScheduler.getItemAt(1)))
                {
                    scheduler = new FCFSScheduler();
                    scheduler.setProcessList(processPanel.getProcessList());
                }
                else if(cboScheduler.getSelectedItem().equals(cboScheduler.getItemAt(2)))
                {
                    scheduler = new SJFScheduler();
                    scheduler.setProcessList(processPanel.getProcessList());
                }
                else if(cboScheduler.getSelectedItem().equals(cboScheduler.getItemAt(3)))
                {
                    scheduler = new RRScheduler();
                    scheduler.setProcessList(processPanel.getProcessList());
                }
                else if(cboScheduler.getSelectedItem().equals(cboScheduler.getItemAt(4)))
                {
                    scheduler = new PriorityScheduler();
                    scheduler.setProcessList(processPanel.getProcessList());
                }
                
                setQuantumVisibility();
                setPriorityVisibility();
                
                if(processPanel != null) //Properties panel is initialized before process panel so this check is necessary.
                {
                    processPanel.setPriorityVisibility();
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(cboScheduler, gbc);
        
        btnClear = new JButton();
        btnClear.setText("Clear Process Fields");
        btnClear.addActionListener(new ActionListener()
        {
            //Clears burst times, wait times, turnaround times in the process panel and the Gantt chart in the Gantt panel.
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                processPanel.clearComponents();
                ganttPanel.removeComponents();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(btnClear, gbc);
        
        lblNumProcesses = new JLabel();
        lblNumProcesses.setText("Number of processes:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(lblNumProcesses, gbc);
        
        txtNumProcesses = new JTextField();
        txtNumProcesses.setPreferredSize(new Dimension(192, 20));
        txtNumProcesses.setText("10");
        txtNumProcesses.addFocusListener(new numProcessesFocusAdapter());
        txtNumProcesses.addActionListener(new ActionListener()
        {
            //Will add/remove processes in the process panel based on the input.
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                int input;
                
                try
                {
                    input = Integer.parseInt(txtNumProcesses.getText());
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Invalid input for the number of processes.");
                    return;
                }
                
                if(input <= 0)
                {
                    JOptionPane.showMessageDialog(null, "Number of processes must be greater than 0.");
                    return;
                }
                
                int numProcesses = processPanel.getProcessList().getNumProcesses();
                
                if(input > numProcesses)
                {
                    processPanel.addProcesses(input - numProcesses);
                }
                else if(input < numProcesses)
                {
                    processPanel.removeProcesses(numProcesses - input);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(txtNumProcesses, gbc); 
        
        btnCalculate = new JButton();
        btnCalculate.setText("Calculate");
        btnCalculate.addActionListener(new ActionListener()
        {
            //Performs the algorithm and outputs the wait/turnaround times and their averages, and also shows the Gantt chart.
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if(scheduler != null && !cboScheduler.getSelectedItem().equals(cboScheduler.getItemAt(0)))
                {
                    boolean completed = processPanel.saveComponents(); //In case the user makes manual changes, components are saved.
                    
                    if(!completed)
                    {
                        return;
                    }
                    
                    scheduler.setProcessList(processPanel.getProcessList()); //Refresh the process list based on any changes.
                    
                    if(scheduler.getClass() == RRScheduler.class)
                    {
                        try
                        {
                            int input = Integer.parseInt(txtQuantum.getText());
                            
                            if(input <= 0)
                            {
                                JOptionPane.showMessageDialog(null, "The quantum must be greater than 0.");
                                return;
                            }
                                
                            ((RRScheduler)scheduler).setQuantum(input);
                        }
                        catch(NumberFormatException ex)
                        {
                            JOptionPane.showMessageDialog(null, "Invalid input for the quantum.");
                            return;
                        }
                    }
                    
                    scheduler.setProcessOrderAndTimes();
                    processPanel.validateComponents();
                    ganttPanel.validateComponents();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Select a scheduling algorithm.");
                }
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(btnCalculate, gbc); 
        
        lblQuantum = new JLabel();
        lblQuantum.setText("Time quantum:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(lblQuantum, gbc);
        
        txtQuantum = new JTextField();
        txtQuantum.setPreferredSize(new Dimension(26, 20));
        txtQuantum.setText("5");
        //Setting the quantum is done once Calculate is clicked on the scheduler is round robin, so a listener isn't needed.
        //This was done so that the user doesn't need to hit enter in order to confirm the quantum.
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(txtQuantum, gbc);
        setQuantumVisibility(); //Since this is during initialization, the visibility is set to false.
        
        btnRandomBurstTimes = new JButton();
        btnRandomBurstTimes.setText("Set Random Burst Times");
        btnRandomBurstTimes.addActionListener(new ActionListener()
        {
            //Sets the burst times to random times and refreshes the text fields.
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                processPanel.getProcessList().setRandomBurstTimes();
                processPanel.validateBurstTimes();  
                processPanel.clearWaitAndTurnaroundTimesComponents();
                ganttPanel.removeComponents();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(btnRandomBurstTimes, gbc);
        
        btnRandomPriorities = new JButton();
        btnRandomPriorities.setText("Set Random Priorities");
        btnRandomPriorities.addActionListener(new ActionListener()
        {
            //Sets the priorities to random values and refreshes the text fields.
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                processPanel.getProcessList().setRandomPriorities();
                processPanel.validatePriorities();  
                processPanel.clearWaitAndTurnaroundTimesComponents();
                ganttPanel.removeComponents();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(btnRandomPriorities, gbc);
        setPriorityVisibility(); //Since this is during initialization, the visibility is set to false.
    }//end initializeComponents
    
    public Scheduler getScheduler()
    {
        return scheduler;
    }
    
    public String getTxtQuantum()
    {
        return txtQuantum.getText();
    }
    
    //Sets the visibility of the quantum label and text field depending on the scheduler.
    private void setQuantumVisibility()
    {
        boolean visibility;
        
        visibility = !(scheduler == null || scheduler.getClass() != RRScheduler.class);
        
        lblQuantum.setVisible(visibility);
        txtQuantum.setVisible(visibility);
    }
    
    //Sets the visibility of the random priorities button depending on the scheduler.
    private void setPriorityVisibility()
    {
        boolean visibility;
        
        visibility = !(scheduler == null || scheduler.getClass() != PriorityScheduler.class);
        
        btnRandomPriorities.setVisible(visibility);
    }
    
    private class numProcessesFocusAdapter extends FocusAdapter
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            JTextField source = (JTextField)e.getSource();
            source.selectAll(); //Highlights all of the text in the JTextField
        }
    }
}//end class
