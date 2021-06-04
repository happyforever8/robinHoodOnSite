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
