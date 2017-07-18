package cn.appscomm.ota.mode;

/**
 * 作者：hsh
 * 日期：2017/5/27
 * 说明：
 */

public class CommandInfo {
    public byte[] content;
    public boolean is1531Flag;
    public String note;

    public CommandInfo(byte[] content, boolean is1531Flag, String note) {
        this.content = content;
        this.is1531Flag = is1531Flag;
        this.note = note;
    }
}
