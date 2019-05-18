import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    PriorityQueue<Flight> startMinPQ;
    PriorityQueue<Flight> endMinPQ;

    public FlightSolver(ArrayList<Flight> flights) {
        Comparator<Flight> compareStart = (f1, f2) -> {return f1.startTime - f2.startTime;};
        Comparator<Flight> compareEnd = (f1, f2) -> {return f1.endTime - f2.endTime;};
        startMinPQ = new PriorityQueue<>(compareStart);
        endMinPQ = new PriorityQueue<>(compareEnd);
        for (Flight f : flights) {
            startMinPQ.add(f);
            endMinPQ.add(f);

        }
    }

    public int solve() {
        int res = 0;
        int count = 0;
        while(!startMinPQ.isEmpty()) {
            Flight flightFromStart = startMinPQ.peek();
            Flight flightFromEnd = endMinPQ.peek();
            if(flightFromStart.startTime <= flightFromEnd.endTime) {
                count += startMinPQ.remove().passengers;
                if (count > res) {
                    res = count;
                }
            } else {
                count -= endMinPQ.remove().passengers;
            }
        }
        return res;
    }

}
