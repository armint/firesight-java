package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Map;

import nu.pattern.OpenCV;

import org.firepick.firesight.utils.OpenCvUtils;
import org.firepick.firesight.utils.SharedLibLoader;
import org.opencv.core.Mat;

public class FireSight {
	private final static String[] LIBS = new String[] { "zbar", "_firesight", "firesight-java" };
    static {
		OpenCV.loadShared();
    	SharedLibLoader.loadLibraries(LIBS);
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
		return process(image, json, null);
	}

	public static String process(BufferedImage image, String json, Map<String, String> argMap) {
		Mat mat = OpenCvUtils.toMat(image);
		String[] argNames = null;
		String[] argValues = null;
		if (argMap != null) {
			int index = 0;
			argNames = new String[argMap.size()];
			argValues = new String[argMap.size()];
			for (Map.Entry<String, String> e : argMap.entrySet()) {
				argNames[index] = e.getKey();
				argValues[index] = e.getValue();
				index++;
			}
		}
		String result = process(mat.nativeObj, json, argNames, argValues);
		mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
		return result;
	}
	
	protected static native String process(long nativeMat, String json, String[] argNames, String[] argValues);
}
