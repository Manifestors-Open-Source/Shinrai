package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.module.modules.combat.config.SwordBlockingConfig;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwordBlockingClient {
    public static Map<BipedEntityRenderState, LivingEntity> RENDER_STATE_TO_ENTITY_MAP = new HashMap<>();
    public static List<Item> swordList = new ArrayList<>();
    public static void init() {
        SwordBlockingConfig.init("manifestors", SwordBlockingConfig.class);
        swordList.add(Items.WOODEN_SWORD);
        swordList.add(Items.IRON_SWORD);
        swordList.add(Items.STONE_SWORD);
        swordList.add(Items.GOLDEN_SWORD);
        swordList.add(Items.DIAMOND_SWORD);
        swordList.add(Items.NETHERITE_SWORD);
    }
    public static boolean isEntityBlocking(LivingEntity entity) {
        return SwordBlockingConfig.enabled && entity.isUsingItem() && canShieldSwordBlock(entity);
    }

    public static boolean canShieldSwordBlock(LivingEntity entity) {


        if (SwordBlockingConfig.enabled && (entity.getOffHandStack().getItem() instanceof ShieldItem || entity.getMainHandStack().getItem() instanceof ShieldItem)) {
            Item weaponItem = entity.getOffHandStack().getItem() instanceof ShieldItem ? entity.getMainHandStack().getItem() : entity.getOffHandStack().getItem();
            return weaponItem.getComponents().contains(DataComponentTypes.DAMAGE);
        } else {
            return false;
        }
    }

    public static boolean shouldHideShield(LivingEntity entity, ItemStack stack) {
        return SwordBlockingConfig.enabled && (SwordBlockingConfig.alwaysHideShield && SwordBlockingConfig.hideShield && stack.getItem() instanceof ShieldItem)
                || (SwordBlockingConfig.hideShield && stack.getItem() instanceof ShieldItem && SwordBlockingClient.canShieldSwordBlock(entity));
    }
}
