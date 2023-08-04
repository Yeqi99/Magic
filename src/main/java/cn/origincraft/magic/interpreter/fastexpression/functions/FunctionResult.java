package cn.origincraft.magic.interpreter.fastexpression.functions;

public abstract class FunctionResult {
    public enum Status {
        SUCCESS,
        FAILURE,
        ERROR
    }

    public static class DefaultResult extends FunctionResult {
        private final Status status;

        public DefaultResult(Status status) {
            this.status = status;
        }

        public Status getStatus() {
            return status;
        }
    }

    public static class IntResult extends FunctionResult {
        private final int value;

        public IntResult(int value) {
            this.value = value;
        }

        public int getInt() {
            return value;
        }
    }
}
