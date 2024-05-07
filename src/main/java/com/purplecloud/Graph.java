package com.purplecloud;

import java.util.*;

public class Graph {
    private final Map<Integer, List<Edge>> adjacencyList;
    private final Map<Integer, Node> nodes;
    private int[] nodeName;
    private List<Integer> sequence;
    private double fitness=0.0;

    public Graph(Map<Integer, List<Edge>> adjacencyList,Map<Integer, Node> nodes,int[] node) {
        this.adjacencyList = adjacencyList;
        this.nodes = nodes;
        this.nodeName = node;
    }
    public int[] getNodeName(){
        return nodeName;
    }


    public List<Integer> greedyPath(int start, int unloadPoint, int maxLoad, Robot robot) {

        Set<Integer> visited = new HashSet<>();
        List<Integer> path = new ArrayList<>();
        int current = start;

        while (visited.size() < adjacencyList.size()) {
            Node currentNode = nodes.get(current);
            if (couldService(currentNode,robot,maxLoad)){
                robot.setBooksToDispatch(robot.getBooksToDispatch()-currentNode.getBooksToDropOff());
                robot.setBooksToRecycle(robot.getBooksToRecycle()+currentNode.getBooksToPickUp());
                path.add(current);
                visited.add(current);
            }else {
                current = unloadPoint;
                Node unloadNode = nodes.get(unloadPoint);
                robot.setBooksToRecycle(0); // Drop off books
                robot.setBooksToDispatch(unloadNode.getBooksToPickUp());
            }
            List<Edge> edges = adjacencyList.get(current);
            edges.sort(Comparator.comparingDouble(Edge::getWeight));

            for (Edge edge : edges) {
                if (!visited.contains(edge.getTo())) {
                    current = edge.getTo();
                    break;
                }
            }
        }
        this.sequence = path;
        return path;
    }
    public boolean couldService(Node currentNode,Robot robot,int maxLoad){
        return currentNode.getBooksToDropOff() <= robot.getBooksToDispatch()
                && (robot.getTotalLoad()-currentNode.getBooksToDropOff() + currentNode.getBooksToPickUp() <= maxLoad);

    }
    public double getFitness(){
        return fitness;
    }
    public List<Integer> decode(Chromosome chromosome){
        return null;
    }
}

class Edge {
    private final int to;
    private final double weight;

    public Edge(int to, double weight) {
        this.to = to;
        this.weight = weight;
    }

    public int getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }
}

class Node {
    private final int booksToPickUp;
    private final int booksToDropOff;
    private final int id;
    public int getId() {
        return id;
    }
    public Node(int booksToPickUp, int booksToDropOff, int id) {
        this.booksToPickUp = booksToPickUp;
        this.booksToDropOff = booksToDropOff;
        this.id = id;
    }

    public int getBooksToPickUp() {
        return booksToPickUp;
    }

    public int getBooksToDropOff() {
        return booksToDropOff;
    }
}
class Robot {
    private int booksToRecycle;
    private int booksToDispatch;
    private int speed;

    public Robot(int booksToRecycle, int booksToDispatch,int speed) {
        this.booksToRecycle = booksToRecycle;
        this.booksToDispatch = booksToDispatch;
        this.speed = speed;
    }

    public int getBooksToRecycle() {
        return booksToRecycle;
    }

    public void setBooksToRecycle(int booksToRecycle) {
        this.booksToRecycle = booksToRecycle;
    }

    public int getBooksToDispatch() {
        return booksToDispatch;
    }

    public void setBooksToDispatch(int booksToDispatch) {
        this.booksToDispatch = booksToDispatch;
    }

    public int getTotalLoad() {
        return booksToRecycle + booksToDispatch;
    }
}
class Road{
    private int start;
    private int end;
    private double weight;
    private String name;


    public Road(int start, int end, double weight,String name) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    public String getName(){
        return name;
    }
    public int getStart() {
        return start;
    }
    public int getNode1(){
        return start;
    }
    public int getNode2(){
        return end;
    }

    public int getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }
    public boolean hasNode(int node){
        return start==node||end==node;
    }
}