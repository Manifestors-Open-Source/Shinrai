package com.manifestors.shinrai.client.event.events.game;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.CancellableEvent;
import com.manifestors.shinrai.client.module.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeyPressEvent extends CancellableEvent {

    private int key;

    public void toggleModulesByKey() {
        for (Module module : Shinrai.INSTANCE.getModuleManager().getModules())
            if (module.getKeyCode() == getKey())
                module.toggleModule();
    }

}
