package cn.korostudio.facecheckin.main;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.korostudio.facecheckin.data.ClassData;
import cn.korostudio.facecheckin.data.Data;
import cn.korostudio.facecheckin.data.UserData;
import cn.korostudio.facecheckin.view.main.MainApplication;
import com.lzw.face.SeetafaceBuilder;
import javafx.application.Application;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        loadData();
        SeetafaceBuilder.build();
        Application.launch(MainApplication.class);
    }

    public static void loadData(){
        String jsonDataStr = new FileReader(System.getProperty("user.dir")+"/data/data.json").readString();
        Data.SaveJson = new JSONObject(jsonDataStr);
        JSONArray jsonArray = Data.SaveJson.getJSONArray("classes");
        if(jsonArray==null){
            return;
        }
        for(Object classes:jsonArray){
            Data.classData.put((String)classes,new ClassData((String) classes));
        }
        JSONArray studentArray = Data.SaveJson.getJSONArray("students");
        if(studentArray==null){
            return;
        }
        UserData[] userData = studentArray.toArray(new UserData[0]);
        for(UserData userData1:userData){
            Data.classData.get(userData1.getClassName()).getUserData().put(userData1.getUUID(),userData1);
        }
    }

    public static void saveData(){
        FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/data/data.json");
        JSONObject baseJson = new JSONObject();
        HashMap<String,JSONArray> baseMap = new HashMap<>();

        JSONArray jsonArrayClass = new JSONArray(Data.classData.keySet().toArray());
        baseMap.put("classes",jsonArrayClass);

        JSONArray jsonArrayStudent = new JSONArray();
        for(ClassData classData:Data.classData.values()){
            for(UserData userData:classData.getUserData().values()){
                jsonArrayStudent.put(userData);
            }
        }
        baseMap.put("students",jsonArrayStudent);

        baseJson.putAll(baseMap);

        writer.write(baseJson.toStringPretty());
    }
}
