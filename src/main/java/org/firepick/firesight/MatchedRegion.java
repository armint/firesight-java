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

	public MatchedRegion(Range xRange, Range yRange, Point average, int pointCount, double covar) {
		this.xRange = xRange;
		this.yRange = yRange;
		this.average = average;
		this.pointCount = pointCount;
		this.covar = covar;
	}
	

}
