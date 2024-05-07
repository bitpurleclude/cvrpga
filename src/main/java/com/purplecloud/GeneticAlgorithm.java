package com.purplecloud;

import java.nio.ByteBuffer;
import java.util.*;

public class GeneticAlgorithm {
    private List<Chromosome> population;
    int maxLoad;
    int unloadPoint;
    List<Node> nodes;
    List<Road> roads;
    Robot robot;
    Node unloadNode;
    Node startNode;

    public GeneticAlgorithm() {
        this.population = new ArrayList<>();
    }
    public Node getNodeById(int id) {
        Optional<Node> result = nodes.stream()
                .filter(node -> node.getId() == id)
                .findFirst();
        return result.orElse(null);
    }
    public GeneticAlgorithm(int populationSize,
                            List<Node> nodes,
                            List<Road> roads,
                            int unloadPoint,
                            int maxLoad,
                            int startNode,
                            Robot robot) {
        this.population = new ArrayList<>();
        this.unloadPoint = unloadPoint;
        this.maxLoad = maxLoad;
        this.nodes = nodes;
        this.roads = roads;
        this.robot = robot;
        initializePopulation(populationSize,startNode,unloadPoint);
    }

    public void initializePopulation(int populationSize, int startNode, int unloadPoint) {
        this.startNode = getNodeById(startNode);
        this.unloadNode = getNodeById(unloadPoint);
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            List<Integer> sequence = new ArrayList<>();
            List<Node> nodesCopy = new ArrayList<>(nodes); // Create a copy of nodes
            while (!nodesCopy.isEmpty()) {
                int index = random.nextInt(nodesCopy.size());
                if (nodesCopy.get(index).getId() != startNode&&nodesCopy.get(index).getId() != unloadPoint ){
                    sequence.add(nodesCopy.get(index).getId());
                    nodesCopy.remove(index);
                }else {
                    nodesCopy.remove(index);
                }
            }
            Chromosome chromosome = new Chromosome(sequence);
            getFitness(chromosome); // Calculate fitness for the new chromosome
            //System.out.println(chromosome.toString());
            this.population.add(chromosome);
        }
    }
    public void selection() {
        // Calculate the total fitness of the population
        double totalFitness = population.stream().mapToDouble(Chromosome::getFitness).sum();

        // Create a list for the new population
        List<Chromosome> newPopulation = new ArrayList<>();

        // Select chromosomes to the new population based on their fitness
        for (int i = 0; i < population.size(); i++) {
            double rand = Math.random() * totalFitness;
            double cumulativeFitness = 0.0;

            for (Chromosome chromosome : population) {
                cumulativeFitness += chromosome.getFitness();
                if (cumulativeFitness >= rand) {
                    newPopulation.add(chromosome);
                    break;
                }
            }
        }

        // If the size of the new population is odd, select one more chromosome
        if (newPopulation.size() % 2 != 0) {
            double rand = Math.random() * totalFitness;
            double cumulativeFitness = 0.0;

            for (Chromosome chromosome : population) {
                cumulativeFitness += chromosome.getFitness();
                if (cumulativeFitness >= rand) {
                    newPopulation.add(chromosome);
                    break;
                }
            }
        }

        // Replace the old population with the new one
        population = newPopulation;
    }
    public void heuristicCrossover() {
        Random random = new Random();
        List<Chromosome> newOffspring = new ArrayList<>();

        for (int i = 0; i < population.size(); i += 2) {
            Chromosome parent1 = population.get(i);
            Chromosome parent2 = population.get(i + 1);

            List<Integer> child1Sequence = new ArrayList<>();
            List<Integer> child2Sequence = new ArrayList<>();

            // Step 1: Randomly select a gene from the parent
            int startGeneIndex = random.nextInt(parent1.getSequence().size());
            int startGene = parent1.getSequence().get(startGeneIndex);

            child1Sequence.add(startGene);
            child2Sequence.add(startGene);

            List<Integer> parent1SequenceCopy = new ArrayList<>(parent1.getSequence());
            List<Integer> parent2SequenceCopy = new ArrayList<>(parent2.getSequence());
            List<Integer> parent3SequenceCopy = new ArrayList<>(parent1.getSequence());
            List<Integer> parent4SequenceCopy = new ArrayList<>(parent2.getSequence());
            //System.out.println("删除"+startGene);
            parent1SequenceCopy.remove(Integer.valueOf(startGene));
            parent2SequenceCopy.remove(Integer.valueOf(startGene));
            parent3SequenceCopy.remove(Integer.valueOf(startGene));
            parent4SequenceCopy.remove(Integer.valueOf(startGene));
            int nextGeneIndex1 = (parent1SequenceCopy.indexOf(startGene) + 1) % parent1SequenceCopy.size();
            int nextGeneIndex2 = (parent2SequenceCopy.indexOf(startGene) + 1) % parent2SequenceCopy.size();
            int nextGene1 = parent1SequenceCopy.get(nextGeneIndex1);
            int nextGene2 = parent2SequenceCopy.get(nextGeneIndex2);
            // Step 2-5: Fill the rest of the child sequence
            while (child1Sequence.size() < parent1.getSequence().size() &&parent1SequenceCopy.size()>0&&parent2SequenceCopy.size()>0){


                double distance1 = distance(startGene, nextGene1);
                double distance2 = distance(startGene, nextGene2);

                if (distance1 >= distance2) {
                    child1Sequence.add(nextGene2);
                    //System.out.println("子代"+child1Sequence);
                    startGene = nextGene2;
                    nextGeneIndex1 = (parent1SequenceCopy.indexOf(startGene)+1) % parent1SequenceCopy.size();
                    nextGeneIndex2 = (parent2SequenceCopy.indexOf(startGene)+1) % parent2SequenceCopy.size();
                    //System.out.println("索引"+nextGeneIndex1+","+nextGeneIndex2);
                    //System.out.println(parent1SequenceCopy);
                    //System.out.println(parent2SequenceCopy);
                    nextGene1 = parent1SequenceCopy.get(nextGeneIndex1);
                    nextGene2 = parent2SequenceCopy.get(nextGeneIndex2);
                    //System.out.println("数字"+nextGene1+","+nextGene2);
                    parent1SequenceCopy.remove(Integer.valueOf(startGene));
                    parent2SequenceCopy.remove(Integer.valueOf(startGene));
                    //System.out.println(parent1SequenceCopy);
                    //System.out.println(parent2SequenceCopy);
                } else {
                    child1Sequence.add(nextGene1);
                    //System.out.println("子代"+child1Sequence);
                    startGene = nextGene1;
                    nextGeneIndex1 = (parent1SequenceCopy.indexOf(startGene)+1) % parent1SequenceCopy.size();
                    nextGeneIndex2 = (parent2SequenceCopy.indexOf(startGene)+1) % parent2SequenceCopy.size();
                    //System.out.println("索引"+nextGeneIndex1+","+nextGeneIndex2);
                   // System.out.println(parent1SequenceCopy);
                    //System.out.println(parent2SequenceCopy);
                    nextGene1 = parent1SequenceCopy.get(nextGeneIndex1);
                    nextGene2 = parent2SequenceCopy.get(nextGeneIndex2);
                    //System.out.println("数字"+nextGene1+","+nextGene2);
                    parent1SequenceCopy.remove(Integer.valueOf(startGene));
                    parent2SequenceCopy.remove(Integer.valueOf(startGene));
                    //System.out.println(parent1SequenceCopy);
                    //System.out.println(parent2SequenceCopy);
                }
            }
            while (child2Sequence.size() < parent1.getSequence().size()&&parent3SequenceCopy.size()>0&&parent4SequenceCopy.size()>0 ){


                double distance1 = distance(startGene, nextGene1);
                double distance2 = distance(startGene, nextGene2);

                if (distance1 >= distance2) {
                    child2Sequence.add(nextGene2);
                    //System.out.println("子代"+child2Sequence);
                    startGene = nextGene2;
                    nextGeneIndex1 = (parent3SequenceCopy.indexOf(startGene)-1+parent3SequenceCopy.size()) % parent3SequenceCopy.size();
                    nextGeneIndex2 = (parent4SequenceCopy.indexOf(startGene)-1+parent3SequenceCopy.size()) % parent4SequenceCopy.size();
                    //System.out.println("索引"+nextGeneIndex1+","+nextGeneIndex2);
                    //System.out.println(parent3SequenceCopy);
                    //System.out.println(parent4SequenceCopy);
                    nextGene1 = parent3SequenceCopy.get(nextGeneIndex1);
                    nextGene2 = parent4SequenceCopy.get(nextGeneIndex2);
                    //System.out.println("数字"+nextGene1+","+nextGene2);
                    parent3SequenceCopy.remove(Integer.valueOf(startGene));
                    parent4SequenceCopy.remove(Integer.valueOf(startGene));
                    //System.out.println(parent3SequenceCopy);
                    //System.out.println(parent4SequenceCopy);
                } else {
                    child2Sequence.add(nextGene1);
                    //System.out.println("子代"+child2Sequence);
                    startGene = nextGene1;
                    nextGeneIndex1 = (parent3SequenceCopy.indexOf(startGene)-1+parent3SequenceCopy.size()) % parent3SequenceCopy.size();
                    nextGeneIndex2 = (parent4SequenceCopy.indexOf(startGene)-1+parent3SequenceCopy.size()) % parent4SequenceCopy.size();
                    //System.out.println("索引"+nextGeneIndex1+","+nextGeneIndex2);
                    //System.out.println(parent3SequenceCopy);
                    //System.out.println(parent4SequenceCopy);
                    nextGene1 = parent3SequenceCopy.get(nextGeneIndex1);
                    nextGene2 = parent4SequenceCopy.get(nextGeneIndex2);
                    //System.out.println("数字"+nextGene1+","+nextGene2);
                    parent3SequenceCopy.remove(Integer.valueOf(startGene));
                    parent4SequenceCopy.remove(Integer.valueOf(startGene));
                    //System.out.println(parent3SequenceCopy);
                    //System.out.println(parent4SequenceCopy);
                }
            }

            Chromosome child1 = new Chromosome(child1Sequence);
            Chromosome child2 = new Chromosome(child2Sequence);
            getFitness(child1);
            getFitness(child2);
            newOffspring.add(child1);
        }

        population = newOffspring;
    }

    public double distance(int gene1, int gene2) {
        Node node1 = getNodeById(gene1);
        Node node2 = getNodeById(gene2);
        AStar aStar = new AStar(node1, node2, nodes, roads);
        PathAndWay search = aStar.search();
        return search.getDistance();
    }
    public void crossover() {
        Random random = new Random();
        // Create a list for the new offspring
        List<Chromosome> newOffspring = new ArrayList<>();

        for (int i = 0; i < population.size(); i += 2) {
            Chromosome parent1 = population.get(i);
            Chromosome parent2 = population.get(i + 1);

            // Choose a random crossover point
            int crossoverPoint = random.nextInt(parent1.getSequence().size());

            // Create children by swapping genes at and after the crossover point
            List<Integer> child1Sequence = new ArrayList<>(parent1.getSequence().subList(0, crossoverPoint));
            child1Sequence.addAll(parent2.getSequence().subList(crossoverPoint, parent2.getSequence().size()));

            List<Integer> child2Sequence = new ArrayList<>(parent2.getSequence().subList(0, crossoverPoint));
            child2Sequence.addAll(parent1.getSequence().subList(crossoverPoint, parent1.getSequence().size()));

            // Create new chromosomes for the children and add them to the new offspring
            Chromosome child1 = new Chromosome(child1Sequence);
            getFitness(child1);
            Chromosome child2 = new Chromosome(child2Sequence);
            getFitness(child2);

            newOffspring.add(child1);
            newOffspring.add(child2);
        }

        // Replace the old population with the new offspring
        population = newOffspring;
    }

    public void mutation() {
        Random random = new Random();
        // Define the mutation rate
        double mutationRate = 0.05;

        // Perform mutation on some chromosomes
        for (Chromosome chromosome : population) {
            for (int i = 0; i < chromosome.getSequence().size(); i++) {
                // If the random value is less than the mutation rate, perform mutation
                if (random.nextDouble() < mutationRate) {
                    // Swap the gene at index i with a gene at a random index
                    int randomIndex = random.nextInt(chromosome.getSequence().size());
                    int temp = chromosome.getSequence().get(i);
                    chromosome.getSequence().set(i, chromosome.getSequence().get(randomIndex));
                    chromosome.getSequence().set(randomIndex, temp);
                }
            }
            getFitness(chromosome); // Calculate fitness for the mutated chromosome
        }
    }

    public Chromosome run(int iterations) {
        for (int i = 0; i < iterations; i++) {
            selection();
            //crossover();
            heuristicCrossover();
            mutation();
        }
        // Find the chromosome with the highest fitness
        Chromosome bestChromosome = population.stream().max(Comparator.comparing(Chromosome::getFitness)).orElse(null);

        // Print the best path
        if (bestChromosome != null) {
            System.out.println("Best path: " + bestChromosome.getSequence());
            show(bestChromosome);
        }

        return bestChromosome;
    }
    public boolean couldService(Node currentNode,Robot robot,int maxLoad){
        return currentNode.getBooksToDropOff() <= robot.getBooksToDispatch()
                && (robot.getTotalLoad()-currentNode.getBooksToDropOff() + currentNode.getBooksToPickUp() <= maxLoad);

    }
    public void getFitness(Chromosome chromosome){
        List<Integer> sequence = chromosome.getSequence();
        double totalDistance = 0;
        int i = 0;
        AStar aStar = new AStar(startNode, getNodeById(sequence.get(i)), nodes, roads);
        PathAndWay search = aStar.search();
        totalDistance += search.getDistance();
        for ( i = 0; i < sequence.size() - 1; i++) {
            if (couldService(getNodeById(sequence.get(i)), robot, maxLoad)) {
                robot.setBooksToDispatch(robot.getBooksToDispatch() - getNodeById(sequence.get(i)).getBooksToDropOff());
                robot.setBooksToRecycle(robot.getBooksToRecycle() + getNodeById(sequence.get(i)).getBooksToPickUp());
            }else {
                aStar = new AStar(getNodeById(sequence.get(i)), getNodeById(unloadPoint), nodes, roads);
                search = aStar.search();
                totalDistance += search.getDistance();
                robot.setBooksToRecycle(0); // Drop off books
                robot.setBooksToDispatch(unloadNode.getBooksToPickUp());
                totalDistance += search.getDistance();
                robot.setBooksToDispatch(robot.getBooksToDispatch() - getNodeById(sequence.get(i)).getBooksToDropOff());
                robot.setBooksToRecycle(robot.getBooksToRecycle() + getNodeById(sequence.get(i)).getBooksToPickUp());
            }
            aStar = new AStar(getNodeById(sequence.get(i)), getNodeById(sequence.get(i + 1)), nodes, roads);
            search = aStar.search();
            totalDistance += search.getDistance();
        }
        aStar = new AStar(getNodeById(sequence.get(i)), startNode, nodes, roads);
        search = aStar.search();
        totalDistance += search.getDistance();
        chromosome.setFitness(1/totalDistance);
        //System.out.println(1/totalDistance);
    }
    public void show(Chromosome chromosome){
        List<Integer> sequence = chromosome.getSequence();
        double totalDistance = 0;
        int i = 0;
        List<Integer> pathRecord = new ArrayList<>();

        AStar aStar = new AStar(startNode, getNodeById(sequence.get(i)), nodes, roads);
        System.out.println("到达"+startNode.getId()+"节点");
        PathAndWay search = aStar.search();
        totalDistance += search.getDistance();
        pathRecord.add(startNode.getId());
        pathRecord.addAll(search.path);
        for ( i = 0; i < sequence.size() - 1; i++) {
            if (couldService(getNodeById(sequence.get(i)), robot, maxLoad)) {
                robot.setBooksToDispatch(robot.getBooksToDispatch() - getNodeById(sequence.get(i)).getBooksToDropOff());
                System.out.println("放出书籍"+getNodeById(sequence.get(i)).getBooksToDropOff());
                robot.setBooksToRecycle(robot.getBooksToRecycle() + getNodeById(sequence.get(i)).getBooksToPickUp());
                System.out.println("回收书籍"+getNodeById(sequence.get(i)).getBooksToPickUp());
                System.out.println("已经收取书籍"+robot.getBooksToRecycle());
                System.out.println("剩余书籍"+robot.getBooksToDispatch());

            }else {
                System.out.println("不满足条件,回到补给点");
                aStar = new AStar(getNodeById(sequence.get(i)), getNodeById(unloadPoint), nodes, roads);
                search = aStar.search();
                totalDistance += search.getDistance();
                pathRecord.addAll(search.path);
                Collections.reverse(search.path);
                pathRecord.addAll(search.path);
                robot.setBooksToRecycle(0); // Drop off books
                robot.setBooksToDispatch(unloadNode.getBooksToPickUp());
                totalDistance += search.getDistance();
                System.out.println("到达"+getNodeById(sequence.get(i)).getId()+"节点");
                robot.setBooksToDispatch(robot.getBooksToDispatch() - getNodeById(sequence.get(i)).getBooksToDropOff());
                System.out.println("收回书籍"+getNodeById(sequence.get(i)).getBooksToPickUp());
                System.out.println("放出书籍"+getNodeById(sequence.get(i)).getBooksToDropOff());
                robot.setBooksToRecycle(robot.getBooksToRecycle() + getNodeById(sequence.get(i)).getBooksToPickUp());
                System.out.println("已经收取书籍"+robot.getBooksToRecycle());
                System.out.println("剩余书籍"+robot.getBooksToDispatch());
            }
            System.out.println("从"+getNodeById(sequence.get(i)).getId()+"到"+getNodeById(sequence.get(i + 1)).getId()+"节点");
            aStar = new AStar(getNodeById(sequence.get(i)), getNodeById(sequence.get(i + 1)), nodes, roads);
            search = aStar.search();
            totalDistance += search.getDistance();
            System.out.println("距离"+search.getDistance());
            System.out.println("到达"+getNodeById(sequence.get(i + 1)).getId()+"节点");
            pathRecord.addAll(search.path);
        }
        //最后一趟回图书馆
        aStar = new AStar(getNodeById(sequence.get(i)), getNodeById(unloadPoint), nodes, roads);
        search = aStar.search();
        totalDistance += search.getDistance();
        pathRecord.addAll(search.path);
        //回充电站
        aStar = new AStar(getNodeById(unloadPoint), startNode, nodes, roads);
        search = aStar.search();
        totalDistance += search.getDistance();
        pathRecord.addAll(search.path);
        chromosome.setFitness(1/totalDistance);
        System.out.println("\n");
        System.out.println(pathRecord);
        System.out.println("\n");
        System.out.println(1/totalDistance);
    }

    public List<Chromosome>  getPopulation() {
        return population;
    }
}