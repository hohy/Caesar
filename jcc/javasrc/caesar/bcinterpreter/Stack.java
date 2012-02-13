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
        pointer = size-1;
    }

    public void pushObject(CObject object) {
        byte[] objData = object.getData();
        System.arraycopy(objData, 0, data, pointer-objData.length, objData.length);
        pointer -= objData.length;
    }

    public CObject popObject() {
        int objSize = CaesarBCInterpreter.readIntFromBytearray(pointer+CaesarBCInterpreter.POINTER_SIZE,data);
        byte[] objData = new byte[objSize];
        System.arraycopy(data,pointer,objData,0,objSize);
        pointer += objSize;
        return new CObject(objData);
    }

    public CObject peekObject() {
        int objSize = CaesarBCInterpreter.readIntFromBytearray(pointer+CaesarBCInterpreter.POINTER_SIZE,data);
        byte[] objData = new byte[objSize];
        System.arraycopy(data,pointer,objData,0,objSize);
        return new CObject(objData);
    }
}
