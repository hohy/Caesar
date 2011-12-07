/**
 *
 * @author hohy
 */
class InterpreterObject {
    private Object value;
    private InterpreterClass type;

    public InterpreterObject(Object value, InterpreterClass type) {
        this.value = value;
        this.type = type;
    }
    
    public InterpreterClass getType() {
        return type;
    }

    public void setType(InterpreterClass type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return type.getName() + "("+ value.toString() + ")";
    }
}
