Weighted Job Scheduling
Given N jobs where every job is represented by following three elements of it.

Start Time
Finish Time
Profit or Value Associated
Find the maximum profit subset of jobs such that no two jobs in the subset overlap.

Example:

Input: Number of Jobs n = 4
       Job Details {Start Time, Finish Time, Profit}
       Job 1:  {1, 2, 50} 
       Job 2:  {3, 5, 20}
       Job 3:  {6, 19, 100}
       Job 4:  {2, 100, 200}
Output: The maximum profit is 250.
We can get the maximum profit by scheduling jobs 1 and 4.
Note that there is longer schedules possible Jobs 1, 2 and 3 
but the profit with this schedule is 20+50+100 which is less than 250.  
  
  
  The algorithm is:

Sort the jobs by non-decreasing finish times.
For each i from 1 to n, determine the maximum value of the schedule from the subsequence of jobs[0..i].
  Do this by comparing the inclusion of job[i] to the schedule to the exclusion of job[i] to the schedule, and then taking the max.
To find the profit with inclusion of job[i]. 
  we need to find the latest job that doesn’t conflict with job[i]. 
  The idea is to use Binary Search to find the latest non-conflicting job.
  
  
  
 public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
     Job jobs[] = {new Job(1, 2, 50), new Job(3, 5, 20),
                      new Job(6, 19, 100), new Job(2, 100, 200)};
  
        System.out.println("Optimal profit is " + schedule(jobs));
  }
  
  static public int binarySearch(Job jobs[], int index)
    {
        // Initialize 'lo' and 'hi' for Binary Search
        int lo = 0, hi = index - 1;
  
        // Perform binary Search iteratively
        while (lo <= hi)
        {
            int mid = (lo + hi) / 2;
            if (jobs[mid].finish <= jobs[index].start)
            {
                if (jobs[mid + 1].finish <= jobs[index].start)
                    lo = mid + 1;
                else
                    return mid;
            }
            else
                hi = mid - 1;
        }
  
        return -1;
    }
  
    // The main function that returns the maximum possible
    // profit from given array of jobs
    static public int schedule(Job jobs[])
    {
        // Sort jobs according to finish time
        Arrays.sort(jobs, (a, b) -> a.finish - b.finish);
  
        // Create an array to store solutions of subproblems.
        // table[i] stores the profit for jobs till jobs[i]
        // (including jobs[i])
        int n = jobs.length;
        int table[] = new int[n];
        table[0] = jobs[0].profit;
  
        // Fill entries in M[] using recursive property
        for (int i=1; i<n; i++)
        {
            // Find profit including the current job
            int inclProf = jobs[i].profit;
           // int l = binarySearch(jobs, i);
          int l = latestNonConflict(jobs, i);
            if (l != -1)
                inclProf += table[l];
  
            // Store maximum of including and excluding
            table[i] = Math.max(inclProf, table[i-1]);
        }
  
        return table[n-1];
    }
  
  
      static public  int latestNonConflict(Job arr[], int i)
      {
          for (int j=i-1; j>=0; j--)
          {
              if (arr[j].finish <= arr[i].start)
                  return j;
          }
          return -1;
      }
}


class Job
{
    int start, finish, profit;
  
    // Constructor
    Job(int start, int finish, int profit)
    {
        this.start = start;
        this.finish = finish;
        this.profit = profit;
    }
}
