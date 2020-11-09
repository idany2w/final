package com.tsystems.javaschool.tasks.pyramid;

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
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        int [][] result = null;
        if(inputNumbers.contains(null)) throw new CannotBuildPyramidException();

        for (int c = 0, i = 0 ; c <= inputNumbers.size(); c += ++i) {
            if(c == inputNumbers.size()) {
                if(i < 0) throw       new CannotBuildPyramidException();
                result = new int[i][i+i-1];
            }
        }
        if (result == null) throw new CannotBuildPyramidException();

        inputNumbers.sort(Integer::compareTo);
        int inputNumbersIndex = 0;
        int center= ((result[0].length-1)/2);

        for (int i = 0; i < result.length; i++) {
            for (int j = center-i; j <= center+i; j+=2) {
                result[i][j] = inputNumbers.get(inputNumbersIndex++);
            }
        }

        return result;
    }
}
