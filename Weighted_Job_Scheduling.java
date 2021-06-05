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
  
  
  
  class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    String[][] pro = {{"1","AAPL", "B", "10", "10"},{ "3","GOOG", "B", "20", "5"}, 
                    {"10","AAPL", "S", "5", "15"}};
    buildPortfolio(pro, 1000);
    
  }
  
  public static void buildPortfolio(String[][] pro, int amount){
      Map<String, List<List<String>>> portMap = new HashMap<>();
      Map<String, String> resultPro = new TreeMap<>();
      
      for (String[] str : pro){
        
        String symbol = str[1];
        portMap.putIfAbsent(symbol, new ArrayList<>());
        
        List<String> list = Arrays.asList(str[2], str[3], str[4]);
        //   new ArrayList<>();
        // list.add(str[2]);
        // list.add(str[3]);
        // list.add(str[4]);
        // list.add(str[5]);
        
        //list.add(Arrays.asList());
        portMap.get(symbol).add(list);
      }
    int total = 0;
    
    for (Map.Entry<String, List<List<String>>> entry : portMap.entrySet()){
      
      int currShare = 0;
      
      for (List<String> list : entry.getValue()){
        
          int quantity = Integer.parseInt(list.get(1));
          int price = Integer.parseInt(list.get(2));
      
          if (list.get(0).equals("B")){
            total += quantity * price;
            currShare += quantity;
          } else if (list.get(0).equals("S")){
            total -= quantity * price;
            currShare -= quantity;
          }

      }
      resultPro.put(entry.getKey(), String.valueOf(currShare));
    }
    System.out.println("CASH" + "====" + (amount - total));
    for (Map.Entry<String, String> entry: resultPro.entrySet()){
      System.out.println(entry.getKey() + "===" + entry.getValue());
    }
    //System.out.println("CASH" + "====" + (amount - total));
  }
}

