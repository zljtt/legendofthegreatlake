package github.zljtt.legendofthegreatlake.blocks;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LegendOfTheGreatLake.MODID);
    public static final RegistryObject<Block> PIPE = BLOCKS.register("tobacco_pipe",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.BAMBOO, MaterialColor.WOOD).strength(1.0F).sound(SoundType.WOOD),
                    Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D)));

    public static final RegistryObject<Block> CRUTCH_1 = BLOCKS.register("crutch_1",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.0F).sound(SoundType.WOOD),
                    Block.box(8.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D)));

    public static final RegistryObject<Block> CRUTCH_2 = BLOCKS.register("crutch_2",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.0F).sound(SoundType.WOOD),
                    Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D)));

    public static final RegistryObject<Block> FRAME_GROUND = BLOCKS.register("frame_ground",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.0F).sound(SoundType.WOOD),
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 12.0D, 12.0D)));

    public static final RegistryObject<Block> FRAME_WALL = BLOCKS.register("frame_wall",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.0F).sound(SoundType.WOOD),
                    Block.box(15.0D, 2.0D, 3.0D, 16.0D, 15.0D, 12.0D)));

    public static final RegistryObject<Block> GLASSES = BLOCKS.register("glasses",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.METAL).strength(1.0F).sound(SoundType.GLASS),
                    Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D)));

//    public static final RegistryObject<Block> HANGER = BLOCKS.register("hanger",
//            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.0F).sound(SoundType.GLASS),
//                    Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));

    public static final RegistryObject<Block> POCKET_WATCH = BLOCKS.register("pocket_watch",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.METAL).strength(1.0F).sound(SoundType.METAL),
                    Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D)));

    public static final RegistryObject<Block> WINE_BOTTLE_FALL = BLOCKS.register("wine_bottle_fall",
            () -> new FourWayBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.METAL).strength(1.0F).sound(SoundType.GLASS),
                    Block.box(6.0D, 0.0D, 1.0D, 10.0D, 4.0D, 15.0D)));
}

