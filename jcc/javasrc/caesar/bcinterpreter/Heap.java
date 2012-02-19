package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 14:24
 */
public class Heap {

    private byte[] data;
    int freepointer;
    CaesarBCInterpreter interpreter;

    public Heap(int size, CaesarBCInterpreter interpreter) {
        this.interpreter = interpreter;
        data = new byte[size];
        freepointer = 0;
    }

    public int put(CObject obj) {
        int address = freepointer;
        System.arraycopy(obj.getData(), 0, data, freepointer, obj.getData().length);
        freepointer += obj.getData().length;
        return address;
    }

    public CObject get(int id) {
        int address = interpreter.getCurrentEnvironment().get(id);
        int objSize = CaesarBCInterpreter.readIntFromBytearray(address+CaesarBCInterpreter.POINTER_SIZE,data);
        byte[] objData = new byte[objSize];
        System.arraycopy(data,address,objData,0,objSize);
        return new CObject(objData);
    }

    public CObject getFromAddress(int address) {
        int objSize = CaesarBCInterpreter.readIntFromBytearray(address+CaesarBCInterpreter.POINTER_SIZE,data);
        byte[] objData = new byte[objSize];
        System.arraycopy(data,address,objData,0,objSize);
        return new CObject(objData);
    }

    public void set(int id, CObject obj) {
        int address = interpreter.getCurrentEnvironment().get(id);
        int oldObjSize = CaesarBCInterpreter.readIntFromBytearray(address+CaesarBCInterpreter.POINTER_SIZE,data);
        int newObjSize = obj.getData().length;
        if(newObjSize <= oldObjSize) {
            System.arraycopy(obj.getData(),0,data,address,oldObjSize);
        } else {
            int newAddress = put(obj);
            interpreter.getCurrentEnvironment().set(id, newAddress);
        }
    }

    /**
     * Na dane adrese nastavi danou hodnotu
     * @param address
     * @param value
     */
    public void set(int address, int value) {
        byte[] vdata = ByteConvertor.toByta(value);
        System.arraycopy(vdata,0,data,address,vdata.length);
    }
}
