/**
 * Created by Marshal on 4/12/15.
 */
public class Node {
    int n;                  //The number n of basic terms in the corresponding subset
    double p;               //the product p of the selectivities of all terms in the subset
    boolean b=false;        //whether the no-branch optimization was used to get the best cost, initialized to 0
    int c;                  //current best cost c for the subset
    Node L=null,R=null;     //the left child L and right child R of the subplans giving the best cost

    //Constructor
    public Node(){

    }

}
