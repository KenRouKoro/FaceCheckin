package cn.korostudio.facecheckin.view.dialog;

import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.util.FXUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ExitDialog {
    @FXML
    protected Button left;
    @FXML
    protected Button right;
    @FXML
    protected Label info;


    public static Stage ShowDialog(String title,String info,EventHandler<MouseEvent>leftClick){
        return ShowDialog(title,info,"是","否",leftClick,null);
    }

    public static Stage ShowDialog(String title,String info, String left, String right, EventHandler<MouseEvent> leftClick,EventHandler<MouseEvent> rightClick)  {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ExitDialog.class.getResource("ExitDialog.fxml"));

        Scene scene ;
        try {
            scene = new Scene(fxmlLoader.load(),300,100);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        FXUtil.SetMetro(scene,Style.LIGHT);
        URL mainCssUrl = ExitDialog.class.getResource("ExitDialog.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().add(FXData.ICON);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);

        ExitDialog dialog = fxmlLoader.getController();

        dialog.info.setText(info);

        dialog.left.setOnMouseClicked(Objects.requireNonNullElseGet(leftClick, () -> event -> stage.close()));
        dialog.left.setText(left);
        dialog.right.setOnMouseClicked(Objects.requireNonNullElseGet(rightClick, () -> event -> stage.close()));
        dialog.right.setText(right);

        stage.show();
        return stage;
    }
}
