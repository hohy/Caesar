package caesar.bcinterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 19.2.12
 * Time: 14:27
 */
public class Frame {

    int returnAddress;
    int[] params;
    int thisObj;

    public Frame(int returnAddress, int[] params, int thisObj) {
        this.returnAddress = returnAddress;
        this.params = params;
        this.thisObj = thisObj;
    }

    public int getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(int returnAddress) {
        this.returnAddress = returnAddress;
    }

    public int[] getParams() {
        return params;
    }

    public void setParams(int[] params) {
        this.params = params;
    }

    public int getThisObj() {
        return thisObj;
    }

    public void setThisObj(int thisObj) {
        this.thisObj = thisObj;
    }
}
