/*
 * nativeUtil.cpp
 *
 *  Created on: Nov 6, 2014
 *      Author: armin
 */

#include <jni.h>
#include <jni_md.h>

template<typename T> T* getNativeObjectPointer(JNIEnv *env, jobject& obj) {
	jclass clazz = env->GetObjectClass(obj);
	jfieldID fieldId = env->GetFieldID(clazz, "nativeObject", "J");
	jlong field = env->GetLongField(obj, fieldId);
	// TODO check for 0, and throw exception
	return (T*) field;
}

template<typename T> void setNativeObjectPointer(JNIEnv *env, jobject& jobj, T* obj) {
	jclass clazz = env->GetObjectClass(jobj);
	jfieldID fieldId = env->GetFieldID(clazz, "nativeObject", "J");
	env->SetLongField(jobj, fieldId, (jlong)obj);
}

