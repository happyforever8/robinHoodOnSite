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
                                  
                                  
                                  
                                  List<Tuple> tuples = new ArrayList<>();
    for (int i = 0; i < currentPrice.length; i++) {
        tuples.add(new Tuple(((double) futurePrice[i])/currentPrice[i], currentPrice[i], futurePrice[i], units[i]));
    }
    // Just sort in the descending order of the futurePrice/currentPrice ratio, because the higher the ratio, the higher the profit.
    tuples.sort((Tuple t1, Tuple t2) -> (Double.compare(t2.ratio, t1.ratio)));
    for (int i = 0; i < currentPrice.length; i++) {
        currentPrice[i] = tuples.get(i).currentPrice;
        futurePrice[i] = tuples.get(i).futurePrice;
        units[i] = tuples.get(i).unit;
    }
    int i = 0;
    double res = 0d;
    // greedily now start buying from the best to worse.
    while (i < tuples.size() && amount > 0) {
        double numShares = Math.min(amount / (double) currentPrice[i], units[i]);
        res += numShares * futurePrice[i];
        amount -= numShares * currentPrice[i];
        i++;
    }
    return res;
}
static class Tuple {
    public Tuple(double r, int cp, int fp, int u) {
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

int optimizeWithoutFractionals(int amount, int[] currentPrice, int[] futurePrice, int[] units) {
    return dfs(0, amount, currentPrice, futurePrice, units, new Integer[currentPrice.length][amount + 1]);
}

int dfs(int start, int amount, int[] currentPrice, int[] futurePrice, int[] units, Integer[][] matrix) {
    if (start == currentPrice.length) {
        // reached the end, return the remaining (cash) balance.
        return amount;
    }
    if (matrix[start][amount] != null) {
        // cache hit
        return matrix[start][amount];
    }
    if (currentPrice[start] >= futurePrice[start]) {
        // not a profitable transaction as futurePrice will not be any higher.
        return 0;
    }
    int max = Integer.MIN_VALUE;
    for (int i = 0; i <= units[start]; i++) {
        // i == 0, implies no shares bought.
        if (amount < i * currentPrice[start]) {
            // not enough amount to buy i shares, break. no point of going further.
            break;
        }
        // we can buy i shares of type [start] at currentPrice[start], move on to the next share with the remaining amount
        // and see what is the best we can do.
        int res = dfs(start + 1, amount - i * currentPrice[start], currentPrice, futurePrice, units, matrix);
        max = Math.max(max, res + i * futurePrice[start]);
    }
    // when we came to [start] indexed share, we only had amount left. And maximum we could do is: max.
    matrix[start][amount] = max;
    return max;
}
