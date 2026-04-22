package com.rpg_saba;

import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.entity.EntityType;

public class StoneProjectile extends ThrownItemEntity {

    public StoneProjectile(EntityType<? extends StoneProjectile> type, World world) {
        super(type, world);
    }

    public StoneProjectile(World world, LivingEntity owner) {
        super(ModEntities.STONE_PROJECTILE, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.POINTED_DRIPSTONE;
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        super.onEntityHit(hit);

        Entity target = hit.getEntity();

        target.damage(
                this.getDamageSources().thrown(this, this.getOwner()),
                8.0F
        );
    }
}