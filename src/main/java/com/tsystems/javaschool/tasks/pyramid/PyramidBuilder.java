package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

        // root of the equation   n^2 + n - Sn = 0 уравнение взял из арифм прогрессии
        double n = (-1 + Math.sqrt(1 + 4 * 2 * inputNumbers.size()))/2;
        int[][] output = new int[0][0];

        if(n == (int) n) {     // если решение уравнения целое то пирмамиду построить сможем

            try{
                Collections.sort(inputNumbers, Comparator.reverseOrder());  // сортируию по убыванию
            } catch (NullPointerException e) {  // ловим неправильные данные
                throw new CannotBuildPyramidException();
            }
            int pyramidHeight = (int)n;
            output = new int[pyramidHeight][pyramidHeight*2 - 1];

            int k = 0;  //определяет до какого момента писать нолики по краям
            int zeros = 0;   // определяет когда внутри пирамиды писать ноль или цифры ( по четности)
            int posInInput = 0;  // по листу Ходим вот этим

            for(int i = pyramidHeight-1; i >= 0; i--){
                zeros = 0;
                for( int j = pyramidHeight*2 - 1 - 1; j >= 0; j --) {


                    if(j <= pyramidHeight*2 - 1 - 1 - k && j >= k) {
                        if(zeros % 2 == 0) {
                            output[i][j] = inputNumbers.get(posInInput);
                            posInInput ++;
                        } else {
                            output[i][j] = 0;
                        }
                        zeros ++;
                    }else{
                        output[i][j] = 0;
                    }


                }
                k ++;   // сужаем место для цифрж
            }
        }else {
            throw new CannotBuildPyramidException();
        }
        return output;
    }


}
