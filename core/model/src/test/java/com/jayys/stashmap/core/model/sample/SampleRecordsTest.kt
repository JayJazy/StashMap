package com.jayys.stashmap.core.model.sample

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * [SampleRecords] 좌표 위생 테스트
 *
 * 좌표는 소비자가 아직 없어 틀려도 드러나지 않는다 (위·경도 맞바꿈이 대표적)
 * → Phase 2 지도에서 바다에 핀이 꽂히기 전에 bbox 로 걸러둔다
 *
 * TODO: SampleRecords 가 삭제되면 함께 삭제
 */
class SampleRecordsTest {

    @Test
    fun `모든 샘플 좌표가 한국 bbox 안에 있다`() {
        val located = SampleRecords.records.filter { it.latitude != null && it.longitude != null }
        // 비어 있으면 아래 단언이 공허하게 통과한다
        assertTrue("샘플에 좌표가 없으면 bbox 를 검증할 수 없다", located.isNotEmpty())

        located.forEach { record ->
            // 위·경도를 맞바꾸면 위도가 124~132 가 되어 이 범위를 벗어난다
            assertTrue(
                "${record.id} 위도 ${record.latitude} 가 한국 범위(33~39) 밖",
                record.latitude!! in KoreaLatitudes,
            )
            assertTrue(
                "${record.id} 경도 ${record.longitude} 가 한국 범위(124~132) 밖",
                record.longitude!! in KoreaLongitudes,
            )
        }
    }

    @Test
    fun `좌표는 위도와 경도가 함께 있다`() {
        // 한쪽만 채운 기록은 지도에 그릴 수 없다
        val halfFilled = SampleRecords.records
            .filter { (it.latitude == null) != (it.longitude == null) }

        assertEquals(emptyList<String>(), halfFilled.map { it.id })
    }

    private companion object {
        val KoreaLatitudes = 33.0..39.0
        val KoreaLongitudes = 124.0..132.0
    }
}
