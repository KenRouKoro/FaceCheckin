package cn.korostudio.facecheckin.view.main;

import cn.korostudio.facecheckin.data.FXData;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class MainController {
    @FXML
    protected AnchorPane mainViewPanel;
    @FXML
    protected AnchorPane rightToolPanel;
    @FXML
    protected ImageView showFaceImagePanel;
    @FXML
    protected void fullScreenButtonClick(){
        FXData.mainApplication.getStage().setFullScreen(true);
    }
    @FXML
    public void exitButtonClick(MouseEvent event) {
        System.exit(0);
    }
}