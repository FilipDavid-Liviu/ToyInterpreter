package model.adt;

import java.util.List;
public interface IMyTree<T>{
    public void inorderTraversal(List<T> list, Node<T> node);
    public boolean isEmpty();
    public void removeRoot();
}
