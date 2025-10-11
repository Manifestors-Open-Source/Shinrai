package com.manifestors.shinrai.client.event

open class CancellableEvent : ConstantEvent() {
    var cancelled = false
}
