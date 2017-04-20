package lab8;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by GritShiva on 2017/4/19 0019.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{


    private Node root;

    private class Node{
        private K key;
        private V value;
        private Node left, right;
        private int N;

        public Node(K key, V value, int N){
            this.key = key;
            this.value = value;
            this.N = N;
            this.left = null;
            this.right = null;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if(key == null){
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }

    private V get(Node x, K key){
        if(key == null){
            throw new IllegalArgumentException("called get() with a null key");
        }
        if(x == null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0 ){
            return get(x.left,key);
        }else if(cmp > 0){
            return get(x.right, key);
        }else{
            return x.value;
        }
    }



    @Override
    public int size() {
        return size(root);
    }


    private int size(Node x){
        if(x == null){
            return 0;
        }else {
            return x.N;
        }
    }


    @Override
    public void put(K key, V value) {
        root = put(root,key,value);
    }


    private Node put(Node x, K key, V value){
        if(x == null){
            return new Node(key,value,1);
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.left = put(x.left,key,value);
        }else if( cmp > 0){
            x.right = put(x.right,key,value);
        }else{
            x.value = value;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


    // Your BSTMap should also add an additional method printInOrder() (not given in the Map61B interface)
    // that prints out your BSTMap in order of increasing Key.
    // You will find this helpful for testing your implementation!


    public void printInOrder() {
        printInOrderHelper(root);
    }


    //不是很会写recursion, 查一下有没有什么通用的方法再多加练习
    private void printInOrderHelper(Node x){
        if (x == null){
            System.out.println("");
        }else if(x.left == null && x.right == null){
            System.out.println(x.key +"," + x.value);
        }else if(x.left != null && x.right == null){
            printInOrderHelper(x.left);
            System.out.println(x.key +"," + x.value);
        }else if(x.left == null & x.right != null){
            System.out.println(x.key +"," + x.value);
            printInOrderHelper(x.right);
        }else{
            printInOrderHelper(x.left);
            System.out.println(x.key +"," + x.value);
            printInOrderHelper(x.right);
        }
    }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi0", 1);
        b.put("hi3", 1);
        b.put("hi1", 1);
        b.put("hi2", 1);
        b.printInOrder();
    }
}
