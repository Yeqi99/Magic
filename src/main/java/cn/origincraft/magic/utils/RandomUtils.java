package cn.origincraft.magic.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    public static Object selectRandomItem(List<?> items, List<?> weights) {
        if (items == null || weights == null || items.isEmpty() || weights.isEmpty() || items.size() != weights.size()) {
            return null; // 返回 null 如果条件不符合
        }

        double totalWeight = 0;
        for (Object weight : weights) {
            if (!(weight instanceof Number)) {
                return null; // 如果权重不是数字类型，返回 null
            }
            totalWeight += ((Number) weight).doubleValue();
        }

        double randomValue = Math.random() * totalWeight;

        double cumulativeWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            Object currentWeight = weights.get(i);
            if (!(currentWeight instanceof Number)) {
                return null; // 如果权重不是数字类型，返回 null
            }
            cumulativeWeight += ((Number) currentWeight).doubleValue();
            if (randomValue <= cumulativeWeight) {
                return items.get(i);
            }
        }

        return null;
    }
    public static Object selectRandomItem(List<?> items) {
        if (items == null || items.isEmpty()) {
            return null; // 返回 null 如果列表为空或为 null
        }

        Random random = new Random();
        int randomIndex = random.nextInt(items.size()); // 生成一个随机索引

        return items.get(randomIndex); // 返回随机选择的元素
    }
}
