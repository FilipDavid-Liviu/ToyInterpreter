package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyTree<T> implements IMyTree<T>{
    private Node<T> root;

    public MyTree() {
        root=null;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void removeRoot() {
        root = root.right;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public void inorderTraversal(List<T> list, Node<T> node) {
        if (node == null) return ;
        inorderTraversal(list, node.left);
        list.add((T) node.value);
        inorderTraversal(list, node.right);
    }

    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public String toString() {
        List<T> list = new ArrayList<T>();
        inorderTraversal(list, root);
        return list.toString();
    }
}
