plugins {
    id("stashmap.jvm.library")
    // 공용 인터페이스의 Fake 를 다른 모듈에서 쓰려면 src/test 가 아니라 이 소스셋이어야 한다
    `java-test-fixtures`
}

dependencies {
    // SettingsRepository 가 StashMapLanguage 를 공개 시그니처로 내보낸다 → api
    api(project(":core:model"))
}
