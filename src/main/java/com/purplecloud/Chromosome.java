package com.purplecloud;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chromosome {
    private List<Integer> sequence;
    private double fitness;

    public Chromosome(List<Integer> sequence) {
        this.sequence = new ArrayList<>(sequence);
        this.fitness = 0.0;
    }

    public Chromosome() {
    }

    public Chromosome(List<Integer> sequence, double fitness) {
        this.sequence = sequence;
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        String sequenceString = sequence.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return "Chromosome{" +
                "sequence=[" + sequenceString + "]" +
                ", fitness=" + fitness +
                '}';
    }

    /**
     * 获取
     * @return sequence
     */
    public List<Integer> getSequence() {
        return sequence;
    }

    /**
     * 设置
     * @param sequence
     */
    public void setSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    /**
     * 获取
     * @return fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * 设置
     * @param fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    // getters and setters
}