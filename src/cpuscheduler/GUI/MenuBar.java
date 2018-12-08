//Brandon Selzer

package cpuscheduler.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar
{
    private JMenu menu;
    private JMenuItem exit;
    private JMenuItem help; 
    
    public MenuBar()
    {
        initializeMenuBar();
    }
    
    private void initializeMenuBar()
    {
        initializeComponents();
    }
    
    private void initializeComponents()
    {
        
        menu = new JMenu();
        menu.setText("Options");
        add(menu);
        
        help = new JMenuItem();
        help.setText("Help");
        help.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                new HelpFrame();
            }  
        });
        
        exit = new JMenuItem();
        exit.setText("Exit");
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                System.exit(0);
            }  
        });
        
        menu.add(help);
        menu.add(exit);
    }
}
