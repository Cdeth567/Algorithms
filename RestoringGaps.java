/*
Given a dictionary and a corrupted text with all 
spaces and punctuation removed, restore the original text. 
The restored text is valid if it consists of a sequence of 
dictionary words separated by spaces, and removing the spaces 
recreates the corrupted text.
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class RestoringGaps {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] inputString1 = scanner.nextLine().split(" ");
        int n = Integer.parseInt(inputString1[0]);
        int k = Integer.parseInt(inputString1[1]);
        String[] inputString2 = scanner.nextLine().split(" ");
        String inputString3 = scanner.nextLine();
        String str = "";
        HashMap<String, Boolean> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(inputString2[i], false);
        }
        boolean[] array = new boolean[k+1];
        array[0] = true;
        for (int ind = 1; ind <= k; ind++) {
            for (int u = 0; u < n; u++) {
                String word = inputString2[u];
                if (ind >= word.length() && word.equals(inputString3.substring(ind - word.length(), ind)) &&
                        array[ind - word.length()] == true) {
                    array[ind] = true;
                    break;
                }
            }
        }
        if (array[k]) {
            ArrayList<String> result = new ArrayList();
            int i = k;
            while (i > 0) {
                for (int u = 0; u < n; u++) {
                    String word = inputString2[u];
                    if (i >= word.length() && (array[i - word.length()]
                    && inputString3.substring(i - word.length(), i).equals(word))) {
                        result.add(word);
                        i -= word.length();
                        break;
                    }
                }
            }
            Collections.reverse(result);
            for (String element : result) {
                System.out.print(element + " ");
            }
        }
    }
}
