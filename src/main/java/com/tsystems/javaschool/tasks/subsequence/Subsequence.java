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
        int check = 0;
        int j = 0;

        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        outer:
        for(Object x1 : x) {
            for (;j < y.size(); j ++) {
                if(x1.equals(y.get(j))) {
                    check ++;
                    continue outer;
                }
            }
        }
        return check == x.size()? true : false;
    }
}
