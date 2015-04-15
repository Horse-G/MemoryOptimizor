/**
 * Created by Marshal on 4/13/15.
 */
public class Pair {
    double q;
    double p;
    public Pair(){

    }

    public Pair(double a, double b){
        this.q=a;
        this.p=b;
    }

    public static boolean dominate(Pair A, Pair B){
        if(A.q>B.q&&A.p>B.p)
            return true;
        return false;
    }
}
