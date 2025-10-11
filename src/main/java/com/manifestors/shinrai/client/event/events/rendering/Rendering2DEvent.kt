package com.manifestors.shinrai.client.event.events.rendering;

import com.manifestors.shinrai.client.event.ConstantEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.DrawContext;

@Getter
@AllArgsConstructor
public class Rendering2DEvent extends ConstantEvent {

    private final DrawContext context;
    private int width;
    private int height;

}
