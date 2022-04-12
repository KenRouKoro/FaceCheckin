package cn.korostudio.facecheckin.view.loading;

import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.util.FXUtil;
import cn.korostudio.facecheckin.view.dialog.ExitDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.Style;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;

@Getter
public class LoadingApplication {
    @FXML
    protected Label textLabel;
    protected Stage stage;
    protected Stage otherStage;

    public void stop(){
        Platform.runLater(()->{
            otherStage.close();
            stage.close();
        });
    }

    public void setText(String text){
        textLabel.setText(text);
    }

    public static LoadingApplication show(String title,String text){
        Stage stage = new Stage();
        Stage otherStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoadingApplication.class.getResource("Loading.fxml"));

        Scene scene ;
        try {
            scene = new Scene(fxmlLoader.load(),300,80);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        FXUtil.SetMetro(scene, Style.LIGHT);
        URL mainCssUrl = LoadingApplication.class.getResource("Loading.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().add(FXData.ICON);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        otherStage.initStyle(StageStyle.UTILITY);
        otherStage.setOpacity(0);

        stage.initOwner(otherStage);

        LoadingApplication loader = fxmlLoader.getController();

        loader.textLabel.setText(text);
        loader.stage=stage;
        loader.otherStage=otherStage;

        otherStage.show();
        stage.show();


        return loader;

    }

}
