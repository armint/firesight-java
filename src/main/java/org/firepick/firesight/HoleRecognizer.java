package org.firepick.firesight;

import java.util.ArrayList;
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

	public Collection<MatchedRegion> scan(Mat matRGB) {
		return scan(matRGB, 1.05f, 2.0f);
	}

	public Collection<MatchedRegion> scan(Mat matRGB, float maxEllipse, float maxCovar) {
		long[] scan = scan(nativeObject, matRGB.nativeObj, maxEllipse, maxCovar);
		ArrayList<MatchedRegion> matches = new ArrayList<MatchedRegion>(scan.length);
		for (int i = 0; i < scan.length; i++) {
			matches.add(MatchedRegion.fromNative(scan[i]));
			MatchedRegion.releaseNative(scan[i]);
		}
		return matches;

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

	private static native long[] scan(long nativeObject, long nativeMat, float maxEllipse, float maxCovar);
}
