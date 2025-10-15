/*
Calculate the frequency of each word in a given text.
The first line of input contains an integer n (1 ≤ n ≤ 10³) — the number of words in the text.
The next line contains n words separated by spaces, each consisting of lowercase English letters
and no more than 30 letters. Output each word and its frequency on a separate line, sorted by frequency.
Words with the same frequency should be sorted in lexicographic order.
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.abs;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        Map<String, Integer> outputMap = new HashMap<>(n);
        for (String s : inputArray) {
            if (outputMap.get(s) != null) {
                int value = outputMap.get(s) + 1;
                outputMap.put(s, value);
            } else {
                outputMap.put(s, 1);
            }
        }
        List<KeyValuePair<String, Integer>> entrySet = outputMap.entrySet();
        bubbleSort(entrySet);
        for (KeyValuePair<String, Integer> pair : entrySet) {
            System.out.println(pair.key + " " + pair.value);
        }
    }

    public static void bubbleSort(List<KeyValuePair<String, Integer>> arr) {
        boolean flag;
        do {
            flag = false;
            for (int i =  0; i < arr.size() -  1; i++) {
                KeyValuePair<String, Integer> current = arr.get(i);
                KeyValuePair<String, Integer> next = arr.get(i +  1);

                int resultOfTheComparison = next.value - current.value;
                if (resultOfTheComparison >  0 || (resultOfTheComparison ==  0 && current.key.compareTo(next.key) >  0)) {
                    arr.set(i, next);
                    arr.set(i +  1, current);
                    flag = true;
                }
            }
        } while (flag);
    }
}

interface Map<K,V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();
    List<KeyValuePair<K, V>> entrySet();
}

class KeyValuePair<K, V> {
    K key;
    V value;

    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

class HashMap<K, V> implements Map<K, V> {
    List<KeyValuePair<K, V>>[] hashTable;
    int capacity;
    int numberOfElements;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.numberOfElements = 0;
        this.hashTable = new List[capacity];
        for (int i = 0; i < capacity; i++)
            this.hashTable[i] = new LinkedList<>();
    }

    @Override
    public void put(K key, V value) {
        int i = key.hashCode() % this.capacity;
        if(i < 0)
            i *= -1;
        for (KeyValuePair<K,V> kv : this.hashTable[i]) {
            if (kv.key.equals(key)) {
                kv.value = value;
                return;
            }
        }
        this.hashTable[i].add(
                new KeyValuePair<>(key, value)
        );
        ++this.numberOfElements;

    }

    @Override
    public void remove(K key) {
        int i = key.hashCode() % this.capacity;
        for (KeyValuePair<K,V> kv : this.hashTable[i]) {
            if (kv.key.equals(key)) {
                kv.value = null;
                --this.numberOfElements;
                break;
            }
        }
    }

    @Override
    public V get(K key) {
        int i = abs(key.hashCode()) % this.capacity;
        for (KeyValuePair<K,V> kv : this.hashTable[i]) {
            if (kv.key.equals(key))
                return kv.value;
        }
        return null;
    }

    @Override
    public int size() { return this.numberOfElements; }

    @Override
    public boolean isEmpty() { return (this.numberOfElements == 0); }

    @Override
    public List<KeyValuePair<K, V>> entrySet() {
        List<KeyValuePair<K, V>> entries = new ArrayList<>();
        for (List<KeyValuePair<K, V>> element : hashTable) {
            entries.addAll(element);
        }
        return entries;
    }
}
