import java.awt.*;
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
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    private static boolean initialized = false;
    /** The tile images are in the IMG_ROOT folder. */
    private String IMG_ROOT ;
    public QuadTree imgTree;


    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
        if(initialized){
            return;
        }
        IMG_ROOT = imgRoot;
        imgTree = new QuadTree(ROOT_ULLON,ROOT_ULLAT,ROOT_LRLON,ROOT_LRLAT,IMG_ROOT);
        initialized = true;
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


    public Map<String, Object> getMapRaster(Map<String, Double> params) {

        RasteredImages rasteredImages = imgTree.getRasteredImages(params.get("ullon"), params.get("ullat"),
                params.get("lrlon"), params.get("lrlat"), params.get("w"),params.get("h"));

        Map<String,Object> results = rasteredImages.getRasteredImgParams();

        return results;

    }


//    public static void main(String[] args) {
//        Rasterer rasterer = new Rasterer("img/");
//        HashMap<String, Double> params = new HashMap<>();
//        params.put("ullon",-122.30410170759153);
//        params.put("ullat",37.870213571328854);
//        params.put("lrlon",-122.2104604264636);
//        params.put("lrlat",37.8318576119893);
//        params.put("w",1085.0);
//        params.put("h",566.0);
//        Map<String, Object> rasteredImgParams = rasterer.getMapRaster(params);
//        System.out.println(rasteredImgParams);
//    }

}
