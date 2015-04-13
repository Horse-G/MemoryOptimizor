/**
 * Created by Marshal on 4/12/15.
 */

import java.util.ArrayList;
public class Node {
    int n=0;                  //The number n of basic terms in the corresponding subset
    double p=1;               //the product p of the selectivities of all terms in the subset
    boolean b=false;        //whether the no-branch optimization was used to get the best cost, initialized to 0
    double c=Integer.MAX_VALUE;                  //current best cost c for the subset
    Node L=null,R=null;     //the left child L and right child R of the subplans giving the best cost
    int[] bitmap;
    ArrayList<Double> subset;

    //Constructor
    public Node(ArrayList<Double> incoming){
        this.subset=incoming;
        this.n = incoming.size();
        for(double d:incoming)
            p*=d;

    }

    public Node(){

    }

}
