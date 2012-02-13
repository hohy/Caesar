package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 14:25
 */
public class Stack {

    private byte[] data;
    public int pointer;

    public Stack(int size) {
        data = new byte[size];
        pointer = 0;
    }

    public void pushObject(CObject object) {
        byte[] objData = object.getData();
        System.arraycopy(objData, 0, data, pointer, objData.length);
        pointer += data.length;
    }
}
