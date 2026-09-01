package com.massh.masshology.entity.draugr;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DraugrEntity extends Monster implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.draugr.walk");
    private static final RawAnimation ATTACK_2_ANIM = RawAnimation.begin().thenPlay("animation.draugr.attack_2");
    private static final RawAnimation ATTACK_3_ANIM = RawAnimation.begin().thenPlay("animation.draugr.attack3");
    private static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.draugr.death");

    // Cuánto dura tu animación de muerte, en ticks (20 ticks = 1 segundo).
    // Ajustá este número si cambiaste la duración total en Blockbench.
    private static final int DEATH_ANIMATION_TICKS = 50; // ~2.5 segundos

    public DraugrEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0); // 1.0 = inmune al empuje al recibir golpes
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // Se ejecuta cada vez que el Draugr conecta un golpe cuerpo a cuerpo.
    // Acá disparamos, al azar, una de las dos animaciones de ataque.
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean success = super.doHurtTarget(level, target);
        if (success) {
            String trigger = this.random.nextBoolean() ? "attack_2" : "attack_3";
            this.triggerAnim("attack", trigger);
        }
        return success;
    }

    // Sobreescribimos cuánto tarda en desaparecer después de morir,
    // para darle tiempo a que la animación de muerte termine completa
    // en vez de que Minecraft lo borre al segundo (20 ticks) por default.
    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime == DEATH_ANIMATION_TICKS) {
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // 1. Movimiento (idle/walk) — se registra primero, es la base
        controllers.add(new AnimationController<>("movement", 5, state -> {
            if (state.isMoving()) {
                return state.setAndContinue(WALK_ANIM);
            }
            return state.setAndContinue(IDLE_ANIM);
        }));

        // 2. Ataque — no reproduce nada por sí solo, espera a que lo dispares con triggerAnim()
        controllers.add(new AnimationController<>("attack", state -> PlayState.STOP)
                .triggerableAnim("attack_2", ATTACK_2_ANIM)
                .triggerableAnim("attack_3", ATTACK_3_ANIM));

        // 3. Muerte — se registra AL FINAL para que tenga prioridad sobre todo lo demás
        controllers.add(new AnimationController<>("death", state -> {
            if (this.isDeadOrDying()) {
                return state.setAndContinue(DEATH_ANIM);
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}