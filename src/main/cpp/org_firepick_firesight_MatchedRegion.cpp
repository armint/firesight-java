/*
 * org_firepick_firesight_MatchedRegion.cpp
 *
 *  Created on: Nov 2, 2014
 *      Author: armin
 */

#include "org_firepick_firesight_MatchedRegion.h"

#include <FireSight.hpp>
#include <jni.h>
#include <jni_md.h>
#include <opencv2/core/core.hpp>
#include <opencv2/core/operations.hpp>

using namespace firesight;

JNIEXPORT jlong JNICALL Java_org_firepick_firesight_MatchedRegion_createNative(JNIEnv *, jclass, jint xRangeStart,
		jint xRangeEnd, jint yRangeStart, jint yRangeEnd, jdouble averageX, jdouble averageY, jint pointCount,
		jfloat covar) {
	Range xRange = Range(xRangeStart, xRangeEnd);
	Range yRange = Range(yRangeStart, yRangeEnd);
	Point2f average = Point(averageX, averageY);
	MatchedRegion* region = new MatchedRegion(xRange, yRange, average, pointCount, covar);
	return (jlong) region;
}

JNIEXPORT void JNICALL Java_org_firepick_firesight_MatchedRegion_releaseNative(JNIEnv *, jclass, jlong nativeObject) {
	delete (MatchedRegion*) nativeObject;
}

JNIEXPORT jintArray JNICALL Java_org_firepick_firesight_MatchedRegion_getXRangeFromNative(JNIEnv * env, jclass,
		jlong nativeObject) {
	MatchedRegion* region = (MatchedRegion*) nativeObject;
	jintArray result = env->NewIntArray(2);
	jint* resultVals = env->GetIntArrayElements(result, 0);
	resultVals[0] = region->xRange.start;
	resultVals[1] = region->xRange.end;
	env->ReleaseIntArrayElements(result, resultVals, 0);
	return result;
}

JNIEXPORT jintArray JNICALL Java_org_firepick_firesight_MatchedRegion_getYRangeFromNative(JNIEnv * env, jclass,
		jlong nativeObject) {
	MatchedRegion* region = (MatchedRegion*) nativeObject;
	jintArray result = env->NewIntArray(2);
	jint* resultVals = env->GetIntArrayElements(result, 0);
	resultVals[0] = region->yRange.start;
	resultVals[1] = region->yRange.end;
	env->ReleaseIntArrayElements(result, resultVals, 0);
	return result;
}

JNIEXPORT jdoubleArray JNICALL Java_org_firepick_firesight_MatchedRegion_getAverageFromNative(JNIEnv * env, jclass,
		jlong nativeObject) {
	MatchedRegion* region = (MatchedRegion*) nativeObject;
	jdoubleArray result = env->NewDoubleArray(2);
	jdouble* resultVals = env->GetDoubleArrayElements(result, 0);
	resultVals[0] = region->average.x;
	resultVals[1] = region->average.y;
	env->ReleaseDoubleArrayElements(result, resultVals, 0);
	return result;
}

JNIEXPORT jint JNICALL Java_org_firepick_firesight_MatchedRegion_getPointCountFromNative(JNIEnv *, jclass,
		jlong nativeObject) {
	MatchedRegion* region = (MatchedRegion*) nativeObject;
	return region->pointCount;
}

JNIEXPORT jdouble JNICALL Java_org_firepick_firesight_MatchedRegion_getCovarFromNative(JNIEnv *, jclass,
		jlong nativeObject) {
	MatchedRegion* region = (MatchedRegion*) nativeObject;
	return region->covar;
}
