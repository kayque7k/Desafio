package com.wolfdeveloper.wolfdevlovers.commons.extensions

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


private const val SIZE = 256

@Throws(Exception::class)
fun String.compress(): String {
    if (isEmpty()) {
        return this
    }
    val out = ByteArrayOutputStream()
    val gizp = GZIPOutputStream(out)
    gizp.write(this.toByteArray(charset(UTF_8)))
    gizp.close()
    return URLEncoder.encode(out.toString(ISO_8859_1), UTF_8)
}

@Throws(Exception::class)
fun String.decompress(): String {
    val decode = URLDecoder.decode(this, UTF_8)
    val gis = GZIPInputStream(ByteArrayInputStream(decode.toByteArray(charset(ISO_8859_1))))
    val out = ByteArrayOutputStream()
    var n: Int
    val buffer = ByteArray(SIZE)
    while (gis.read(buffer).also { n = it } >= ZERO) {
        out.write(buffer, ZERO, n)
    }
    return out.toString()
}