package cn.korostudio.facecheckin.util;

import cn.hutool.core.util.URLUtil;
import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.awt.*;

public class FXUtil {

    //设置Metro主题
    public static void SetMetro(Scene scene,Style style){
        JMetro jMetro = new JMetro(style);
        jMetro.setScene(scene);
    }

    public static String  getResURLStr(String url){
        return URLUtil.getURL(url).toExternalForm();
    }

    public static boolean isPointInRect(int x, int y,double[]PointXs,double[]PointYs) {
        final Point A = new Point((int) PointXs[0],(int)PointYs[0]);
        final Point B = new Point((int) PointXs[1],(int)PointYs[1]);
        final Point C = new Point((int) PointXs[2],(int)PointYs[2]);
        final Point D = new Point((int) PointXs[3],(int)PointYs[3]);
        final int a = (B.x - A.x)*(y - A.y) - (B.y - A.y)*(x - A.x);
        final int b = (C.x - B.x)*(y - B.y) - (C.y - B.y)*(x - B.x);
        final int c = (D.x - C.x)*(y - C.y) - (D.y - C.y)*(x - C.x);
        final int d = (A.x - D.x)*(y - D.y) - (A.y - D.y)*(x - D.x);
        return (a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0);
    }

}
