/**
 * Created by Marshal on 4/12/15.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
public class Node {
    int n=0;                  //The number n of basic terms in the corresponding subset
    double p=1;               //the product p of the selectivities of all terms in the subset
    boolean b=false;        //whether the no-branch optimization was used to get the best cost, initialized to 0
    double c=Integer.MAX_VALUE;                  //current best cost c for the subset
    Node L=null,R=null;     //the left child L and right child R of the subplans giving the best cost
    int[] bitmap;
    ArrayList<Node> result = new ArrayList<Node>();

    //Constructor
    public Node(){

    }

    //return the left most leave node of the tree
    //in support of the c_metric comparison
    public Node leftMostLeave(){
        Node temp = this;
        while(temp.L!=null){
            temp=temp.L;
        }
        return temp;
    }

    //Convert the Node into printable format
    public String toString(){
        String result="";
        //if the node is empty
        if(this.c==Integer.MAX_VALUE){
            return " ";
        }
        //match the bitmap to &-term
        for(int i=0;i<this.bitmap.length;i++) {
            if(this.bitmap[i]==1)
                result = result + "t"+(i+1)+"[o"+(i+1)+"[i]]&";
        }
        //get ride of the last "&" term
        result=result.substring(0,result.length()-1);

        if(this.n>1){
            result="("+result+")";
        }

        return result;
    }

    //return all the leave nodes for a particular node
    public ArrayList<Node> allLeave(){
        result = new ArrayList<Node>();
        getLeave(this);
        return result;
    }
    //supporting function for get leave node
    private void getLeave(Node node){
        if(node==null)
            return;
        if(node.L==null&&node.R==null)
            result.add(node);

        getLeave(node.L);
        getLeave(node.R);

    }

}
