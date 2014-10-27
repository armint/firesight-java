package org.firepick.firesight;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class OpenCvUtils {
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }
    
    /*
     * Notes
     * cvtColor(src,src,CV_8UC1); may come in handy later
     * 
     *                 BufferedImage resultImage = new BufferedImage(result.width(),
                        result.height(), BufferedImage.TYPE_USHORT_GRAY);
     */
    
    /**
     * TODO: This probably doesn't work right on submats. Need to test and fix.
     * @param m
     * @return
     */
    public static BufferedImage toBufferedImage(Mat m) {
        Integer type = null;
        if (m.type() == CvType.CV_8UC1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        }
        else if (m.type() == CvType.CV_8UC3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        if (type == null) {
            throw new Error(String.format("Unsupported Mat: type %d, channels %d, depth %d", m.type(), m.channels(), m.depth()));
        }
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        m.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }
    
    public static Mat toMat(BufferedImage img) {
        Integer type = null;
        if (img.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            type = CvType.CV_8UC1;
        }
        else if (img.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            type = CvType.CV_8UC3;
        }
        else {
            img = convertBufferedImage(img, BufferedImage.TYPE_3BYTE_BGR);
            type = CvType.CV_8UC3;
        }
        Mat mat = new Mat(img.getHeight(), img.getWidth(), type);
        mat.put(0, 0, ((DataBufferByte) img.getRaster().getDataBuffer()).getData());
        return mat;
    }

    /**
     * Convert a BufferedImage from it's current type to a new, specified type
     * by creating a new BufferedImage and drawing the source image onto it. If
     * the image is already of the specified type it is returned unchanged.
     * 
     * @param src
     * @param type
     * @return
     */
    public static BufferedImage convertBufferedImage(BufferedImage src, int type) {
        if (src.getType() == type) {
            return src;
        }
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(),
                type);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return img;
    }
    
    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("/Users/jason/Pictures/Mario_Love_5.jpg"));
        Mat mat = toMat(image);
        image = toBufferedImage(mat);
        image = convertBufferedImage(image, BufferedImage.TYPE_BYTE_GRAY);
        mat = toMat(image);
        image = toBufferedImage(mat);
        ImageIO.write(image, "PNG", new File("/Users/jason/Desktop/Mario_Love_5.jpg"));
    }
}
