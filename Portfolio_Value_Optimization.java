You have some securities available to buy that each has a price Pi.
Your friend predicts for each security the stock price will be Si at some future date.
But based on volatility of each share, you only want to buy up to Ai shares of each security i.
Given M dollars to spend, calculate the maximum value you could potentially
achieve based on the predicted prices Si (and including any cash you have remaining).

Pi = Current Price
Si = Expected Future Price
Ai = Maximum units you are willing to purchase
M = Dollars available to invest
Example 1:
Input:
M = $140 available

P1=15, S1=45, A1=3 (AAPL)
P2=40, S2=50, A2=3 (BYND)
P3=25, S3=35, A3=3 (SNAP)
P4=30, S4=25, A4=4 (TSLA)

Output: $265 (no cash remaining) (I'm not sure if this is with fractional or without) This was not specified in the question.

But we'll have two answers based on our implementation:

With fractional buying (Unbounded knapsack)
Without fractional buying (1/0 Knapsack)
Example 2:
Input:
M = $30
P1=15, S1=30, A1=3 (AAPL)
P2=20, S2=45, A2=3 (TSLA)

Output:
When buying fractionals,
Buy 1.5 shares of TSLA ($67.5 value)

When buying whole shares,
Buy 2 shares of AAPL ($60 value)
                                  
                                  
                                  
  class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    
    int[] prices = {15, 40, 25, 30};
    int[] futurePrices = {45, 50, 35, 25};
    int[] maxUnit = {3, 3, 3, 4};
    int amount = 140;
    
    
    int res = maxProfit(prices, futurePrices, maxUnit, amount);
    
    //System.out.println(res);
    System.out.println(optimizeWithFractionals(140, prices, futurePrices, maxUnit));
    
  }
  
  
  
    // without fractional, it will return 255
  
  public static int maxProfit(int[] prices, int[] futurePrices, 
                        int[] maxUnit, int amount){
    int[][] dp = new int[prices.length][amount + 1];
    
    return maxHelper(prices, futurePrices, maxUnit, amount, dp, 0);
  }
  
  public static int maxHelper(int[] prices, int[] futurePrices, 
                  int[] maxUnit, int amount, int[][] dp, int index){
    
    if (index == prices.length){
      return amount;
    }
    
    if (dp[index][amount] != 0){
      return dp[index][amount];
    }
    
    if (prices[index] >= futurePrices[index]){
      // not a profit as the futrue prices is lower than price
      return 0;
    
    }
    
    int max = Integer.MIN_VALUE;
    
    for (int i = 0; i <= maxUnit[index]; i++){
      if (amount <= i * prices[index]){
          break;
      }
      
      int res = maxHelper(prices, futurePrices, maxUnit, 
                          amount - i * prices[index], dp, index + 1);
      
      max = Math.max(max, res + i * futurePrices[index]);
    }
    
    dp[index][amount] = max;
    
    return max;
  }
  
  // this will return 265
  
  static double optimizeWithFractionals(int amount, int[] currentPrice, 
                     int[] futurePrice, int[] units) {
    
    // we need to sort by ratio, so int[] does 
    //not work as the ratio is double
    
//     List<int[]> stock = new ArrayList<>();
    
//     for (int i = 0; i < prices.length; i++){
//       double ratio = (double) futurePrices[i] / prices[i];
      
//       int[] stockInfo = new int[]{ratio, prcies[i], futurePrices[i], maxUnit[i]};
      
//       stock.add(stockInfo);
//     }
    
//     Collections.sort(stock, (a, b) -> b[0] - a[0]);
    
//     int index = 0;
//     double maxProfit = 0;
    
//     while (index < stock.size() && amount > 0){
//       double numShares = Math.min(amount / (double)prices[index], maxUnit[index]);
      
//       maxProfit += numShares * futurePrices[index];
//       amount -= numShares * prices[index];
//       index++;
//     }
//     return maxProfit;
    
    
    List<stockInfo> stockInfos = new ArrayList<>();
    
    for (int i = 0; i < currentPrice.length; i++) {
        stockInfos.add(new stockInfo(((double) futurePrice[i])/currentPrice[i], currentPrice[i], futurePrice[i], units[i]));
    }
    // Just sort in the descending order of the futurePrice/currentPrice ratio, because the higher the ratio, the higher the profit.
    stockInfos.sort((stockInfo t1, stockInfo t2) -> 
                 (Double.compare(t2.ratio, t1.ratio)));
    
    
    
    // after the sort, we also need to reassign the value of the currPrices and future prices
    // as after sort, the order changes
    for (int i = 0; i < currentPrice.length; i++) {
        currentPrice[i] = stockInfos.get(i).currentPrice;
        futurePrice[i] = stockInfos.get(i).futurePrice;
        units[i] = stockInfos.get(i).unit;
    }
    int i = 0;
    double res = 0d;
    // greedily now start buying from the best to worse.
    while (i < stockInfos.size() && amount > 0) {
        double numShares = Math.min(amount / (double) currentPrice[i], units[i]);
        res += numShares * futurePrice[i];
        amount -= numShares * currentPrice[i];
        i++;
    }
    return res;
}
  
  static class stockInfo {
    public stockInfo(double r, int cp, int fp, int u) {
        ratio = r;
        currentPrice = cp;
        futurePrice = fp;
        unit = u;
    }
    double ratio; // only used to sort in descending order.
    int currentPrice;
    int futurePrice;
    int unit;
}

}


  // one transaction 
  public static int One_transaction_maxProfit(int[] prices, int[] sellPrices, int[] unit, int amount){
    
      int maxProfit = 0;
    
      for (int i = 0; i < unit.length; i++){
        int currUnit = 1;
        
        while (currUnit <= unit[i] && currUnit * prices[i] <= amount){
          currUnit++;
        }
        
        maxProfit = Math.max(maxProfit, (sellPrices[i] -  prices[i]) * (currUnit - 1));
        
      }
    return maxProfit;
  }


