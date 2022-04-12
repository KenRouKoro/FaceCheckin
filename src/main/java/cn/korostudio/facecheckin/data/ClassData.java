package cn.korostudio.facecheckin.data;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class ClassData {
    public ClassData(String className){
        this.className=className;
    }
    protected String className;
    protected ConcurrentHashMap<String,UserData>userData = new ConcurrentHashMap<>();//studentUUID,userData
}
