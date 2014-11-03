package org.firepick.firesight;

import org.opencv.core.Point;
import org.opencv.core.Range;

public class MatchedRegion {

	public Range xRange;
	public Range yRange;
	public Point average;
	public int pointCount;
	public double covar;

	public MatchedRegion() {
	}

	public MatchedRegion(Range xRange, Range yRange, Point average, int pointCount, float covar) {
		this.xRange = xRange;
		this.yRange = yRange;
		this.average = average;
		this.pointCount = pointCount;
		this.covar = covar;
	}
	
	protected void fillFromNative(long nativeObject) {
		int[] xRangeFromNative = getXRangeFromNative(nativeObject);
		xRange = new Range(xRangeFromNative[0], xRangeFromNative[1]);
		int[] yRangeFromNative = getYRangeFromNative(nativeObject);
		yRange = new Range(yRangeFromNative[0], yRangeFromNative[1]);
		double[] averageFromNative = getAverageFromNative(nativeObject);
		average = new Point(averageFromNative);
		pointCount = getPointCountFromNative(nativeObject);
		covar = getCovarFromNative(nativeObject);
	}
	
	protected static MatchedRegion fromNative(long nativeObject)  {
		MatchedRegion matchedRegion = new MatchedRegion();
		matchedRegion.fillFromNative(nativeObject);
		return matchedRegion;
	}

	protected static native long createNative(int xRangeStart, int xRangeEnd, int yRangeStart, int yRangeEnd, double avarageX, double averageY, int pointCount, float covar);
	protected static native void releaseNative(long nativeObject);
	
	protected static native int[] getXRangeFromNative(long nativeObject);
	protected static native int[] getYRangeFromNative(long nativeObject);
	protected static native double[] getAverageFromNative(long nativeObject);
	protected static native int getPointCountFromNative(long nativeObject);
	protected static native double getCovarFromNative(long nativeObject);
	

}
