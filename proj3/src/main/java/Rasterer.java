import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */


public class Rasterer {
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.


    public static final int degreeofLongitude = 288200;
    public static final double bestPossibleLonDPP = 0.773;
    public final double biggestLonDPP = degreeofLongitude * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
    public QuadTree initialQuadTree;
    public Set<QuadTree> returnTiles;


    //    MapServer.ROOT_ULLON,MapServer.ROOT_ULLAT,MapServer.ROOT_ULLON,MapServer.ROOT_ULLAT



    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
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
     * </p>
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *

     * @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *                    Can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *                    forget to set this to true! <br>
     * @see #REQUIRED_RASTER_REQUEST_PARAMS
     *
     * private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat","lrlon", "w", "h"};
     * eg:
     * {lrlon=-122.2104604264636, ullon=-122.30410170759153, w=1085.0, h=566.0, ullat=37.870213571328854, lrlat=37.8318576119893}

     *
     */





    public void findIntersects (QuadTree qt, double query_ullon, double query_ullat,
                                double query_lrlon, double query_lrlat, int level){
        //start from qt, and in given depth, return the tiles that intersects with the query box


        if( qt == null || qt.root.upLeftLongitude > query_lrlon || qt.root.upLeftLatitude < query_lrlat ||
                qt.root.lowerRightLongitude < query_ullon || qt.root.lowerRightLatitude > query_ullat){
            return;

        } else {

            if(level == 0){
                returnTiles.add(qt);
            } else {
                level -= 1;
                findIntersects(new QuadTree(qt.root.UL), query_ullon, query_ullat, query_lrlon, query_lrlat, level);
                findIntersects(new QuadTree(qt.root.UR), query_ullon, query_ullat, query_lrlon, query_lrlat, level);
                findIntersects(new QuadTree(qt.root.LL), query_ullon, query_ullat, query_lrlon, query_lrlat, level);
                findIntersects(new QuadTree(qt.root.LR), query_ullon, query_ullat, query_lrlon, query_lrlat, level);
            }
        }

    }


    /**
     * The problem of finding the correct images for a given query is thus equivalent to
     * going to the shallowest level whose LonDPP is less than or equal to the query box,
     * and finding all images at that level that intersect the query box.
     *
     */

    public Map<String, Object> getMapRaster(Map<String, Double> params) {

        System.out.println(params);
        System.out.println(biggestLonDPP);


        double query_ullon = params.get("ullon");
        double query_ullat = params.get("ullat");
        double query_lrlon = params.get("lrlon");
        double query_lrlat = params.get("lrlat");
        double query_width = params.get("w");

        Map<String, Object> results = new HashMap<>();

        String[][] tiles = new String [][] {};

        double queryLonDPP = degreeofLongitude * Math.abs( query_lrlon - query_ullon ) / query_width;
        double bestLonDPP = biggestLonDPP;
        int depthTraversed = 0;
        initialQuadTree = new QuadTree(new QuadTree.Node(MapServer.ROOT_ULLON, MapServer.ROOT_LRLAT,MapServer.ROOT_LRLON,MapServer.ROOT_LRLAT,queryLonDPP));


        if(!initialQuadTree.intersectsQueryBox(query_ullon,query_ullat,query_lrlon,query_lrlat)){
            return null;
        } else{
            while( bestLonDPP > queryLonDPP && bestLonDPP > bestPossibleLonDPP){
                bestLonDPP = bestLonDPP / 2;
                depthTraversed += 1;
            }
        }

        System.out.println(bestLonDPP);
        findIntersects(initialQuadTree,query_ullon,query_ullat,query_lrlon,query_lrlat, depthTraversed);



        System.out.println(returnTiles.size());

        results.put("render_grid", tiles);
        results.put("raster_ul_lon", 313);
        results.put("raster_ul_lat", 37.87701580361881 );
        results.put("raster_lr_lon", -122.24006652832031 );
        results.put("raster_lr_lat", 37.87538940251607 );
        results.put("depth", depthTraversed);
        results.put("query_success", true);


        return results;


    }


    public static void main(String[] args) {
        Rasterer rasterer = new Rasterer("img/");
        Map<String, Double> params = new HashMap<>();
        params.put("ullon",-122.30410170759153);
        params.put("ullat",37.870213571328854);
        params.put("lrlon",-122.2104604264636);
        params.put("lrlat",37.8318576119893);
        params.put("w",1091.0);
        params.put("h",566.0);
        Map<String, Object> rasteredImgParams = rasterer.getMapRaster(params);
        System.out.println(params);
    }


}
