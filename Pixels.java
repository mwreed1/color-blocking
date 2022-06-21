import java.util.*;
import java.util.stream.Collectors;

public class Pixels{
    public static Distance d = new Distance();
    public static ArrayList<int[]> initCent(ArrayList<int[]> pixelArray, int k){
    ArrayList<int[]> intialCentroids =  new ArrayList<int[]>(pixelArray.subList(0, k-8));
        intialCentroids.add(new int[]{0,0,0});
        intialCentroids.add(new int[]{255,255,255});
        intialCentroids.add(new int[]{0,255,0});
        intialCentroids.add(new int[]{0,0,255});
        intialCentroids.add(new int[]{255,0,0});
        intialCentroids.add(new int[]{255,255,0});
        intialCentroids.add(new int[]{255,0,255});
        intialCentroids.add(new int[]{0,255,255});
        return(intialCentroids);
    }
    
    public static ArrayList<ArrayList<Integer>> voronoi(ArrayList<int[]> pixelArray, int k, ArrayList<int[]> centroids){
        int n = pixelArray.size();
        Map<Integer, ArrayList> newClusters = new HashMap<Integer, ArrayList>();
        for(int i = 0; i < n; i++){
            double best_dist = 100000000.0;
            int[] cur = pixelArray.get(i);
            int best_cent = -1;
            for(int j = 0; j < k; j++){
                int[] centroid = centroids.get(j);
                double dist = d.distance(cur, centroid);
                if(dist < best_dist){
                    best_dist = dist;
                    best_cent = j;
                }
            }
            ArrayList<Integer> current = new ArrayList<Integer>();      
            if(newClusters.containsKey(best_cent)) {
                current = newClusters.get(best_cent);
            }
            current.add(i);
            newClusters.put(best_cent, current);
        }
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        for(int m = 0; m < k; m++){
            ret.add(newClusters.get(m));
        }
        return(ret);
    
}

public static ArrayList<int[]> findCentroids(ArrayList<ArrayList<Integer>> clusters, int k, ArrayList<int[]> pixelArray){
    int n = clusters.size();
    ArrayList<int[]> centroids = new ArrayList<int[]>();
    for(int i = 0; i < n; i++){
        int[] centroid = new int[3];
        if(clusters.get(i) != null){
            ArrayList<Integer> cluster = new ArrayList<Integer>(clusters.get(i));
            int m = cluster.size();
            int red_tot = 0;
            int green_tot = 0;
            int blue_tot = 0;
            for(int j = 0; j < m; j++){
                int[] pixel = pixelArray.get(cluster.get(j));
                red_tot += pixel[0];
                green_tot += pixel[1];
                blue_tot += pixel[2];
            }
            centroid = new int[]{red_tot/m, green_tot/m, blue_tot/m};
        }
        else{
            Random rd = new Random();
            centroid = new int[]{rd.nextInt(256), rd.nextInt(256), rd.nextInt(256)};
        }
        centroids.add(centroid);
    }
    return(centroids);
}

public static int[][] kmeans(int k, ArrayList<int[]> pixelArray, int it){
    ArrayList<int[]> cents = initCent(pixelArray, k);
    ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
    int[][] ret = new int[pixelArray.size()][3];
    for(int i = 0; i < it; i ++){
        clusters = voronoi(pixelArray, k, cents);
        cents = findCentroids(clusters, k, pixelArray);
    }
    for(int j = 0; j < cents.size(); j++){
        ArrayList<Integer> l1 = new ArrayList<Integer>();
        if(clusters.get(j) != null){
            l1 = clusters.get(j);
        }
        int[] color = cents.get(j);
        if(cents.get(j) == null){
            color = new int[]{0,0,0};
        }
        for(int m = 0; m < l1.size(); m++){
            ret[l1.get(m)] = color;
        }
    }
    return(ret);
}
    
}