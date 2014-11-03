#include "org_firepick_firesight_HoleRecognizer.h"
#include "FireSight.hpp"

using namespace firesight;

JNIEXPORT jlong JNICALL Java_org_firepick_firesight_HoleRecognizer_init(JNIEnv *, jclass, jfloat minDiameter,
		jfloat maxDiameter) {
	HoleRecognizer* hr = new HoleRecognizer(minDiameter, maxDiameter);
	return (jlong) hr;
}
JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer__1showMatches(JNIEnv *, jclass, jlong nativeObject,
		jint show) {
	((HoleRecognizer*) nativeObject)->showMatches(show);
}

JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer_release(JNIEnv *, jclass, jlong nativeObject) {
	delete (HoleRecognizer*) nativeObject;
}

JNIEXPORT jlongArray JNICALL Java_org_firepick_firesight_HoleRecognizer_scan(JNIEnv *env, jclass, jlong nativeObject,
		jlong nativeMat, jfloat maxEllipse, jfloat maxCovar) {
	HoleRecognizer* holeRecognizer = (HoleRecognizer*) nativeObject;
	Mat& mat = *(Mat*) nativeMat;
	std::vector<MatchedRegion> matches;
	holeRecognizer->scan(mat, matches, maxEllipse, maxCovar);
	jlongArray result = env->NewLongArray(matches.size());
	jlong* resultVals = env->GetLongArrayElements(result, 0);
	for (size_t i = 0; i < matches.size(); i++) {
		resultVals[i] = (jlong) &matches[i];
	}
	env->ReleaseLongArrayElements(result, resultVals, 0);
	return result;
}
