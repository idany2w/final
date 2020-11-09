package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) throw new IllegalArgumentException();
        if (x.size() > y.size()) return false;

        int prevIndex = -2;

        for (Object o : x) {
            int index = y.indexOf(o);
            if (index == -1) return false;
            if (prevIndex > index) {
                index = y.lastIndexOf(o);
                if (prevIndex > index) {
                    return false;
                }
            }
            prevIndex = index;
        }
        return true;
    }
}
