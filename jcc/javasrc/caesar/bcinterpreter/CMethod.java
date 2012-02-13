package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:30
 */
public abstract class CMethod {
    
    private CClass returnType;
    private String name;
    private Integer key;

    public abstract void execute();
}
