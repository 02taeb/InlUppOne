import java.util.Objects;

public class Edge {

    private final City destination;
    private final String name;
    private final double weight;

    public Edge(City destination, String name, double weight) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public City getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public boolean equals(Object other) {
        if (other instanceof Edge edge) {
            return Objects.equals(name, edge.name) &&
                    Objects.equals(destination, edge.destination);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(name, destination);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "destination=" + destination +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
