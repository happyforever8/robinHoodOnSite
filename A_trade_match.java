A *trade* is defined as a fixed-width string containing the 4 properties given below separated by commas:
- Symbol (4 alphabetical characters, left-padded by spaces)
- Type (either "B" or "S" for buy or sell)
- Quantity (4 digits, left-padded by zeros)
- ID (6 alphanumeric characters)

e.g. `"AAPL,B,0100,ABC123"`

which represents a trade of a buy of 100 shares of AAPL with ID "ABC123"

Given two lists of trades - called "house" and "street" trades, write code to
filter out groups of *matches* between trades and return a list of *unmatched*
house and street trades sorted alphabetically. There are many ways to *match* trades,
the first and most important way is an *exact match* (Tests 1-5):

- An exact match is a house_trade+street_trade pair with identical symbol, type, quantity, and ID

**Note: Trades are distinct but not unique**

For example, given the following input:

```
// house_trades:
[
  "AAPL,B,0100,ABC123",
  "AAPL,B,0100,ABC123",
  "GOOG,S,0050,CDC333"
]

// street_trades:
[
  " FB,B,0100,GBGGGG",
  "AAPL,B,0100,ABC123"
]
```

We would expect the following output:

```
[
  " FB,B,0100,GBGGGG",
  "AAPL,B,0100,ABC123",
  "GOOG,S,0050,CDC333"
]
```

Because the first (or second) house trade and second street trade form an exact
match, leaving behind three unmatched trades.

Follow-up 1 (Test 6,7,8,9): A "fuzzy" match is a house_trade+street_trade pair
with identical symbol, type, and quantity *ignoring* ID. Prioritize exact matches
over fuzzy matches. Prioritize matching the earliest alphabetical house trade with the earliest alphabetical street trade in case of ties.

Follow-up 2: (Test 10) An offsetting match is a house_trade+house_trade or 
street_trade+street_trade pair where the symbol and quantity of both trades are 
the same, but the type is different (one is a buy and one is a sell). 
Prioritize exact and fuzzy matches over offsetting matches. 
Prioritize matching the earliest alphabetical buy with the earliest alphabetical sell.

  
  
 import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
    
  static Map<String, String> map = new HashMap<>();
  
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    String[] houseTrade = {"AAPL,B,0100,ABC123", "AAPL,B,0100,ABC123", "GOOG,S,0050,CDC333"};
    String[] streetTrade  = {" FB,B,0100,GBGGGG", "AAPL,B,0100,ABC123" };

    
    //List<String> houseTrade = Arrays.asList(houseTrade1);
    //List<String> streetTrade = Arrays.asList(streetTrade1);
    
     String[] houseTrade1 = {
 "AAPL,B,0010,ABC123", 
 "AAPL,S,0015,ZYX444", 
 "AAPL,S,0015,ZYX444", 
 "GOOG,S,0050,GHG545"};
    
    String[] streetTrade1 = {
  "GOOG,S,0050,GHG545", 
 "AAPL,S,0015,ZYX444", 
 "AAPL,B,0500,TTT222"};
    
    
    
        String[] houseTrade2 = {
"AAPL, B, 15, UUID1", 
"GOOG, S, 5, UUID2", 
"AAPLE, B, 15, UUID1"};
    
    String[] streetTrade2 = {
  "GOOG,S,0050,GHG545", 
 "AAPL,S,0015,ZYX444", 
 "AAPL,B,0500,TTT222"};
    
            String[] houseTrade3 = {
 "AAPL,B,0100,ABC123", 
 "AAPL,B,0100,ABC123", 
 "AAPL,B,0100,ABC123", 
 "GOOG,S,0050,CDC333"};
    
    String[] streetTrade3 = {
  "FB,B,0100,GBGGGG", 
 "AAPL,B,0100,ABC123"};
    
    
    
    
        String[] fuzzyhouseTrade2 = {
 "AAPL,S,0010,ZYX444", 
 "AAPL,S,0010,ZYX444", 
 "AAPL,B,0010,ABC123", 
 "GOOG,S,0050,GHG545"};
    
    String[] fuzzystreetTrade2 = {
  "GOOG,S,0050,GHG555", 
 "AAPL,S,0010,ZYX444", 
 "AAPL,B,0010,TTT222"};
    
    
    //exactMatch(houseTrade, streetTrade);
    // match(houseTrade2, streetTrade2);
    fuzzyMatch(fuzzyhouseTrade2, fuzzystreetTrade2);
    //offSetMatch(fuzzystreetTrade2);
  }


  
  public static String offSetHelper(String trade){
    
    String[] str = trade.split(",");
    StringBuilder sb = new StringBuilder();
    
    sb.append(str[0]);
    sb.append(str[2]);
    
    return sb.toString();
  }
  
  
  public static void offSetMatch(String[] trades){
    List<String> offset = new ArrayList<>();
    
    Map<String, List<String>> map = new HashMap<>();
    
    for (String str : trades){
      String temp = offSetHelper(str);
      // the key is the symbol + quantity
      map.putIfAbsent(temp, new ArrayList<>());
      map.get(temp).add(str);
    }
    
    for (Map.Entry<String, List<String>> entry : map.entrySet()){
      
      List<String> buyList = new ArrayList<>();
      List<String> sellList = new ArrayList<>();
      
      for (String str : entry.getValue()){
        String[] s = str.split(",");
        
        if (s[1].equals("B")){
          buyList.add(str);
        } else {
          sellList.add(str);
        }
        
      }
      
    for (int i = 0; i < buyList.size(); i++){
      String buyStr = buyList.get(i);
      
      for (int j = 0; j < sellList.size(); j++){
        offset.add(buyStr);
        offset.add(sellList.get(j));
        System.out.println("-----"+ buyStr + "----" +sellList.get(j));
      }
      }
    }
    
    
//     List<String> fuzzyMatch3 =  fuzzyMatch(trades, trades);
      
//       for (String str : fuzzyMatch3){
//          System.out.println("---fuzzy match is--"+ str );
//       }
      
//       for (String s : fuzzyMatch3){
//         if (offset.contains(s)){
//            System.out.println("---fuzzy match oder  is--"+ s );
//         }
//       }
    
    
  }
    
 //===================================FUzzy Match=================================================================
  static List<String> exactMath = new ArrayList<>();
  
  
  public static List<String> fuzzyMatch(String[] houseTrade, String[] streetTrade){
    List<String> fuzMatch = new ArrayList<>();
    
    Arrays.sort(houseTrade);
    Arrays.sort(streetTrade);
    List<String> list = new ArrayList<>();
    // get the exact match
    exactMatchHelper(new ArrayList<>(), houseTrade,streetTrade, 0, 0 );
    // get the fuzzy match which is excluded the exact match
    fuzzyMatchHelper(list, houseTrade, streetTrade, 0, 0);
    
    fuzMatch.addAll(exactMath);
    fuzMatch.addAll(list);
    
    for (String str : exactMath){
      System.out.println(str);
    }
    System.out.println("===========");
    for (String str : list){
      System.out.println(str);
    }
    
    return fuzMatch;
      
  }
  
 
   public static void fuzzyMatchHelper(List<String> list, String[] houseTrade,String[] streetTrade, int hLen, int sLen ){
    
    while (hLen < houseTrade.length && sLen < streetTrade.length){
      //fuzzy match
      
      String currHouse = fuzzyHelper(houseTrade[hLen]);
      String currStreet = fuzzyHelper(streetTrade[sLen]); 
      
        if (currHouse.equals(currStreet)){
          // exclude the exactMatch and add later
          if (!exactMath.contains(houseTrade[hLen])){ 
               list.add(houseTrade[hLen]);
               list.add(streetTrade[sLen]);
          }
          hLen++;
          sLen++;
          
        } else if (currHouse.compareTo(currStreet) < 0){
          
          hLen++;
        } else {
          sLen++;
        }
    }
  }
  
  public static String fuzzyHelper(String trade){
    
    int index = trade.lastIndexOf(",");
    return trade.substring(0, index);
  
  }
  
  
  
  
  //====================================================================================================
  public static void exactMatch(String[] houseTrade, String[] streetTrade ){
    Arrays.sort(houseTrade);
    Arrays.sort(streetTrade);
    
    List<String> unMatchlist = exactMatchHelper(new ArrayList<>(), houseTrade, streetTrade, 0, 0);
  
    
    for (String str : unMatchlist){
      System.out.println(str);
    }
  }
  

  public static List<String> exactMatchHelper(List<String> list, String[] houseTrade,String[] streetTrade, int hLen, int sLen ){
    
        

    while (hLen < houseTrade.length && sLen < streetTrade.length){
      
        //exact match
        String currHouse = houseTrade[hLen];
        String currStreet = streetTrade[sLen];
      
        if (currHouse.equals(currStreet)){
          exactMath.add(currStreet);
          hLen++;
          sLen++;
          
        } else if (currHouse.compareTo(currStreet) < 0){
          list.add(currHouse);
          
          hLen++;
        } else {
          //list.add(currHouse);
          list.add(currStreet);
          sLen++;
        }
    }
    
    while (hLen < houseTrade.length){
          list.add(houseTrade[hLen]);
          
          hLen++;
    } 
    while (sLen < streetTrade.length){
          list.add(streetTrade[sLen]);
          sLen++;
    }
    
    return list;
  }
}


