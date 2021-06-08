Given a stream of incoming "buy" and "sell" orders (as lists of limit price, quantity, and side, like ["155", "3", "buy"]), determine the total quantity (or number of "shares") executed.

A "buy" order can be executed if there is a corresponding "sell" order with a price that is less than or equal to the price of the "buy" order.
Similarly, a "sell" order can be executed if there is a corresponding "buy" order with a price that is greater than or equal to the price of the "sell" order.
It is possible that an order does not execute immediately if it isn't paired to a counterparty. In that case, you should keep track of that order and execute it at a later time when a pairing order is found.
You should ensure that orders are filled immediately at the best possible price. That is, an order should be executed when it is processed, if possible. Further, "buy" orders should execute at the lowest possible price and "sell" orders at the highest possible price at the time the order is handled.

Note that orders can be partially executed.

--- Sample Input ---

orders = [
  ['150', '5', 'buy'],    # Order A
  ['190', '1', 'sell'],   # Order B
  ['200', '1', 'sell'],   # Order C
  ['100', '9', 'buy'],    # Order D
  ['140', '8', 'sell'],   # Order E
  ['210', '4', 'buy'],    # Order F
]

Sample Output
9
————————————————
版权声明：本文为CSDN博主「flyatcmu」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u013325815/article/details/106206059

*
   price  share buy/sell
  ['150', '5', 'buy'],    # Order A
  ['190', '1', 'sell'],   # Order B
  ['200', '1', 'sell'],   # Order C
  ['100', '9', 'buy'],    # Order D
  ['140', '8', 'sell'],   # Order E 5
  ['210', '4', 'buy'],    # Order F 3 + 1
  output: 9;
*/
 
//100, 140, 150, 
//rule1: buy 
private class Node {
    public int price;
    public int share;
    public Node(int price, int share) {
        this.price = price;
        this.share = share;
    }
}
 
private class BuyNodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node a, Node b) {
        return b.price - a.price;
    }
}
 
private class SellNodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node a, Node b) {
        return a.price - b.price;
    }
}
 
int order_book(String[][] orders) {
    if(orders == null || orders.length == 0 || orders[0].length == 0) {
        return 0;
    }
    int res = 0;
    PriorityQueue<Node> buypq = new PriorityQueue<Node>(new BuyNodeComparator());
    PriorityQueue<Node> sellpq = new PriorityQueue<Node>(new SellNodeComparator());
 
    for(int i = 0; i < orders.length; i++) {
        int price = Integer.parseInt(orders[i][0]);
        int share = Integer.parseInt(orders[i][1]);
 
        if(orders[i][2].equals("buy")) {
            Node buynode = new Node(price, share);
            while(!sellpq.isEmpty() && sellpq.peek().price <= buynode.price && buynode.share > 0) {
                Node sellnode = sellpq.poll();
                if(sellnode.share > buynode.share) {
                    res += buynode.share;
                    sellnode.share = sellnode.share - buynode.share;
                    sellpq.offer(sellnode);
                    buynode.share = 0;
                } else {
                    // sellnode.share <= buynode.share;
                    res += sellnode.share;
                    buynode.share -= sellnode.share;
                }
            }
            if(buynode.share > 0) {
                buypq.offer(buynode);
            }
        } else {
            // “SELL” buy price >= sell price;
            Node sellnode = new Node(price, share);
            while(!buypq.isEmpty() && buypq.peek().price >= sellnode.price && sellnode.share > 0) {
                Node buynode = buypq.poll();
                if(buynode.share > sellnode.share) {
                    res += sellnode.share;
                    buynode.share = buynode.share - sellnode.share;
                    buypq.offer(buynode);
                    sellnode.share = 0;
                } else {
                    // buynode.share >= sellnode.share;
                    res += buynode.share;
                    sellnode.share -= buynode.share;
                }
            }
            if(sellnode.share > 0) {
                sellpq.offer(sellnode);
            }
        }
    }
    return res;
}

