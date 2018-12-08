//Brandon Selzer

package cpuscheduler.Schedulers;

import cpuscheduler.Processes.Process;
import cpuscheduler.Processes.ProcessList;
import java.util.ArrayList;

public class RRScheduler extends Scheduler
{
    private int quantum;
    
    public RRScheduler()
    {
        super();
    }
    
    @Override
    public void setProcessOrderAndTimes() 
    {
        ProcessList queue = new ProcessList();
        
        //Need a deep copy since processes will get removed.
        queue.setListOfProcesses(ProcessList.getListOfProcessesDeepCopy(super.getProcessList().getListOfProcesses()));

        ArrayList<Integer> order = new ArrayList();
        ArrayList<Integer> times = new ArrayList();
        
        times.add(0);
        
        while(queue.getNumProcesses() > 0)
        {
            Process process = queue.getListOfProcesses().get(0);
            
            if(quantum < process.getBurstTime()) //Process needs more time so it will be added back into the queue.
            {
                times.add(times.get(times.size() - 1) + quantum);
                order.add(process.getPID());
                process.setBurstTime(process.getBurstTime() - quantum);
                queue.removeFirstProcess();
                queue.addProcess(process);
            }
            else //Process doesn't need any more time so it will be removed from the queue.
            {
                order.add(process.getPID());
                times.add(times.get(times.size() - 1) + process.getBurstTime()); //The whole quantum doesn't get used up, just the burst time.

                int originalBurstTime = super.getProcessList().getListOfProcesses().get(process.getPID() - 1).getBurstTime();
                int turnaroundTime = times.get(times.size() - 1);
                int waitTime = turnaroundTime - originalBurstTime; //Wait time = turnaround time - burst time (- arrival time too but its zero)

                super.getProcessList().getListOfProcesses().get(process.getPID() - 1).setWaitTime(waitTime);
                super.getProcessList().getListOfProcesses().get(process.getPID() - 1).setTurnaroundTime(turnaroundTime);
                queue.removeFirstProcess();
            }//end else
        }//end while loop
        
        super.order = order;
        super.times = times; 
    }
    
    public int getQuantum()
    {
        return quantum;
    }
    
    public void setQuantum(int quantum)
    {
        this.quantum = quantum;
    }
}//end class
