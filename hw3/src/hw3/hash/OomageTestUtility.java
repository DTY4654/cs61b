package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {

    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        /* TODO: Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        ArrayList<Oomage> [] buckets = new ArrayList[M];

        for( int i = 0; i < M; i++){
            buckets[i] = new ArrayList<Oomage>();
        }

        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFF) % M;
            buckets[bucketNumber].add(s);

        }


        for(int i = 0; i < M; i++){
            if (buckets[i].size() >= oomages.size() / 50 && buckets[i].size() <= oomages.size() / 2.5 ){
                return true;
            }
        }

        return false;

    }
}
