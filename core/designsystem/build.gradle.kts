plugins {
    id("stashmap.android.library.compose")
}

android {
    namespace = "com.jayys.stashmap.core.designsystem"
}

dependencies {
    // 매퍼가 Evaluation 을 공개 시그니처로 노출 → implementation 이면 쓰는 쪽에서 타입을 못 본다
    api(project(":core:model"))
}
