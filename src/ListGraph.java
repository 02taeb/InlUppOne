import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ListGraph<T> implements Graph<T>, Serializable {

    @Override
    public void add(T node) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Set<T> getNodes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void disconnect(T node1, T node2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(T node) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean pathExists(T from, T to) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        // TODO Auto-generated method stub
        return null;
    }
    
}