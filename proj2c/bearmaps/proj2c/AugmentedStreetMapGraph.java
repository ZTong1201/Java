package bearmaps.proj2c;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import edu.princeton.cs.algs4.TrieST;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private Map<Point, Node> pointNodeMap = new HashMap<>();
    private List<Point> points = new ArrayList<>();
    private TrieST<Integer> trie = new TrieST<>();
    private Map<String, String> cleanToFullName = new HashMap<>();
    private Map<String, List<Node>> cleanNameToNodes = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if(!neighbors(node.id()).isEmpty()) {
                pointNodeMap.put(new Point(node.lon(), node.lat()), node);
                points.add(new Point(node.lon(), node.lat()));
            }
            if(node.name() != null) {
                trie.put(cleanString(node.name()), 1);
                cleanToFullName.put(cleanString(node.name()), node.name());
                if(!cleanNameToNodes.containsKey(cleanString(node.name()))) {
                    cleanNameToNodes.put(cleanString(node.name()), new LinkedList<>());
                }
                cleanNameToNodes.get(cleanString(node.name())).add(node);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        KDTree kdTree = new KDTree(points);
        Point nearestPoint = kdTree.nearest(lon, lat);
        //WeirdPointSet weirdPointSet = new WeirdPointSet(points);
        //Point nearestPoint = weirdPointSet.nearest(lon, lat);
        return pointNodeMap.get(nearestPoint).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanPrefix = cleanString(prefix);
        Set<String> keySet = new HashSet<>();
        for(String s : trie.keysWithPrefix(cleanPrefix)) {
            if(!keySet.contains(s)) {
                keySet.add(cleanToFullName.get(s));
            }
        }
        List<String> keyWithPrefix = new ArrayList<>(keySet);
        return keyWithPrefix;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanName = cleanString(locationName);
        List<Node> nodes = cleanNameToNodes.get(cleanName);
        List<Map<String, Object>> locationList = new LinkedList<>();
        for(Node node : nodes) {
            Map<String, Object> locParams = new HashMap<>();
            locParams.put("lat", node.lat());
            locParams.put("lon", node.lon());
            locParams.put("name", node.name());
            locParams.put("id", node.id());
            locationList.add(locParams);
        }
        return locationList;
    }

    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
