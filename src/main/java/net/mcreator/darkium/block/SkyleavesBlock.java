
package net.mcreator.darkium.block;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.util.Direction;
import net.minecraft.state.StateContainer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.darkium.itemgroup.SkyhotiumItemGroup;
import net.mcreator.darkium.DarkiumModElements;

import java.util.List;
import java.util.Collections;

@DarkiumModElements.ModElement.Tag
public class SkyleavesBlock extends DarkiumModElements.ModElement {
	@ObjectHolder("darkium:skyleaves")
	public static final Block block = null;
	public SkyleavesBlock(DarkiumModElements instance) {
		super(instance, 87);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(SkyhotiumItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public CustomBlock() {
			super(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT).hardnessAndResistance(0.05f, 10f).lightValue(0));
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
			setRegistryName("skyleaves");
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING);
		}

		public BlockState rotate(BlockState state, Rotation rot) {
			return state.with(FACING, rot.rotate(state.get(FACING)));
		}

		public BlockState mirror(BlockState state, Mirror mirrorIn) {
			return state.rotate(mirrorIn.toRotation(state.get(FACING)));
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(Blocks.AIR, (int) (0)));
		}
	}
}
