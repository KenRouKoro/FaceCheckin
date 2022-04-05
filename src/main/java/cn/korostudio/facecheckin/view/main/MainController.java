package cn.korostudio.facecheckin.view.main;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class MainController {
    @FXML
    protected AnchorPane mainViewPanel;
    @FXML
    protected AnchorPane rightToolPanel;

}