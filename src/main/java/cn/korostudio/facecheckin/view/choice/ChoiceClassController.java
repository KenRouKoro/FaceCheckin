package cn.korostudio.facecheckin.view.choice;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class ChoiceClassController implements Initializable {
    @FXML
    protected ListView<String> leftChoice;
    @FXML
    protected ListView<String> rightChoice;
    protected ObservableList<String> leftList;
    protected ObservableList<String> rightList;
    protected ChoiceClassApplication.ChoiceBack callback;
    protected Stage stage;
    protected ChoiceClassApplication cApplication;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    @FXML
    public void toLeftClick(MouseEvent mouseEvent){
        String item=rightChoice.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        rightChoice.getSelectionModel().clearSelection();
        rightList.remove(item);
        leftList.add(item);
    }
    @FXML
    public void toRightClick(MouseEvent mouseEvent){
        String item=leftChoice.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        leftChoice.getSelectionModel().clearSelection();
        leftList.remove(item);
        rightList.add(item);
    }
    @FXML
    public void finalClick(){
        callback.run(cApplication);
        cApplication.finishChoice();
        stage.close();
    }
}
