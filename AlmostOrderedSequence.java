/*
Brief description of the problem:
Given a nearly sorted sequence of integers, efficiently sort it.
Implement a separate sorting function that either returns a new sorted array or sorts the input array in place.
*/
import java.util.Scanner;

public class AlmostOrderedSequence {
    public static void bubbleSort(int[] arr, int n) {
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int k = 1; k <= n-1; k++) {
                if (arr[k-1] > arr[k]) {
                    int temp = arr[k];
                    arr[k] = arr[k-1];
                    arr[k-1] = temp;
                    flag = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String input = scanner.nextLine();
        String[] numbers = input.split(" ");
        int[] arr = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            arr[i] = Integer.parseInt(numbers[i]);
        }

        bubbleSort(arr, n);

        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
