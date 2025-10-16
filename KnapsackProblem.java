/*
You are given n items, each with a weight
wi and a cost ci, and a knapsack that can hold a total weight of at most W.
Find the subset of items that maximizes the total cost without exceeding
the weight limit. Use an efficient algorithm to solve the problem.
The first line of input contains two integers: n (≤ 1000) and W (≤ 10 000).
The second line contains n natural numbers wi (≤ 100), representing item weights.
The third line contains n integers ci (≤ 100), representing item costs.
The output should include two lines: the first line — the number of items selected,
and the second line — the indices (from 1 to n) of the chosen items that yield the maximum total cost.
*/

import java.util.*;
public class KnapsackProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int w = scanner.nextInt();
        int[] weights = new int[n];
        int[] costs = new int[n];
        for (int i = 0; i < n; i++)
            weights[i] = scanner.nextInt();
        for (int i = 0; i < n; i++)
            costs[i] = scanner.nextInt();
        int[][] dp = new int[n + 1][w + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                if (weights[i - 1] > j)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + costs[i - 1]);
            }
        }
        int j = w;
        List<Integer> items = new ArrayList<>();
        for (int i = n; i > 0 && j > 0; i--)
            if (dp[i][j] != dp[i - 1][j]) {
                items.add(i);
                j -= weights[i - 1];
            }
        System.out.println(items.size());
        Collections.reverse(items);
        for (int item : items) 
            System.out.print(item + " ");
    }
}
