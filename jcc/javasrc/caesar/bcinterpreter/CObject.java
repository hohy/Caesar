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

    public CObject(int code, byte[] data) {
        int size = data.length + CClass.HEADER_SIZE;
        this.data = new byte[size];
        byte[] bcode = ByteConvertor.toByta(code);
        byte[] bsize = ByteConvertor.toByta(size);
        System.arraycopy(bcode, 0, this.data, 0, CaesarBCInterpreter.CLASS_ID_SIZE);
        System.arraycopy(bsize, 0, this.data, CaesarBCInterpreter.CLASS_ID_SIZE, CaesarBCInterpreter.SIZE_INFO_SIZE);
        System.arraycopy(data, 0, this.data, CClass.HEADER_SIZE, data.length);
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

    public int getFieldAddress(int field_id) {
        byte[] baddr = new byte[CaesarBCInterpreter.POINTER_SIZE];
        System.arraycopy(data,CClass.HEADER_SIZE + CaesarBCInterpreter.POINTER_SIZE*field_id, baddr, 0, CaesarBCInterpreter.POINTER_SIZE);
        return ByteConvertor.toInt(baddr);
    }
}
