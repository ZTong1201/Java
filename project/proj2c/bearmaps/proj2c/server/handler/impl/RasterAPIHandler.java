package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};




    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        double ullat = requestParams.get(REQUIRED_RASTER_REQUEST_PARAMS[0]);
        double ullon = requestParams.get(REQUIRED_RASTER_REQUEST_PARAMS[1]);
        double lrlat = requestParams.get(REQUIRED_RASTER_REQUEST_PARAMS[2]);
        double lrlon = requestParams.get(REQUIRED_RASTER_REQUEST_PARAMS[3]);
        double width = requestParams.get(REQUIRED_RASTER_REQUEST_PARAMS[4]);
        double height = requestParams.get(REQUIRED_RASTER_REQUEST_PARAMS[5]);
        Map<String, Object> results = new HashMap<>();
        if(!isInbound(ullon, ullat, lrlon, lrlat)) {
            return queryFail();
        } else if(!isValidInput(ullon, ullat, lrlon, lrlat)) {
            return queryFail();
        } else {
            double LonDPP = (lrlon- ullon)/ width;
            boolean query_success = true;
            int depth = searchForDepth(LonDPP);
            Map<Integer, Double> ULLON = searchULLON(depth, ullon);
            int lonStart =  (int) ULLON.keySet().toArray()[0];
            double raster_ul_lon = ULLON.get(lonStart);
            Map<Integer, Double> ULLAT = searchULLAT(depth, ullat);
            int latStart = (int) ULLAT.keySet().toArray()[0];
            double raster_ul_lat = ULLAT.get(latStart);
            Map<Integer, Double> LRLON = searchLRLON(depth, lrlon);
            int lonEnd = (int) LRLON.keySet().toArray()[0];
            double raster_lr_lon = LRLON.get(lonEnd);
            Map<Integer, Double> LRLAT = searchLRLAT(depth, lrlat);
            int latEnd = (int) LRLAT.keySet().toArray()[0];
            double raster_lr_lat = LRLAT.get(latEnd);
            int rowPointer = lonStart;
            int colPointer = latStart;
            String[][] render_grid = new String[latEnd - latStart + 1][lonEnd - lonStart + 1];
            for(int i = 0; i < latEnd - latStart + 1; i++) {
                for(int j = 0; j < lonEnd - lonStart + 1; j++) {
                    render_grid[i][j] = "d" + depth + "_x" + rowPointer + "_y" + colPointer + ".png";
                    rowPointer += 1;
                }
                rowPointer = lonStart;
                colPointer += 1;
            }
            results.put(REQUIRED_RASTER_RESULT_PARAMS[0], render_grid);
            results.put(REQUIRED_RASTER_RESULT_PARAMS[1], raster_ul_lon);
            results.put(REQUIRED_RASTER_RESULT_PARAMS[2], raster_ul_lat);
            results.put(REQUIRED_RASTER_RESULT_PARAMS[3], raster_lr_lon);
            results.put(REQUIRED_RASTER_RESULT_PARAMS[4], raster_lr_lat);
            results.put(REQUIRED_RASTER_RESULT_PARAMS[5], depth);
            results.put(REQUIRED_RASTER_RESULT_PARAMS[6], query_success);
        }
        //System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        //System.out.println(requestParams);
        return results;
    }

    private boolean isInbound(double ullon, double ullat, double lrlon, double lrlat) {
        if(ullon > ROOT_LRLON || lrlon < ROOT_ULLON || ullat < ROOT_LRLAT || lrlat > ROOT_ULLAT) {
            return false;
        }
        return true;
    }

    private boolean isValidInput(double ullon, double ullat, double lrlon, double lrlat) {
        if(ullon > lrlon || ullat < lrlat) {
            return false;
        }
        return true;
    }

    private int searchForDepth(double LonDPP) {
        int i = 0;
        while(i < 8) {
            if(LonDPP >= (ROOT_LRLON - ROOT_ULLON)/((double) TILE_SIZE * Math.pow(2.0, i))) {
                return i;
            }
            i += 1;
        }
        return i - 1;
    }


    private Map<Integer, Double> searchULLON(int i, double ullon) {
        double rasterULLON = 0.0;
        int startPoint = 0;
        Map<Integer, Double> ULLON = new HashMap<>();
        if(ullon <= ROOT_ULLON) {
            rasterULLON = ROOT_ULLON;
        }
        else {
            for(int j = 0; j < Math.pow(2.0, i); j++) {
                if(ROOT_ULLON + lonPerTile(i) * j > ullon) {
                    rasterULLON = ROOT_ULLON + lonPerTile(i) * (j - 1);
                    startPoint = j - 1;
                    break;
                }
            }
        }
        ULLON.put(startPoint, rasterULLON);
        return ULLON;
    }

    private Map<Integer, Double> searchLRLON(int i, double lrlon) {
        double rasterLRLON = 0.0;
        int endPoint = 0;
        Map<Integer, Double> LRLON = new HashMap<>();
        if(lrlon >= ROOT_LRLON) {
            endPoint = (int) Math.pow(2.0, i) - 1;
            rasterLRLON = ROOT_LRLON;
        }
        else {
            for(int j = 0; j < Math.pow(2.0, i); j++) {
                if(ROOT_LRLON - lonPerTile(i) * j < lrlon) {
                    rasterLRLON = ROOT_LRLON - lonPerTile(i) * (j - 1);
                    endPoint = (int) Math.pow(2.0, i) - j;
                    break;
                }
            }
        }
        LRLON.put(endPoint, rasterLRLON);
        return LRLON;
    }

    private Map<Integer, Double> searchULLAT(int i, double ullat) {
        double rasterULLAT = 0.0;
        int startPoint = 0;
        Map<Integer, Double> ULLAT = new HashMap<>();
        if(ullat >= ROOT_ULLAT) {
            rasterULLAT = ROOT_ULLAT;
        }
        else {
            for(int j = 0; j < Math.pow(2.0, i); j++) {
                if(ROOT_ULLAT - latPerTile(i) * j < ullat) {
                    rasterULLAT = ROOT_ULLAT - latPerTile(i) * (j - 1);
                    startPoint = j - 1;
                    break;
                }
            }
        }
        ULLAT.put(startPoint, rasterULLAT);
        return ULLAT;
    }

    private Map<Integer, Double> searchLRLAT(int i, double lrlat) {
        double rasterLRLAT = 0.0;
        int endPoint = 0;
        Map<Integer, Double> LRLAT = new HashMap<>();
        if(lrlat <= ROOT_LRLAT) {
            endPoint = (int) Math.pow(2.0, i) - 1;
            rasterLRLAT = ROOT_LRLAT;
        }
        else {
            for(int j = 0; j < Math.pow(2.0, i); j++) {
                if(ROOT_LRLAT + latPerTile(i) * j > lrlat) {
                    rasterLRLAT = ROOT_LRLAT + latPerTile(i) * (j - 1);
                    endPoint = (int) Math.pow(2.0, i) - j;
                    break;
                }
            }
        }
        LRLAT.put(endPoint, rasterLRLAT);
        return LRLAT;
    }

    private double lonPerTile(int i) {
        return (ROOT_LRLON - ROOT_ULLON) / Math.pow(2.0, i);
    }

    private double latPerTile(int i) {
        return (ROOT_ULLAT - ROOT_LRLAT) / Math.pow(2.0 ,i);
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                //File in = new File(imgPath);
                //tileImg = ImageIO.read(in);
                tileImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource(imgPath));
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
