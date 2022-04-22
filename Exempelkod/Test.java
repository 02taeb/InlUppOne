public class Test {

    public static void main(String[] args) {
        City stockholm = new City("Stockholm");
        City malmö = new City("Malmö");
        City oslo = new City("Oslo");
        City kiruna = new City("Kiruna");
        City köpenhamn = new City("Köpenhamn");
        City berlin = new City("Berlin");

        ListGraph graph = new ListGraph();
        graph.add(stockholm);
        graph.add(malmö);
        graph.add(oslo);
        graph.add(kiruna);
        graph.add(köpenhamn);
        graph.add(berlin);


        graph.connect(köpenhamn, berlin, "Autobahn", 1000);
        graph.connect(stockholm, malmö, "E20", 660);
        graph.connect(stockholm, oslo, "E18", 400);
        graph.connect(oslo, malmö, "E5", 777);
        graph.connect(stockholm, kiruna, "E4", 1700);
        graph.connect(malmö, köpenhamn, "Broen", 7);

        //System.out.println(graph);

        System.out.println(graph.getShortestPath(malmö, kiruna));
        //System.out.println(graph.pathExists(stockholm, berlin));
        //System.out.println(graph.pathExists(malmö, kiruna));
    }
}
