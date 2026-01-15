package sets;

import java.util.ArrayList;

public class BSTSet<E extends Comparable<E>> {
    private final static Node<Boolean> guard = new Node<>();

    private Node root;
    private int size = 0;

    //Empty constructor
    public BSTSet(){
        root = guard;
    }
    //Constructor of a ballanced tree from a list.
    //The list should be without duplicates and sorted
    //T: O(n) S: O(n)
    public BSTSet(ArrayList<E> in){
        size = in.size();
        root = construct(in, 0, size - 1);
    }

    private Node construct(ArrayList<E> in, int start, int end){
        if(start > end) return guard;
        int middle = (start + end)/2;
        Node<E> out = new Node<>(in.get(middle));
        out.left = construct(in, start, middle - 1);
        out.right = construct(in, middle + 1, end);
        return out;
    }

    //Sum of two sets.
    //T: O(n + m) S: O(n + m)
    public BSTSet<E> sum(BSTSet<E> set){
        ArrayList<E> s1 = new ArrayList<>();
        ArrayList<E> s2 = new ArrayList<>();
        root.inOrderTraversal(s1);
        set.root.inOrderTraversal(s2);
        ArrayList<E> sum = new ArrayList<>();
        int i = 0; int j = 0;
        while(i < s1.size() || j < s2.size()){
            if(i < s1.size() && j < s2.size()){
                if(s1.get(i).compareTo(s2.get(j)) < 0){
                    sum.add(s1.get(i));
                    i++;
                } else if (s1.get(i).compareTo(s2.get(j)) > 0){
                    sum.add(s1.get(j));
                    j++;
                } else {
                    sum.add(s1.get(i));
                    i++; j++;
                }
            } else if(i < s1.size()){
                sum.add(s1.get(i++));
            } else{
                sum.add(s2.get(j++));
            }

        }
        return new BSTSet<>(sum);
    }

    public void add(BSTSet<E> set){
        BSTSet<E> newSet = sum(set);
        this.root = newSet.root;
        this.size = newSet.size;
    }
    //Difference of two sets.
    //T: O(n + m) S: O(n + m)
    public BSTSet<E> difference(BSTSet<E> set){
        ArrayList<E> s1 = new ArrayList<>();
        ArrayList<E> s2 = new ArrayList<>();
        root.inOrderTraversal(s1);
        set.root.inOrderTraversal(s2);
        ArrayList<E> difference = new ArrayList<>();
        int i = 0; int j = 0;
        while(i < s1.size()){
            if(j < s2.size()){
                if(s1.get(i).compareTo(s2.get(j)) < 0){
                    difference.add(s1.get(i));
                    i++;
                } else if (s1.get(i).compareTo(s2.get(j)) > 0){
                    j++;
                } else {
                    i++; j++;
                }
            } else {
                difference.add(s1.get(i++));
            }
        }
        return new BSTSet<>(difference);
    }

    public void remove(BSTSet<E> set){
        BSTSet<E> newSet = difference(set);
        this.root = newSet.root;
        this.size = newSet.size;
    }
    //Intersection of two sets.
    //T: O(n + m) S: O(n + m)
    public BSTSet<E> intersect(BSTSet<E> set){
        ArrayList<E> s1 = new ArrayList<>();
        ArrayList<E> s2 = new ArrayList<>();
        root.inOrderTraversal(s1);
        set.root.inOrderTraversal(s2);
        ArrayList<E> common = new ArrayList<>();
        int i = 0; int j = 0;
        while(i < s1.size() && j < s2.size()){
            if(s1.get(i).compareTo(s2.get(j)) < 0){
                i++;
            } else if (s1.get(i).compareTo(s2.get(j)) > 0){
                j++;
            } else {
                common.add(s1.get(i));
                i++; j++;
            }
        }
        return new BSTSet<>(common);
    }

    public void cut(BSTSet<E> set){
        BSTSet<E> newSet = intersect(set);
        this.root = newSet.root;
        this.size = newSet.size;
    }

    //T: O(log n) S: O(lon n)
    public void add(E element){
        if(this.contains(element)) return;
        size++;
        Node<E> tmp = new Node<>(element);
        if(root.isGuard){
            root = tmp;
            return;
        }
        root.add(tmp);
    }

    //T: O(log n) S: O(lon n)
    public void remove(E element){
        if(!this.contains(element)) return;
        size--;
        if(root.val.equals(element)){
            if(root.right.isGuard){
                root = root.left;
                return;
            }
            if(root.left.isGuard){
                root = root.right;
                return;
            }
            Node<E> tmp = root.right;
            Node<E> tmpParent = root;
            while(!tmp.left.isGuard || !tmp.right.isGuard){
                tmpParent = tmp;
                if(!tmp.left.isGuard) tmp = tmp.left;
                else tmp = tmp.right;
            }
            if(tmpParent.left == tmp) tmpParent.left = guard;
            if(tmpParent.right == tmp) tmpParent.right = guard;
            tmp.left = root.left;
            tmp.right = root.right;
            root = tmp;
            return;
        }
        root.remove(element);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    //T: O(log n) S: O(lon n)
    public boolean contains(E element){
        if(root.isGuard) return false;
        return root.contains(element);
    }

    //String of in order array
    public String toString(){
        if(root.isGuard) return "[]";
        ArrayList<E> inOrder = new ArrayList<>();
        root.inOrderTraversal(inOrder);
        StringBuilder out = new StringBuilder("[");
        for(int i = 0; i < inOrder.size(); i++){
            out.append(inOrder.get(i).toString()).append(", ");
        }
        out.delete(out.length() - 2, out.length());
        out.append("]");
        return out.toString();
    }

    private static class Node<T extends Comparable<T>>{
        private Node left, right;
        private T val;
        final boolean isGuard;
        private Node(){
            val = null;
            isGuard = true;
        }
        private Node(T val){
            isGuard = false;
            this.val = val;
            left = guard;
            right = guard;
        }
        private boolean contains(T element){
            if(val.equals(element)) return true;
            if(val.compareTo(element) > 0){
                return !left.isGuard && left.contains(element);
            }
            return !right.isGuard && right.contains(element);
        }

        private void add(Node<T> node){

            if(val.compareTo(node.val) > 0){
                if(left.isGuard) left = node;
                else left.add(node);
            } else {
                if(right.isGuard) right = node;
                else right.add(node);
            }
        }

        private void remove(T element){
            if(val.compareTo(element) > 0){
                if(left.val.equals(element)) left.remove(this, false);
                else left.remove(element);
            } else if(val.compareTo(element) < 0){
                if(right.val.equals(element)) right.remove(this, true);
                else right.remove(element);
            }
        }

        private void remove(Node<T> parent, boolean dir){ //false = left; true = right
            if(right.isGuard){
                if(dir) parent.right = left;
                else parent.left = left;
                return;
            }
            if(left.isGuard){
                if(dir) parent.right = right;
                else parent.left = right;
                return;
            }
            Node<T> tmpParent = this;
            Node<T> tmp = right;
            while(!tmp.left.isGuard || !tmp.right.isGuard){
                tmpParent = tmp;
                if(!tmp.left.isGuard) tmp = tmp.left;
                else tmp = tmp.right;
            }
            if(tmpParent.left == tmp) tmpParent.left = guard;
            if(tmpParent.right == tmp) tmpParent.right = guard;
            tmp.left = left;
            tmp.right = right;
            if(dir) parent.right = tmp;
            else parent.left = tmp;
        }

        private ArrayList<T> inOrderTraversal(ArrayList<T> out){
            if(!left.isGuard) left.inOrderTraversal(out);
            out.add(val);
            if(!right.isGuard) right.inOrderTraversal(out);
            return out;
        }

        @Override
        public String toString() {
            return "Node{val=" + val +
                    ", isGuard=" + isGuard +
                    '}';
        }
    }
}
