package com.faisaldev.wallet.utils

import java.time.Instant



class UniqueIdGeneratorBitManipulation(private val datacenterId: Long, private val nodeId: Long) {

    companion object {
        private val EPOCH_OFFSET = Instant.parse("2024-01-01T00:00:00Z").toEpochMilli()
        private const val DATACENTER_ID_BITS = 5
        private const val NODE_ID_BITS = 5
        private const val SEQUENCE_BITS = 12

        private const val MAX_DATACENTER_ID = (1L shl DATACENTER_ID_BITS) - 1
        private const val MAX_NODE_ID = (1L shl NODE_ID_BITS) - 1
        private const val MAX_SEQUENCE = (1L shl SEQUENCE_BITS) - 1
    }

    private var lastTimestamp = -1L
    private var sequence = 0L

    init {
        require(datacenterId in 0..MAX_DATACENTER_ID) {
            "Datacenter ID must be between 0 and $MAX_DATACENTER_ID"
        }
        require(nodeId in 0..MAX_NODE_ID) {
            "Node ID must be between 0 and $MAX_NODE_ID"
        }
    }

    @Synchronized
    fun generateUniqueId(): Long {
        var currentTimestamp = System.currentTimeMillis()

        if (currentTimestamp < lastTimestamp) {
            throw IllegalStateException("Clock moved backwards, refusing to generate ID for ${lastTimestamp - currentTimestamp} milliseconds")
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) % (MAX_SEQUENCE + 1)
            if (sequence == 0L) {
                currentTimestamp = getNextTimestamp()
            }
        } else {
            sequence = 0L // Reset sequence
        }

        lastTimestamp = currentTimestamp
        val timestamp = currentTimestamp - EPOCH_OFFSET

        return (timestamp shl (DATACENTER_ID_BITS + NODE_ID_BITS + SEQUENCE_BITS)) or
                (datacenterId shl (NODE_ID_BITS + SEQUENCE_BITS)) or
                (nodeId shl SEQUENCE_BITS) or
                sequence
    }

    private fun getNextTimestamp(): Long {
        var timestamp = System.currentTimeMillis()
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }
}
