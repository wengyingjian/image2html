package com.wengyingjian.image2html;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by hzwengyingjian on 17/1/20.
 */
public class Main {
    // http://weixin.qq.com/r/PTm-p5PEvc7CrdfE92wv

    // 一个点5像素
    public static final int per = 5;

    private static final String size = "1px";

    private static final String WHITE_NORMAL = "<div class='nw'></div>";

    private static final String WHITE_NEW_LINE = "<div class='lw'></div>";

    private static final String BLACK_NORMAL = "<div class='nb'></div>";

    private static final String BLACK_NEW_LINE = "<div class='lb'></div>";

    public static void main(String[] args) throws IOException {
        int[] rgb = new int[3];
        File file = new File(args[0]);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();

        StringBuffer html = new StringBuffer();

        System.out.println("width=" + width + ",height=" + height + ".");
        for (int i = 0; i < width; i += per) {
            boolean newLine = true;
            for (int j = 0; j < height; j += per) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                if (rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0) {
                    System.out.print("黑");
                    if (newLine) {
                        newLine = false;
                        html.append(BLACK_NEW_LINE);
                    } else {
                        html.append(BLACK_NORMAL);
                    }
                } else if (rgb[0] == 255 && rgb[1] == 255 && rgb[2] == 255) {
                    System.out.print("白");
                    if (newLine) {
                        newLine = false;
                        html.append(WHITE_NEW_LINE);
                    } else {
                        html.append(WHITE_NORMAL);
                    }
                } else {
                    System.out.print("--");
                }
            }
            System.out.println("");
        }

        System.out.println("html=" + html.toString());
        addStyle(html);
        FileWriter fileWriter = new FileWriter("dest.html");
        fileWriter.write(html.toString());
        fileWriter.close();
    }

    private static void addStyle(StringBuffer html) {
        String format = "<style>    .nb {        background: #000;        width: %s;        height: %s;        float: left;    }    .nw {        background: #FFF;        width: %s;        height: %s;        float: left;    }    .lb {        background: #000;        width: %s;        height: %s;        float: left;        clear: left    }    .lw {        background: #FFF;        width: %s;        height: %s;        float: left;        clear: left    }</style>";
        html.append(format.replaceAll("\\%s", size));
    }
}
