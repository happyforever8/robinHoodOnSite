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
    
     String[] houseTradefuzzey = {
 "AAPL,B,0010,ABC123", 
 "AAPL,S,0015,ZYX444", 
 "AAPL,S,0015,ZYX444", 
 "GOOG,S,0050,GHG545"};
    
    String[] streetTradeFuzzy = {
  "GOOG,S,0050,GHG545", 
 "AAPL,S,0015,ZYX444", 
 "AAPL,B,0010,TTT222", "AAPL,S,0015,ZYX444"};
    
    fuzzyMath(houseTradefuzzey, streetTradeFuzzy);
  }
  
  
  
  
  
  public static void offSet(String[] trade){
    
  }
  
  public static void fuzzyMath(String[] houseTrade, String[] streetTrade ){
    
    houseTrade = construct(houseTrade, 3);
    
    streetTrade = construct(streetTrade, 3);
    
    Arrays.sort(houseTrade);
    Arrays.sort(streetTrade);
    
    List<String> list = new ArrayList<>();
    
    int h = 0;
    int s = 0;
    
    while (h < houseTrade.length && s < streetTrade.length){
        String currHouse = houseTrade[h];
        String currStreet = streetTrade[s];
      
        if (currHouse.equals(currStreet)){
          h++;
          s++;
          list.add(map.get(currHouse));
          
        } else if (currHouse.compareTo(currStreet) < 0){
          //list.add(map.get(currHouse));
          
          h++;
        } else {
          //list.add(currHouse);
          //list.add(map.get(currStreet));
          s++;
        }
    }
    
//     while (h < houseTrade.length){
//           list.add(houseTrade[h]);
          
//           h++;
//     } 
//     while (s < streetTrade.length){
//           list.add( streetTrade[s]);
//           s++;
//     }
    
    for (String str : list){
      System.out.println(str);
    }
  }

  public static String[] construct(String[] trade, int n){
    
    String[] result = new String[trade.length];
    
    for (int i = 0; i < trade.length; i++){
      String[] ch = trade[i].split(",");
      
      StringBuilder sb = new StringBuilder();
      
      for (int j = 0; j < 3; j++){
        if (j == 3){
          sb.append(ch[j]);
        } else {
          sb.append(ch[j]).append(",");
        }
      }
      
      result[i] = sb.toString();
      map.put(result[i], trade[i]);
      
    }
    
     Arrays.sort(result);
    
    return result;
  }
  
  
  public static void exactNotMath(String[] houseTrade, String[] streetTrade ){
    
    
    Arrays.sort(houseTrade);
    Arrays.sort(streetTrade);
    
    List<String> list = new ArrayList<>();
    
    int h = 0;
    int s = 0;
    
    while (h < houseTrade.length && s < streetTrade.length){
        String currHouse = houseTrade[h];
        String currStreet = streetTrade[s];
      
        if (currHouse.equals(currStreet)){
          h++;
          s++;
          
        } else if (currHouse.compareTo(currStreet) < 0){
          list.add(currHouse);
          
          h++;
        } else {
          //list.add(currHouse);
          list.add(currStreet);
          s++;
        }
    }
    
    while (h < houseTrade.length){
          list.add(houseTrade[h]);
          
          h++;
    } 
    while (s < streetTrade.length){
          list.add(streetTrade[s]);
          s++;
    }
    
    for (String str : list){
      System.out.println(str);
    }
  }
}


