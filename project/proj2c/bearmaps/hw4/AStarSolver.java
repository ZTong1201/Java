package bearmaps.hw4;

import java.util.*;
import bearmaps.hw4.Stopwatch;
import bearmaps.proj2ab.*;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private double timeSpent;
    private List<Vertex> solutions;
    private double bestWeight;
    private int numOfOperations;
    //private DoubleMapPQ<Vertex> minPQ;
    private ArrayHeapMinPQ<Vertex> minPQ;
    private Map<Vertex, Double> distTo = new HashMap<>();
    private Map<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solutions = new ArrayList<>();
        minPQ = new ArrayHeapMinPQ<>();
        minPQ.add(start, input.estimatedDistanceToGoal(start, end));
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);

        while (minPQ.size() > 0) {
            if (sw.elapsedTime() > timeout * Math.pow(10, 6)) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            if(minPQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }
            Vertex vertex = minPQ.removeSmallest();
            numOfOperations += 1;

            List<WeightedEdge<Vertex>> neighbors = input.neighbors(vertex);
            for (int i = 0; i < neighbors.size(); i++) {
                WeightedEdge<Vertex> edge = neighbors.get(i);
                if(edge.from().equals(vertex)) {
                    relax(edge, input, end);
                } else {
                    distTo.put(vertex, edge.weight());
                }
            }
        }
        bestWeight = distTo.getOrDefault(end, 0.0);
        if (outcome == SolverOutcome.SOLVED) {
            while (!end.equals(start)) {
                solutions.add(end);
                end = edgeTo.get(end);
            }
            solutions.add(start);
            Collections.reverse(solutions);
        }
        if (outcome != SolverOutcome.TIMEOUT && outcome != SolverOutcome.SOLVED) outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> edge, AStarGraph<Vertex> input, Vertex goal) {
        Vertex p = edge.from();
        Vertex q = edge.to();
        if(distTo.get(p) + edge.weight() < distTo.getOrDefault(q, Double.POSITIVE_INFINITY)) {
            distTo.put(q, distTo.get(p) + edge.weight());
            edgeTo.put(q, p);
            //edgeTo.put(p, q);
            if(minPQ.contains(q)) {
                minPQ.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            } else {
                minPQ.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            }
        }
    }


    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solutions;
    }

    @Override
    public double solutionWeight() {
        return bestWeight;
    }

    @Override
    public int numStatesExplored() {
        return numOfOperations;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
