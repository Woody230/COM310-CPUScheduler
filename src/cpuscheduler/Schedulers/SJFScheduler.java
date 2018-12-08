//Brandon Selzer

package cpuscheduler.Schedulers;

import cpuscheduler.Processes.ProcessList;
import cpuscheduler.Processes.Process;
import java.util.ArrayList;

public class SJFScheduler extends Scheduler
{
    public SJFScheduler()
    {
        super();
    }
    
    @Override
    public void setProcessOrderAndTimes() 
    {
        ProcessList queue = new ProcessList();
        ArrayList<Integer> order = new ArrayList();
        ArrayList<Integer> times = new ArrayList();
        
        //Need a deep copy since processes will get sorted by burst time, and not the natural ordering of PID.
        queue.setListOfProcesses(ProcessList.getListOfProcessesDeepCopy(super.getProcessList().getListOfProcesses()));
        queue.sortListOfProcessesByBurstTime(); //Need to get list sorted from smallest to largest burst times.
        
        times.add(0);
        
        //Since the queue has been sorted by burst time, the algorithm now effectively functions like FCFS.
        for(int i = 0; i < queue.getNumProcesses(); i++)
        {
            Process process = queue.getListOfProcesses().get(i);
            order.add(process.getPID());
            times.add(times.get(i) + process.getBurstTime());
            
            //Since the processes aren't necessarily ordered by P1, P2, ...,Pn need to get super process by PID 
            super.getProcessList().getListOfProcesses().get(process.getPID() - 1).setWaitTime(times.get(i));
            super.getProcessList().getListOfProcesses().get(process.getPID() - 1).setTurnaroundTime(times.get(i + 1));
        }
        
        super.order = order;
        super.times = times;
    }
}//end class
