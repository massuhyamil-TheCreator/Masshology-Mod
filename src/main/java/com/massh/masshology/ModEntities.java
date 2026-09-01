package com.massh.masshology;

import com.massh.masshology.entity.cabrunu.CabrunuEntity;
import com.massh.masshology.entity.draugr.DraugrEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MasshologyMasshbestiary.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<DraugrEntity>> DRAUGR_WARRIOR =
            ENTITY_TYPES.register("draugr_warrior",
                    () -> EntityType.Builder.of(DraugrEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(MasshologyMasshbestiary.MODID, "draugr_warrior"))));
    public static final net.neoforged.neoforge.registries.DeferredItem<net.minecraft.world.item.SpawnEggItem> DRAUGR_WARRIOR_SPAWN_EGG =
            MasshologyMasshbestiary.ITEMS.registerItem("draugr_warrior_spawn_egg",
                    properties -> new net.minecraft.world.item.SpawnEggItem(
                            properties.spawnEgg(DRAUGR_WARRIOR.get())                    ));
    public static final DeferredHolder<EntityType<?>, EntityType<CabrunuEntity>> CABRUNU =
            ENTITY_TYPES.register("cabrunu",
                    () -> EntityType.Builder.of(CabrunuEntity::new, MobCategory.CREATURE)
                            .sized(1.125f, 2.375f)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(MasshologyMasshbestiary.MODID, "cabrunu"))));
    public static final net.neoforged.neoforge.registries.DeferredItem<net.minecraft.world.item.SpawnEggItem> CABRUNU_SPAWN_EGG =
            MasshologyMasshbestiary.ITEMS.registerItem("cabrunu_spawn_egg",
                    properties -> new net.minecraft.world.item.SpawnEggItem(
                            properties.spawnEgg(CABRUNU.get())));

    public static void registerAttributes(net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent event) {
        event.put(DRAUGR_WARRIOR.get(), DraugrEntity.createAttributes().build());
        event.put(CABRUNU.get(), CabrunuEntity.createAttributes().build());
    }
}