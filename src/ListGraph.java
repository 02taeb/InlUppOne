import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ListGraph<T> implements Graph<T>, Serializable {
    private Map<T, Set<Edge<T>>> nodes = new HashMap<>();

    @Override
    public void add(T node) {
        nodes.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException("Error: One or both destinations missing.");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Error: Weight cannot be negative.");
        }

        Set<Edge<T>> edgesFromOne = nodes.get(node1);
        Set<Edge<T>> edgesFromTwo = nodes.get(node2);

        for (Edge<T> edge : edgesFromOne) {
            if (edge.getDestination() == node2) {
                throw new IllegalStateException("Error: Connection between " + node1.toString() + " and " + node2.toString() + " already exists.");
            }
        }
        for (Edge<T> edge : edgesFromTwo) {
            if (edge.getDestination() == node1) {
                throw new IllegalStateException("Error: Connection between " + node1.toString() + " and " + node2.toString() + " already exists.");
            }
        }

        edgesFromOne.add(new Edge<T>(node2, name, weight));
        edgesFromTwo.add(new Edge<T>(node1, name, weight));
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        Edge<T> edge1 = getEdgeBetween(node1, node2);
        Edge<T> edge2 = getEdgeBetween(node2, node1);
        if(edge1 != null && edge2 != null){
            if(weight > 0){
                edge1.setWeight(weight);
                edge2.setWeight(weight);
            }else{
                throw new IllegalArgumentException("Error: Cannot add a negative weight.");
            }
        }else{
            throw new NoSuchElementException("Error: there is no edge between these nodes.");
        }
        
    }

    @Override
    public Set<T> getNodes() {
        return Collections.unmodifiableSet(nodes.keySet());
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException(node.toString() + " does not exist.");
        }
        return Collections.unmodifiableCollection(nodes.get(node));
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)){
            throw new NoSuchElementException("Error: One or both destinations missing.");
        }
        for (Edge<T> edge : nodes.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }

        return null;
    }

    @Override
    public void disconnect(T node1, T node2) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)){
            throw new NoSuchElementException("Error: One or both destinations missing.");
        }
        
        Edge<T> edge1 = getEdgeBetween(node1, node2);
        Edge<T> edge2 = getEdgeBetween(node2, node1);
        if(edge1 == null || edge2 == null){
            throw new IllegalStateException("Error: There is no edge between the nodes.");
        }
        Set<Edge<T>> edgesFromOne = nodes.get(node1);
        Set<Edge<T>> edgesFromTwo = nodes.get(node2);
        
        edgesFromOne.remove(edge1);
        edgesFromTwo.remove(edge2);
    }

    @Override
    public void remove(T node) {
        if(!nodes.containsKey(node)){
            throw new NoSuchElementException("Error: The node does not exist in the graph.");
        }
        ArrayList<T> destinations = new ArrayList<T>();
        for (Edge<T> edge : getEdgesFrom(node)) {
            destinations.add(edge.getDestination());
        }
        while (destinations.size() > 0) {
            disconnect(node, destinations.get(0));
            destinations.remove(0);
        }
        nodes.remove(node);
    }

    @Override
    public boolean pathExists(T from, T to) {
        if(!nodes.containsKey(from) || !nodes.containsKey(to)){
            return false;
        }
        Set<T> visited = new HashSet<>();
        depthFirstVisitAll(from, visited);
        return visited.contains(to);
    }

    private void depthFirstVisitAll(T current, Set<T> visited) {
        visited.add(current);
        for (Edge<T> edge : nodes.get(current)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll(edge.getDestination(), visited);
            }
        }
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        Map<T, T> connection = new HashMap<>();
        depthFirstConnection(from, null, connection);
        if (!connection.containsKey(to)) {
            return null;
        }
        return gatherPath(from, to, connection);
    }

    private List<Edge<T>> gatherPath(T from, T to, Map<T, T> connection) {
        LinkedList<Edge<T>> path = new LinkedList<>();
        T current = to;
        while (!current.equals(from)) {
            T next = connection.get(current);
            Edge<T> edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }

    private void depthFirstConnection(T to, T from, Map<T, T> connection) {
        connection.put(to, from);
        for (Edge<T> edge : nodes.get(to)) {
            if (!connection.containsKey(edge.getDestination())) {
                depthFirstConnection(edge.getDestination(), to, connection);
            }
        }

    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T node : nodes.keySet()) {
            sb.append(node.toString());
            for (Edge<T> edge : nodes.get(node)) {
                sb.append(" " + edge.toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}