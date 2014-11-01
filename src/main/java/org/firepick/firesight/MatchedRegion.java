package org.firepick.firesight;

import org.opencv.core.Point;
import org.opencv.core.Range;

public class MatchedRegion {

	public Range xRange;
	public Range yRange;
	public Point average;
	public int pointCount;
	public float covar;

	public MatchedRegion() {
	}

	protected MatchedRegion(int xRangeStart, int xRangeEnd, int yRangeStart, int yRangeEnd, double avarageX, double averageY, int pointCount, float covar) {
		xRange = new Range(xRangeStart, xRangeEnd);
		yRange = new Range(yRangeStart, yRangeEnd);
		average = new Point(avarageX, averageY);
		this.pointCount = pointCount;
		this.covar = covar;
	}

	public MatchedRegion(Range xRange, Range yRange, Point average, int pointCount, float covar) {
		this.xRange = xRange;
		this.yRange = yRange;
		this.average = average;
		this.pointCount = pointCount;
		this.covar = covar;
	}

	private native void fillFromNative(long nativeObject);
	protected static native long createNative(int xRangeStart, int xRangeEnd, int yRangeStart, int yRangeEnd, double avarageX, double averageY, int pointCount, float covar);
	protected static native void releaseNative(long nativeObject);
}
