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

    public boolean find(List x, List y){
        // TODO: Implement the logic here
        if(x == null | y == null){
            throw new IllegalArgumentException();
        }
        else{
            if((y.size() > x.size())){
                if (x.size() == 0) {
                    return true;
                }
                else {
                    Boolean rs[] = new Boolean[x.size()];
                    for(int i = 0; i < x.size(); i++){
                        rs[i]=false;
                        for (int j = 0; j < y.size(); j++){
                            if(y.get(j) == x.get(i)){
                                y = y.subList(j+1,y.size());
                                rs[i] = true;
                                break;
                            }
                        }
                    }
                    boolean rst = true;
                    for(int i = 0; i < x.size(); i++){
                        rst = rst&rs[i];
                    }
                    return rst;
                }
            }
            else {
                if(x.size() == y.size()){
                    return true;
                }
                return false;
            }
        }
    }
}
