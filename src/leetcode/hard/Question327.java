package leetcode.hard;

/**
 * description：https://leetcode.cn/problems/count-of-range-sum/
 * 第327题：给你一个整数数组 nums 以及两个整数 lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper]（包含 lower 和 upper）之内的区间和的个数。
 *
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 *
 * @author staring
 * @date 2022/6/1
 */
public class Question327 {
    public static void main(String[] args) {
        int[] array = {-2147483647,0,-2147483647,2147483647};
        int up = 3864;
        int lower = -564;
        long[] preArray = preSum(array);
        for (long value : preArray) {
            System.out.print(value + " ");
        }
        System.out.println();
        int sum = grouping(preArray, 0, preArray.length - 1, up, lower);
        for (long value : preArray) {
            System.out.print(value + " ");
        }
        System.out.println();
        System.out.println(sum);

    }

    public static long[] preSum(int[] array) {
        long[] preArray = new long[array.length];
        preArray[0] = array[0];
        for (int i = 1;i < array.length;i++) {
            preArray[i] = preArray[i - 1] + array[i];
        }
        return preArray;
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
        int upIndex = left;
        int lowerIndex = left;
        int midIndex = mid + 1;
        while (midIndex <= right) {
            long max = array[midIndex] - lower;
            long min = array[midIndex] - up;
            while (lowerIndex <= mid) {
                // lowerIndex 向右移动的过程中，是在获取区间的最小值
                // 比 lowerIndex 大的数，是我们需要的，小的数需要去掉 [lowerIndex, +...]
                // 所以需要在 array 中找到第一个比 array[lowerIndex] 相等或者大于的数，然后退出，否则继续移动
                if (array[lowerIndex] < min) {
                    lowerIndex++;
                } else {
                    break;
                }
            }
            while (upIndex <= mid) {
                if (array[upIndex] <= max) {
                    upIndex++;
                } else {
                    break;
                }
            }
            // 需要注意是否 + 1；
            sum += upIndex - lowerIndex;
            midIndex++;
        }



        int l = left;
        int m = mid + 1;
        int newArrayLength = right - left + 1;
        long[] newArray = new long[newArrayLength];
        int i = 0;
        while(l <= mid && m <= right) {
            if (array[l] < array[m]) {
                newArray[i++] = array[l++];
            } else {
                newArray[i++] = array[m++];
            }
        }
        while(l <= mid) {
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


}
