#include <jni.h>
#include "NitroGoogleSsoOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
  return margelo::nitro::nitrogooglesso::initialize(vm);
}
