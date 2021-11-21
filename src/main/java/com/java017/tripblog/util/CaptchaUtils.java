package com.java017.tripblog.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:19
 */

@Component
public class CaptchaUtils {
    private final int width = 120;
    private final int height = 26;
    private final int space = 15;
    private final int drawY = 20;
    private final int charCount = 6;
    private final int lineCount = 4;
    private final String[] chars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "A", "Z"};

    //圖形生成
    public void makeCaptchaCode(HttpSession session, HttpServletResponse response) throws IOException {
        //創建背景
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //繪製
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        response.setContentType("image/png");
        //字形
        Font font = new Font("宋體", Font.BOLD, 16);
        graphics.setFont(font);
        //文字
        StringBuilder buffer = new StringBuilder(charCount);
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < charCount; i++) {
            int random = new Random().nextInt(chars.length);
            buffer.append(chars[random]);
            graphics.setColor(setColor());
            graphics.drawString(chars[random], (i + 1) * space, drawY);
        }
        //干擾線
        for (int i = 0; i < lineCount; i++) {
            graphics.setColor(setColor());
            int dot[] = setLineDot();
            graphics.drawLine(dot[0], dot[1], dot[2], dot[3]);
        }
        //禁止回應緩存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //回應寫出
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
        session.setAttribute("captcha", buffer.toString());
        System.out.println("圖形驗證碼:" + buffer);
    }

    //隨機顏色
    private Color setColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }

    //繪製干擾線
    private int[] setLineDot() {
        Random random = new Random();
        int x1 = random.nextInt(width / 2);
        int y1 = random.nextInt(height);
        int x2 = random.nextInt(width);
        int y2 = random.nextInt(height);
        return new int[]{x1, y1, x2, y2};
    }
}
