# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

# ========== Retrofit & OkHttp ==========
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# ========== Kotlinx Serialization ==========
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.kuit.findu.**$$serializer { *; }
-keepclassmembers class com.kuit.findu.** {
    *** Companion;
}
-keepclasseswithmembers class com.kuit.findu.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep all data classes and their properties
-keep class com.kuit.findu.data.** { *; }
-keep class com.kuit.findu.domain.** { *; }

# ========== Hilt ==========
-dontwarn com.google.errorprone.annotations.**
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.internal.GeneratesRootInput { *; }
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel { *; }

# ========== Glide ==========
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# ========== Naver Map SDK ==========
-keep class com.naver.maps.** { *; }
-keep interface com.naver.maps.** { *; }
-dontwarn com.naver.maps.**

# ========== CameraX ==========
-keep class androidx.camera.** { *; }
-dontwarn androidx.camera.**

# ========== Kakao SDK ==========
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class com.kakao.sdk.auth.** { *; }
-keep class com.kakao.sdk.user.** { *; }
-keep class com.kakao.sdk.common.** { *; }

# ========== Firebase ==========
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# ========== Jetpack Compose ==========
-keep class androidx.compose.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-dontwarn androidx.compose.**

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}

# ========== AndroidX Navigation ==========
-keep class androidx.navigation.fragment.NavHostFragment { *; }
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable

# ========== Material Components ==========
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# ========== ViewBinding & DataBinding ==========
-keep class * implements androidx.viewbinding.ViewBinding {
    public static * inflate(android.view.LayoutInflater);
    public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
    public static * bind(android.view.View);
}

# ========== Lottie ==========
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# ========== WebView ==========
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# ========== Kotlin ==========
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

# ========== Coroutines ==========
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# ========== R8 Full Mode ==========
-allowaccessmodification
-repackageclasses
