#include <jni.h>
#include <string>

#ifdef Debug
#include <android/log.h>
#define LOG_TAG "native:"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#endif

extern "C"
JNIEXPORT jstring JNICALL
Java_com_dhx_duhuixiu_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /*this*/,
        jstring str) {
    const char *question = env->GetStringUTFChars(str,JNI_FALSE);
    std::string answer = "this string comes from jni";

#ifdef Debug
LOGE("this is jni log");
#endif

    return env->NewStringUTF(answer.c_str());
}
