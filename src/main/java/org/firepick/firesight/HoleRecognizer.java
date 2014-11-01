package org.firepick.firesight;

import java.util.Collection;

import org.opencv.core.Mat;

public class HoleRecognizer {
	protected long nativeObject;

	public HoleRecognizer(float minDiameter, float maxDiameter) {
		nativeObject = init(minDiameter, maxDiameter);
	}

	public void showMatches(int show) {
		_showMatches(nativeObject, show);
	}

	public void scan(Mat matRGB, Collection<MatchedRegion> matches) {
		scan(matRGB, matches, 1.05f, 2.0f);
	}

	public void scan(Mat matRGB, Collection<MatchedRegion> matches, float maxEllipse, float maxCovar) {

	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (nativeObject != 0) {
			dispose();
		}
	}

	public void dispose() {
		release(nativeObject);
		nativeObject = 0;
	}

	public static native void release(long nativeObject);

	private static native long init(float minDiameter, float maxDiameter);

	private static native void _showMatches(long nativeObject, int show);

}
