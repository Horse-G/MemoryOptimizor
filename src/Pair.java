//Used for checking domination conditions in the case od c and d metrics

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
    //only return true if both value stored in A is less than B
    public static boolean dominate(Pair A, Pair B){
        if(A.q<B.q&&A.p<B.p)
            return true;
        return false;
    }
}
