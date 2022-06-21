import java.util.*;
import java.lang.Math;

public class Distance{
    public static double distance(int[] v1, int[] v2){
        int total = 0;
        for(int i = 0; i < 3; i++){
            int dif = v1[i] - v2[i];
            total += Math.pow(dif, 2);
        }
        return(Math.sqrt(total));
    }
}