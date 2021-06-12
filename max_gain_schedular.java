
deadline，必须在deadline之前做完才行。根据一组task，求如何能使gain的总和最大。注意：不需要所有task都能做完，只要gain最大就可以
Task  |   Gain  |  deadline
   A           3           3
   B           2           3
   C           4           2
   D           1           1

class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    int[][] arr = {{3, 3}, {2, 3}, {4, 2}, {1, 1}};
     Arrays.sort(arr, (a, b) -> a[1] - b[1]);
    
    
      
    int[][] arr1 = {{5, 1},{1, 1}};
     Arrays.sort(arr1, (a, b) -> a[1] - b[1]);
    
    
    
    int result =  helper(arr, 0, 0);
     System.out.println(result);
    
    
     List<List<int[]>> result1 = new ArrayList<>();
    List<int[]> list = new ArrayList<>();
    
    
     backTrack(arr, 0, 0, list, result1, new HashSet<>());
    
    for (int i = 0; i < result1.size(); i++){
    
      for (int[] num : result1.get(i)){
          System.out.println(num[0] + "--" +num[1]);
      }
      
       System.out.println("==============");
    }
    
    
    
  }
  static Map<int[], Integer> map = new HashMap<>();
  
  List<int[]> list = new ArrayList<>();
  
  public static int helper(int[][] arr, int index, int currTime){
    
    if (index == arr.length){
        return 0;
    }
    int[] curr = new int[]{index, currTime};
    if (map.containsKey(curr)){
      return map.get(curr);
    }
    int taken = 0;
    int notTaken = 0;
    if (currTime + 1 <= arr[index][1]){
       taken = arr[index][0] + helper(arr, index + 1, currTime + 1);
       //System.out.println(taken + "----");
    }
    
    notTaken = helper(arr, index + 1, currTime);
    map.put(curr, Math.max(taken, notTaken));
    
    return map.get(curr);
  }
 

  public static void backTrack(int[][] arr, int amount, int index, List<int[]> list,  List<List<int[]>> result, Set<int[]> set){
    
    if (index >= arr.length){
      return;
    }
    
    if (amount == 9){
      result.add(new ArrayList<>(list));
     // System.out.println(result.size());
      return;
    }
    set.add(arr[index]);
    for (int i = index; i < arr.length; i++){
      int[] curr = arr[i];
      list.add(curr);
      if (set.add(curr)){
        backTrack(arr, amount  + curr[0], index + 1, list, result, set);
      }
      
      list.remove(list.size() - 1);
    }
    
  }

}
================================================ this solution is from geekfor geeks===========================================================
class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    
    ArrayList<Job> arr = new ArrayList<Job>();
 
        // arr.add(new Job('a', 2, 100));
        // arr.add(new Job('b', 1, 19));
        // arr.add(new Job('c', 2, 27));
        // arr.add(new Job('d', 1, 25));
        // arr.add(new Job('e', 3, 15));
    
    
      arr.add(new Job('a', 3, 3));
        arr.add(new Job('b', 2, 3));
        arr.add(new Job('c', 4, 2));
        arr.add(new Job('d', 1, 1));
       
        // Function call
        System.out.println("Following is maximum "
                           + "profit sequence of jobs");
 
        //Job job = new Job();
 
        // Calling function
        int maxProfit = printJobScheduling(arr, 3);
    System.out.println(maxProfit);
    
  }
  
  
  static int printJobScheduling(ArrayList<Job> arr, int t)
    {
    
         int maxProfit = 0;
        // Length of array
        int n = arr.size();
 
        // Sort all jobs according to
        // decreasing order of profit
        Collections.sort(arr,
                         (a, b) -> b.profit - a.profit);
 
        // To keep track of free time slots
        boolean result[] = new boolean[t];
 
        // To store result (Sequence of jobs)
        char job[] = new char[t];
 
        // Iterate through all given jobs
        for (int i = 0; i < n; i++)
        {
            // Find a free slot for this job
            // (Note that we start from the
            // last possible slot)
            for (int j = Math.min(t - 1, arr.get(i).deadline - 1);
                 j >= 0; j--) {
 
                // Free slot found
                if (result[j] == false)
                {
                    result[j] = true;
                    job[j] = arr.get(i).id;
                    maxProfit += arr.get(i).profit;
                    break;
                }
            }
        }
 
        // Print the sequence
        for (char jb : job)
        {
            System.out.print(jb + " ");
        }
        System.out.println();
    
        return maxProfit;
    }
  
  
  
  
  static class Job {
    // Each job has a unique-id,
    // profit and deadline
    char id;
    int deadline, profit;
 
    // Constructors
    public Job() {}
 
    public Job(char id, int profit, int deadline)
    {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
  }
}

