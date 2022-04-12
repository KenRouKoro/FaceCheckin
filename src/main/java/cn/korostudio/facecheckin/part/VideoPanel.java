package cn.korostudio.facecheckin.part;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.embed.swing.SwingNode;
import lombok.Getter;

import javax.swing.*;

public class VideoPanel {
    @Getter
    protected WebcamPanel webcamPanel;

    public SwingNode getVideoPanel() {
        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        return swingNode;
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            /*Swing中的调用摄像头方法*/
            Webcam webcam = Webcam.getDefault();
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            //webcam.setCustomViewSizes(WebcamResolution.VGA.getSize());
            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setDisplayDebugInfo(true);
            webcamPanel.setImageSizeDisplayed(true);
            webcamPanel.setMirrored(true);

            swingNode.setContent(webcamPanel);
        });
    }
}
