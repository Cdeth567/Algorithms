/*
Given two texts, find all unique words from the second text that
do not appear in the first text. The first line contains
an integer n (1 ≤ n ≤ 10⁵) — the number of words in the first text,
followed by a line with n lowercase English words.
The third line contains an integer m (1 ≤ m ≤ 10⁵) — the number
of words in the second text, followed by a line with m lowercase
English words. Each word has up to 30 letters. The output should
start with an integer k — the number of unique words from the second
text not found in the first — followed by k lines listing these words
in the order they appear in the second text.
*/

import java.util.*;
import static java.lang.Math.abs;


public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> firstMap = new HashMap<>();
        HashMap<String, Integer> secondMap = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        int n1 = scan.nextInt();
        scan.nextLine();
        String input1 = scan.nextLine();
        int n2 = scan.nextInt();
        scan.nextLine();
        String input2 = scan.nextLine();
        String[] inputArray1 = input1.split(" ");
        String[] inputArray2 = input2.split(" ");
        for (int i = 0; i < n1; i++) {
            if (firstMap.containsKey(inputArray1[i])) {
                firstMap.put(inputArray1[i], firstMap.get(inputArray1[i]) + 1);
            } else {
                firstMap.put(inputArray1[i], 1);
            }
        }

        ArrayList<String> arr = new ArrayList<>();
        for (String s : inputArray2) {
            if (!firstMap.containsKey(s)) {
                arr.add(s);
            }
        }
        Set<String> set = new LinkedHashSet<>(arr);
        System.out.println(set.size());
        for (String i : set) {
            System.out.println(i);
        }
    }
}

interface Map<K,V> {
    V getValue(K key);
    K getKey(V value);
    boolean containsKey(K key);
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();
    List<KeyValuePair<K, V>> entrySet();
}

interface Entry<K, V> {
    K getKey();
    V getValue();
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
    int capacity = 1001;
    int numberOfElements;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.numberOfElements = 0;
        this.hashTable = new List[capacity];
        for (int i = 0; i < capacity; i++)
            this.hashTable[i] = new LinkedList<>();
    }

    public HashMap() {
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
    @Override
    public V getValue(K key) {
        int i = abs(key.hashCode()) % this.capacity;
        for (KeyValuePair<K, V> kv : this.hashTable[i]) {
            if (kv.key.equals(key))
                return kv.value;
        }
        return null;
    }

    @Override
    public K getKey(V value) {
        for (List<KeyValuePair<K, V>> bucket : hashTable) {
            for (KeyValuePair<K, V> kv : bucket) {
                if (kv.value.equals(value))
                    return kv.key;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int i = abs(key.hashCode()) % this.capacity;
        for (KeyValuePair<K, V> kv : this.hashTable[i]) {
            if (kv.key.equals(key))
                return true;
        }
        return false;
    }
}
