/**
 * Created by Marshal on 4/13/15.
 */
public class Pair {
    double q;
    double p;

    //constructor
    public Pair(){
    }

    public Pair(double a, double b){
        this.q=a;
        this.p=b;
    }

    //return comparison of two pairs
    //only return true if both value stored in A is greater than B
    public static boolean dominate(Pair A, Pair B){
        if(A.q>B.q&&A.p>B.p)
            return true;
        return false;
    }
}
