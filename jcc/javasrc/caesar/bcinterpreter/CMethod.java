package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:30
 */
public abstract class CMethod {

    public static final int PRINT_METHOD_CODE = 1;

    private CClass returnType;
    private String name;
    private Integer key;

    public abstract void execute();
}
