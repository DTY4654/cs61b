package hw3.hash;
import java.util.ArrayList;
import java.util.List;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        /* After getting your simpleOomages to spread out
           nicely, be sure to try
           scale = 0.5, N = 2000, M = 100. */

        double scale = 1.0;
        int N = 100;
        int M = 10;

//        double scale = 0.5;
//        int N = 2000;
//        int M = 100;

        HashTableDrawingUtility.setScale(scale);
        List<hw3.hash.Oomage> oomies = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(hw3.hash.SimpleOomage.randomSimpleOomage());
        }
        visualize(oomies, M, scale);


//        List<hw3.hash.Oomage> complexOos = new ArrayList<>();
//        for( int i = 0; i< N; i++ ) {
//            complexOos.add(hw3.hash.ComplexOomage.randomComplexOomage());
//        }
//        visualize(complexOos, M, scale);


        /*

        For example, if we have a SimpleOomage called someOomage, and it is in position number 3 of bucket number 9 out of 16
        buckets, then xCoord(3) would give us the desired x coordinate and yCoord(9, 16) would give us the desired y coordinate.
        Thus, we'd call someOomage.draw(xCoord(3), yCoord(9, 16), scale) to visualize the SimpleOomage as it appears in the
        hash table with the scaling factor scale.

        One potential ambiguity is how to map hash codes to bucket numbers. While there are many ways to do this,
        we'll use the technique from the optional textbook, where we calculate (hashCode & 0x7FFFFFFF) % M.
        You should not use Math.abs(hashCode) % M.

        In case you're curious, & 0x7FFFFFFF throws away the top bit of a number.

         */


    }

    public static void visualize(List<hw3.hash.Oomage> oomages, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);
//        int[] numInBucket = new int[M];
        int[] buckets = new int[M];
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFF) % M;
            double x = HashTableDrawingUtility.xCoord(buckets[bucketNumber]);
            buckets[bucketNumber] += 1;
            double y = HashTableDrawingUtility.yCoord(bucketNumber, M);
            s.draw(x, y, scale);
        }
    }
}
