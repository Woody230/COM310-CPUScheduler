//Brandon Selzer

package cpuscheduler.Processes;

import java.util.ArrayList;
import java.util.Random;

//This class was created in order to have an ArrayList of processes with methods that manage it.
public class ProcessList 
{
    private int numProcesses = 0;
    
    //ArrayLists are used since they expand automatically.
    //Some of the methods below create queue like properties but every element needs to accessed so ArrayLists are used.
    private ArrayList<Process> processList; 
    
    public ProcessList()
    {
        processList = new ArrayList();
    }
    
    public ArrayList<Process> getListOfProcesses()
    {
        return processList;
    }
    
    //A deep copy is necessary in some of the schedulers in order to keep the original list intact.
    public static ArrayList<Process> getListOfProcessesDeepCopy(ArrayList<Process> processList)
    {
        ArrayList<Process> deepCopy = new ArrayList();
        
        for(Process i: processList)
        {
            Process copy = new Process(i.getPID());
            copy.setBurstTime(i.getBurstTime());
            copy.setPriority(i.getPriority());
            copy.setWaitTime(i.getWaitTime());
            copy.setTurnaroundTime(i.getTurnaroundTime());
            deepCopy.add(copy);
        }
        
        return deepCopy;
    }
    
    public int getNumProcesses()
    {
        return numProcesses;
    } 
    
    //Used by the schedulers to set the list of processes to the deep copy of the original.
    public void setListOfProcesses(ArrayList<Process> processList)
    {
        this.processList = processList;
        numProcesses = processList.size();
    }
 
    public void addProcess(Process process)
    {
        processList.add(process);
        numProcesses++;
    }
    
    //Used when reducing the # of processes since this is more convenient than finding the PID of the last process.
    public void removeLastProcess()
    {
        processList.remove(numProcesses - 1);
        numProcesses--;
    }
    
    //Used by round robin's deep copy in order to go "around" circularly. 
    public void removeFirstProcess()
    {
        processList.remove(0);
        numProcesses--;
    }
    
    //Random burst times range from 1 to 99.
    public void setRandomBurstTimes()
    {
        Random rng = new Random();
        
        for(Process i: processList)
        {
            i.setBurstTime(rng.nextInt(99) + 1);
        }
    }
    
    //Random priorities range from 0 to the # of processes 
    public void setRandomPriorities()
    {
        Random rng = new Random();
        
        for(Process i: processList)
        {
            i.setPriority(rng.nextInt(numProcesses));
        }
    }
    
    //Used by shortest job first in order to order the list from shortest to longest times.
    public void sortListOfProcessesByBurstTime()
    {
        mergeSort(true, false, 0, numProcesses - 1);
    }
    
    //Used by priority in order to order the list from highest to lowest priority.
    public void sortListOfProcessesByPriority()
    {
        mergeSort(false, true, 0, numProcesses - 1);
    } 
    
    //From and to refer to the beginning and end index of the partition.
    private void mergeSort(boolean sortByBurstTime, boolean sortByPriority, int from, int to)
    {
        int mid;
        
        if(from < to)
        {
            mid = (from + to) / 2;
            mergeSort(sortByBurstTime, sortByPriority, from, mid);
            mergeSort(sortByBurstTime, sortByPriority, mid + 1, to);
            merge(sortByBurstTime, sortByPriority, from, mid, to);
        }
    }
    
    //Compare each partition's elements and put the smallest burst times first.
    private void merge(boolean sortByBurstTime, boolean sortByPriority, int from, int mid, int to)
    {
        ArrayList<Process> tempList = getListOfProcessesDeepCopy(processList);
        
        int left = from; 
        int right = mid + 1;
        int tempIndex = from;
        
        while(left <= mid && right <= to) //Both partitions haven't been exhausted.
        {
            if(sortByBurstTime)
            {
                //Using <= instead of < allows for FCFS order for equal burst times.
                //Put shorter burst times first.
                if(processList.get(left).getBurstTime() <= processList.get(right).getBurstTime())
                {
                    assignProcessInOneListToAnother(tempList, processList, tempIndex, left);
                    left++;
                }
                else
                {
                    assignProcessInOneListToAnother(tempList, processList, tempIndex, right);
                    right++;
                }
            }
            else if(sortByPriority)
            {
                //Using >= instead of > allows for FCFS order for equal priorities.
                //Put higher priorities first.
                if(processList.get(left).getPriority() >= processList.get(right).getPriority())
                {
                    assignProcessInOneListToAnother(tempList, processList, tempIndex, left);
                    left++;
                }
                else
                {
                    assignProcessInOneListToAnother(tempList, processList, tempIndex, right);
                    right++;
                }
            }
            
            tempIndex++;
        }
        
        while(left <= mid) //Right partition exhausted so keep putting everything from the left into the tempList.
        {
            assignProcessInOneListToAnother(tempList, processList, tempIndex, left);
            left++;
            tempIndex++;
        }
        
        while(right <= to) //Left partition exhausted so keep putting everything from the right into the tempList. 
        {
            assignProcessInOneListToAnother(tempList, processList, tempIndex, right);
            right++;
            tempIndex++;
        }
        
        processList = tempList;
    }
    
    //Compare each partition's elements and put the highest priorities first.
//    private void mergeByPriority(int from, int mid, int to)
//    {
//        ArrayList<Process> tempList = getListOfProcessesDeepCopy(processList);
//        
//        int left = from; 
//        int right = mid + 1;
//        int tempIndex = from;
//        
//        while(left <= mid && right <= to) //Both partitions haven't been exhausted.
//        {
//            //Using >= instead of > allows for FCFS order for equal priorities.
//            //Put higher priorities first.
//            if(processList.get(left).getPriority() >= processList.get(right).getPriority())
//            {
//                assignProcessInOneListToAnother(tempList, processList, tempIndex, left);
//                left++;
//            }
//            else
//            {
//                assignProcessInOneListToAnother(tempList, processList, tempIndex, right);
//                right++;
//            }
//            
//            tempIndex++;
//        }
//        
//        while(left <= mid) //Right partition exhausted so keep putting everything from the left into the tempList.
//        {
//            assignProcessInOneListToAnother(tempList, processList, tempIndex, left);
//            left++;
//            tempIndex++;
//        }
//        
//        while(right <= to) //Left partition exhausted so keep putting everything from the right into the tempList.
//        {
//            assignProcessInOneListToAnother(tempList, processList, tempIndex, right);
//            right++;
//            tempIndex++;
//        }
//        
//        processList = tempList;
//    }
    
    //Copy the contents of the object (process) from one list to another.
    private void assignProcessInOneListToAnother(ArrayList<Process> assignee, ArrayList<Process> assigner, int assigneeIndex, int assignerIndex)
    {
        assignee.get(assigneeIndex).setPID(assigner.get(assignerIndex).getPID());
        assignee.get(assigneeIndex).setBurstTime(assigner.get(assignerIndex).getBurstTime());
        assignee.get(assigneeIndex).setPriority(assigner.get(assignerIndex).getPriority());
        assignee.get(assigneeIndex).setTurnaroundTime(assigner.get(assignerIndex).getTurnaroundTime());
        assignee.get(assigneeIndex).setWaitTime(assigner.get(assignerIndex).getWaitTime());
    }
}//end class
