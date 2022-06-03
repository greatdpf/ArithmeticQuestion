package leetcode.hard;

import java.util.Arrays;

/**
 * description：
 * 327 测试用例
 * @author staring
 * @date 2022/6/2
 */
public class Question237Test {
    public static void main(String[] args) {
        int maxLength = 10;
        int maxValue = 10;
        int testTime = 10000;
        for (int i = 0;i < testTime;i++) {
            int num1 = (int)(Math.random() * maxValue + 1) - (int)(Math.random() * maxValue + 1);
            int num2 = (int)(Math.random() * maxValue + 1) - (int)(Math.random() * maxValue + 1);
            int up = Math.max(num1, num2);
            int lower = Math.min(num1, num2);
            int[] array = generateArray(maxLength, maxValue);
            int[] contrastArray = Arrays.copyOf(array, array.length);
            // 借用归并排序的方式解决
            int s1 = solveQuestion(array, up, lower);
            // 使用三重循环的方式解决
            int s2 = threeLoop(contrastArray, up, lower);
            if (s1 != s2) {
                System.out.println(lower + " : " + up);
                for (long value : contrastArray) {
                    System.out.print(value + " ");
                }
                System.out.println();
                for (long value : array) {
                    System.out.print(value + " ");
                }
                System.out.println();
                System.out.println("错误！");
                System.out.println(s1 +" : " + s2);
                break;
            }

        }
    }

    /**
     *
     * @param maxLength
     * @param maxValue
     * @return
     */
    public static int[] generateArray(int maxLength, int maxValue) {
        int length = (int)(Math.random() * maxLength + 1);
        int[] array = new int[length];
        for (int i = 0;i < array.length;i++) {
            array[i] = (int)(Math.random() * maxValue + 1) - (int)(Math.random() * maxValue + 1);
        }
        return array;
    }

    public static int solveQuestion(int[] array, int up, int lower) {
        long[] preSum = getPreSumArray(array);
        int sum = grouping(preSum, 0, preSum.length - 1, up, lower);
        return sum;
    }

    public static long[] getPreSumArray(int[] array) {
        long[] preSum = new long[array.length];
        preSum[0] = array[0];
        for (int i = 1;i < array.length;i++) {
            preSum[i] = preSum[i - 1] + array[i];
        }
        return preSum;
    }

    public static int grouping(long[] array, int left, int right, int up, int lower) {
        if (left >= right) {
            if (array[left] >= lower && array[left] <= up) {
                return 1;
            } else {
                return 0;
            }
        }
        int mid = left + (right - left >> 1);
        return grouping(array, left, mid, up, lower) +
                grouping(array, mid + 1, right, up, lower) +
                merge(array, left, mid, right, up, lower);

    }

    public static int merge(long[] array, int left, int mid, int right, int up, int lower) {
        int sum = 0;
        // 右数组中的，右边界，初始值为 0
        int leftIndex = left;
        // 右数组中的，左边界，初始值为 0
        int rightIndex = left;
        // 需要找一个边界[leftIndex ~ rightIndex] = arr[m] - [up ~ lower];
        int midIndex = mid + 1;
        while (midIndex <= right) {
            while (leftIndex <= mid) {
                if (array[leftIndex] >= (array[midIndex] - up)) {
                    break;
                } else {
                    leftIndex++;
                }
            }
            while (rightIndex <= mid) {
                if (array[rightIndex] <= (array[midIndex] - lower)) {
                    rightIndex++;
                } else {
                    break;
                }
            }
            sum += rightIndex - leftIndex;
            midIndex++;
        }

        // 排序
        int l = left;
        int m = mid + 1;
        int newArrayLength = right - left + 1;
        long[] newArray = new long[newArrayLength];
        int i = 0;
        while (l <= mid && m <= right) {
            if (array[l] < array[m]) {
                newArray[i++] = array[l++];
            } else {
                newArray[i++] = array[m++];
            }
        }
        while (l <= mid) {
            newArray[i++] = array[l++];
        }
        while (m <= right) {
            newArray[i++] = array[m++];
        }
        for (int j = 0;j < newArrayLength;j++) {
            array[left + j] = newArray[j];
        }
        return sum;
    }

    public static int threeLoop(int[] array, int up, int lower) {
        int sums = 0;
        for (int j = 0; j < array.length; j++) {
            int s = 0;
            for (int k = j; k < array.length; k++) {
                s += array[k];
                if (s <= up && s >= lower) {
                    sums += 1;
                }
            }
        }
        return sums;
    }
}
