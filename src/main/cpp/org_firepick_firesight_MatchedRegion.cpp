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
#include <cstdio>

using namespace firesight;
JNIEXPORT void JNICALL Java_org_firepick_firesight_MatchedRegion_fillFromNative(JNIEnv *env, jobject javaObject,
		jlong nativeObject) {
	jclass clsObj = env->GetObjectClass(javaObject);
	if (clsObj == NULL) {
		env->ThrowNew(env->FindClass("java/lang/IllegalStateException"), "Can't call fillFromNative on null!");
	}
//	   env->GetFieldID(clsObj, );
}

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

