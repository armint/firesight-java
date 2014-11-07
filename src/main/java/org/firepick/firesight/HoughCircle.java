package org.firepick.firesight;

import java.util.Arrays;
import java.util.Collection;

import org.opencv.core.Mat;

public class HoughCircle extends NativeObject {
	
	public HoughCircle(int minDiameter, int maxDiameter) {
		init(minDiameter, maxDiameter);
	}

	public Collection<Circle> scan(Mat matRGB) {
		Circle[] scan = scan(matRGB.nativeObj);
		return Arrays.asList(scan);
	}

	protected native void init(int minDiameter, int maxDiameter);
	
	/**
	 * Update the working image to show detected circles. Image must have at least three channels representing RGB values.
	 * 
	 * @param show
	 *            matched regions. Default is CIRCLE_SHOW_NONE
	 */
	public native void setShowCircles(int show);
	public native void setFilterParams(int d, double sigmaColor, double sigmaSpace);
	public native void setHoughParams(double dp, double minDist, double param1, double param2);
	protected native Circle[] scan(long nativeMat);

	@Override
	public native void dispose();
}
