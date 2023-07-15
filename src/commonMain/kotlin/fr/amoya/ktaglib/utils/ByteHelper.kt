package fr.amoya.ktaglib.utils

import kotlin.reflect.KClass

/*
* fr.amoya.ktaglib.utils
* As a part of the Project k-taglib
* @Author Arnaud Moya : <contact@amoya.fr>
* Created on 01/05/2021
*/

object ByteHelper {

    @ExperimentalUnsignedTypes
    inline fun <reified T : Any> aggregateBytes(
        rawData: ByteArray,
        numberOfBytes: Int,
        toClass: KClass<T>,
    ): T = when (toClass) {
        Long::class -> aggregateBytesToLong(rawData, numberOfBytes)
        ULong::class -> aggregateBytesToULong(rawData, numberOfBytes)
        Int::class -> aggregateBytesToLong(rawData, numberOfBytes).toInt()
        UInt::class -> aggregateBytesToULong(rawData, numberOfBytes).toUInt()
        String::class -> aggregateBytesToString(rawData, numberOfBytes)
        else -> throw Exception("Type ${toClass.simpleName} is not supported")
    }.run { if (this is T) this else throw Exception("Could not aggregate bytes") }

    @ExperimentalUnsignedTypes
    fun aggregateBytesToULong(rawData: ByteArray, numberOfBytes: Int = 8, offset: Int = 0): ULong {
        require(numberOfBytes <= rawData.size)
        require(numberOfBytes in 1..rawData.size) { "The Long type is able to contain only 1 to 8 bytes" }
        return rawData.copyOfRange(offset, offset + numberOfBytes).toUByteArray()
            .fold(0x00uL) { aggregate, nextByte -> aggregate shl (8) or nextByte.toULong() }
    }

    fun aggregateBytesToLong(rawData: ByteArray, numberOfBytes: Int = 8, offset: Int = 0): Long {
        require(numberOfBytes <= rawData.size)
        require(numberOfBytes in 1..8) { "The Long type is able to contain only 1 to 8 bytes" }
        return rawData.copyOfRange(offset, offset + numberOfBytes)
            .fold(0L) { aggregate, nextByte -> aggregate shl (8) or nextByte.toLong() }
    }

    fun aggregateBytesToString(rawData: ByteArray, numberOfBytes: Int, offset: Int = 0): String {
        require(numberOfBytes in 1..rawData.size) { "You must read at least one Byte" }
        return rawData.decodeToString(offset, numberOfBytes)
    }

    fun synchSafeBytesToUInt(rawSize: ByteArray): UInt = synchSafeBytesToLong(rawSize).toUInt()

    private fun synchSafeBytesToLong(rawSize: ByteArray): Long {
        require(rawSize.size == 4) { "ByteArray containing tag size must be 4 Bytes long" }
        var result: Long = 0
        rawSize.forEachIndexed { index, byte ->
            if (byte.toUByte() and 0x80u > 0u) throw NumberFormatException("Synch-unsafe size is not supported")
            result = result or ((byte.toLong() and 0x7f) shl (((rawSize.size - 1) - index) * 7))
        }
        return result
    }
}
