package onlineservices.utils;

import java.util.List;

public class Utils {

    public static boolean areNumbersEqual(List<Integer> numbers) {
        if (!numbers.isEmpty()) {
            int firstElement = numbers.get(0);
            for (int i = 1; i < numbers.size(); i++) {
                if (numbers.get(i) != firstElement) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
