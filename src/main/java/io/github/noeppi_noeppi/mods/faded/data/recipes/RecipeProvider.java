package io.github.noeppi_noeppi.mods.faded.data.recipes;

import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.crafting.ingredient.EffectIngredient;
import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.faded.Faded;
import io.github.noeppi_noeppi.mods.faded.mods.extrabaubles.ExtraBaubles;
import io.github.noeppi_noeppi.mods.faded.mods.keksmagic.ItemMagicalCocoa;
import io.github.noeppi_noeppi.mods.faded.mods.keksmagic.KeksMagic;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.MoreTNT;
import io.github.noeppi_noeppi.mods.faded.mods.random_wands.RandomWands;
import io.github.noeppi_noeppi.mods.faded.mods.steelcraftio.SteelCraftIO;
import io.github.noeppi_noeppi.mods.faded.mods.turtlecraft.TurtleCraft;
import io.github.noeppi_noeppi.mods.faded.mods.usefultotems.UsefulTotems;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeProvider extends RecipeProviderBase {

    public RecipeProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }
    
    @Override
    @SuppressWarnings("ConstantConditions")
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        this.makeBlockItem(consumer, SteelCraftIO.steelBlock, SteelCraftIO.steel);
        this.makeBlockItem(consumer, Items.COAL, SteelCraftIO.coalNugget);
        this.makeBlockItem(consumer, Items.DIAMOND, SteelCraftIO.tinyDiamond);
        this.makeBlockItem(consumer, SteelCraftIO.bedrockIngot, SteelCraftIO.bedrockNugget);
        this.makeBlockItem(consumer, SteelCraftIO.devilBlock, SteelCraftIO.devil);

        ShapedRecipeBuilder.shapedRecipe(SteelCraftIO.rawSteel)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('c', SteelCraftIO.coalNugget)
                .patternLine("ccc")
                .patternLine("cic")
                .patternLine("ccc")
                .setGroup(SteelCraftIO.rawSteel.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_item1", hasItem(SteelCraftIO.coalNugget))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(SteelCraftIO.devil)
                .addIngredient(SteelCraftIO.steel)
                .addIngredient(SteelCraftIO.devilDust)
                .setGroup(SteelCraftIO.devil.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(SteelCraftIO.steel))
                .addCriterion("has_item1", hasItem(SteelCraftIO.devilDust))
                .build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(SteelCraftIO.rawSteel), SteelCraftIO.steel, 0.2f, 200)
                .addCriterion("has_item", hasItem(SteelCraftIO.rawSteel))
                .build(consumer);
        
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(SteelCraftIO.rawSteel), SteelCraftIO.steel, 0.1f, 100)
                .addCriterion("has_item", hasItem(SteelCraftIO.rawSteel))
                .build(consumer, SteelCraftIO.steel.getRegistryName() + "_from_blasting");
        
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(SteelCraftIO.devilOre), SteelCraftIO.devilDust, 1f, 200)
                .addCriterion("has_item", hasItem(SteelCraftIO.devilOre))
                .build(consumer);
        
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(SteelCraftIO.devilOre), SteelCraftIO.devilDust, 0.5f, 100)
                .addCriterion("has_item", hasItem(SteelCraftIO.devilOre))
                .build(consumer, SteelCraftIO.devilDust.getRegistryName() + "from_blasting");
        
        this.makeTools(consumer, SteelCraftIO.steel, SteelCraftIO.steelSword, SteelCraftIO.steelAxe, SteelCraftIO.steelPickaxe, SteelCraftIO.steelShovel, SteelCraftIO.steelHoe);
        this.makeArmor(consumer, SteelCraftIO.steel, SteelCraftIO.steelHelmet, SteelCraftIO.steelChestplate, SteelCraftIO.steelLeggings, SteelCraftIO.steelBoots);

        this.makeTools(consumer, SteelCraftIO.devil, SteelCraftIO.devilSword, SteelCraftIO.devilAxe, SteelCraftIO.devilPickaxe, SteelCraftIO.devilShovel, SteelCraftIO.devilHoe);
        this.makeArmor(consumer, SteelCraftIO.devil, SteelCraftIO.devilHelmet, SteelCraftIO.devilChestplate, SteelCraftIO.devilLeggings, SteelCraftIO.devilBoots);
        
        this.makeSmallBlockItem(consumer, SteelCraftIO.tinyDiamond, Blocks.COAL_BLOCK, false);
        
        ShapedRecipeBuilder.shapedRecipe(TurtleCraft.turtleApple)
                .key('a', Items.APPLE)
                .key('s', TurtleCraft.turtleShell)
                .patternLine("sss")
                .patternLine("sas")
                .patternLine("sss")
                .setGroup(TurtleCraft.turtleApple.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.APPLE))
                .addCriterion("has_item1", hasItem(TurtleCraft.turtleShell))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(TurtleCraft.turtleRod)
                .key('w', Tags.Items.RODS_WOODEN)
                .key('s', TurtleCraft.turtleShell)
                .patternLine(" ss")
                .patternLine(" ws")
                .patternLine("w  ")
                .setGroup(TurtleCraft.turtleRod.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.RODS_WOODEN))
                .addCriterion("has_item1", hasItem(TurtleCraft.turtleShell))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(MoreTNT.weakTnt, 4)
                .key('g', Tags.Items.GUNPOWDER)
                .key('s', ItemTags.SAND)
                .patternLine("sss")
                .patternLine("sgs")
                .patternLine("sss")
                .setGroup(MoreTNT.weakTnt.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.GUNPOWDER))
                .addCriterion("has_item1", hasItem(ItemTags.SAND))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(MoreTNT.betterTnt)
                .key('g', Tags.Items.GUNPOWDER)
                .key('t', Blocks.TNT)
                .patternLine("g")
                .patternLine("t")
                .setGroup(MoreTNT.weakTnt.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.GUNPOWDER))
                .addCriterion("has_item1", hasItem(Blocks.TNT))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(MoreTNT.groundTnt)
                .key('g', Tags.Items.GUNPOWDER)
                .key('s', Tags.Items.STONE)
                .patternLine("gsg")
                .patternLine("sgs")
                .patternLine("gsg")
                .setGroup(MoreTNT.weakTnt.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.GUNPOWDER))
                .addCriterion("has_item1", hasItem(ItemTags.SAND))
                .build(consumer);
        
        ShapelessRecipeBuilder.shapelessRecipe(ExtraBaubles.cutLapis)
                .addIngredient(ExtraBaubles.crystalCutter)
                .addIngredient(Tags.Items.GEMS_LAPIS)
                .setGroup(ExtraBaubles.cutLapis.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.crystalCutter))
                .addCriterion("has_item1", hasItem(Tags.Items.GEMS_LAPIS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ExtraBaubles.cutQuartz)
                .addIngredient(ExtraBaubles.crystalCutter)
                .addIngredient(Tags.Items.GEMS_QUARTZ)
                .setGroup(ExtraBaubles.cutLapis.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.crystalCutter))
                .addCriterion("has_item1", hasItem(Tags.Items.GEMS_QUARTZ))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ExtraBaubles.cutDiamond)
                .addIngredient(ExtraBaubles.crystalCutter)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .setGroup(ExtraBaubles.cutLapis.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.crystalCutter))
                .addCriterion("has_item1", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ExtraBaubles.cutObsidian)
                .addIngredient(ExtraBaubles.crystalCutter)
                .addIngredient(Tags.Items.OBSIDIAN)
                .setGroup(ExtraBaubles.cutLapis.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.crystalCutter))
                .addCriterion("has_item1", hasItem(Tags.Items.OBSIDIAN))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.crystalCutter)
                .key('s', Tags.Items.RODS_WOODEN)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("id")
                .patternLine("id")
                .patternLine("s ")
                .setGroup(ExtraBaubles.crystalCutter.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.RODS_WOODEN))
                .addCriterion("has_item1", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_item2", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.amuletLife1)
                .key('s', Tags.Items.STRING)
                .key('a', ExtraBaubles.heart1)
                .patternLine("sss")
                .patternLine("s s")
                .patternLine(" a ")
                .setGroup(ExtraBaubles.amuletLife1.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.STRING))
                .addCriterion("has_item1", hasItem(ExtraBaubles.heart1))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.amuletLife2)
                .key('s', Tags.Items.STRING)
                .key('a', ExtraBaubles.heart2)
                .patternLine("sss")
                .patternLine("s s")
                .patternLine(" a ")
                .setGroup(ExtraBaubles.amuletLife2.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.STRING))
                .addCriterion("has_item1", hasItem(ExtraBaubles.heart2))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.amuletLife3)
                .key('s', Tags.Items.STRING)
                .key('a', ExtraBaubles.heart3)
                .patternLine("sss")
                .patternLine("s s")
                .patternLine(" a ")
                .setGroup(ExtraBaubles.amuletLife3.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.STRING))
                .addCriterion("has_item1", hasItem(ExtraBaubles.heart3))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.amuletLife4)
                .key('s', Tags.Items.STRING)
                .key('a', ExtraBaubles.heart4)
                .patternLine("sss")
                .patternLine("s s")
                .patternLine(" a ")
                .setGroup(ExtraBaubles.amuletLife4.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.STRING))
                .addCriterion("has_item1", hasItem(ExtraBaubles.heart4))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.amuletLife5)
                .key('s', Tags.Items.STRING)
                .key('a', ExtraBaubles.heart5)
                .patternLine("sss")
                .patternLine("s s")
                .patternLine(" a ")
                .setGroup(ExtraBaubles.amuletLife5.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.STRING))
                .addCriterion("has_item1", hasItem(ExtraBaubles.heart5))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.amuletLife6)
                .key('s', Tags.Items.STRING)
                .key('a', ExtraBaubles.heart6)
                .patternLine("sss")
                .patternLine("s s")
                .patternLine(" a ")
                .setGroup(ExtraBaubles.amuletLife6.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.STRING))
                .addCriterion("has_item1", hasItem(ExtraBaubles.heart6))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.beltGemmal)
                .key('p', Tags.Items.GLASS_PANES)
                .key('g', Tags.Items.GLASS)
                .key('b', Items.BLAZE_POWDER)
                .patternLine("ppp")
                .patternLine("gbg")
                .patternLine("ppp")
                .setGroup(ExtraBaubles.beltGemmal.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.GLASS_PANES))
                .addCriterion("has_item1", hasItem(Tags.Items.GLASS))
                .addCriterion("has_item2", hasItem(Items.BLAZE_POWDER))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.antiPoisonCrystal)
                .key('f', Items.FERMENTED_SPIDER_EYE)
                .key('m', Items.MAGMA_CREAM)
                .key('i', Tags.Items.INGOTS_NETHERITE)
                .patternLine("fmf")
                .patternLine("mim")
                .patternLine("fmf")
                .setGroup(ExtraBaubles.antiPoisonCrystal.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.FERMENTED_SPIDER_EYE))
                .addCriterion("has_item1", hasItem(Items.MAGMA_CREAM))
                .addCriterion("has_item2", hasItem(Tags.Items.INGOTS_NETHERITE))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.ring)
                .key('n', Tags.Items.NUGGETS_GOLD)
                .key('i', Tags.Items.INGOTS_GOLD)
                .patternLine("nin")
                .patternLine("n n")
                .patternLine("nnn")
                .setGroup(ExtraBaubles.ring.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.NUGGETS_GOLD))
                .addCriterion("has_item1", hasItem(Tags.Items.INGOTS_GOLD))
                .build(consumer);
        
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ExtraBaubles.ring), Ingredient.fromItems(ExtraBaubles.pearl), ExtraBaubles.pearlRing)
                .addCriterion("has_item0", hasItem(ExtraBaubles.ring))
                .addCriterion("has_item1", hasItem(ExtraBaubles.pearl))
                .build(consumer, ExtraBaubles.pearlRing.getRegistryName());

        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ExtraBaubles.ring), Ingredient.fromItems(ExtraBaubles.antiPoisonCrystal), ExtraBaubles.antiPoisonRing)
                .addCriterion("has_item0", hasItem(ExtraBaubles.ring))
                .addCriterion("has_item1", hasItem(ExtraBaubles.antiPoisonCrystal))
                .build(consumer, ExtraBaubles.antiPoisonRing.getRegistryName());

        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ExtraBaubles.ring), Ingredient.fromItems(ExtraBaubles.cutLapis), ExtraBaubles.lapisRing)
                .addCriterion("has_item0", hasItem(ExtraBaubles.ring))
                .addCriterion("has_item1", hasItem(ExtraBaubles.cutLapis))
                .build(consumer, ExtraBaubles.lapisRing.getRegistryName());

        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ExtraBaubles.ring), Ingredient.fromItems(ExtraBaubles.cutQuartz), ExtraBaubles.quartzRing)
                .addCriterion("has_item0", hasItem(ExtraBaubles.ring))
                .addCriterion("has_item1", hasItem(ExtraBaubles.cutQuartz))
                .build(consumer, ExtraBaubles.quartzRing.getRegistryName());

        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ExtraBaubles.ring), Ingredient.fromItems(ExtraBaubles.cutDiamond), ExtraBaubles.diamondRing)
                .addCriterion("has_item0", hasItem(ExtraBaubles.ring))
                .addCriterion("has_item1", hasItem(ExtraBaubles.cutDiamond))
                .build(consumer, ExtraBaubles.diamondRing.getRegistryName());

        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ExtraBaubles.ring), Ingredient.fromItems(ExtraBaubles.cutObsidian), ExtraBaubles.obsidianRing)
                .addCriterion("has_item0", hasItem(ExtraBaubles.ring))
                .addCriterion("has_item1", hasItem(ExtraBaubles.cutObsidian))
                .build(consumer, ExtraBaubles.obsidianRing.getRegistryName());
        
        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.heart1)
                .key('h', Tags.Items.DYES_RED)
                .key('u', Items.ENDER_EYE)
                .key('d', Items.DIAMOND)
                .patternLine("u u")
                .patternLine("dhd")
                .patternLine(" d ")
                .setGroup(ExtraBaubles.heart1.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.DYES_RED))
                .addCriterion("has_item1", hasItem(Items.ENDER_EYE))
                .addCriterion("has_item2", hasItem(Items.DIAMOND))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.heart2)
                .key('h', ExtraBaubles.heart1)
                .key('u', Items.WITHER_SKELETON_SKULL)
                .key('d', Items.GOLDEN_APPLE)
                .patternLine("u u")
                .patternLine("dhd")
                .patternLine(" d ")
                .setGroup(ExtraBaubles.heart2.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.heart1))
                .addCriterion("has_item1", hasItem(Items.WITHER_SKELETON_SKULL))
                .addCriterion("has_item2", hasItem(Items.GOLDEN_APPLE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.heart3)
                .key('h', ExtraBaubles.heart2)
                .key('u', Items.ELYTRA)
                .key('d', Items.SHULKER_SHELL)
                .patternLine("u u")
                .patternLine("dhd")
                .patternLine(" d ")
                .setGroup(ExtraBaubles.heart3.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.heart2))
                .addCriterion("has_item1", hasItem(Items.ELYTRA))
                .addCriterion("has_item2", hasItem(Items.SHULKER_SHELL))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.heart4)
                .key('h', ExtraBaubles.heart3)
                .key('u', Items.ENCHANTED_GOLDEN_APPLE)
                .key('d', Items.QUARTZ_BLOCK)
                .patternLine("u u")
                .patternLine("dhd")
                .patternLine(" d ")
                .setGroup(ExtraBaubles.heart4.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.heart3))
                .addCriterion("has_item1", hasItem(Items.ENCHANTED_GOLDEN_APPLE))
                .addCriterion("has_item2", hasItem(Items.QUARTZ_BLOCK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.heart5)
                .key('h', ExtraBaubles.heart4)
                .key('u', Items.NETHER_STAR)
                .key('d', Items.DIAMOND_BLOCK)
                .patternLine("u u")
                .patternLine("dhd")
                .patternLine(" d ")
                .setGroup(ExtraBaubles.heart5.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.heart4))
                .addCriterion("has_item1", hasItem(Items.NETHER_STAR))
                .addCriterion("has_item2", hasItem(Items.DIAMOND_BLOCK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraBaubles.heart6)
                .key('h', ExtraBaubles.heart5)
                .key('u', Items.POPPED_CHORUS_FRUIT)
                .key('e', Items.DRAGON_EGG)
                .patternLine("u u")
                .patternLine("uhu")
                .patternLine(" e ")
                .setGroup(ExtraBaubles.heart6.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(ExtraBaubles.heart5))
                .addCriterion("has_item1", hasItem(Items.POPPED_CHORUS_FRUIT))
                .addCriterion("has_item2", hasItem(Items.DRAGON_EGG))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(KeksMagic.cookieAssembler)
                .key('c', Items.COOKIE)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('l', Tags.Items.GEMS_LAPIS)
                .patternLine("ici")
                .patternLine("ili")
                .patternLine("iii")
                .setGroup(KeksMagic.cookieAssembler.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.COOKIE))
                .addCriterion("has_item1", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_item2", hasItem(Tags.Items.GEMS_LAPIS))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(KeksMagic.formSelf)
                .key('i', Tags.Items.NUGGETS_IRON)
                .patternLine(" i ")
                .patternLine("i i")
                .patternLine("iii")
                .setGroup(KeksMagic.formSelf.getRegistryName().toString())
                .addCriterion("has_item", hasItem(Tags.Items.NUGGETS_IRON))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(KeksMagic.formProjectile)
                .key('i', Tags.Items.NUGGETS_IRON)
                .patternLine("ii ")
                .patternLine("i i")
                .patternLine(" ii")
                .setGroup(KeksMagic.formProjectile.getRegistryName().toString())
                .addCriterion("has_item", hasItem(Tags.Items.NUGGETS_IRON))
                .build(consumer);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(Faded.getInstance().modid))
                .filter(item -> item instanceof ItemMagicalCocoa)
                .map(item -> (ItemMagicalCocoa) item)
                .forEach(item -> ShapelessRecipeBuilder.shapelessRecipe(item, 4)
                        .addIngredient(Items.COCOA_BEANS)
                        .addIngredient(Items.COCOA_BEANS)
                        .addIngredient(Items.COCOA_BEANS)
                        .addIngredient(Items.COCOA_BEANS)
                        .addIngredient(new EffectIngredient(Items.POTION, ImmutableList.of(new EffectInstance(item.effect, 20, 0)), false, true, true))
                        .setGroup(item.getRegistryName().toString())
                        .addCriterion("has_item", hasItem(Items.COCOA_BEANS))
                        .build(consumer));

        ShapelessRecipeBuilder.shapelessRecipe(KeksMagic.dough1)
                .addIngredient(Items.BOWL)
                .addIngredient(Items.APPLE)
                .addIngredient(Tags.Items.SEEDS)
                .addIngredient(Items.BREAD)
                .setGroup(KeksMagic.dough1.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.BOWL))
                .addCriterion("has_item1", hasItem(Items.APPLE))
                .addCriterion("has_item2", hasItem(Tags.Items.SEEDS))
                .addCriterion("has_item3", hasItem(Items.BREAD))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(KeksMagic.dough2)
                .addIngredient(Items.BOWL)
                .addIngredient(Items.GOLDEN_CARROT)
                .addIngredient(Items.SPIDER_EYE)
                .addIngredient(Items.DRIED_KELP)
                .setGroup(KeksMagic.dough2.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.BOWL))
                .addCriterion("has_item1", hasItem(Items.GOLDEN_CARROT))
                .addCriterion("has_item2", hasItem(Items.SPIDER_EYE))
                .addCriterion("has_item3", hasItem(Items.DRIED_KELP))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(KeksMagic.dough3)
                .addIngredient(Items.BOWL)
                .addIngredient(Items.CAKE)
                .addIngredient(Items.POISONOUS_POTATO)
                .addIngredient(Items.SWEET_BERRIES)
                .setGroup(KeksMagic.dough3.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.BOWL))
                .addCriterion("has_item1", hasItem(Items.CAKE))
                .addCriterion("has_item2", hasItem(Items.POISONOUS_POTATO))
                .addCriterion("has_item3", hasItem(Items.SWEET_BERRIES))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(KeksMagic.dough4)
                .addIngredient(Items.BOWL)
                .addIngredient(Items.GOLDEN_APPLE)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(Items.PUFFERFISH)
                .setGroup(KeksMagic.dough4.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.BOWL))
                .addCriterion("has_item1", hasItem(Items.GOLDEN_APPLE))
                .addCriterion("has_item2", hasItem(Items.HONEY_BOTTLE))
                .addCriterion("has_item3", hasItem(Items.PUFFERFISH))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(UsefulTotems.teleportTotem)
                .key('t', Items.TOTEM_OF_UNDYING)
                .key('e', Tags.Items.ENDER_PEARLS)
                .patternLine("eee")
                .patternLine("ete")
                .patternLine("eee")
                .setGroup(UsefulTotems.teleportTotem.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.TOTEM_OF_UNDYING))
                .addCriterion("has_item1", hasItem(Tags.Items.ENDER_PEARLS))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(UsefulTotems.salvageTotem)
                .key('t', Items.TOTEM_OF_UNDYING)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("ddd")
                .patternLine("dtd")
                .patternLine("ddd")
                .setGroup(UsefulTotems.salvageTotem.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.TOTEM_OF_UNDYING))
                .addCriterion("has_item1", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(RandomWands.pigWand)
                .key('x', Items.COOKED_PORKCHOP)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.pigWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.COOKED_PORKCHOP))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RandomWands.cowWand)
                .key('x', Items.COOKED_BEEF)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.cowWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.COOKED_BEEF))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RandomWands.chickenWand)
                .key('x', Items.FEATHER)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.chickenWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.FEATHER))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RandomWands.mooshroomWand)
                .key('x', Items.RED_MUSHROOM_BLOCK)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.mooshroomWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.RED_MUSHROOM_BLOCK))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RandomWands.squidWand)
                .key('x', Items.INK_SAC)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.squidWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.INK_SAC))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RandomWands.iceWand)
                .key('x', Items.ICE)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.iceWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Items.ICE))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RandomWands.obsidianWand)
                .key('x', Tags.Items.OBSIDIAN)
                .key('s', Tags.Items.RODS_WOODEN)
                .patternLine("  x")
                .patternLine(" s ")
                .patternLine("s  ")
                .setGroup(RandomWands.obsidianWand.getRegistryName().toString())
                .addCriterion("has_item0", hasItem(Tags.Items.OBSIDIAN))
                .addCriterion("has_item1", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer);
    }
}
