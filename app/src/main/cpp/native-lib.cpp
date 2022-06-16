#include <jni.h>
#include <string>
#include <list>
#include <vector>

extern "C" JNIEXPORT jlongArray JNICALL
Java_com_clover_sieve_MainActivity_sieve(
        JNIEnv* env,
        jobject /* this */,
        jlong n) {

    bool prime_map[n];
    // Initialize all to true
    for (int i = 0; i < n; i++) {
        prime_map[i] = true;
    }

    // Sieve
    for (long i = 2; i < sqrt(n); i++) {
        if (prime_map[i]) {
            int j = i^2;
            while (j <= n) {
                prime_map[j] = false;
                j = j + i;
            }
        }
    }
    // Copy map to found
    std::vector<long> found = { 2 };
    for (int i = 3; i < n; i++) {
        if (prime_map[i]) {
            found.push_back(i);
        }
    }

    // Convert found to an array
    jlong r[found.size()];
    for (int i = 0; i < found.size(); i++) {
        r[i] = found.at(i);
    }

    // Convert array to java
    jlongArray result = (*env).NewLongArray(found.size());
    (*env).SetLongArrayRegion(result, 0, found.size(), r);

    return result;
}