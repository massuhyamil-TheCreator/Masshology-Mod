package com.massh.masshology;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MasshologyMasshbestiary.MODID);

    public static final DeferredBlock<GlowLichenBlock> MUSGO_GLACIAL =
            BLOCKS.register("musgo_glacial",
                    () -> new GlowLichenBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .replaceable()
                            .noCollision()
                            .strength(0.2F)
                            .sound(net.minecraft.world.level.block.SoundType.GLOW_LICHEN)
                            .lightLevel(GlowLichenBlock.emission(7))
                            .pushReaction(PushReaction.DESTROY)));

    public static final DeferredItem<BlockItem> MUSGO_GLACIAL_ITEM =
            MasshologyMasshbestiary.ITEMS.registerSimpleBlockItem("musgo_glacial", MUSGO_GLACIAL);
}