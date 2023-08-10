package cn.origincraft.magic.interpreter.fastexpression.functions;

import cn.origincraft.magic.object.MagicWords;
import cn.origincraft.magic.object.Spell;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static class BooleanResult extends FunctionResult {
        private final boolean value;

        public BooleanResult(boolean value) {
            this.value = value;
        }

        public boolean getBoolean() {
            return value;
        }
    }

    public static class ListResult extends FunctionResult {
        private final List<Object> value;

        public ListResult(List<Object> value) {
            this.value = value;
        }

        public List<Object> getList() {
            return value;
        }
    }

    public static class MapResult extends FunctionResult {
        private final Map<String,Object> value;

        public MapResult(Map<String,Object> value) {
            this.value = value;
        }

        public Map<String,Object> getMap() {
            return value;
        }
    }

    public static class SetResult extends FunctionResult {
        private final Set<Object> value;

        public SetResult(Set<Object> value) {
            this.value = value;
        }

        public Set<Object> getSet() {
            return value;
        }
    }
    public static class SpellResult extends FunctionResult {
        private final Spell value;

        public SpellResult(Spell value) {
            this.value = value;
        }

        public Spell getSpell() {
            return value;
        }
    }
    public static class MagicWordsResult extends FunctionResult {
        private final MagicWords value;

        public MagicWordsResult(MagicWords value) {
            this.value = value;
        }

        public MagicWords getMagicWords() {
            return value;
        }
    }
}
