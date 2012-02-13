package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 14:26
 */
public class Constants {

    byte[] data;
    CaesarBCInterpreter interpreter;

    public Constants(int size) {
        data = new byte[size];
    }

    public Constants(byte[] constants) {
        data = constants;
    }

    public CObject readObject(int address) {
        int objSize = CaesarBCInterpreter.readIntFromBytearray(address + CaesarBCInterpreter.POINTER_SIZE, data);
        byte[] objBytes = new byte[objSize];
        System.arraycopy(data, address, objBytes, 0, objSize);
        CObject obj = new CObject(objBytes);
        return obj;
    }

    public void setInterpreter(CaesarBCInterpreter interpreter) {
        this.interpreter = interpreter;
    }
}
