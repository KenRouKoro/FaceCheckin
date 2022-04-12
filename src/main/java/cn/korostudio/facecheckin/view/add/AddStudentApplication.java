package cn.korostudio.facecheckin.view.add;

import cn.hutool.core.util.IdUtil;
import cn.korostudio.facecheckin.data.Data;
import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.data.UserData;
import cn.korostudio.facecheckin.util.FXUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.Style;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;

@Getter
public class AddStudentApplication {

    protected Stage stage;
    protected AddCallback callback;

    @FXML
    public void leftClick(){
        callback.run(this);
        stage.close();
    }
    @FXML
    public void rightClick(){
        stage.close();
    }

    @FXML
    protected TextField nameText;
    @FXML
    protected TextField idText;
    @FXML
    protected TextField majorText;
    @FXML
    protected ChoiceBox<String> classChoiceBox;

    public UserData getStudents(){
        UserData userData = new UserData();
        userData.setName(nameText.getText());
        userData.setStudentID(idText.getText());
        userData.setClassName(classChoiceBox.getValue());
        userData.setMajor(majorText.getText());
        userData.setUUID(IdUtil.randomUUID());
        return userData;
    }


    public static AddStudentApplication show(String title,AddCallback callback){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AddStudentApplication.class.getResource("Add.fxml"));

        Scene scene ;
        try {
            scene = new Scene(fxmlLoader.load(),600,400);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        FXUtil.SetMetro(scene, Style.LIGHT);
        URL mainCssUrl = AddStudentApplication.class.getResource("Add.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().add(FXData.ICON);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);

        AddStudentApplication addStudentApplication = fxmlLoader.getController();
        addStudentApplication.stage=stage;
        addStudentApplication.callback=callback;

        addStudentApplication.classChoiceBox.setItems(FXCollections.observableArrayList(Data.classData.keySet()));

        stage.show();

        return addStudentApplication;
    }

    public interface AddCallback{
        void run(AddStudentApplication addStudentApplication);
    }
}
