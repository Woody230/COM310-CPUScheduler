//Brandon Selzer

package cpuscheduler.Processes;

//Since the quantum is assumed to be the same for round robin, 3 of the 4 process types use the same things.
//Therefore instead of using an abstract class, priority is just added to this single process class.
//Essentially, the processes are all fundamentally the same so abstraction isn't necessary.
public class Process 
{
    private int PID;
    private int burstTime = 0;
    private int waitTime = 0;
    private int turnaroundTime = 0;
    private int priority = 0;
    
    public Process(int PID) 
    {
        this.PID = PID;
    }
    
    public int getPID()
    {
        return PID;
    }
    
    public int getBurstTime()
    {
        return this.burstTime;
    }
    
    public int getWaitTime()
    {
        return this.waitTime;
    }
    
    public int getTurnaroundTime()
    {
        return this.turnaroundTime;
    }

    public int getPriority()
    {
        return this.priority;
    }
    
    public void setPID(int PID)
    {
        this.PID = PID;
    }
    
    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }
    
    public void setWaitTime(int waitTime)
    {
        this.waitTime = waitTime;
    }
    
    public void setTurnaroundTime(int turnaroundTime)
    {
        this.turnaroundTime = turnaroundTime;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }
}
