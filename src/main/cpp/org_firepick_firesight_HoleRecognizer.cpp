#include "org_firepick_firesight_HoleRecognizer.h"
#include "FireSight.hpp"

using namespace firesight;

JNIEXPORT jlong JNICALL Java_org_firepick_firesight_HoleRecognizer_init(JNIEnv *, jclass, jfloat minDiameter, jfloat maxDiameter) {
	HoleRecognizer* hr = new HoleRecognizer(minDiameter, maxDiameter);
	return (jlong)hr;
}
JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer__1showMatches(JNIEnv *, jclass, jlong nativeObject, jint show) {
	((HoleRecognizer*)nativeObject)->showMatches(show);
}

JNIEXPORT void JNICALL Java_org_firepick_firesight_HoleRecognizer_release
  (JNIEnv *, jclass, jlong nativeObject) {
	delete (HoleRecognizer*)nativeObject;
}
