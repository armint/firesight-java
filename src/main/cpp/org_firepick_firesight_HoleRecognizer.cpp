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


jobject createRange(JNIEnv *env, int s, int e) {
	jclass cls = env->FindClass("org/opencv/core/Range");
	jmethodID constructor = env->GetMethodID(cls, "<init>", "(II)V");
	jobject range = env->NewObject(cls, constructor, s, e);
	return range;
}

jobject createPoint(JNIEnv *env, int x, int y) {
	jclass cls = env->FindClass("org/opencv/core/Point");
	jmethodID constructor = env->GetMethodID(cls, "<init>", "(II)V");
	jobject point = env->NewObject(cls, constructor, x, y);
	return point;
}

jobject createMatchedRegion(JNIEnv *env, jobject& rangeX, jobject& rangeY, jobject& average, int pointCoint, jdouble covar) {
	jclass cls = env->FindClass("org/firepick/firesight/MatchedRegion");
	jmethodID constructor = env->GetMethodID(cls, "<init>", "(LLLID)V");
	jobject matchedRegion = env->NewObject(cls, constructor, rangeX, rangeY, average, pointCoint, covar);
	return matchedRegion;
}


JNIEXPORT jobjectArray JNICALL Java_org_firepick_firesight_HoleRecognizer_scan(JNIEnv *env, jobject javaObject,
		jlong nativeMat, jfloat maxEllipse, jfloat maxCovar) {
	HoleRecognizer* holeRecognizer = getNativeObjectPointer<HoleRecognizer>(env, javaObject);
	Mat *mat = (Mat*) nativeMat;
	std::vector<MatchedRegion> matches;
	holeRecognizer->scan(*mat, matches, maxEllipse, maxCovar);
	jobjectArray result = env->NewObjectArray(matches.size(), env->FindClass("org/firepick/firesight/MatchedRegion"), 0);
	for (size_t i = 0; i < matches.size(); i++) {
		jobject xRange = createRange(env, matches[i].xRange.start, matches[i].xRange.end);
		jobject yRange = createRange(env, matches[i].yRange.start, matches[i].yRange.end);
		jobject average = createPoint(env, matches[i].average.x, matches[i].average.y);
		jobject region = createMatchedRegion(env, xRange, yRange, average, matches[i].pointCount, matches[i].covar);
		env->SetObjectArrayElement(result, i, region);
	}
	return result;
}
