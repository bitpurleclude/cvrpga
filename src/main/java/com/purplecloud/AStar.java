package com.purplecloud;

import java.util.*;

public class AStar {
    private final PriorityQueue<AStarNode> open;
    private final List<AStarNode> nodes;
    private final List<Road> roads;
    private final AStarNode startNode;
    private final AStarNode endNode;

    public AStar(Node startNode, Node endNode, List<Node> nodes, List<Road> roads) {
        this.open = new PriorityQueue<>((AStarNode n1, AStarNode n2) -> (int) (n1.getF() - n2.getF()));
        this.roads = roads;
        this.endNode = convertToAStarNodeEndNode(endNode);
        this.startNode = convertToAStarNode(startNode);
        this.nodes = convertToAStarNodes(nodes);
    }
    private List<AStarNode> convertToAStarNodes(List<Node> nodes) {
        List<AStarNode> aStarNodes = new ArrayList<>();
        for (Node node : nodes) {
            aStarNodes.add(convertToAStarNode(node));
        }
        return aStarNodes;
    }
    private AStarNode convertToAStarNodeEndNode(Node node) {
        AStarNode aStarNode = new AStarNode(node.getBooksToPickUp(), node.getBooksToDropOff(), node.getId());
        aStarNode.setG(0); // Initialize G value
        aStarNode.setH(0); // Initialize H value
        aStarNode.setF(0); // Initialize F value
        return aStarNode;
    }
    private AStarNode convertToAStarNode(Node node) {
        AStarNode aStarNode = new AStarNode(node.getBooksToPickUp(), node.getBooksToDropOff(), node.getId());
        aStarNode.setG(0); // Initialize G value
        aStarNode.setH(calculateH(aStarNode, endNode)); // Initialize H value
        aStarNode.setF(aStarNode.getH()); // Initialize F value
        return aStarNode;
    }

    public PathAndWay search() {
        startNode.setG(0);
        startNode.setH(calculateH(startNode, endNode));
        startNode.setF(startNode.getG() + startNode.getH());

        open.add(startNode);

        while (!open.isEmpty()) {
            //printOpen(open);
            AStarNode currentNode = open.poll();

            if (currentNode.getId() == endNode.getId()) {
                return printPath(currentNode);
            } else {
                addToOpen(currentNode);
            }
        }
        return null; // Return null if no path is found
    }
    public void printOpen(PriorityQueue<AStarNode> open) {
        open.forEach(node -> System.out.print(node.getId()+" "));
        System.out.println("\n");
    }
    private final Set<AStarNode> closed = new HashSet<>();

    private void addToOpen(AStarNode currentNode) {
        for (Road road : roads) {
            if (road.getNode1() == currentNode.getId() || road.getNode2() == currentNode.getId()) {
                AStarNode neighbor = getOtherNode(road, currentNode);
                if (neighbor == null || closed.contains(neighbor)) {
                    continue;
                }
                if (!open.contains(neighbor)) {
                    neighbor.setParent(currentNode);
                    neighbor.setG(currentNode.getG() + road.getWeight());
                    neighbor.setTotalDistance(currentNode.getTotalDistance() + road.getWeight());
                    neighbor.setH(calculateH(neighbor, endNode));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    open.add(neighbor);
                } else {
                    if (neighbor.getG() > currentNode.getG() + road.getWeight()) {
                        neighbor.setParent(currentNode);
                        neighbor.setG(currentNode.getG() + road.getWeight());
                        neighbor.setTotalDistance(currentNode.getTotalDistance() + road.getWeight());
                        neighbor.setF(neighbor.getG() + neighbor.getH());
                    }
                }
            }
        }
        closed.add(currentNode);
    }

    private AStarNode getOtherNode(Road road, AStarNode currentNode) {
        if (road.getNode1() == currentNode.getId()) {
            //System.out.println(road.getNode2());
            return getNodeById(road.getNode2());
        } else {
            //System.out.println(road.getNode1());
            return getNodeById(road.getNode1());
        }
    }

    private AStarNode getNodeById(int id) {
        for (AStarNode node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    private int calculateH(AStarNode node1, AStarNode node2) {
        int dx = Math.abs(node1.getI() - node2.getI());
        int dy = Math.abs(node1.getJ() - node2.getJ());
        return dx * dx + dy * dy;
    }

    private PathAndWay printPath(AStarNode currentNode) {
        List<Integer> path = new ArrayList<>();
        double totalDistance = 0;
        while (currentNode != null && currentNode.getId() != startNode.getId()) {
            path.add(currentNode.getId());
            totalDistance += currentNode.getTotalDistance();
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path); // Reverse the path to start from the startNode
        return new PathAndWay(path, totalDistance);
    }
}