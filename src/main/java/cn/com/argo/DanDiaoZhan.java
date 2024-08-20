package cn.com.argo;

import java.util.Arrays;
import java.util.Stack;

public class DanDiaoZhan {


    public static int[] test(int[] nums) {
        int[] result = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            if (stack.isEmpty() || nums[stack.peek()] > nums[i]) {
                stack.push(i);
            } else {
                // 清栈
                while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                    result[stack.pop()] = nums[i];
                }
            }
        }
// 清栈
        while (!stack.isEmpty()) {
            result[stack.pop()] = -1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(test(new int[]{2, 1, 2, 4, 3})));
    }

}
