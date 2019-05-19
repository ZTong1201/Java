package bearmaps.hw4;

import java.util.*;
import edu.princeton.cs.algs4.Stopwatch;
import bearmaps.proj2ab.*;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private double timeSpent;
    private List<Vertex> solutions;
    private double bestWeight;
    private int numOfOperations;
    private Set<Vertex> visitedVertices;
    //private DoubleMapPQ<Vertex> minPQ;
    private ArrayHeapMinPQ<Vertex> minPQ;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        visitedVertices = new HashSet<>();
        solutions = new ArrayList<>();
        minPQ = new ArrayHeapMinPQ<>();
        minPQ.add(start, input.estimatedDistanceToGoal(start, end));
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);
        while(minPQ.size() > 0) {
            if(sw.elapsedTime() > timeout * Math.pow(10, 6)) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            Vertex vertex = minPQ.removeSmallest();
            visitedVertices.add(vertex);
            numOfOperations += 1;
            if(vertex.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }

            List<WeightedEdge<Vertex>> neighbors = input.neighbors(vertex);
            for(int i = 0; i < neighbors.size(); i++) {
                WeightedEdge<Vertex> newVertex = neighbors.get(i);
                if(!visitedVertices.contains(newVertex.to())) {
                    relax(newVertex, input, end);
                }
            }
        }
        bestWeight = distTo.getOrDefault(end, 0.0);
        if(outcome == SolverOutcome.SOLVED) {
            while(!end.equals(start)) {
                solutions.add(end);
                end = edgeTo.get(end);
            }
            solutions.add(start);
            Collections.reverse(solutions);
        }
        if(outcome != SolverOutcome.TIMEOUT && outcome != SolverOutcome.SOLVED) outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> vertex, AStarGraph<Vertex> input, Vertex goal) {
        Vertex p = vertex.from();
        Vertex q = vertex.to();
        if(distTo.get(p) + vertex.weight() < distTo.getOrDefault(q, Double.POSITIVE_INFINITY)) {
            distTo.put(q, distTo.get(p) + vertex.weight());
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
