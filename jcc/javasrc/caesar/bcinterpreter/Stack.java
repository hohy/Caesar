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

    public void pushInt(int integer) {
        byte[] idata = ByteConvertor.toByta(integer);
        System.arraycopy(idata, 0, data, pointer-idata.length, idata.length);
        pointer -= idata.length;
    }
    
    public int popInt() {
        byte[] data = new byte[CaesarBCInterpreter.POINTER_SIZE];
        System.arraycopy(this.data,pointer,data,0,CaesarBCInterpreter.POINTER_SIZE);
        pointer += CaesarBCInterpreter.POINTER_SIZE;
        return ByteConvertor.toInt(data);
    }
}
