//Brandon Selzer

package cpuscheduler.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

//Container holding all of the components that deal with displaying results from the CPU scheduler.
public class GUI extends JFrame
{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private ProcessPanel processPanel;
    private PropertiesPanel propertiesPanel;
    private GanttPanel ganttPanel;
    private JSeparator separator;
    private JScrollPane scrollProcess;
    private JSeparator separator2;
    private JScrollPane scrollGantt;
    
    public GUI()
    {
        initialize();
    }

    private void initialize() 
    {
        setTitle("CPU Scheduler by Brandon Selzer");
        setSize(650, 550);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        initializeComponents();
    }
    
    private void initializeComponents()
    {
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        setJMenuBar(new MenuBar());
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        propertiesPanel = new PropertiesPanel();
        add(propertiesPanel, gbc);
        
        separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(this.getWidth() - 10, 10));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(separator, gbc);

        processPanel = new ProcessPanel(propertiesPanel);
        scrollProcess = new JScrollPane(processPanel);
        scrollProcess.setPreferredSize(new Dimension(this.getWidth() - 10, 225));
        scrollProcess.getVerticalScrollBar().setUnitIncrement(16);
        scrollProcess.getHorizontalScrollBar().setUnitIncrement(16);
        gbc.gridx = 0;
        gbc.gridy = 2; 
        add(scrollProcess, gbc);
        
//        separator2 = new JSeparator(JSeparator.HORIZONTAL);
//        separator2.setPreferredSize(new Dimension(this.getWidth() - 10, 10));
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        add(separator2, gbc);
        
        ganttPanel = new GanttPanel();
        scrollGantt = new JScrollPane(ganttPanel);
        scrollGantt.setPreferredSize(new Dimension(this.getWidth() - 10, 100));
        scrollGantt.getVerticalScrollBar().setUnitIncrement(16);
        scrollGantt.getHorizontalScrollBar().setUnitIncrement(16);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollGantt, gbc);
        
        propertiesPanel.setProcessPanel(processPanel);
        propertiesPanel.setGanttPanel(ganttPanel);
        processPanel.setGanttPanel(ganttPanel);
        ganttPanel.setPropertiesPanel(propertiesPanel);
        
        revalidate();
    }//end initializeComponents
}//end class
