package com.rpg_saba;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.entity.EntityDimensions;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

public class ModEntities {

    public static EntityType<StoneProjectile> STONE_PROJECTILE;
    

    public static void register() {
        STONE_PROJECTILE = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("rpg_saba", "stone_projectile"),
                FabricEntityTypeBuilder.<StoneProjectile>create(SpawnGroup.MISC, StoneProjectile::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .trackRangeBlocks(4)
                        .trackedUpdateRate(10)
                        .build()
        );
    }
}