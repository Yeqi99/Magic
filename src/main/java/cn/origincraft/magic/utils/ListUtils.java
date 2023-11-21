package cn.origincraft.magic.utils;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static FunctionResult generateRange(double start, double end, double step) {

        if (step == 0) {
            return new ErrorResult("STEP_ERROR","step can't be zero");
        }
        if ((start < end && step < 0) || (start > end && step > 0)) {

            return new ErrorResult("STEP_ERROR","The step direction is inconsistent with the starting and ending values");
        }

        List<Double> result = new ArrayList<>();
        double currentValue = start;
        while ((start <= end && currentValue < end) || (start >= end && currentValue > end)) {

            result.add(currentValue);
            currentValue += step;
        }

        return new ListResult(result);
    }
}
