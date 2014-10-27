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
		jstring json, jobjectArray argNames, jobjectArray argValues) {

	const char *str = env->GetStringUTFChars(json, NULL);

	Pipeline pipeline(str);

	cv::Mat* image = (cv::Mat*) nativeMat;

	//Create an argMap.  Can be empty.
	ArgMap argMap;
	if (argNames != NULL) {
		if (argValues == NULL) {
			 env->ThrowNew(env->FindClass("java/lang/IllegalArgumentException"), "argNames and argValues should have equal sizes!");
			 return NULL;
		}
		jsize len = env->GetArrayLength(argNames);
		if (env->GetArrayLength(argValues) != len) {
			 env->ThrowNew(env->FindClass("java/lang/IllegalArgumentException"), "argNames and argValues should have equal sizes!");
			 return NULL;
		}
		for (int i = 0; i < len ; i++) {
			const char* argName = env->GetStringUTFChars((jstring)env->GetObjectArrayElement(argNames, i), NULL);
			const char* argValue = env->GetStringUTFChars((jstring)env->GetObjectArrayElement(argValues, i), NULL);
			argMap[argName] = argValue;
		}
	}

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

