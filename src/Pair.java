//Used for checking domination conditions in the case od c and d metrics

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
