package com.manifestors.shinrai.client.event.events.player

import com.manifestors.shinrai.client.event.CancellableEvent
import lombok.AllArgsConstructor
import lombok.Getter

class ChatMessageSendEvent(
    val message: String
) : CancellableEvent()
