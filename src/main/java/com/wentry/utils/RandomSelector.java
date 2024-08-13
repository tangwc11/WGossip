package com.wentry.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomSelector {
    private static final Random RANDOM = new Random();

    public static <T> List<T> selectRandomElements(List<T> list, int n) {
        if (list == null || list.size() <= n) {
            return list; // 如果列表为空或需要的元素数量大于等于列表大小，直接返回原列表
        }

        List<T> copy = new ArrayList<>(list); // 创建列表的副本，避免修改原始列表
        int size = copy.size();
        for (int i = 0; i < n; i++) {
            // 随机选择一个索引，范围是从i到列表的末尾
            int randomIndex = i + RANDOM.nextInt(size - i);
            // 交换当前元素和随机选择的元素
            T temp = copy.get(i);
            copy.set(i, copy.get(randomIndex));
            copy.set(randomIndex, temp);
        }
        // 截取前n个元素并返回
        return copy.subList(0, n);
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> selectedNumbers = selectRandomElements(numbers, 5);
        System.out.println(selectedNumbers);
    }
}
