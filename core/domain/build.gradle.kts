plugins {
    id("stashmap.jvm.library")
}

dependencies {
    // SettingsRepository 가 StashMapLanguage 를 공개 시그니처로 내보낸다 → api
    api(project(":core:model"))
}
