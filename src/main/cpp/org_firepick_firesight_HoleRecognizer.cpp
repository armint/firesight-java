#include "org_firepick_firesight_HoleRecognizer.h"

#include <FireSight.hpp>
#include <opencv2/core/core.hpp>
#include <vector>

#include "nativeUtil.h"

using namespace firesight;

JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer_init(JNIEnv *env, jobject javaObject,
		jfloat minDiameter, jfloat maxDiameter) {
	HoleRecognizer* hr = new HoleRecognizer(minDiameter, maxDiameter);
	setNativeObjectPointer(env, javaObject, hr);
}
JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer__1showMatches(JNIEnv *env, jobject javaObject,
		jint show) {
	HoleRecognizer* hr = getNativeObjectPointer<HoleRecognizer>(env, javaObject);
	hr->showMatches(show);
}

JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer_dispose(JNIEnv *env, jobject javaObject) {
	HoleRecognizer* hr = getNativeObjectPointer<HoleRecognizer>(env, javaObject);
	delete hr;
	setNativeObjectPointer<HoleRecognizer>(env, javaObject, 0);
}

JNIEXPORT jlongArray JNICALL Java_org_firepick_firesight_HoleRecognizer_scan(JNIEnv *env, jobject javaObject,
		jlong nativeMat, jfloat maxEllipse, jfloat maxCovar) {
	HoleRecognizer* holeRecognizer = getNativeObjectPointer<HoleRecognizer>(env, javaObject);
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
