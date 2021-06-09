Our goal is to build a simplified version of a real Robinhood system that reads a customer's trades from a stream, maintains what they own, and rectifies running out of cash (through a process called a "margin call", which we'll define later). We’re looking for clean code, good naming, testing, etc. We're not particularly looking for the most performant solution.

**Step 1 (tests 1-4): Parse trades and build a customer portfolio**

Your input will be a list of trades, each of which is itself a list of strings in the form [timestamp, symbol, B/S (for buy/sell), quantity, price], e.g.

[["1", "AAPL", "B", "10", "10"], ["3", "GOOG", "B", "20", "5"], ["10", "AAPL", "S", "5", "15"]]

is equivalent to buying 10 shares (i.e. units) of AAPL for 5 each at timestamp 3, and selling 5 shares of AAPL for $15 at timestamp 10.

**Input assumptions:**

- The input is sorted by timestamp
- All numerical values are nonnegative integers
- Trades will always be valid (i.e. a customer will never sell more of a stock than they own).

From the provided list of trades, our goal is to maintain the customer's resulting portfolio (meaning everything they own), **assuming they begin with $1000**. For instance, in the above example, the customer would end up with $875, 5 shares of AAPL, and 20 shares of GOOG. You should return a list representing this portfolio, formatting each individual position as a list of strings in the form [symbol, quantity], using 'CASH' as the symbol for cash and sorting the remaining stocks alphabetically based on symbol. For instance, the above portfolio would be represented as

[["CASH", "875"], ["AAPL", "5"], ["GOOG", "20"]]

**Step 2 (tests 5-7): Margin calls**

If the customer ever ends up with a negative amount of cash **after a buy**, they then enter a process known as a **margin call** to correct the situation. In this process, we forcefully sell stocks in the customer's portfolio (sometimes including the shares we just bought) until their cash becomes non-negative again.

We sell shares from the most expensive to least expensive shares (based on each symbol's most-recently-traded price) with ties broken by preferring the alphabetically earliest symbol. Assume we're able to sell any number of shares in a symbol at that symbol's most-recently-traded price.

For example, for this input:

```
[["1", "AAPL", "B", "10", "100"],
["2", "AAPL", "S", "2", "80"],
["3", "GOOG", "B", "15", "20"]]
```

The customer would be left with 8 AAPL shares, 15 GOOG shares, and 80 a share) to cover the deficit. Afterwards, they would have 6 shares of AAPL, 15 shares of GOOG, and a cash balance of $20.

The expected output would be

[["CASH", "20"], ["AAPL", "6"], ["GOOG", "15"]]

**Step 3/Extension 1 (tests 8-10): Collateral**

Certain stocks have special classifications, and require the customer to also own another "collateral" stock, meaning it cannot be sold during the margin call process. Our goal is to handle a simplified version of this phenomenon.

Formally, we'll consider stocks with symbols ending in "O" to be special, with the remainder of the symbol identifying its collateral stock. For example, AAPLO is special, and its collateral stock is AAPL. **At all times**, the customer must hold at least as many shares of the collateral stock as they do the special stock; e.g. they must own at least as many shares of AAPL as they do of AAPLO.

As a result, the margin call process will now sell the most valuable **non-collateral** share until the balance is positive again. Note that if this sells a special stock, some of the collateral stock may be freed up to be sold.

For example, if the customer purchases 5 shares of AAPL for 75 each, then finally 5 shares of AAPLO for 125, but their shares of AAPL can no longer be used to cover the deficit (since they've become collateral for AAPLO). As a result, 2 shares of GOOG would be sold back (again at 25, 5 AAPL, 5 AAPLO, and 3 GOOG. Thus, with an input of

[["1", "AAPL", "B", "5", "100"], ["2", "GOOG", "B", "5", "75"], ["3", "AAPLO", "B", "5", "50"]]

the corresponding output would be

[["CASH", "25"], ["AAPL", "5"], ["AAPLO", "5"], ["GOOG", "3"]


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
    
        String[][] pro1 = {{"1", "AAPL", "B", "10", "100"},{ "2", "AAPL", "S", "2", "80"}, 
                    {"3", "GOOG", "B", "15", "20"}};
    
    
        String[][] pro3 = {{"1", "AAPL", "B", "5", "100"},{"2", "GOOG", "B", "5", "75"}, 
                    {"3", "AAPLO", "B", "5", "50"}};
    buildPortfolio(pro1, 1000);
    
  }

  public static void buildPortfolio(String[][] pro, int amount){
    
      Map<String, Integer> resultPro = new TreeMap<>();
    
      //margin
      // key is the symbol, value is the arr curr qualiy + price
       // add the map to the pq and sort by the price;
       // sold by high price
      Map<String, int[]> marginMap = new TreeMap<>();
    
      //collaberal
       //key is the origial symel
      // value is the symbol with 0
       // remove 
      Map<String, String> colMap = new HashMap<>();
    
    
      int totalAmount = 0;
      
      for (String[] str : pro){
        String symbol = str[1];
        String sign = str[2];
        int quality = Integer.parseInt(str[3]);
        int price = Integer.parseInt(str[4]);
        
        int currQuality = resultPro.getOrDefault(symbol, 0);        
        if (sign.equals("B")){
                    
            resultPro.put(str[1], currQuality + quality);
            totalAmount += quality * price;
             
  //Collateral=============start=========================================== 
//           if (symbol.contains("O")){
//             int index = symbol.indexOf('O');
            
//             if (resultPro.containsKey(symbol.substring(0, index))){
//               colMap.put(symbol.substring(0, index), symbol);
//             }
//           }
          
  //Collateral============ end============================================
          
  //marign ================start===========================================
            int[] marginArray = marginMap.getOrDefault(symbol, new int[]{currQuality + quality, price});
            marginMap.put(symbol, marginArray);
            marginMap.get(symbol)[1] = price;
          
             PriorityQueue<Map.Entry<String, int[]>> pq =
            new PriorityQueue<>((a, b) -> b.getValue()[1] - a.getValue()[1]);
              
            pq.addAll(marginMap.entrySet());            
            Map.Entry<String, int[]> prev = null;
 //marign =================end==========================================          
            while (totalAmount > 1000){
              
                Map.Entry<String, int[]> currMap = pq.poll();
              
  //Collateral ========================= start
                // from the pq, do not add the symbol with o related
                 // if (colMap.containsKey(currMap.getKey())){
                 //    continue;
                 // }
  //Collateral =========================end
                 
                String currKey = currMap.getKey();
                int currCount = currMap.getValue()[0];
              
                marginMap.get(currKey)[0] = currCount - 1;
                totalAmount -= currMap.getValue()[1];

                if (marginMap.get(currKey)[0] > 0){

                  pq.offer(currMap);
                }

                prev = currMap;

            }        
          
        } else if (sign.equals("S")){
            resultPro.put(str[1], currQuality - quality);
            totalAmount -= quality * price;
          
          
  //margin============================== start
            int[] marginArray = marginMap.getOrDefault(symbol, new int[]{currQuality - quality, price});
            marginMap.put(symbol, marginArray);
            marginMap.get(symbol)[0] = currQuality - quality;
            marginMap.get(symbol)[1] = price;
  //margin============================== end       
        }
      }
    
    
    System.out.println("CASH" + "====" + (amount - totalAmount));
    
    // for (Map.Entry<String, Integer> entry: resultPro.entrySet()){
    //   System.out.println(entry.getKey() + "===" + entry.getValue());
    // }
    
     System.out.println("==================" );
    for (Map.Entry<String, int[]> entry: marginMap.entrySet()){
      System.out.println(entry.getKey() + "===" + entry.getValue()[0]);
    }
    
    
    
  }
}

