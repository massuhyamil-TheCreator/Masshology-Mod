package com.massh.masshology.entity.cabrunu;

import com.google.common.collect.ImmutableList;
import com.massh.masshology.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import net.minecraft.world.entity.ai.Brain;
import com.mojang.serialization.Dynamic;

public class CabrunuEntity extends Animal implements Shearable {
    public CabrunuEntity(EntityType<? extends CabrunuEntity> entityType, Level level) {
        super(entityType, level);
    }

    protected static final ImmutableList<SensorType<? extends Sensor<? super CabrunuEntity>>> SENSOR_TYPES =
            ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES =
            ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.PATH,
                    MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryModuleType.RAM_TARGET,
                    MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryModuleType.LONG_JUMP_MID_JUMP);

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }
    protected Brain.Provider<CabrunuEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return CabrunuAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }
    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModBlocks.MUSGO_GLACIAL_ITEM.get());
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void shear(ServerLevel serverLevel, SoundSource soundSource, ItemStack itemStack) {

    }

    @Override
    public boolean readyForShearing() {
        return false;
    }
}
