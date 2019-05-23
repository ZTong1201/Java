import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    List<Bear> bears;
    List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        List<Bed> bedList = beds;
        Collections.sort(bedList, new bedComparator());
        this.bears = bears;
        this.beds = new ArrayList<>();
        for(int i = 0; i < this.bears.size(); i++) {
            this.beds.add(binarySearch(bedList, this.bears.get(i)));
        }

        /*this.beds = new ArrayList<>();
        this.bears = bears;
        for(int i = 0; i < bears.size(); i++) {
            for(int j = 0; j < beds.size(); j++) {
                if(bears.get(i).compareTo(beds.get(j)) == 0) {
                    this.beds.add(beds.get(j));
                    break;
                }
            }
        }*/
    }

    private Bed binarySearch(List<Bed> beds, Bear bear) {
        int left = 0;
        int right = beds.size();
        Bed res = null;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(beds.get(mid).compareTo(bear) == 0) {
                res = beds.get(mid);
                break;
            } else if(beds.get(mid).compareTo(bear) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }

    private class bedComparator implements Comparator<Bed> {
        @Override
        public int compare(Bed bed1, Bed bed2) {
            return bed1.getSize() - bed2.getSize();
        }
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return this.bears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return this.beds;
    }
}
