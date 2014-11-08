package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.firepick.firesight.utils.OpenCvUtils;
import org.opencv.core.Mat;

public class HoleRecognizer extends NativeObject {

	public HoleRecognizer(float minDiameter, float maxDiameter) {
		init(minDiameter, maxDiameter);
	}

	public void showMatches(int show) {
		_showMatches(show);
	}

	public Collection<MatchedRegion> scan(Mat matRGB) {
		return scan(matRGB, 1.05f, 2.0f);
	}

	public Collection<MatchedRegion> scan(BufferedImage image) {
		return scan(image, 1.05f, 2.0f);
	}

	public Collection<MatchedRegion> scan(BufferedImage image, float maxEllipse, float maxCovar) {
		Mat mat = OpenCvUtils.toMat(image);
		Collection<MatchedRegion> matches = scan(mat, maxEllipse, maxCovar);
		mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
		mat.release();
		return matches;
	}

	public Collection<MatchedRegion> scan(Mat matRGB, float maxEllipse, float maxCovar) {
		MatchedRegion[] scan = scan(matRGB.nativeObj, maxEllipse, maxCovar);
//		ArrayList<MatchedRegion> matches = new ArrayList<MatchedRegion>(scan.length);
//		for (int i = 0; i < scan.length; i++) {
//			matches.add(MatchedRegion.fromNative(scan[i]));
//			MatchedRegion.releaseNative(scan[i]);
//		}
		return Arrays.asList(scan);

	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (nativeObject != 0) {
			dispose();
		}
	}

	public native void dispose();

	protected native void init(float minDiameter, float maxDiameter);

	protected native void _showMatches(int show);

	protected native MatchedRegion[] scan(long nativeMat, float maxEllipse, float maxCovar);
}
