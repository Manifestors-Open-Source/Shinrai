package com.manifestors.shinrai.client.event.events.player

import com.manifestors.shinrai.client.event.CancellableEvent

class ChatMessageSendEvent(
    val message: String
) : CancellableEvent()
