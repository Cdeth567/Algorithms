/*
You need to implement a phonebook system that
supports adding, deleting, and searching contacts 
using a custom HashTable implementation. The input starts with 
an integer N (0 < N ≤ 10⁴) — the number of commands. Each of the 
following N lines contains one command. The `ADD <Contact name>,<phone>` 
command adds a phone number to a contact (creating the contact if 
it doesn’t exist). The `DELETE <Contact name>` command removes the 
contact entirely, while `DELETE <Contact name>,<phone>` removes a 
specific phone number from that contact (doing nothing if it doesn’t exist). 
The `FIND <Contact name>` command searches for a contact. If no contact or 
phone numbers are found, output `No contact info found for <Contact name>`. 
Otherwise, output all phone numbers in the format: `Found <K> phone numbers 
for <Contact name>: <phone> <phone> ... <phone>`.
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        HashMap<String, String> hashMap = new HashMap<>(n);
        String name;
        String phoneNumber;
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String s = scanner.nextLine();
            switch (s.substring(0, s.indexOf(" "))) {
                case "ADD":
                    name = s.substring(s.indexOf(" ")+1, s.indexOf(","));
                    phoneNumber = s.substring(s.indexOf(",")+1);
                    hashMap.put(name, phoneNumber);
                    break;
                case "DELETE":
                    if (s.contains(",")) {
                        name = s.substring(s.indexOf(" ")+1, s.indexOf(","));
                        phoneNumber = s.substring(s.indexOf(",")+1);
                        hashMap.delete(new KeyValuePair<String, String>(name, phoneNumber));
                    } else {
                        name = s.substring(s.indexOf(" ")+1);
                        hashMap.remove(name);
                    }
                    break;
                case "FIND":
                    hashMap.find(s.substring(s.indexOf(" ")+1));
                    break;
            }
        }
    }
}

// Interface of Map ADT
interface Map<K,V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();
    void delete(KeyValuePair<K, V> pair);
    boolean isFound(K key, V value);
    void find(K key);
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
    List<Integer> codes;
    int capacity; // size of hashTable (# of slots)
    int numberOfElements; // number of key-value pairs

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.numberOfElements = 0;
        this.hashTable = new List[capacity];
        this.codes = new ArrayList<>();
        for (int i = 0; i < capacity; i++)
            this.hashTable[i] = new LinkedList<>();
    }
    
    boolean isUnique(int i) {
        for (int el : codes) if (i == el) return false;
        return true;
    }
    @Override
    public void put(K key, V value) {
        int i = key.hashCode() % this.capacity;
        if(i < 0)
            i *= -1;
        while (!isUnique(i)) i++;
        if (!isFound(key, value)) {
            for (KeyValuePair<K, V> kv : this.hashTable[i]) {
                if (kv.key.equals(key) && kv.value == null) {
                    kv.value = value;
                    return;
                }
            }

            this.hashTable[i].add(
                    new KeyValuePair<>(key, value)
            );
            ++this.numberOfElements;
        }

    }

    @Override
    public void delete(KeyValuePair<K, V> pair) {
        int i = pair.key.hashCode() % this.capacity;
        if (i < 0) i *= -1;
        for (KeyValuePair<K, V> kv : this.hashTable[i]) {
            if (kv.value != null) {
                if (kv.key.equals(pair.key) && kv.value.equals(pair.value)) {
                    kv.value = null;
                    --this.numberOfElements;
                    break;
                }
            }
        }
    }

    @Override
    public boolean isFound(K key, V value) {
        int i = key.hashCode() % this.capacity;
        if (i < 0) i *= -1;
        for (KeyValuePair<K, V> kv : this.hashTable[i]) {
            if (kv.value != null) {
                if (kv.key.equals(key) && kv.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void find(K key) {
        int count = 0;
        List<V> values = new ArrayList<>();
        int i = key.hashCode() % this.capacity;
        if (i < 0) i *= -1;
        for (KeyValuePair<K, V> kv : this.hashTable[i]) {
            if (kv.key.equals(key) && kv.value != null) {
                count += 1;
                values.add(kv.value);
            }
        }
        if (count == 0) {
            System.out.println("No contact info found for " + key);
        } else {
            System.out.print("Found " + count + " phone numbers for " + key + ": ");
            for (V value : values) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void remove(K key) {
        int i = key.hashCode() % this.capacity;
        if (i < 0) i *= -1;
        this.numberOfElements -= this.hashTable[i].size();
        if (this.hashTable[i] != null) hashTable[i].removeIf(kvKeyValuePair -> kvKeyValuePair.key.equals(key));
    }

    @Override
    public V get(K key) {
        int i = key.hashCode() % this.capacity;
        for (KeyValuePair<K,V> kv : this.hashTable[i])
        {
            if (kv.key.equals(key))
                return kv.value;
        }
        return null;
    }

    @Override
    public int size() { return this.numberOfElements; }

    @Override
    public boolean isEmpty() { return (this.numberOfElements == 0); }
}


