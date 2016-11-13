package java5.generics.basicsgenerics;

/**
 * @author Tushar Chokshi @ 8/28/15.
 */

//https://docs.oracle.com/javase/tutorial/java/generics/index.html

public class GenericsBasics {
    public static void main(String[] args) {
        // Type Erasure and Bridge Method explanation
        MyNode myNode = new MyNode(5);
        myNode.setData(10);

        Node node = myNode;
        node.setData("hi");  // RuntimeException - ClassCastException. At runtime it calls setData(Object data)
    }

    static class Node<T> {// at the time of compilation it is turned to 'static class Node'

        public T data; // at the time of compilation it is turned to 'Object data'

        public Node(T data) {// at the time of compilation it is turned to 'public Node(Object data)'
            this.data = data;
        }

        public void setData(T data) { // at the time of compilation it is turned to setData(Object data)
            System.out.println("Node.setData");
            this.data = data;
        }

        public void setTitle(String title) {

        }
    }

    static class MyNode extends Node<Integer> {
        public MyNode(Integer data) {
            super(data);
        }

        // at the time of compilation this  BRIDGE method is added
        /*@Override
        public void setData(Object data) {
            setData((Integer) data);
        }*/

        // You can override even though parameter type is different compare to its parent method. How is it possible?- Answer is bridge method
        @Override
        public void setData(Integer data) {
            System.out.println("MyNode.setData");
            super.setData(data);
        }

        //@Override // cant override because parameter types are different
        public void setTitle(Integer title) {

        }
    }
}
