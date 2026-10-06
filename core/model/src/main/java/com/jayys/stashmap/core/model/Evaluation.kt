package com.jayys.stashmap.core.model

/**
 * 행동 기반 평가 4상태
 *
 * 앞 3개는 방문 후 평가, [WantToTry] 는 방문 전 위시리스트
 */
enum class Evaluation {
    Favorite,
    Average,
    Avoid,
    WantToTry,
}
