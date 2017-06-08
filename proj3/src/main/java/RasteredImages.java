import java.util.HashMap;
import java.util.Map;

/**
 * Created by GritShiva on 2017/6/6 0006.
 */
public class RasteredImages {
    public double ullon;
    public double ullat;
    public double lrlon;
    public double lrlat;
    public String[][] images;
    public boolean success = true;


    public RasteredImages(double ullon, double ullat, double lrlon, double lrlat, String [][] images){
        this.ullon = ullon;
        this.ullat = ullat;
        this.lrlon = lrlon;
        this.lrlat = lrlat;
        this.images = images;
    }

    public double getUllon(){
        return this.ullon;
    }
    public double getUllat(){
        return this.ullat;
    }
    public double getLrlon(){
        return this.lrlon;
    }
    public double getLrlat(){
        return this.lrlat;
    }


    //in getRasteredImgParams() this step :String[][] processedImages = processImages(images); has made
    //imgFile String became : "img/" + images[i][j]+ ".png".
    //So when you get to the step:  rasteredImgParams.put("depth", getDepth());
    //getDepth() should return the actual length of the number like"1234" except "img/" and ".png", so
    //when writing getDepth(), you need to minus 8 which equals to the length of "img/" + ".png"


    public int getDepth(){
        return images[0][0].length() - 8;
    }


    //  After you find all rastered images, this will get you the required format
    //  of HashMap to return.

    /** @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *  Can also be interpreted as the length of the numbers in the image string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *  forget to set this to true! <br>
     */

    public HashMap<String, Object> getRasteredImgParams(){
        HashMap<String, Object> rasteredImgParams = new HashMap<>();
        String[][] processedImages = processImages(images);
        rasteredImgParams.put("render_grid", processedImages);
        rasteredImgParams.put("raster_ul_lon", getUllon());
        rasteredImgParams.put("raster_ul_lat", getUllat() );
        rasteredImgParams.put("raster_lr_lon", getLrlon() );
        rasteredImgParams.put("raster_lr_lat", getLrlat() );
        rasteredImgParams.put("depth", getDepth());
        rasteredImgParams.put("query_success", true);
        return rasteredImgParams;
    }


    public String[][] processImages(String[][] images){
        for(int i = 0; i < images.length; i++){
            for(int j = 0; j < images[0].length; j++){
                images[i][j] = "img/" + images[i][j]+ ".png";
            }
        }
        return  images;
    }
}
