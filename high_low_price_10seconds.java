第一轮算法数据处理。白人小哥。有time, value的pair,然后总结出每十秒钟的最高，最低，开始，结束价格。
（16， 7），（19， 8）, (30, 2), (37, 3) (64, 6) -> (10, 8, 7, 7, 8), (20, 8, 8,8,8), 
(30, 3, 2, 2, 3), (40, 3, 3, 3, 3) 50, 3, 3, 3, 3)， (60, 6,6,6,6)
  
  class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    int[][] pairs = {{16, 7}, {19, 8}, {30, 2}, {37, 3}, {64, 6} };
    interval(10, pairs);
      
  }
  
  public static Map<Integer, List<Integer>> interval(int n, int[][] pairs){
    
    System.out.println("111");
    Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
    
    int minTime = pairs[0][0];
    int maxTime = pairs[pairs.length - 1][0];
    

    Map<Integer, List<int[]>> map = new HashMap<>();
    Map<Integer, List<Integer>> result = new HashMap<>();
    
    for (int[] pair : pairs){
       int time = pair[0];
      
       map.putIfAbsent(time / 10, new ArrayList<>());
      
      map.get(time / 10).add(pair);
    }

    
    int start = minTime / 10;
      int end = maxTime / 10;
    

    int prev = 0;
   for (int i = start; i <= end; i++){
     
   
     if (map.containsKey((Integer)i)){
       prev = i;

        List<int[]> list = map.get(i);
       
        Collections.sort(list, (a, b) -> a[0] - b[0]);
       
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
       
        for (int[] curr : list){
          min = Math.min(curr[1], min);
          max = Math.max(curr[1], max);
        }
       
       result.put(i, Arrays.asList(max, min, list.get(0)[1], list.get(list.size() - 1)[1]));
       
     } else {
       
       System.out.println(prev);
       
       int value = result.get(prev).get(3);
       
       result.put(i, Arrays.asList(value, value, value, value));
     }
      
   }
    
    for (Map.Entry<Integer, List<Integer>> entry : result.entrySet()){
      System.out.print(entry.getKey() * 10 + "===");
      
      for (int num : entry.getValue()){
        System.out.print("*" + num + "*");
      }
      System.out.println("============");
    }
    
   return result;
  }
  
}
