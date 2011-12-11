package caesar.ast;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 10.12.11
 * Time: 16:42
 */
public class MethodParam {
    private String name;
    private String className;

    public MethodParam(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}
