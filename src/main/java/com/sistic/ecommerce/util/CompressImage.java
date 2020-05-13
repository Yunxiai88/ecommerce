package com.sistic.ecommerce.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class CompressImage {
    private static int width = 634;
    private static int height = 811;

    public static void compress(byte[] data, String target) throws IOException {
        Image img = ImageIO.read(new ByteArrayInputStream(data));

        Image tempImage = img.getScaledInstance(width, height, Image.SCALE_REPLICATE);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) resized.getGraphics();
        g2d.drawImage(tempImage, 0, 0, null);
        g2d.dispose();

        File output = new File(target);
        ImageIO.write(resized, "jpg", output);
    }
}