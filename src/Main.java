import sun.awt.image.ImageWatched;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    static int r=1,t=2,l=1,m=16,a=2,f=4;    //cost parameters
    static double[] S;
    static int k;              //cardinality of S
    static Node[][] matrix;    //
    static Node[] A=new Node[5];

    public static void main(String[] args) {


        String configFile = "config.txt";
        String queryFile = "query.txt";
        //init(configFile);

        //read in input file
        try{
            File query = new File(queryFile);
            Scanner in = new Scanner(query);

            while(in.hasNextLine()){
                String[] buffer = in.nextLine().split("\\s+");
                k = buffer.length;
                S=new double[k];
                for(int i=0;i<k;i++){
                    S[i]=Double.parseDouble(buffer[i]);
                }

                //initialize array A
                A = new Node[(int)Math.pow(2,k)];
                for(int i=0;i<A.length;i++){
                    A[i]=new Node();
                    A[i].bitmap=toBinary(i);
                    for(int j=0;j<k;j++){
                        if(A[i].bitmap[j]==1){
                            A[i].n++;
                            A[i].p*=S[j];
                        }
                    }
                    A[i].c=logicalAndCostFunction(A[i]);
                    if(noBranchCostFunction(A[i])<A[i].c){
                        A[i].c=noBranchCostFunction(A[i]);
                        A[i].b=true;
                    }
                }
                //System.out.println("Step one result:=============");
                //printNodeArray();

                //second step update
                for(int i=0;i<A.length;i++){
                    //i is the corresponding s in the algorithm
                    for(int j=0;j<A.length;j++){
                        //j is the corresponding s' in the algorithm
                        //make sure there is no intersection between two set
                        if(intersect(A[i].bitmap, A[j].bitmap))
                            continue;
                        if(Pair.dominate(c_matrix(A[i].leftMostLeave()), c_matrix(A[j]))){
                            //do nothing according to the algorithm
                        }else{

                            if(A[j].p<=0.5&&compareLeave(A[j],A[i])){
                                //compareLeave function take (s',s)
                                //do nothing according to the algorithm
                            }else{
                                double cost = fcost(A[j])+m*Math.min(A[j].p,1-A[j].p)+A[j].p*A[i].c;
                                int index = findUnionIndex(A[i],A[j]);
                                if(cost<A[index].c){
                                    A[index].c=cost;
                                    A[index].L=A[j];
                                    A[index].R=A[i];
                                }
                            }
                        }

                    }

                }
                //System.out.println("Second Step Result:=============");
                printNodeArray();
                //System.out.println("Final Node: ****************");
                printNode(A[A.length - 1]);
                //printNode();

                getResult(A[A.length - 1]);


            }
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
        }




    }

    //supporting functions group
    private static int findUnionIndex(Node A, Node B){
        int[] result = new int[A.bitmap.length];
        for(int i=0;i<result.length;i++){
            if(A.bitmap[i]==1||B.bitmap[i]==1)
                result[i]=1;
        }
        return toDecimal(result);
    }

    private static boolean compareLeave(Node A, Node B){
        //return true if any leave node of B is dominating node A
        ArrayList<Node> leaveNode = B.allLeave();
        for(Node a: leaveNode){
            if(Pair.dominate(d_matrix(a),d_matrix(A)))
                return true;
        }
        return false;
    }

    private static void getResult(Node node){
        LinkedList<Node> result = new LinkedList<Node>();
        if(node.L==null&&node.R==null){
            result.push(node);
            resultString(result);
            return;
        }

        result.push(node.L);
        result.push(new Node());
        result.push(node.R);

        while(result.peek().L!=null||result.peek().R!=null){
            Node temp = result.pop();
            result.push(temp.L);
            result.push(new Node());
            result.push(temp.R);
        }
       //printResult(result);
        resultString(result);
    }

    private static String resultString(LinkedList<Node> queue){
        String result;
        if(queue.peek().b){
            //System.out.println(queue.peek().toString()+ "is no branching");
            String NoBranching = queue.pop().toString();
            System.out.println("No Branching: " + NoBranching);
            if(queue.peek().n==0){
                queue.pop();
            }
        }

        result = queue.pop().toString();
        while(!queue.isEmpty()){
            if(queue.peek().n==0){
                queue.pop();
                result="("+queue.pop().toString()+"&&"+result+")";
            }
        }
        System.out.println(result);
        return result;

    }

    private static void printResult(LinkedList<Node> result){
        System.out.println("Result Queue"+result.size());
        int size = result.size();
        for(int i=0;i<size;i++) {
            System.out.println("Node " + i);
            if (result.get(size-1-i).n == 0) {
                System.out.println("&&");
            } else {
                printNode(result.get(size-1-i));
            }
        }

    }

    //operator functions group
    private static int toDecimal(int[] binary){
        int result=0;
        for(int i=0;i<binary.length;i++){
            if(binary[i]==1)
                result+=Math.pow(2,binary.length-i-1);
        }
        return result;
    }

    private static int[] toBinary(int n){
        int[] result = new int[k];
        int s,i=0;
        while(n>0){
            s=n%2;
            n=n>>1;
            result[i++]=s;
        }
        int temp;
        for(int j=0;j<k/2;j++){
            temp=result[j];
            result[j]=result[k-1-j];
            result[k-1-j]=temp;
        }
        return result;
    }

    private static boolean intersect(int[] A, int[] B){
        //return true if A and B indeed intersect with each other
        for(int i=0;i<A.length;i++){
            if((A[i]&B[i])==1)
                return true;
        }
        return false;
    }


    //cost functions group
    private static double noBranchCostFunction(Node node){
        //n is the "k", the number of terms stored in the node,
        //or the "k basic terms" in the algorithm
        double cost=0;
        cost = node.n*r + (node.n-1)*l + node.n*f + a;
        return cost;
    }

    private static double logicalAndCostFunction(Node node){
        double cost=0, q=0;
        q = (node.p<=0.5)? node.p : 1-node.p;
        cost = node.n*r+(node.n-1)*l + node.n*f + t+ m*q + node.p*a;
        return cost;
    }

    private static Pair c_matrix(Node node){
        Pair result = new Pair((1-node.p)/fcost(node),node.p);
        return result;
    }

    private static Pair d_matrix(Node node){
        Pair result = new Pair(fcost(node),node.p);
        return result;
    }

    private static double fcost(Node node){
/*
        if(node.L!=null||node.R!=null){
            System.out.println("error: node is not a &-term!");
            return 0.0;
        }
*/
        double result = node.n*r+(node.n-1)*l+node.n*f+t;
        return result;

    }


    //assign cost parameters from configuration file
    private static void init(String configFile){
        File config = new File(configFile);
        //matching the cost parameters with those in configuration file

    }

    private static void printNodeArray(){
        //print Node
        for(int i=0;i<A.length;i++){
            System.out.println("======Node"+i+" ==========");
            printNode(A[i]);
        }
        System.out.println("*************************************");
    }

    private static void printNode(Node node){
        if(node==null)
            return;
        //System.out.println("======Node==========");
        for(int j=0;j<node.bitmap.length;j++){
            System.out.print(node.bitmap[j]);
        }
        System.out.println();
        System.out.println("n; "+node.n);
        System.out.println("c: "+node.c);
        System.out.println("b: "+ node.b);
        System.out.println("p: "+node.p);

        if(node.L!=null) {
            System.out.println("LeftNode: ");
            for (int j = 0; j < node.L.bitmap.length; j++) {
                System.out.print(node.L.bitmap[j]);
            }
            System.out.println();
        }

        if(node.R!=null) {
            System.out.println("RightNode: ");
            for (int j = 0; j < node.R.bitmap.length; j++) {
                System.out.print(node.R.bitmap[j]);
            }
            System.out.println();
        }


    }
}
