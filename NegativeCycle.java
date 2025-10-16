/*
Given a directed weighted graph, determine whether it 
contains a cycle with a negative total weight and print the cycle 
if it exists. The input starts with an integer N (1 ≤ N ≤ 100) — 
the number of vertices — followed by an N×N adjacency matrix. 
Each value represents the edge weight, with absolute values 
strictly less than 10000; if there is no edge, the value is 
exactly 100000. The output should be YES if a negative cycle 
exists and NO otherwise. If such a cycle exists, the second line
should contain the number of vertices in the cycle, and the third 
line should list the vertex indices in cycle order.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Graph<Integer> graph = new Graph<>();
        int i;
        for(i = 0; i < n; ++i) {
            graph.addVertex(i);
        }
        for(i = 0; i < n; i++) {
            String[] inputLine = scanner.nextLine().split(" ");
            for(int j = 0; j < n; j++) {
                String s = inputLine[j];
                if (!s.equals("100000")) {
                    graph.addEdge(graph.vertices.get(i), graph.vertices.get(j), Integer.parseInt(s));
                }
            }
        }
        graph.ValeriiaKolesnikova_sp(graph.vertices.get(0));
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
        if (temp.value == null) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (i == y) {
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
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
    class Vertex implements Comparable<Vertex> {
        V value;
        DoublyLikedList<Edge> adjacent;
        String color;
        int distance;
        Vertex predecessor;

        public Vertex(V value) {
            this.value = value;
            this.adjacent = new DoublyLikedList<>();
            this.color = "white";
            this.distance = 10000;
            this.predecessor = null;
        }

        public DoublyLikedList<Edge> getAdjacent() {
            return adjacent;
        }

        @Override
        public int compareTo(Vertex another) {
            return Integer.compare(this.distance, another.distance);
        }
    }

    class Edge {
        Vertex from;
        Vertex to;
        int weight;

        public Edge(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
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

    void addEdge(Vertex from, Vertex to, int weight) {
        Edge edge = new Edge(from, to, weight);
        this.edges.add(edge);
        from.adjacent.add(edge);
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

    public void showPath(Vertex v) {
        ArrayList<Vertex> list = new ArrayList<>();
        int count = 0;
        while (v != null) {
            if (list.contains(v)) {
                v = null;
            } else {
                list.add(v);
                count += 1;
                v = v.predecessor;
            }
        }

        System.out.println("YES");
        System.out.println(count);
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.print(((Integer) list.get(i).value+1) + " ");
        }
    }
    public void goBack(Vertex v) {
        for (int i = 0; i < vertices.size-1; i++) {
            if (v.predecessor != null) {
                v = v.predecessor;
            } else {
                break;
            }
        }
        showPath(v);
    }
    void ValeriiaKolesnikova_sp(Vertex source) {
        source.distance = 0;
        for (int i = 0; i < vertices.size-1; i++) {
            Node<Vertex> temp1 = vertices.head;
            while (temp1 != null) {
                Node<Edge> s = temp1.value.adjacent.head;
                while (s != null) {
                    if (s.value.to.distance > temp1.value.distance + s.value.weight) {
                        s.value.to.predecessor = temp1.value;
                        s.value.to.distance = temp1.value.distance + s.value.weight;
                    }
                    s = s.next;
                }
                temp1 = temp1.next;
            }
        }
        boolean flag = false;
        for (int i = 0; i < 1; i++) {
            Node<Vertex> temp2 = vertices.head;
            while (temp2 != null) {
                Node<Edge> s = temp2.value.adjacent.head;
                while (s != null) {
                    if (s.value.to.distance > (temp2.value.distance + s.value.weight)) {
                        flag = true;
                        goBack(s.value.to);
                        s.next = null;
                        temp2.next = null;
                        break;
                    }
                    s = s.next;
                }
                temp2 = temp2.next;
            }
        }
        if (!flag) {
            System.out.println("NO");
        }
    }
}
