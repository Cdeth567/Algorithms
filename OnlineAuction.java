/*
In the online auction system youPay, each item is listed
with a current (or minimum) bid and a maximum price.
Items must be sorted so that those with a higher current
bid appear first; if two items have the same current bid,
they are sorted by maximum price in ascending order; if both
values are equal, their original input order is preserved.
The input consists of an integer n (1 ≤ n ≤ 100000) —
the number of items — followed by n lines, each containing
two integers L (0 ≤ L ≤ 100) representing the current bid
and H (0 ≤ H ≤ 100000) representing the maximum price.
The output should be a sequence of n integers —
the indices of the items in the sorted order.
*/

import java.util.Scanner;

public class OnlineAuction {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Integer[][] data = new Integer[n][3];
        for (int i = 0; i < n; i++) {
            String[] input = scanner.nextLine().split(" ");
            data[i][0] = i + 1;
            data[i][1] = Integer.parseInt(input[0]);
            data[i][2] = Integer.parseInt(input[1]);
        }
        ValeriiaKolesnikova_radix_srt(data, n);
    }

    public static <T> void ValeriiaKolesnikova_radix_srt(T[][] arr, int n) {
        int mx1 = 0;
        int mx2 = 0;
        for (int i = 0; i < n; i++) {
            if ((Integer) arr[i][2] > mx2) {
                mx2 = (Integer) arr[i][2];
            }
            if ((Integer) arr[i][1] > mx1) {
                mx1 = (Integer) arr[i][1];
            }
        }
        for (int exp = 1; mx2/exp > 0; exp *= 10) {
            arr = ValeriiaKolesnikova_count_srt(arr, n, exp, true, 2);
        }
        for (int exp = 1; mx1/exp > 0; exp *= 10) {
            arr = ValeriiaKolesnikova_count_srt(arr, n, exp, false, 1);
        }
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i][0] + " ");
        }
    }

    public static <T> T[][] ValeriiaKolesnikova_count_srt(T[][] arr, int n, int exp, boolean flag, int k) {
        Integer[] index = new Integer[10];
        Integer[][] dataCopyForDigits = new Integer[n][3];
        for (int i = 0; i < n; i++) {
            dataCopyForDigits[i][k] = (Integer) arr[i][k] / exp % 10;
        }
        for (int id = 0; id < 10; id++) {
            index[id] = 0;
        }
        for (int i = 0; i < n; i++) {
            index[dataCopyForDigits[i][k]] += 1;
        }
        if (flag) {
            for (int i = 1; i < 10; i++) {
                index[i] += index[i - 1];
            }
        } else {
            for (int i = 8; i >= 0; i--) {
                index[i] += index[i + 1];
            }
        }
        Integer[][] output = new Integer[n][3];
        for (int i = n - 1; i >= 0; i--) {
            Integer number = dataCopyForDigits[i][k];
            int d = index[number];
            output[d-1][0] = (Integer) arr[i][0];
            output[d-1][1] = (Integer) arr[i][1];
            output[d-1][2] = (Integer) arr[i][2];
            index[number] -= 1;
        }
        return (T[][]) output;
    }
}
