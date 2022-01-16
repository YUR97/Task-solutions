package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        if(!inputNumbers.contains(null)){
            int NumbLines = checkTriangleSize(inputNumbers.size())[0];
            int NumbColumns = checkTriangleSize(inputNumbers.size())[1];
            Collections.sort(inputNumbers);
            int a;
            int integ;
            int jteg;
            int putIter = 0;
            int exampArr[][] = new int[NumbLines][NumbColumns];
            int g = (exampArr[0].length-1)/2;
            List<Integer> NOI = new ArrayList<>();
            exampArr[0][g]=inputNumbers.get(putIter);
            putIter++;
            NOI.add(g);
            for(int i = 0; i < exampArr.length; i++){
                integ = NOI.size();
                a = NOI.get(integ-1);
                NOI.add(a+2);
                for (int j = 0; j < integ+1; j++){
                    jteg = NOI.get(j)-1;
                    NOI.set(j,jteg);
                }
                if (i != 0){
                    for(int k = 0; k < exampArr[0].length; k++){
                        if(NOI.contains(k)){
                            exampArr[i+1][NOI.get(NOI.indexOf(k))]=inputNumbers.get(putIter);
                            putIter++;
                        }
                    }
                }
                else{
                    for(int k = 0; k < exampArr[0].length; k++){
                        if(NOI.contains(k)){
                            exampArr[i+1][NOI.get(NOI.indexOf(k))]=inputNumbers.get(putIter);
                            putIter++;
                        }
                    }
                }
                if (NOI.get(0)==0){
                    break;
                }
            }
            return exampArr;
        }
        else{
            throw new CannotBuildPyramidException();
        }
    }
    public static int [] checkTriangleSize (int collectionSize) throws CannotBuildPyramidException{
        if(collectionSize > 250){
            throw new CannotBuildPyramidException();
        }
        int compare = 0;
        int i = 1;
        int numbersOfLine = 0;
        int numbersOfColumn = 0;
        boolean verdict = false;
        do{
            compare+=i;
            i++;
            numbersOfLine++;
            if(collectionSize == compare){
                verdict = true;
                break;
            }
        }
        while (compare < collectionSize);
        numbersOfColumn = 2*numbersOfLine-1;
        int res[] = {numbersOfLine,numbersOfColumn};
        return res;
    }
}
