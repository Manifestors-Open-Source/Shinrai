package com.manifestors.shinrai.client.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancellableEvent extends ConstantEvent {

    private boolean cancelled;

}
