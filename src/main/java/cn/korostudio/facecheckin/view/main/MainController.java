package cn.korostudio.facecheckin.view.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.korostudio.facecheckin.data.FXData;
import cn.korostudio.facecheckin.data.UserData;
import cn.korostudio.facecheckin.main.Main;
import cn.korostudio.facecheckin.util.FXUtil;
import cn.korostudio.facecheckin.view.add.AddStudentApplication;
import cn.korostudio.facecheckin.view.choice.ChoiceClassApplication;
import cn.korostudio.facecheckin.view.choice.ChoiceClassController;
import cn.korostudio.facecheckin.view.dialog.ExitDialog;
import cn.korostudio.facecheckin.view.loading.LoadingApplication;
import cn.korostudio.facecheckin.view.show.ShowApplication;
import com.lzw.face.FaceHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Data
public class MainController {

    protected boolean startButtonStatus=false;
    @FXML
    protected Button startButton;
    @FXML
    protected AnchorPane mainViewPanel;
    @FXML
    protected AnchorPane rightToolPanel;
    @FXML
    protected ImageView showFaceImagePanel;
    @FXML
    protected ImageView findImageView;
    @FXML
    protected Label stuIDText;
    @FXML
    protected Label stuNameText;
    @FXML
    protected Label stuMajorText;
    @FXML
    protected Label stuClassText;

    @FXML
    protected void fullScreenButtonClick(MouseEvent event){
        FXData.mainApplication.stage.setResizable(true);
        FXData.mainApplication.stage.setMaximized(true);
        FXData.mainApplication.stage.setResizable(false);
    }
    @FXML
    public void exitButtonClick(MouseEvent event) {
        System.exit(0);
    }
    @FXML
    public void addDataButtonClick(MouseEvent event){
        BufferedImage bufferedImage = FXData.videoPanel.getWebcamPanel().getWebcam().getImage();
        if(FaceHelper.crop(bufferedImage)==null){
            ExitDialog.ShowDialog("错误","未识别到人脸!",back->{});
            return;
        }
        AddStudentApplication addStudentApplication = AddStudentApplication.show("信息采集",add->{
            UserData userData = add.getStudents();
            ThreadUtil.execute(()->{
                try {
                    ImageIO.write(bufferedImage,"PNG", FileUtil.touch(System.getProperty("user.dir")+"/data/imageData/"+userData.getUUID()+".png"));
                } catch (IOException e) {
                    ExitDialog.ShowDialog("错误","写入本地文件失败!",null);
                    return;
                }
                cn.korostudio.facecheckin.data.Data.classData.get(userData.getClassName()).getUserData().put(userData.getUUID(), userData);
                Main.saveData();
            });
        });
    }
    @FXML
    public void startButtonClick(MouseEvent event){
        if(!startButtonStatus) {
            startButton.setText("结束签到");
            startButtonStatus = true;
            ChoiceClassApplication cv = new ChoiceClassApplication();
            for (String className : cn.korostudio.facecheckin.data.Data.classData.keySet()) {
                cv.getStatus().put(className, false);
            }
            cv.show("班级选择", e -> {
                LoadingApplication loadingApplication = LoadingApplication.show("请等待", "正在加载班级模型......");
                ThreadUtil.execute(() -> {
                    ChoiceClassController controller = e.getController();
                    try {
                        FXUtil.registerClass(controller.getRightList());
                    } catch (IOException ex) {
                        ExitDialog.ShowDialog("错误","读取本地文件失败!",null);
                    }finally {
                        if (loadingApplication != null) {
                            loadingApplication.stop();
                        }
                    }
                    FXData.mainApplication.setStartCheckin(true);
                });
            });
        }else{
            startButton.setText("开始签到");
            startButtonStatus = false;
            FXData.mainController.getFindImageView().setImage(null);
            FXData.mainApplication.setStartCheckin(false);

            ArrayList<String> lift = new ArrayList<>();
            ArrayList<String> right = new ArrayList<>();

            for(int i :cn.korostudio.facecheckin.data.Data.nowDataStatus.keySet()){
                cn.korostudio.facecheckin.data.Data.StudentDataAndStatus studentDataAndStatus = cn.korostudio.facecheckin.data.Data.nowDataStatus.get(i);
                UserData userData=cn.korostudio.facecheckin.data.Data.nowDataStatus.get(i).getStudent();
                if(studentDataAndStatus.isStatus()){
                    lift.add(userData.getClassName()+" "+ userData.getStudentID()+" "+userData.getName());
                }else{
                    right.add(userData.getClassName()+" "+ userData.getStudentID()+" "+userData.getName());
                }
            }

            ShowApplication.show("签到结果",lift,right);

            clearFindStudent();
        }
    }

    public void setFindStudent(UserData userData){
        stuIDText.setText(userData.getStudentID());
        stuNameText.setText(userData.getName());
        stuClassText.setText(userData.getClassName());
        stuMajorText.setText(userData.getMajor());
    }

    public void clearFindStudent(){
        stuIDText.setText("");
        stuNameText.setText("");
        stuClassText.setText("");
        stuMajorText.setText("");
    }


}