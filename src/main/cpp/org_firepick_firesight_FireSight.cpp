/*
 * firesight_glue.cpp
 *
 *  Created on: Oct 27, 2014
 *      Author: armin
 */

#include "org_firepick_firesight_FireSight.h"

#include <FireSight.hpp>
#include <jansson.h>
#include <jni.h>
#include <jni_md.h>
#include <opencv2/core/core.hpp>
#include <stdlib.h>

using namespace firesight;
JNIEXPORT jstring JNICALL Java_org_firepick_firesight_FireSight_process(JNIEnv *env, jclass, jlong nativeMat,
		jstring json) {

	const char *str = env->GetStringUTFChars(json, 0);

	Pipeline pipeline(str);

	cv::Mat* image = (cv::Mat*) nativeMat;

	//Create an argMap.  Can be empty.
	ArgMap argMap;

	//Process the pipeline
	json_t *pModel = pipeline.process(*image, argMap);

	char *pModelStr = json_dumps(pModel, JSON_PRESERVE_ORDER | JSON_COMPACT | JSON_INDENT(3));
	jstring retval = env->NewStringUTF(pModelStr);
	free(pModelStr);

	// Free model
	json_decref(pModel);
	env->ReleaseStringUTFChars(json, str);
	return retval;
}

