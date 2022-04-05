package cn.korostudio.facecheckin.view.main;

import cn.hutool.core.thread.ThreadUtil;
import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.main.Main;
import cn.korostudio.facecheckin.part.VideoPanel;
import cn.korostudio.facecheckin.util.FXUtil;
import cn.korostudio.facecheckin.view.dialog.ExitDialog;
import com.lzw.face.FaceHelper;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.Style;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    protected static Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @Getter
    protected Stage stage;
    @Getter
    protected Scene scene;
    @Getter
    protected SwingNode videoSwingNode;

    @Override
    public void start(Stage stage) throws IOException {

        FXData.mainApplication=this;

        this.stage=stage;

        initScene();

        initStage();

        initVideo();

        start();
    }

    public void start(){
        stage.show();
        ThreadUtil.execute(this::run);
    }

    public void initScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Main.fxml"));
        scene = new Scene(fxmlLoader.load(),1920,1080);
        FXData.mainController = fxmlLoader.getController();
        FXUtil.SetMetro(scene,Style.LIGHT);
        URL mainCssUrl = this.getClass().getResource("Main.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());
    }

    public void initStage(){
        stage.setTitle("人脸识别签到系统");
        stage.getIcons().add(FXData.ICON);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.setFullScreen(true);

        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }

    public void initVideo(){
        FXData.videoPanel = new VideoPanel();
        videoSwingNode = FXData.videoPanel.getVideoPanel();
        AnchorPane anchorPane = FXData.mainController.getMainViewPanel();
        anchorPane.getChildren().add(videoSwingNode);
        AnchorPane.setTopAnchor(videoSwingNode, 0.0);
        AnchorPane.setRightAnchor(videoSwingNode, 0.0);
        AnchorPane.setLeftAnchor(videoSwingNode, 0.0);
        AnchorPane.setBottomAnchor(videoSwingNode, 0.0);
    }

    public void run(){
        for(;;){
            ThreadUtil.sleep(500);
            BufferedImage bufferedImage = FXData.videoPanel.getWebcamPanel().getImage();
            BufferedImage backgroundImage = FaceHelper.crop(bufferedImage);
            if(backgroundImage == null ){
                continue;
            }
            FXData.mainController.getShowFaceImagePanel().setImage(SwingFXUtils.toFXImage(backgroundImage,null));
        }
    }


}