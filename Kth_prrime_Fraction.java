You are given a sorted integer array arr containing 1 and prime numbers, where all the integers of arr are unique. You are also given an integer k.

For every i and j where 0 <= i < j < arr.length, we consider the fraction arr[i] / arr[j].

Return the kth smallest fraction considered. Return your answer as an array of integers of size 2, where answer[0] == arr[i] and answer[1] == arr[j].

 
Example 1:

Input: arr = [1,2,3,5], k = 3
Output: [2,5]
Explanation: The fractions to be considered in sorted order are:
1/5, 1/3, 2/5, 1/2, 3/5, and 2/3.
The third fraction is 2/5.
Example 2:

Input: arr = [1,7], k = 1
Output: [1,7]
 

Constraints:

2 <= arr.length <= 1000
1 <= arr[i] <= 3 * 104
arr[0] == 1
arr[i] is a prime number for i > 0.
All the numbers of arr are unique and sorted in strictly increasing order.
1 <= k <= arr.length * (arr.length - 1) / 2


 这道题给了我们一个有序数组，里面是1和一些质数，说是对于任意两个数，都可以组成一个 [0, 1] 
 之间分数，让求第K小的分数是什么，题目中给的例子也很好的说明了题意。那么最直接暴力
 的解法就是遍历出所有的分数，然后再进行排序，返回第K小的即可。
 但是这种无脑暴力搜索的方法 OJ 是不答应的，无奈，只能想其他的解法。
 由于数组是有序的，所以最小的分数肯定是由第一个数字和最后一个数字组成的
 ，而接下来第二小的分数就不确定是由第二个数字和最后一个数字组成的，
 还是由第一个数字跟倒数第二个数字组成的。这里用一个最小堆来存分数，
 那么每次取的时候就可以将最小的分数取出来，由于前面说了，
 不能遍历所有的分数都存入最小堆，那么该怎么办呢，可以先存n个，
 哪n个呢？其实就是数组中的每个数字都和最后一个数字组成的分数。
 由于需要取出第K小的分数，那么在最小堆中取K个分数就可以了，
 第一个取出的分数就是那个由第一个数字和最后一个数字组成的最小的分数，
 然后就是精髓所在了，此时将分母所在的位置前移一位，
 还是和当前的分子组成新的分数，这里即为第一个数字和倒数第二个数字组成的分数，
 存入最小堆中，那么由于之前已经将第二个数字和倒数第一个数字组成的分数
 存入了最小堆，所以不用担心第二小的分数不在堆中，这样每取出一个分数，
 都新加一个稍稍比取出的大一点的分数，这样取出了第K个分数即为所求，参见代码如下：
 
 
 
 
 
 
 
 
     public int[] kthSmallestPrimeFraction(int[] A, int k) {
          PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) ->
                A[a[0]] * A[b[1]] - A[a[1]] * A[b[0]]);

        for (int i = 1; i < A.length; ++i)
            pq.add(new int[]{0, i});

        while (--k > 0) {
            int[] frac = pq.poll();
            if (frac[0]++ < frac[1])
                pq.offer(frac);
        }

        int[] ans = pq.poll();
        return new int[]{A[ans[0]], A[ans[1]]};
    }
}
 
 
 
 
 
 
 
 
 
 
This solution probably doesn't have the best runtime but it's really simple and easy to understand.

Says if the list is [1, 7, 23, 29, 47], we can easily have this table of relationships

1/47  < 1/29    < 1/23 < 1/7
7/47  < 7/29    < 7/23
23/47 < 23/29
29/47
So now the problem becomes "find the kth smallest element of (n-1) sorted list"

Following is my implementation using PriorityQueue, running time is O(nlogn) O(max(n,k) * logn), space is O(n):

    public int[] kthSmallestPrimeFraction(int[] a, int k) {
        int n = a.length;
        // 0: numerator idx, 1: denominator idx
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int s1 = a[o1[0]] * a[o2[1]];
                int s2 = a[o2[0]] * a[o1[1]];
                return s1 - s2;
            }
        });
        for (int i = 0; i < n-1; i++) {
            pq.add(new int[]{i, n-1});
        }
        for (int i = 0; i < k-1; i++) {
            int[] pop = pq.remove();
            int ni = pop[0];
            int di = pop[1];
            if (pop[1] - 1 > pop[0]) {
                pop[1]--;
                pq.add(pop);
            }
        }

        int[] peek = pq.peek();
        return new int[]{a[peek[0]], a[peek[1]]};
    }
