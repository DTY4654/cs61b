package lab9;

import java.util.*;

/**
 * Created by GritShiva on 2017/4/22 0022.
 */
public class MyHashMap<K,V> implements Map61B<K,V> {

    private ArrayList<Entry<K,V>> buckets;
    private int size;
    private float loadFactor;
    private HashSet<K> kHashSet;


    private static class Entry<K,V> implements Map.Entry<K,V> {

        K key;
        V value;
        Entry<K,V> next;

        Entry(K key, V value, Entry<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey () {
            return key;
        }

        public V getValue () {
            return value;
        }

        public V setValue (V x) {
            V old = value; value = x; return old;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?, ?> entry = (Entry<?, ?>) o;

            if (key != null ? !key.equals(entry.key) : entry.key != null) return false;
            if (value != null ? !value.equals(entry.value) : entry.value != null) return false;
            return next != null ? next.equals(entry.next) : entry.next == null;
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }


    public MyHashMap() {
        this(127, 0.75);
    }


    public MyHashMap(int initialSize){
        this(initialSize, 0.75);
    }


    public MyHashMap(int initialSize, double loadFactor){
        if(initialSize < 1 || loadFactor <= 0.0){
            throw new IllegalArgumentException();
        }
        buckets = new ArrayList<Entry<K,V>>(initialSize);
        buckets.addAll(Collections.nCopies(initialSize, null));
        this.size = 0;
        this.loadFactor = (float) loadFactor;
    }


    private void resize(int cap){
        MyHashMap<K,V> newMap = new MyHashMap<>(cap);
        for(Entry list: buckets){
           newMap.buckets.add(list);
        }
    }

    @Override
    public void clear() {
        for(int i = 0; i < buckets.size(); i ++){
            buckets.set(i, null);
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }

        return get(key) != null;
    }




    @Override
    public Object get(Object key) {
        Entry e = find(key, buckets.get(hash(key)));
        return ( e == null) ? null : e.value;
    }


    private Entry<K,V> find(Object key, Entry<K,V> bucket){
        for(Entry<K,V> e = bucket; e!= null; e = e.next){
            if(key == null && e.key == null || key.equals(e.key)){
                return e;
            }
        }
        return null;
    }

    private int hash(Object key){
        return (key == null) ? 0
                : (0x7fffffff & key.hashCode ()) % buckets.size();
       // return (key.hashCode() & 0x7fffffff) & buckets.size();
    }




    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(Object key, Object value) {
        int h = hash(key);
        Entry<K,V> e = find(key, buckets.get(h));
        if (e == null){
            buckets.set(h, new Entry<>((K) key, (V) value, buckets.get(h)));
            size += 1;
            if(size > buckets.size() * loadFactor){
                resize(2*buckets.size());
            }
        }else{
            e.setValue((V)value);
        }

    }


    //Note that you should implement keySet and iterator this time. For these methods, we recommend you
    //simply create a HashSet instance variable that holds all your keys.

    @Override
    public Set keySet() {
        
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        this.kHashSet = new HashSet<K>();
        for(int i = 0; i < buckets.size(); i++){
            kHashSet.add(buckets.get(i).key);
        }
        return kHashSet.iterator();
    }
}
