package caesar.interpreter;
/**
 *
 * @author hohy
 */
public class InterpreterObject {
    private int dataPointer;
    private InterpreterClass type;

    public InterpreterObject(int dataPointer, InterpreterClass type) {
        this.dataPointer = dataPointer;
        this.type = type;
    }
    
    public InterpreterClass getType() {
        return type;
    }

    public void setType(InterpreterClass type) {
        this.type = type;
    }

    public int getDataPointer() {
        return dataPointer;
    }

    public void setDataPointer(int dataPointer) {
        this.dataPointer = dataPointer;
    }

    @Override
    public String toString() {
        return type.getName() + 
                "("+ dataPointer + ")";
    }

}
