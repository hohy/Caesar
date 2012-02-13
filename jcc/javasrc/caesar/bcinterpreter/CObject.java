package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 15:04
 */
public class CObject {

    private byte[] data;

    public CObject(byte[] objBytes) {
        data = objBytes;
    }

    public byte[] getData() {
        return data;
    }

    public int getTypeCode() {
        byte[] code = new byte[CaesarBCInterpreter.CLASS_ID_SIZE];
        System.arraycopy(data, 0, code, 0, CaesarBCInterpreter.CLASS_ID_SIZE);
        return ByteConvertor.toInt(code);
    }

    public byte[] getFieldsData() {
        byte[] fields = new byte[data.length - CClass.HEADER_SIZE];
        System.arraycopy(data, CClass.HEADER_SIZE, fields, 0, fields.length);
        return fields;
    }
}
