package mobi.lab.sample.app.common

import java.time.OffsetDateTime

fun OffsetDateTime.toEpochMilli() = this.toInstant().toEpochMilli()
