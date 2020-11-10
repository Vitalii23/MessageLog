package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        Integer[] arr = new Integer[11];

        for(int i = 0; i <arr.length; i++){ arr[i] = i; }

        List<Integer[]> list = splitArray(arr, 5);

        for(int i = 0; i < list.size(); i++){

            System.out.println("Array " + i);

            for(int j = 0; j < list.get(i).length; j++){
                System.out.println("  " + list.get(i)[j]);
            }
        }
    }

    public static <T extends Object> List<T[]> splitArray(T[] array, int max){

        int x = array.length / max;
        int r = (array.length % max); // remainder

        int lower = 0;
        int upper = 0;

        List<T[]> list = new ArrayList<T[]>();

        int i = 0;

        for(i = 0; i < x; i++){
            upper += max;
            list.add(Arrays.copyOfRange(array, lower, upper));
            lower = upper;
        }

        if(r > 0){
            list.add(Arrays.copyOfRange(array, lower, (lower + r)));
        }

        return list;
    }
}