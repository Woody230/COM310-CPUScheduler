//Brandon Selzer

package cpuscheduler.Schedulers;

import cpuscheduler.Processes.ProcessList;
import java.util.ArrayList;

public abstract class Scheduler 
{
    ProcessList processes;
    ArrayList<Integer> order;
    ArrayList<Integer> times;
    
    public Scheduler()
    {
        processes = new ProcessList();
        order = new ArrayList();
        times = new ArrayList();
    }
    
    public double getAvgWaitTime()
    {
        int numProcesses = processes.getNumProcesses();
        
        if(numProcesses == 0) //Avoids division by 0.
        {
            return 0;
        }
        
        int sumWaitTime = 0;
        
        for(int i = 0; i < numProcesses; i++)
        {
            sumWaitTime += processes.getListOfProcesses().get(i).getWaitTime();
        }

        return sumWaitTime * 1.0 / numProcesses; //Would have integer division without 1.0
    }
    
    public double getAvgTurnaroundTime()
    {
        int numProcesses = processes.getNumProcesses();
        
        if(numProcesses == 0) //Avoids division by 0.
        {
            return 0;
        }
        
        int sumTurnaroundTime = 0;
        
        for(int i = 0; i < numProcesses; i++)
        {
            sumTurnaroundTime += processes.getListOfProcesses().get(i).getTurnaroundTime();
        }

        return sumTurnaroundTime * 1.0 / numProcesses; //Would have integer division without 1.0
    }
    
    public ProcessList getProcessList()
    {
        return processes;
    }
    
    public ArrayList<Integer> getProcessOrder()
    {
        return order;
    }
    
    public ArrayList<Integer> getProcessTimes()
    {
        return times;
    }
     
    public void setProcessList(ProcessList processes)
    {
        this.processes = processes;
    }  
    
    //i.e. Perform the algorithm and obtain the relevant results: process order and times for Gantt chart, wait/turnaround time.
    public abstract void setProcessOrderAndTimes(); 
}
