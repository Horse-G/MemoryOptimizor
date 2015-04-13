import java.io.File;
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

                //print Node
                for(int i=0;i<A.length;i++){
                    System.out.println("======Node"+i+" ==========");
                    for(int j=0;j<A[i].bitmap.length;j++){
                        System.out.print(A[i].bitmap[j]);
                    }
                    System.out.println();
                    System.out.println("n; "+A[i].n);
                    System.out.println("c: "+A[i].c);
                    System.out.println("b: "+ A[i].b);
                    System.out.println("p: "+A[i].p);
                    //System.out.println(A[i].toString());
                }
                System.out.println("*************************************");
                /*
                ArrayList<ArrayList<Double>> result = subset(S);
                int i=0;
                for(ArrayList<Double> a: result){
                    A[i].subset=a;
                    A[i].n=  a.size();

                }
*/

            }
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
        }




    }

    private static double c_matrix(){
        return 0.0;
    }

    private static double d_matrix(){
        return 0.0;
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

    private static ArrayList<ArrayList<Double>> subset(double[] S){
        if (S==null){
            return null;
        }

        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();

        for(int i =0;i<S.length;i++){
            ArrayList<ArrayList<Double>> buffer = new ArrayList<ArrayList<Double>>();

            for(ArrayList<Double>a:result){
                buffer.add(new ArrayList<Double>(a));
            }

            for(ArrayList<Double> a: buffer){
                a.add(S[i]);
            }

            ArrayList<Double> newElement = new ArrayList<Double>();
            newElement.add(S[i]);
            buffer.add(newElement);

        }
        result.add(new ArrayList<Double>());

        return result;

    }
    //assign cost parameters from configuration file
    private static void init(String configFile){
        File config = new File(configFile);
        //matching the cost parameters with those in configuration file

    }
}
