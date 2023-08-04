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
    public static class ObjectResult extends FunctionResult {
        private final Object value;

        public ObjectResult(Object value) {
            this.value = value;
        }

        public Object getObject() {
            return value;
        }
    }

    public static class DoubleResult extends FunctionResult {
        private final double value;

        public DoubleResult(double value) {
            this.value = value;
        }

        public double getDouble() {
            return value;
        }
    }
    public static class StringResult extends FunctionResult {
        private final String value;

        public StringResult(String value) {
            this.value = value;
        }

        public String getString() {
            return value;
        }
    }
}
