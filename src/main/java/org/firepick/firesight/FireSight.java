package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;

public class FireSight {
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary("_firesight");
        System.loadLibrary("firesight-java");
 
    }
    
	/**
	 * Process a buffered image based on a json description
	 *  
	 * @param image the image to process. The original image will be overwritten with the processed one
	 * @param json the processing description
	 * 
	 * @return json status information from FireSight
	 */
	public static String process(BufferedImage image, String json) {
		Mat mat = OpenCvUtils.toMat(image);
		String result = process(mat.nativeObj, json);
		mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
		return result;
	}

	public static native String process(long nativeMat, String json);
}
