import java.util.LinkedList;

/**
 * Created by Marshal on 4/14/15.
 */

public class Queue {
    LinkedList<Node> head;

    Queue(){
        head = new LinkedList<Node>();
    }
    public void push(Node node){
        if(head==null)
            head=new LinkedList<Node>();
        head.push(node);
    }
    public Node pop(Node node){
        return head.pop();

    }


}
