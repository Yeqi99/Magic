package cn.origincraft.magic.function.system.io.input;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.StringResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InputFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            System.out.println("Please provide a prompt message.");
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Input function requires a prompt message.");
        }

        FunctionResult promptArg = args.get(0);
        if (promptArg instanceof ErrorResult) {
            return promptArg;
        }

        String promptMessage;
        if (promptArg instanceof StringResult) {
            promptMessage = ((StringResult) promptArg).getString();
        } else {
            return new ErrorResult("ERROR_IN_TYPE", "Prompt message must be a string.");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(promptMessage);
            String input = reader.readLine();
            return new StringResult(input);
        } catch (IOException e) {
            return new ErrorResult("IO_ERROR", "An error occurred while reading input.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "input";
    }
}