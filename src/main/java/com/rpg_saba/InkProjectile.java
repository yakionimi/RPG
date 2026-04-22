package com.rpg_saba;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class InkProjectile extends ThrownItemEntity {

    public InkProjectile(EntityType<? extends ThrownItemEntity> type, World world) {
        super(type, world);
    }

    public InkProjectile(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.INK_SAC;
    }

    @Override
    protected void onEntityHit(net.minecraft.util.hit.EntityHitResult hit) {
        super.onEntityHit(hit);

        if (hit.getEntity() instanceof LivingEntity target) {
            target.damage(this.getDamageSources().thrown(this, this.getOwner()), 6.0f);
        }

        this.discard();
    }
}