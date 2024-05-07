package com.purplecloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Road> roads=new ArrayList<>();
        roads.add(new Road(0,1,0.7,"新疆路"));
        roads.add(new Road(1,2,0.7,"内蒙古路"));
        roads.add(new Road(2,3,0.5,"吉林路"));
        roads.add(new Road(3,4,1.1,"辽宁路"));
        roads.add(new Road(4,5,0.5,"黑龙江路"));

        roads.add(new Road(0,6,0.6,"河昆仑道"));
        roads.add(new Road(1,7,0.5,"敦煌道"));
        roads.add(new Road(2,8,0.4,"恒山道"));
        roads.add(new Road(3,9,0.9,"武当道"));
        roads.add(new Road(4,10,0.3,"龙虎道"));
        roads.add(new Road(5,11,0.4,"齐云道"));

        roads.add(new Road(6,7,0.6,"甘肃路"));
        roads.add(new Road(7,8,1.1,"湖北路"));
        roads.add(new Road(8,9,0.7,"河北路"));
        roads.add(new Road(9,10,0.6,"天津路"));
        roads.add(new Road(10,11,0.9,"北京路"));

        roads.add(new Road(6,12,0.3,"峨眉道"));
        roads.add(new Road(7,13,1.2,"骊山道"));
        roads.add(new Road(8,14,1.1,"五台道"));
        roads.add(new Road(9,15,0.4,"嵩山道"));
        roads.add(new Road(10,16,1.1,"泰山道"));
        roads.add(new Road(11,17,0.9,"天柱道"));

        roads.add(new Road(12,13,1.5,"青海路"));
        roads.add(new Road(13,14,1.3,"湖南路"));
        roads.add(new Road(14,15,1.0,"河南路"));
        roads.add(new Road(15,16,0.3,"江苏路"));
        roads.add(new Road(16,17,0.8,"上海路"));

        roads.add(new Road(12,18,1.1,"陕西路"));
        roads.add(new Road(13,19,1.8,"华山道"));
        roads.add(new Road(14,20,2.2,"山西路"));
        roads.add(new Road(16,26,2.1,"山东路"));
        roads.add(new Road(17,27,1.4,"雁荡道"));

        roads.add(new Road(18,19,1.7,"宁夏路"));
        roads.add(new Road(19,20,0.7,"重庆路"));
        roads.add(new Road(20,21,0.4,"安徽路"));
        roads.add(new Road(21,22,0.8,"江西路"));
        roads.add(new Road(22,23,0.7,"浙江路"));

        roads.add(new Road(18,24,0.5,"蜀山道"));
        roads.add(new Road(19,25,0.8,"衡山道"));
        roads.add(new Road(20,26,0.7,"黄山道"));
        roads.add(new Road(21,27,0.5,"庐山道"));
        roads.add(new Road(22,28,0.8,"武夷道"));
        roads.add(new Road(23,29,0.6,"普陀道"));

        roads.add(new Road(24,25,2.0,"四川路"));
        roads.add(new Road(25,26,1.0,"云南路"));
        roads.add(new Road(26,27,0.5,"广西路"));
        roads.add(new Road(27,28,0.6,"广东路"));
        roads.add(new Road(28,29,0.5,"福建路"));

        roads.add(new Road(24,30,0.8,"青城道"));
        roads.add(new Road(25,31,1.0,"武陵道"));
        roads.add(new Road(26,32,1.0,"华容道"));
        roads.add(new Road(27,33,0.6,"中山路"));
        roads.add(new Road(28,34,1.1,"岭南道"));
        roads.add(new Road(29,35,0.5,"终南道"));

        roads.add(new Road(30,31,1.5,"西藏路"));
        roads.add(new Road(31,32,1.3,"贵州路"));
        roads.add(new Road(32,33,1.0,"澳门路"));
        roads.add(new Road(33,34,0.3,"香港路"));
        roads.add(new Road(34,35,0.8,"台湾路"));

        List<Node> nodes=new ArrayList<>();
        nodes.add(new Node(0,0,0));
        nodes.add(new Node(6,0,1));
        nodes.add(new Node(4,0,2));
        nodes.add(new Node(7,0,3));
        nodes.add(new Node(5,0,4));
        nodes.add(new Node(3,0,5));

        nodes.add(new Node(3,0,6));
        nodes.add(new Node(5,0,7));
        nodes.add(new Node(3,0,8));
        nodes.add(new Node(4,0,9));
        nodes.add(new Node(3,0,10));
        nodes.add(new Node(4,0,11));

        nodes.add(new Node(4,0,12));
        nodes.add(new Node(2,0,13));
        nodes.add(new Node(0,0,14));
        nodes.add(new Node(2,0,15));
        nodes.add(new Node(2,0,16));
        nodes.add(new Node(8,0,17));

        nodes.add(new Node(0,0,20));
        List<Node>nodes1=new ArrayList<>();
        nodes1.add(new Node(2,0,18));
        nodes1.add(new Node(4,0,19));
        nodes1.add(new Node(0,0,20));
        nodes1.add(new Node(0,0,21));
        nodes1.add(new Node(3,0,22));
        nodes1.add(new Node(1,0,23));

        nodes1.add(new Node(5,0,24));
        nodes1.add(new Node(3,0,25));
        nodes1.add(new Node(6,0,26));
        nodes1.add(new Node(5,0,27));
        nodes1.add(new Node(6,0,28));
        nodes1.add(new Node(5,0,29));

        nodes1.add(new Node(6,0,30));
        nodes1.add(new Node(6,0,31));
        nodes1.add(new Node(2,0,32));
        nodes1.add(new Node(6,0,33));
        nodes1.add(new Node(2,0,34));
        nodes1.add(new Node(0,0,35));

//        AStar aStar = new AStar(nodes.get(10), nodes.get(17), nodes, roads);
//        PathAndWay search = aStar.search();
//        System.out.println(search.getDistance());
        Robot robot = new Robot(0,0,10);
        GeneticAlgorithm ga = new GeneticAlgorithm(10000, nodes1, roads, 20, 10,35,robot);
                List<Chromosome> population = new ArrayList<>();
        double fitness = 0.0;
        do{
            Chromosome run = ga.run(1000);
            fitness = run.getFitness();
            population.add(run);
        }while (fitness<0.0051);
//        Robot robot = new Robot(0,0,10);
//        GeneticAlgorithm ga = new GeneticAlgorithm(10000, nodes, roads, 20, 10,0,robot);
//        List<Chromosome> population = new ArrayList<>();
//        double fitness = 0.0;
//        do{
//            Chromosome run = ga.run(1000);
//            fitness = run.getFitness();
//            population.add(run);
//        }while (fitness<0.0051);
        //ga.getFitness(ga.getPopulation().get(0));
        //ga.run(1000);
    }
}