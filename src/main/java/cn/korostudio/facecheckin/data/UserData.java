package cn.korostudio.facecheckin.data;

import lombok.Data;

@Data
public class UserData {
    protected String name;
    protected String className;
    protected String UUID;//同时作为图片的文件名。
    protected String studentID;
    protected String major;
    protected int registerIndex;//这个实际上是执行时才会生成的，并不固定。
}
