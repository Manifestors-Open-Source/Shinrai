package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.awt.*;

@ModuleData(
        name = "HUD",
        description = "Shows active modules and watermark.",
        category = ModuleCategory.VISUALS
)
public class HUD extends Module {

    @ListenEvent
    public void onRendering2D(Rendering2DEvent event) {
        renderWatermark(event);
        renderArrayList(event);
        renderPotionEffects(event);
    }

    private void renderWatermark(Rendering2DEvent event) {
        event.getContext().drawText(mc.textRenderer, Shinrai.NAME, 2, 3, Color.RED.getRGB(), true);
    }

    private void renderArrayList(Rendering2DEvent event) {
        int yOffset = 0;

        var activeModules = Shinrai.INSTANCE.getModuleManager().getModules().stream()
                .filter(Module::isEnabled)
                .sorted((m1, m2) -> mc.textRenderer.getWidth(m2.getName()) - mc.textRenderer.getWidth(m1.getName()))
                .toList();

        for (Module module : activeModules) {
            int x = event.getWidth() - mc.textRenderer.getWidth(module.getName()) - 3;
            event.getContext().drawText(mc.textRenderer, module.getName(), x, yOffset + 5, Color.RED.getRGB(), true);

            yOffset += 11;
        }
    }

    private void renderPotionEffects(Rendering2DEvent event) {
        var statusEffects = mc.player.getStatusEffects().stream().sorted((e1, e2) ->
                        mc.textRenderer.getWidth(e2.getEffectType().value().getName()) - mc.textRenderer.getWidth(e1.getEffectType().value().getName()))
                .toList();
        int statusCount = 0;
        if (!statusEffects.isEmpty() && (mc.currentScreen == null || !mc.currentScreen.showsStatusEffects())) {
            int yOffset = 0;
            for (var effectInst : statusEffects) {
                var effectName = getStatusEffectDescription(effectInst).getString() + " : " +
                        StatusEffectUtil.getDurationText(effectInst, 1.0F, mc.world.getTickManager().getTickRate()).getLiteralString();

                int x = event.getWidth() - mc.textRenderer.getWidth(effectName) - 3;
                int y = event.getHeight() - 15;

                event.getContext().drawText(mc.textRenderer, effectName, x, y - yOffset, getRandomColor(statusCount), true);
                statusCount++;
                yOffset += 11;
            }
        }
    }

    private Text getStatusEffectDescription(StatusEffectInstance statusEffect) {
        MutableText text = statusEffect.getEffectType().value().getName().copy();
        if (statusEffect.getAmplifier() >= 1 && statusEffect.getAmplifier() <= 9) {
            MutableText spacing = text.append(ScreenTexts.SPACE);
            int statusEffectAmplifier = statusEffect.getAmplifier();
            spacing.append(Text.translatable("enchantment.level." + (statusEffectAmplifier + 1)));
        }

        return text;
    }

    private int getRandomColor(int index) {
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.ORANGE, Color.PINK, Color.YELLOW, Color.MAGENTA, Color.CYAN, new Color(30, 190, 120)};

        if (index > colors.length - 1)
            index = 0;

        return colors[index].getRGB();
    }

}
