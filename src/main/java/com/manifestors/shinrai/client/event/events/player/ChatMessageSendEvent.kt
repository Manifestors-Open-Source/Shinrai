package com.manifestors.shinrai.client.event.events.player;

import com.manifestors.shinrai.client.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageSendEvent extends CancellableEvent {

    private final String message;

}
