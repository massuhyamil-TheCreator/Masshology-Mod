package com.massh.masshology.entity.cabrunu;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.massh.masshology.ModEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

public class CabrunuAi {
    private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(400, 3000);
    private static final UniformInt TIME_BETWEEN_LONG_JUMPS = UniformInt.of(600, 1200);
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    protected static void initMemories(CabrunuEntity cabrunu, RandomSource random) {
        cabrunu.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(random));
        cabrunu.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, TIME_BETWEEN_LONG_JUMPS.sample(random));
    }
    protected static Brain<?> makeBrain(Brain<CabrunuEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initLongJumpActivity(brain);
        initRamActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initRamActivity(Brain<CabrunuEntity> brain) {

    }

    private static void initLongJumpActivity(Brain<CabrunuEntity> brain) {
    }

    private static void initIdleActivity(Brain<CabrunuEntity> brain) {
        brain.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(0, new AnimalMakeLove(ModEntities.CABRUNU.get())),                Pair.of(1, new FollowTemptation((entity) -> 1.25F)),
                Pair.of(2, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)),
                Pair.of(3, new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1))))
        ), ImmutableSet.of(
                Pair.of(MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_ABSENT),
                Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT)
        ));
    }

    private static void initCoreActivity(Brain<CabrunuEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim<>(0.8F),
                new AnimalPanic<>(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS)
        ));
    }
}
