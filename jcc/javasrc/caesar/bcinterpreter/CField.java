package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 12:04
 */
public class CField {
    
    private CClass type;
    private String name;

    public CField(CClass type, String name) {
        this.type = type;
        this.name = name;
    }

    public CClass getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
