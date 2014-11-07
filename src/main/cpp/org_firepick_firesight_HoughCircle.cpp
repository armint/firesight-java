#include "org_firepick_firesight_HoughCircle.h"

#include <FireSight.hpp>
#include <jni.h>
#include <jni_md.h>
#include <opencv2/core/core.hpp>
#include <iterator>
#include <vector>

#include "nativeUtil.h"

using namespace firesight;

JNIEXPORT void JNICALL Java_org_firepick_firesight_HoughCircle_init(JNIEnv *env, jobject javaObject, jint minDiameter,
		jint maxDiameter) {
	HoughCircle *c = new HoughCircle(minDiameter, maxDiameter);
	setNativeObjectPointer(env, javaObject, c);
}

JNIEXPORT jobjectArray JNICALL Java_org_firepick_firesight_HoughCircle_scan(JNIEnv *env, jobject javaObject,
		jlong nativeMat) {
	HoughCircle *c = getNativeObjectPointer<HoughCircle>(env, javaObject);
	Mat& m = *(Mat*) nativeMat;
	vector<Circle> circles;
	c->scan(m, circles);
	jobjectArray array = env->NewObjectArray(circles.size(), env->GetObjectClass(javaObject), 0);
	int index = 0;
	for (vector<Circle>::iterator i = circles.begin(); i < circles.end(); i++) {
		jclass cls = env->FindClass("org/firepick/firesight/Circle");
		jmethodID constructor = env->GetMethodID(cls, "<init>", "(FFF)V");
		jobject circle = env->NewObject(cls, constructor, i->x, i->y, i->radius);
		env->SetObjectArrayElement(array, index, circle);
		index++;
	}
	return array;
}
JNIEXPORT void JNICALL Java_org_firepick_firesight_HoughCircle_setShowCircles(JNIEnv *env, jobject javaObject,
		jint show) {
	HoughCircle *c = getNativeObjectPointer<HoughCircle>(env, javaObject);
	c->setShowCircles(show);
}

JNIEXPORT void JNICALL Java_org_firepick_firesight_HoughCircle_setFilterParams(JNIEnv *env, jobject javaObject, jint d,
		jdouble sigmaColor, jdouble sigmaSpace) {
	HoughCircle *c = getNativeObjectPointer<HoughCircle>(env, javaObject);
	c->setFilterParams(d, sigmaColor, sigmaSpace);
}
JNIEXPORT void JNICALL Java_org_firepick_firesight_HoughCircle_setHoughParams(JNIEnv *env, jobject javaObject, jdouble dp, jdouble minDist, jdouble param1, jdouble param2) {
	HoughCircle *c = getNativeObjectPointer<HoughCircle>(env, javaObject);
	c->setHoughParams(dp, minDist, param1, param2);
}
JNIEXPORT void JNICALL Java_org_firepick_firesight_HoughCircle_dispose(JNIEnv *env, jobject javaObject) {
	HoughCircle *c = getNativeObjectPointer<HoughCircle>(env, javaObject);
	delete c;
	setNativeObjectPointer<HoughCircle>(env, javaObject, 0);

}
