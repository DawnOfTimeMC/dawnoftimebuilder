package org.dawnoftimebuilder;

import org.dawnoftimebuilder.generation.DefaultCropsFeature;
import org.dawnoftimebuilder.generation.DoTBBlockPlacer;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerCommon
{

	@SubscribeEvent
	public static void fMLCommonSetupEvent(final FMLCommonSetupEvent event)
	{

	}

	@SubscribeEvent
	public static void entityAttributeCreationEvent(final EntityAttributeCreationEvent event)
	{
		//We need to set att least the default values here
		event.put(DoTBEntitiesRegistry.SILKMOTH_ENTITY.get(),
				MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).build());
		event.put(DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY.get(),
				MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).build());
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void eggRegisterEvent(final RegistryEvent.Register<Item> event)
	{
		//The eggs can only be registered here
		event.getRegistry().register(new SpawnEggItem(DoTBEntitiesRegistry.SILKMOTH_ENTITY.get(), 0xDBD8BD, 0xFEFEFC,
				new Item.Properties().tab(ItemGroup.TAB_MISC)));
		event.getRegistry().register(new SpawnEggItem(DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY.get(), 0xFFFFFF,
				0xFFFFFF, new Item.Properties().tab(ItemGroup.TAB_MISC)));
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void biomeLoadingEvent(final BiomeLoadingEvent event)
	{
		final ResourceLocation					name			= event.getName();
		final BiomeGenerationSettingsBuilder	generation		= event.getGeneration();
		final DefaultCropsFeature				cropsFeature	= new DefaultCropsFeature(
				BlockClusterFeatureConfig.CODEC);

		if (name != null)
		{
			if (DoTBConfig.CAMELLIA_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(
								new SimpleBlockStateProvider(DoTBBlocksRegistry.CAMELLIA.get().defaultBlockState()),
								new DoTBBlockPlacer()).tries(DoTBConfig.CAMELLIA_ROLLS.get()).xspread(2).zspread(2)
								.build()).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.COMMELINA_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						cropsFeature
								.configured(new BlockClusterFeatureConfig.Builder(
										new SimpleBlockStateProvider(
												DoTBBlocksRegistry.COMMELINA.get().defaultBlockState()),
										new DoTBBlockPlacer()).tries(64).build())
								.decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.CYPRESS_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(
								new SimpleBlockStateProvider(DoTBBlocksRegistry.CYPRESS.get().defaultBlockState()),
								new DoTBBlockPlacer()).tries(DoTBConfig.CYPRESS_ROLLS.get()).xspread(2).zspread(2)
								.build()).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.RED_MAPLE_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(
								new SimpleBlockStateProvider(
										DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().defaultBlockState()),
								new DoTBBlockPlacer()).tries(DoTBConfig.CYPRESS_ROLLS.get()).xspread(2).zspread(2)
								.build()).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.MULBERRY_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						cropsFeature
								.configured(new BlockClusterFeatureConfig.Builder(
										new SimpleBlockStateProvider(
												DoTBBlocksRegistry.MULBERRY.get().defaultBlockState()),
										new DoTBBlockPlacer()).tries(DoTBConfig.MULBERRY_ROLLS.get()).xspread(2)
										.zspread(2).build())
								.decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.RICE_GENERATION.get().contains(name.toString()))
			{
				generation
						.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
								cropsFeature
										.configured(new BlockClusterFeatureConfig.Builder(
												new SimpleBlockStateProvider(
														DoTBBlocksRegistry.RICE.get().defaultBlockState()),
												new DoTBBlockPlacer()).tries(DoTBConfig.RICE_ROLLS.get()).build())
										.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
			}
			if (DoTBConfig.WILD_GRAPE_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						cropsFeature
								.configured(new BlockClusterFeatureConfig.Builder(
										new SimpleBlockStateProvider(
												DoTBBlocksRegistry.WILD_GRAPE.get().defaultBlockState()),
										SimpleBlockPlacer.INSTANCE).tries(DoTBConfig.WILD_GRAPE_ROLLS.get()).xspread(2)
										.zspread(2).build())
								.decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.WILD_MAIZE_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						cropsFeature
								.configured(new BlockClusterFeatureConfig.Builder(
										new SimpleBlockStateProvider(
												DoTBBlocksRegistry.WILD_MAIZE.get().defaultBlockState()),
										new DoTBBlockPlacer()).tries(DoTBConfig.WILD_MAIZE_ROLLS.get()).xspread(2)
										.zspread(2).build())
								.decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
			if (DoTBConfig.GERANIUM_PINK_GENERATION.get().contains(name.toString()))
			{
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						cropsFeature
								.configured(new BlockClusterFeatureConfig.Builder(
										new SimpleBlockStateProvider(
												DoTBBlocksRegistry.GERANIUM_PINK.get().defaultBlockState()),
										SimpleBlockPlacer.INSTANCE).tries(DoTBConfig.GERANIUM_PINK_ROLLS.get())
										.xspread(2).zspread(2).build())
								.decorated(Features.Placements.HEIGHTMAP_SQUARE));
			}
		}
	}
}