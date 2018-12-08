//Brandon Selzer

package cpuscheduler.Schedulers;

import cpuscheduler.Processes.Process;
import cpuscheduler.Processes.ProcessList;
import java.util.ArrayList;

public class FCFSScheduler extends Scheduler
{
    public FCFSScheduler()
    {
        super();
    }

    @Override
    public void setProcessOrderAndTimes() 
    {
        ProcessList queue = super.getProcessList();
        ArrayList<Integer> order = new ArrayList();
        ArrayList<Integer> times = new ArrayList();
        
        times.add(0);
        
        //Queue goes in the order P1, P2, ..., Pn
        for(int i = 0; i < queue.getNumProcesses(); i++)
        {
            Process process = queue.getListOfProcesses().get(i);
            order.add(process.getPID());
            times.add(times.get(i) + process.getBurstTime()); //Get previous time and add process's burst time
            process.setWaitTime(times.get(i));  //Wait time is time at beginning of process in the Gantt chart
            process.setTurnaroundTime(times.get(i + 1)); //Turnaround time is time at end of process of Gantt chart
            
            //TESTING
            //System.out.println(i + " " + order.get(i) + " " + times.get(i + 1));
            //System.out.println(process.getWaitTime() + " " + process.getTurnaroundTime());
        }
        
        super.order = order;
        super.times = times;
    }
}//end class
