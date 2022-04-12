package cn.korostudio.facecheckin.view.show;

import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.util.FXUtil;
import cn.korostudio.facecheckin.view.choice.ChoiceClassApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.Style;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Data
public class ShowApplication {

    protected Stage stage;
    @FXML
    protected ListView<String> rightList;
    @FXML
    protected ListView<String> liftList;

    public static ShowApplication show(String title, List<String> leftList,List<String>rightList){
        ShowApplication controller;
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ShowApplication.class.getResource("Show.fxml"));

        Scene scene ;
        try {
            scene = new Scene(fxmlLoader.load(),600,400);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        FXUtil.SetMetro(scene, Style.LIGHT);
        URL mainCssUrl = ShowApplication.class.getResource("Show.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().addAll(FXData.ICON);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);

        controller = fxmlLoader.getController();

        controller.setStage(stage);

        controller.liftList.getItems().addAll(leftList);
        controller.rightList.getItems().addAll(rightList);

        stage.show();

        return controller;
    }
}
