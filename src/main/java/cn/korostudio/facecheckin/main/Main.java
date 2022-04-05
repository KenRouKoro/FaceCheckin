package cn.korostudio.facecheckin.main;

import cn.korostudio.facecheckin.view.main.MainApplication;
import com.lzw.face.SeetafaceBuilder;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        //System.loadLibrary();
        SeetafaceBuilder.build();
        Application.launch(MainApplication.class);
    }
}
