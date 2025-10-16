/*
You are given a road map of several cities represented
as an adjacency matrix. Determine whether every city is
reachable from every other city, possibly through intermediate cities.
The input starts with an integer N (1 ≤ N ≤ 1000) — the number of
cities — followed by N lines each containing N numbers (0 or 1)
representing the adjacency matrix of the graph.
The output should be YES if the graph is fully connected,
meaning every city can reach every other city, and NO otherwise.
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Graph<String> graph = new Graph<>();
        for (int i = 0; i < n; i++) {
            graph.addVertex(String.valueOf(i+1));
        }

        for (int i = 0; i < n; i++) {
            String[] inputLine = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                String s = inputLine[j];
                if (s.equals("1") && i != j) {
                    graph.addEdge(graph.vertices.get(i), graph.vertices.get(j));
                }
            }
        }
        graph.ValeriiaKolesnikova_dfs(graph.vertices.get(0));
        System.out.println(graph.colors());
    }
}

class Node<T> {
    T value;
    Node<T> next;
    Node<T> previous;

    public Node(T value) {
        this.value = value;
        this.previous = null;
        this.next = null;
    }
}

class DoublyLikedList<T> {
    Node<T> head;
    Node<T> tail;
    int size;

    public DoublyLikedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public Node<T> head() {
        return head;
    }

    public void add(T item) {
        if (head == null) {
            head = new Node<>(item);
            tail = head;
        } else {
            tail.next = new Node<>(item);
            tail = tail.next;
        }
        size++;
    }

    T get(int y) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (i == y) {
                return temp.value;
            }
            temp = temp.next;
        }
        return temp.value;
    }

    public void remove(Node<T> n) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (temp.previous == n.previous) {
                if (temp.next != null)
                    temp.value = temp.next.value;
                if (temp.previous != null && temp.next != null) {
                    temp.previous.next = temp.next;
                    temp.next.previous = temp.previous;
                }
                return;
            }
            temp = temp.next;
        }
    }
}

class Graph<V> {
    class Vertex {
        V value;
        DoublyLikedList<Edge> adjacent;
        String color;

        public Vertex(V value) {
            this.value = value;
            this.adjacent = new DoublyLikedList<Edge>();
            this.color = "white";
        }

        public DoublyLikedList<Edge> getAdjacent() {
            return adjacent;
        }
    }

    class Edge {
        Vertex from;
        Vertex to;

        public Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }
    }

    DoublyLikedList<Vertex> vertices;
    DoublyLikedList<Edge> edges;

    public Graph() {
        this.vertices = new DoublyLikedList<Vertex>();
        this.edges = new DoublyLikedList<Edge>();
    }

    void addVertex(V value) {
        Vertex v = new Vertex(value);
        this.vertices.add(v);
    }

    void addEdge(Vertex from, Vertex to) {
        Edge edge = new Edge(from, to);
        this.edges.add(edge);
        from.adjacent.add(edge);
        to.adjacent.add(edge);
    }

    boolean adjacent(Vertex u, Vertex v) {
        Node<Edge> temp = u.adjacent.head();
        for (int i = 0; i < u.getAdjacent().size(); i++) {
            if (temp.next != null) {
                if (temp.value.from.equals(v) || temp.value.to.equals(v)) {
                    return true;
                }
            }
            temp = temp.next;
        }
        return false;
    }
    

    void ValeriiaKolesnikova_dfs(Vertex v) {
        v.color = "grey";
        Node<Edge> temp = v.adjacent.head();
        while (temp != null) {
            Vertex neighbour = (temp.value).to;
            if (!neighbour.color.equals("grey")) {
                ValeriiaKolesnikova_dfs(neighbour);
            }
            temp = temp.next;
        }
    }

    String colors() {
        Node<Vertex> temp = vertices.head;
        for (int i = 0; i < vertices.size(); i++) {
            assert temp != null;
            if (temp.next != null) {
                if (!temp.value.color.equals("grey")) {
                    return "NO";
                }
            }
            temp = temp.next;
        }
        return "YES";
    }
}
