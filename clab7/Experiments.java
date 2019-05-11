import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        ExperimentHelper expHelper = new ExperimentHelper();
        BST BSTree = new BST();
        RandomGenerator randomGenerator = new RandomGenerator();
        List<Double> OAD = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        List<Double> BSTAD = new ArrayList<>();
        int i =0;
        while(BSTree.size() <= 5000) {
            int rand = randomGenerator.getRandomInt(10000);
            if (!BSTree.contains(rand)) {
                BSTree.add(rand);
                OAD.add(expHelper.optimalAverageDepth(BSTree.size()));
                BSTAD.add(BSTree.averageDepth());
                xValues.add(i);
                i += 1;
            }
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Items").yAxisTitle("Average Depth of BST").build();
        chart.addSeries("Optimal Average Depth", xValues, OAD);
        chart.addSeries("Binary Search Tree Average Depth", xValues, BSTAD);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        ExperimentHelper expHelper = new ExperimentHelper();
        BST BSTree = new BST();
        RandomGenerator randomGenerator = new RandomGenerator();
        List<Integer> xValues = new ArrayList<>();
        List<Double> BSTAD = new ArrayList<>();
        while(BSTree.size() < 5000) {
            int rand = randomGenerator.getRandomInt(10000);
            if (!BSTree.contains(rand)) {
                BSTree.add(rand);
            }
        }
        BSTAD.add(BSTree.averageDepth());
        xValues.add(0);
        for (int i = 0; i < 5000; i++) {
            expHelper.randomDeleteTakingSuccessor(BSTree);
            expHelper.randomInsertItems(BSTree, 20000);
            xValues.add(i + 1);
            BSTAD.add(BSTree.averageDepth());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Operations").yAxisTitle("Average Depth of BST").build();
        chart.addSeries("Asymmetric Deletion", xValues, BSTAD);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        ExperimentHelper expHelper = new ExperimentHelper();
        BST BSTree = new BST();
        RandomGenerator randomGenerator = new RandomGenerator();
        List<Integer> xValues = new ArrayList<>();
        List<Double> BSTAD = new ArrayList<>();
        while(BSTree.size() < 5000) {
            int rand = randomGenerator.getRandomInt(10000);
            if (!BSTree.contains(rand)) {
                BSTree.add(rand);
            }
        }
        BSTAD.add(BSTree.averageDepth());
        xValues.add(0);
        for (int i = 0; i < 5000; i++) {
            expHelper.randomDeleteTakingRandom(BSTree);
            expHelper.randomInsertItems(BSTree, 10000);
            xValues.add(i + 1);
            BSTAD.add(BSTree.averageDepth());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Operations").yAxisTitle("Average Depth of BST").build();
        chart.addSeries("Symmetric Deletion", xValues, BSTAD);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment1();
        experiment2();
        experiment3();
    }
}
