package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sets.BSTSet;

import java.util.ArrayList;
import java.util.Arrays;

class BSTSetTest {

    @Test
    void addAndContain() {
        BSTSet<Integer> set = new BSTSet<>();
        set.add(1);
        set.add(2);
        set.add(4);
        set.add(5);
        set.add(-1);
        set.add(-2);
        set.add(-2);
        assert (set.contains(2));
        assert (set.contains(5));
        assert (set.contains(-2));
        assert (!set.contains(-3));
        assert (!set.contains(7));
        assert (!set.contains(0));
    }

    @Test
    void removeElement() {
        BSTSet<Integer> set = new BSTSet<>();
        set.add(4);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(6);
        set.add(5);
        set.add(7);
        set.remove(4);
        assert (!set.contains(4));
        set.remove(7);
        assert (!set.contains(7));
        assert (set.contains(1));
        assert (set.contains(2));
        assert (set.contains(3));
        assert (set.contains(5));
        assert (set.contains(6));
    }

    @Test
    void setXSet(){
        ArrayList<Integer> l1 = new ArrayList<>();
        ArrayList<Integer> l2 = new ArrayList<>();
        ArrayList<Integer> l3 = new ArrayList<>();
        for(int i = 0; i < 16; i++) l1.add(i);
        for(int i = 8; i < 24; i++) l2.add(i);
        for(int i = 0; i < 24; i+=2) l3.add(i);
        BSTSet<Integer> set1 = new BSTSet<>(l1);
        BSTSet<Integer> set2 = new BSTSet<>(l2);
        BSTSet<Integer> set3 = new BSTSet<>(l3);

        ArrayList<Integer> intersect12 = new ArrayList<>();
        for(int i = 8; i < 16; i++) intersect12.add(i);
        BSTSet<Integer> setIntersect = new BSTSet<>(intersect12);
        assert (setIntersect.toString().equals(set1.intersect(set2).toString()));

        ArrayList<Integer> sum12 = new ArrayList<>();
        for(int i = 0; i < 24; i++) sum12.add(i);
        BSTSet<Integer> setSum = new BSTSet<>(sum12);
        assert (setSum.toString().equals(set1.sum(set2).toString()));

        ArrayList<Integer> diff12_3 = new ArrayList<>();
        for(int i = 1; i < 24; i+=2) diff12_3.add(i);
        BSTSet<Integer> setDiff = new BSTSet<>(diff12_3);
        assert (setDiff.toString().equals(set1.sum(set2).difference(set3).toString()));

    }

    //Poniżej esty od chatGTP, bo czemu nie automatyzować testerki:
    private BSTSet<Integer> of(Integer... values) {
        return new BSTSet<>(new ArrayList<>(Arrays.asList(values)));
    }

    @Test
    void testEmptySet() {
        BSTSet<Integer> set = new BSTSet<>();
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
        assertFalse(set.contains(42));
    }

    @Test
    void testAddSingleElements() {
        BSTSet<Integer> set = new BSTSet<>();
        set.add(5);
        set.add(3);
        set.add(5); // duplicate
        assertTrue(set.contains(3));
        assertTrue(set.contains(5));
        assertFalse(set.contains(1));
        assertEquals(2, set.size());
    }

    @Test
    void testAddSet() {
        BSTSet<Integer> a = of(1, 2, 3);
        BSTSet<Integer> b = of(3, 4, 5);
        BSTSet<Integer> result = a.sum(b);
        assertEquals(of(1, 2, 3, 4, 5).toString(), result.toString());
    }

    @Test
    void testRemoveElement() {
        BSTSet<Integer> set = of(1, 2, 3, 4, 5);
        set.remove(3);
        assertFalse(set.contains(3));
        assertEquals(4, set.size());
        set.remove(10); // non-existent
        assertEquals(4, set.size());
    }

    @Test
    void testRemoveSet() {
        BSTSet<Integer> a = of(1, 2, 3, 4, 5);
        BSTSet<Integer> b = of(2, 4);
        BSTSet<Integer> result = a.difference(b);
        assertEquals(of(1, 3, 5).toString(), result.toString());
    }

    @Test
    void testSum() {
        BSTSet<Integer> a = of(1, 2, 3);
        BSTSet<Integer> b = of(3, 4, 5);
        assertEquals(of(1, 2, 3, 4, 5).toString(), a.sum(b).toString());
    }

    @Test
    void testDifference() {
        BSTSet<Integer> a = of(1, 2, 3, 4);
        BSTSet<Integer> b = of(2, 4, 5);
        assertEquals(of(1, 3).toString(), a.difference(b).toString());
    }

    @Test
    void testIntersect() {
        BSTSet<Integer> a = of(1, 2, 3, 4);
        BSTSet<Integer> b = of(3, 4, 5, 6);
        assertEquals(of(3, 4).toString(), a.intersect(b).toString());
    }

    @Test
    void testSize() {
        BSTSet<Integer> set = of(1, 2, 3, 4, 5);
        assertEquals(5, set.size());
    }


    @Test
    void testChainedOperations() {
        BSTSet<Integer> a = of(1, 2, 3, 4);
        BSTSet<Integer> b = of(3, 4, 5);
        BSTSet<Integer> c = of(5, 6);

        // ((a + b) - c) ∩ a = {1,2, 3, 4}
        BSTSet<Integer> result = a.sum(b).difference(c).intersect(a);
        assertEquals(of(1, 2, 3, 4).toString(), result.toString());
    }

    @Test
    void testAddAndRemoveAll() {
        BSTSet<Integer> set = new BSTSet<>();
        for (int i = 0; i < 10; i++) set.add(i);
        for (int i = 0; i < 10; i++) set.remove(i);
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
    }

    @Test
    void testAddRemoveEdgeCases() {
        BSTSet<Integer> set = of(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
        assertTrue(set.contains(Integer.MIN_VALUE));
        assertTrue(set.contains(0));
        assertTrue(set.contains(Integer.MAX_VALUE));

        set.remove(Integer.MIN_VALUE);
        assertFalse(set.contains(Integer.MIN_VALUE));
        assertEquals(2, set.size());
    }
}