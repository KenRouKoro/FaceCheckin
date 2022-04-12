package cn.korostudio.facecheckin.data;

import cn.hutool.json.JSONObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
    public static ConcurrentHashMap<String, ClassData> classData =  new ConcurrentHashMap<>();//ClassName,ClassData
    public static JSONObject SaveJson;
    public static ConcurrentHashMap<Integer, StudentDataAndStatus> nowDataStatus = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, BufferedImage> nowDataImage = new ConcurrentHashMap<>();
    @lombok.Data
    public static class StudentDataAndStatus{
        protected boolean status = false;
        protected UserData student;
    }
    public static float accuracy = 0.7f;

    public static long sleepTime=500;


}

