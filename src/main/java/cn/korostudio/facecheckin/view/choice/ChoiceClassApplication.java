package cn.korostudio.facecheckin.view.choice;

import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.util.FXUtil;
import cn.korostudio.facecheckin.view.dialog.ExitDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.Style;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class ChoiceClassApplication  {
    @Getter
    protected ChoiceClassController controller;
    @Getter
    protected ConcurrentHashMap<String ,Boolean>status = new ConcurrentHashMap<>();

    public ChoiceClassController show(String title,ChoiceBack callback) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ChoiceClassApplication.class.getResource("Choice.fxml"));

        Scene scene ;
        try {
            scene = new Scene(fxmlLoader.load(),600,400);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        FXUtil.SetMetro(scene, Style.LIGHT);
        URL mainCssUrl = ChoiceClassApplication.class.getResource("Choice.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().addAll(FXData.ICON);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);

        controller = fxmlLoader.getController();

        controller.setCallback(callback);
        controller.setStage(stage);
        controller.setCApplication(this);

        initList();

        stage.show();
        return controller;
    }

    protected void initList(){
        controller.leftList = FXCollections.observableArrayList(status.keySet());
        controller.getLeftChoice().setItems(controller.leftList);

        controller.rightList = FXCollections.observableArrayList();
        controller.rightChoice.setItems(controller.rightList);
    }

    protected void finishChoice(){

    }

    public interface ChoiceBack{
        void run(ChoiceClassApplication classApplication);
    }
}
