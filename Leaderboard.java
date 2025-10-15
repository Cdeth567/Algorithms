/*
Brief description of the problem:
Given an unsorted list of players with their scores, build a leaderboard showing the top K players.
Implement a separate sorting function that takes the list of player records and the maximum leaderboard size K, then returns a new array containing the top K players sorted by descending score.
*/
import java.util.Scanner;

public class Leaderboard {
    public static void selectionSort(int[] scores, String[] players, int length, int count) {
        boolean flag = true;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (scores[j] > scores[i]) {
                    int tempScore = scores[j];
                    String tempPlayer = players[j];
                    scores[j] = scores[i];
                    scores[i] = tempScore;
                    players[j] = players[i];
                    players[i] = tempPlayer;
                }
            }
            if (i <= count - 1) {
                System.out.println(players[i] + ' ' + scores[i]);
            } else {
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println(players[length-1] + ' ' + scores[length-1]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        String[] players = new String[n];
        int[] scores = new int[n];
        scanner.nextLine();
        if (k > n) {
            k = n;
        }
        for (int i = 0; i < n; i++) {
            String input = scanner.nextLine();
            String[] lines = input.split(" ");
            players[i] = lines[0];
            scores[i] = Integer.parseInt(lines[1]);
        }
        selectionSort(scores, players, n, k);
    }
}
