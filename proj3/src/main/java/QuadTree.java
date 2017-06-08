import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GritShiva on 2017/6/6 0006.
 */
public class QuadTree {

    public Node root;

    public QuadTree(double ullon, double ullat, double lrlon, double lrlat, String rootName) {
        //File(String pathname)
        //Creates a new File instance by converting the given pathname string into an abstract pathname.
        try{
            File rootFolder = new File(rootName);
            //listFiles()
            //Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname.
            File[] files = rootFolder.listFiles();

            if (files == null) {
                System.out.println(rootName);
                return ;
            }

            root = new Node(ullon, ullat, lrlon, lrlat, "root.png");

            //FileReader(String fileName)
            //Creates a new FileReader, given the name of the file to read from.
            //handle exception
            FileReader fr = new FileReader("fileNames.txt");

            //BufferedReader(Reader in)
            //Creates a buffering character-input stream that uses a default-sized input buffer.
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> sList = new ArrayList<>();
            String data = null;

            while((data = br.readLine()) != null){
                sList.add(data);
            }


            //insert all the tiles to proper level in the root
            for(String s: sList){
                insert(ullon, ullat, lrlon, lrlat, s);
            }


        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public ArrayList<Node> getNodes(double ullon, double ullat, double lrlon, double lrlat, double QBlonDPP) {
        double rootPP = root.getLonDPP();
        ArrayList<Node> nodes = helpGetNodes(root, rootPP, QBlonDPP, ullon, ullat, lrlon, lrlat);

        Collections.sort(nodes);
        return nodes;
    }


    //starting from Node h.
    public ArrayList<Node> helpGetNodes(Node h, double nodeLonDPP, double QBlonDPP, double ullon, double ullat, double lrlon, double lrlat){
        ArrayList<Node> nodes = new ArrayList<>();

        if(h.intersectsQueryBox(ullon,ullat,lrlon,lrlat)){
            if(nodeLonDPP <= QBlonDPP  || h.getImgFile().length() == 7) {
                nodes.add(h);
            }else{
                for(Node node: h.getChildren()){
                    //addAll(Collection<? extends E> c)
                    //Appends all of the elements in the specified collection to the end of this list,
                    //in the order that they are returned by the specified collection's Iterator.
                    nodes.addAll(helpGetNodes(node, nodeLonDPP/2, QBlonDPP, ullon,ullat,lrlon,lrlat));
                }

            }
        }
        return nodes;
    }




    //return all images corresponding to the qualified nodes
    public String[][] getImages(ArrayList<Node> nodes){
        double firstULlat = nodes.get(0).upLeftLatitude;
        int col = 0;
        for(Node node: nodes){
            if(node.upLeftLatitude == firstULlat){
                col += 1;
            }
        }
        int row = nodes.size() / col;

        //[[img/13.png, img/14.png, img/23.png, img/24.png],
        //[img/31.png, img/32.png, img/41.png, img/42.png],
        //[img/33.png, img/34.png, img/43.png, img/44.png]]


        //2D array, [][] first bracket stands for the number of 1D array which equals to the number of rows,
        //second stands for the number of element of the 1D array which equals to the number of cols.
        String[][] images = new String[row][col];

        int count = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                images[i][j] = nodes.get(count).getImgFile();
                count += 1;
            }
        }
        return images;

    }



    //given the query box params, return the RasteredImages
    public RasteredImages getRasteredImages(double ullon, double ullat, double lrlon, double lrlat,double width, double height){

        double queryBoxLonDPP = (lrlon - ullon) / width;

        ArrayList<Node> nodes = getNodes(ullon, ullat, lrlon, lrlat,queryBoxLonDPP);
        String[][] images = getImages(nodes);

        Node UL = nodes.get(0);
        Node LR = nodes.get(nodes.size() - 1);

        return new RasteredImages(UL.upLeftLongitude, UL.upLeftLatitude, LR.lowerRightLongitude, LR.lowerRightLatitude,images);
    }




    /**
     * insert usually starts at root, and if you insert Node 1234, usually means Node 123 is already there(its not null)
     * inert(root,a,c,v,d,1234)
     * fistDigit = 1
     * num = 234
     *
     * root.NW = insert(root.NW,a,a,a,a,234,"1234"
     * >>>>>>>>>>firstDigit = 2
     *           num = 34
     *           root.NW.NE = insert(root.NW.NE, ullon, ullat, lrlon, lrlat, 34, "!234");
     *           >>>>>>>>>>>>>firstDigit = 3
     *                        num = 4
     *                        root.NW.NE.SW=insert(root.NW.NE.SW, ullon, ullat, lrlon, lrlat, 4, "!234");
     *                        >>>>>>>>>>>>>>>firstDigit = 4
     *                                       num = 4
     *                                       root.NW.NE.SW.SE = insert(root.NW.NE.SW, ullon, ullat, lrlon, lrlat, 4, "!234");
     *                                                        = new Node( ullon, ullat, lrlon, lrlat,"1234")
     *                            return root.NW.NE.SW.SE<<<<<<
     *                       return root.NW.NE.SW <<<<<
     *                  return root.NW.NE <<<<<
     *              return root.NW <<<<
     *          return root<<<<
     *
     *
     */


    public void insert(double ulX, double ulY, double lrX, double lrY, String imgFile){
        root = insert(root,ulX,ulY,lrX,lrY,Integer.parseInt(imgFile),imgFile);
    }


    private Node insert(Node h,double ulX, double ulY, double lrX, double lrY,int num, String imgFile) {
        if (h == null || num <= 0) {
            return new Node(ulX, ulY, lrX, lrY, imgFile);
        } else {
            int quadrant = num;
            while (quadrant >= 10) {
                quadrant /= 10;
            }

            //String substring(int beginIndex): Returns the substring starting from the specified
            // index(beginIndex) till the end of the string.
            // For e.g. "Chaitanya".substring(2) would return "aitanya".

            if (num >= 10) {
                String s = Integer.toString(num);
                num = Integer.parseInt(s.substring(1));
            }

            if(quadrant == 1){
                h.UL = insert(h.UL,ulX,ulY,(ulX+lrX)/2,(ulY+lrY)/2,num,imgFile);
            }else if(quadrant == 2){
                h.UR = insert(h.UR,(ulX+lrX)/2,ulY,lrX,(ulY+lrY)/2,num,imgFile);
            }else if(quadrant == 3){
                h.LL = insert(h.LL,ulX,(ulY+lrY)/2,(ulX+lrX)/2,lrY,num,imgFile);
            }else if(quadrant == 4){
                h.LR = insert(h.LR,(ulX+lrX)/2,(ulY+lrY)/2,lrX,lrY,num,imgFile);
            }
        }
        return h;
    }


    private class Node implements Comparable<Node> {
        double upLeftLongitude;
        double upLeftLatitude;
        double lowerRightLongitude;
        double lowerRightLatitude;


        Node UL;
        Node UR;
        Node LL;
        Node LR;

        String imgFile;

        /**
         * ullon = x1;
         * ullat = y1;
         * lrlon = x2;
         * lrlat = y2;
         *
         * @param ullon
         * @param ullat
         * @param lrlon
         * @param lrlat
         * @param value
         */

        Node(double ullon, double ullat, double lrlon, double lrlat, String value) {
            this.upLeftLongitude = ullon;
            this.upLeftLatitude = ullat;
            this.lowerRightLongitude = lrlon;
            this.lowerRightLatitude = lrlat;
            this.imgFile = value;
        }

        public ArrayList<Node> getChildren() {
            ArrayList<Node> children = new ArrayList<>();
            if (UL != null) {
                children.add(UL);
            }
            if (UR != null) {
                children.add(UR);
            }
            if (LL != null) {
                children.add(LL);
            }
            if (LR != null) {
                children.add(LR);
            }
            return children;

        }

        public double getLonDPP() {
            return (this.lowerRightLongitude - this.upLeftLongitude) / MapServer.TILE_SIZE;
        }


        public boolean intersectsQueryBox(double query_ulX, double query_ulY, double query_lrX, double query_lrY) {

            if (this.upLeftLongitude > query_lrX || this.upLeftLatitude < query_lrY ||
                    this.lowerRightLongitude < query_ulX || this.lowerRightLatitude > query_ulY) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public int compareTo(Node o) {
            if (this.upLeftLatitude == o.upLeftLatitude) {
                if (this.upLeftLongitude < o.upLeftLongitude) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (this.upLeftLatitude > o.upLeftLatitude) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        public String getImgFile() {
            return this.imgFile;
        }

    }
}

