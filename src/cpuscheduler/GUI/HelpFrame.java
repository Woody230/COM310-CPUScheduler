//Brandon Selzer

package cpuscheduler.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//Container holding the info about how to operate the application and what assumptions are made about the CPU scheduler.
public class HelpFrame extends JFrame
{
    private JPanel helpPanel;
    private JEditorPane txtHelp; //An editor pane is used in order to get formatted text using html.
    private JScrollPane scrollPane;
    
    public HelpFrame()
    {
        initialize();
    }
    
    private void initialize()
    {
        setTitle("CPU Scheduler Help");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
        initializeComponents();
        
        pack(); //Set the size of the container to the necessary minimum.
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents()
    {
        txtHelp = new JEditorPane("text/html", "");
        txtHelp.setEditable(false);
        txtHelp.setBackground(null); //Editor pane's color is originally white but this makes it the same as the panel's default.
        txtHelp.setText(
            "CPU Scheduler by Brandon Selzer" + "<br><br>" +
            "<b><u>Program Operation:</b></u>" + "<br>" +
            "&#8226" + "Enter the number of processes." + "<br>" +
                "&emsp&emsp&#8226" + "Make sure to hit enter after typing in the number into the text field." + "<br>" +
                "&emsp&emsp&#8226" + "Enter any positive number of processes." + "<br>" +
                "&emsp&emsp&#8226" + "It is recommended to enter at most 1000 processes." + "<br>" +
                    "&emsp&emsp&emsp&emsp&#8226" + "However, you can still enter as many as you want. <b>BE CAREFUL!</b>" + "<br>" +
            "&#8226" + "Select the scheduling algorithm." + "<br>" +
                "&emsp&emsp&#8226" + "For round robin, type in the time quantum in the text field that becomes visible upon selection." + "<br>" +
                "&emsp&emsp&#8226" + "For priority, type in the priority for each process into the text fields that become visible upon selection." + "<br>" +
                    "&emsp&emsp&emsp&emsp&#8226" + "Bigger numbers represent higher priorities. Only integers are accepted." + "<br>" +
            "&#8226" + "Enter the burst times into the text fields for each process." + "<br>" +
                "&emsp&emsp&#8226" + "Burst times can't be negative. Only whole numbers are accepted." + "<br><br>" +
            
            "&#8226" + "Click the \"Clear Process Fields button\" to clear burst times, wait times, turnaround times, and the Gantt chart." + "<br>" +
            "&#8226" + "Click the \"Calculate\" button to calculate wait/turnaround times and their averages and also show the Gantt chart." + "<br>" +
            "&#8226" + "Click the \"Set Random Burst Times\" button to set burst times to a value between 1 and 99 inclusive." + "<br>" +
            "&#8226" + "Click the \"Set Random Priorities\" button to set priorities to a value between 0 and the number of processes - 1." + "<br><br>" +
            
            "<b><u>Assumptions:</b></u>" + "<br>" +
            "&#8226" + "The process limit depends on how long you are willing to wait for the answer." + "<br>" +
            "&#8226" + "Burst times are somehow known before the algorithm calculates. This is relevant for shortest job first." + "<br>" +
            "&#8226" + "Processes come in at the same time." + "<br>" +
            "&#8226" + "Processes come in the order P1, P2, ..., Pn." + "<br>" +
            "&#8226" + "For round robin, the quantum is the same for all processes." + "<br><br>" +
            
            "<b><u>Design Choices:</b></u>" + "<br>" + 
            "&#8226" + "ArrayLists are consistently used." + "<br>" +
                "&emsp&emsp&#8226" + "ArrayLists expand automatically." + "<br>" + 
                "&emsp&emsp&#8226" + "ArrayLists allow access to every element." + "<br>" +
                    "&emsp&emsp&emsp&emsp&#8226" + "This is necessary for displaying components even though queue like methods are sometimes used." + "<br>" +
                "&emsp&emsp&#8226" + "ArrayLists have O(1) get and add (append version) methods." + "<br>" +
            "&#8226" + "Merge sort is used for SJF and Priority in order to have O(n" + "&#8901" + "log" + "<sub>2</sub>" + "(n)) performance." + "<br>" +
            "&#8226" + "An abstract Process class isn't used since priority/quantum don't make the processes fundamentally different." + "<br>" +
            "&#8226" + "The ProcessList class is used to manage the ArrayList of processes." + "<br>" +
            "&#8226" + "An abstract Scheduler class is used since they have a similar function but different implementations." + "<br>" +
            "&#8226" + "The GUI is split into 3 sections:" + "<br>" +
                "&emsp&emsp&#8226" + "PropertiesPanel handles the properties of the processes/scheduler and managing them." + "<br>" +
                "&emsp&emsp&#8226" + "ProcessPanel handles the processes and what a process's PID, burst time, wait time, priority, etc is." + "<br>" +
                "&emsp&emsp&#8226" + "GanttPanel handles drawing the Gantt chart based on what the scheduler figures out."    
        );
        
        helpPanel = new JPanel();
        helpPanel.add(txtHelp);
        
        scrollPane = new JScrollPane(helpPanel);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }
}
