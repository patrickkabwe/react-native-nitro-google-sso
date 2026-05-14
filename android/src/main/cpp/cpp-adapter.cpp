#include <fbjni/fbjni.h>
#include <jni.h>

#include "NitroGoogleSsoOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
    return facebook::jni::initialize(vm, []() {
        margelo::nitro::nitrogooglesso::registerAllNatives();
    });
}
