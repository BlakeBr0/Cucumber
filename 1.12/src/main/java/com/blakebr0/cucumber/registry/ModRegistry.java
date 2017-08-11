package com.blakebr0.cucumber.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.blakebr0.cucumber.helper.StackHelper;
import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.iface.IModelHelper;
import com.blakebr0.cucumber.iface.IRepairMaterial;
import com.blakebr0.cucumber.item.ItemMeta;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ModRegistry {
	
	private String modid;
	public List<RegistryObject<Block>> blocks = new ArrayList<>();
	public List<RegistryObject<Item>> items = new ArrayList<>();
	public List<RegistryObject<IRecipe>> recipes = new ArrayList<>();
	public Map<Class, String> tiles = new HashMap<>();
	public Map<ItemStack, String> ores = new HashMap<>();
	public Map<IItemColor, Item[]> itemColor = new HashMap<>();
	
	public ModRegistry(String modid){
		this.modid = modid;
	}
	
	public <T extends Block> T register(T block, String name){
		return register(block, name, true);
	}
	
	public <T extends Block> T register(T block, String name, boolean itemBlock){
		if(block instanceof IEnableable){
			if(!((IEnableable)block).isEnabled()){
				return block;
			}
		}
		blocks.add(new RegistryObject<Block>(block, name));
		if(itemBlock){
			items.add(new RegistryObject<Item>(new ItemBlock(block), name));
		}
		return block;
	}
	
	public <T extends Block> T register(T block, String name, ItemBlock itemBlock){
		if(block instanceof IEnableable){
			if(!((IEnableable)block).isEnabled()){
				return block;
			}
		}
		blocks.add(new RegistryObject<Block>(block, name));
		items.add(new RegistryObject<Item>(itemBlock, name));
		return block;
	}
	
	public <T extends Item> T register(T item, String name){
		if(item instanceof ItemBlock){
			if(((ItemBlock)item).getBlock() instanceof IEnableable){	
				if(!((IEnableable)((ItemBlock)item).getBlock()).isEnabled()){
					return item;
				}
			}
		}
		if(item instanceof IEnableable){
			if(!((IEnableable)item).isEnabled()){
				return item;
			}
		}
		items.add(new RegistryObject<Item>(item, name));
		if(item instanceof ItemMeta){
			((ItemMeta)item).init();
		}
		return item;
	}
	
	public <T extends Item> T register(T item, String name, ItemStack stack){
		if(item instanceof ItemBlock){
			if(((ItemBlock)item).getBlock() instanceof IEnableable){	
				if(!((IEnableable)((ItemBlock)item).getBlock()).isEnabled()){
					return item;
				}
			}
		}
		if(item instanceof IEnableable){
			if(!((IEnableable)item).isEnabled()){
				return item;
			}
		}
		register(item, name);
		if(item instanceof IRepairMaterial){
			((IRepairMaterial)item).setRepairMaterial(stack);
		}
		return item;
	}
	
	public <T extends IRecipe> T register(T recipe){
		if(recipe.getRecipeOutput().isEmpty()){
			return recipe;
		}
		if(recipe.getIngredients().isEmpty()){
			return recipe;
		}
		recipes.add(new RegistryObject<IRecipe>(recipe, RecipeHelper.getRecipeLocation(recipe.getRecipeOutput()).toString()));
		return recipe;
	}
	
	public <T extends IRecipe> T register(T recipe, String name){
		if(recipe.getRecipeOutput().isEmpty()){
			return recipe;
		}
		if(recipe.getIngredients().isEmpty()){
			return recipe;
		}
		recipes.add(new RegistryObject<IRecipe>(recipe, modid + ":" + name));
		return recipe;
	}
	
	public void register(Class clazz, String name){
		tiles.put(clazz, name);
	}
	
	public void addOre(Object ore, String name){
		if(ore instanceof Block){
			ores.put(new ItemStack((Block)ore), name);
		} else if(ore instanceof Item){
			ores.put(new ItemStack((Item)ore), name);
		} else if(ore instanceof ItemStack){
			ores.put(((ItemStack)ore).copy(), name);
		} else {
			throw new RuntimeException("Tried to add an invalid object to the OreDictionary, well done." + " (" + ore.toString() + " named: " + name + ")");
		}
	}
	
	public void register(IItemColor color, Item... items){
		itemColor.put(color, items);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event){
		for(RegistryObject<Block> block : blocks){
			if(block.get().getRegistryName() == null){
				block.get().setRegistryName(block.getName());
			}
			event.getRegistry().register(block.get());
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event){		
		for(RegistryObject<Item> item : items){
			if(item.get().getRegistryName() == null){
				item.get().setRegistryName(item.getName());
			}
			event.getRegistry().register(item.get());
		}
		
		for(Map.Entry<ItemStack, String> ore : ores.entrySet()){
			OreDictionary.registerOre(ore.getValue(), ore.getKey());
		}
	}
	
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event){
		for(RegistryObject<IRecipe> recipe : recipes){
			if(recipe.get().getRegistryName() == null){
				recipe.get().setRegistryName(new ResourceLocation(recipe.getName()));
			}
			event.getRegistry().register(recipe.get());
		}
	}
	
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event){
		for(RegistryObject<Item> item : items){
			if(item.get() instanceof ItemMeta){
				((ItemMeta)item.get()).initModels();
			} else if(item.get() instanceof IModelHelper){
				((IModelHelper)item.get()).initModels();
			} else if(item.get() instanceof ItemBlock){
				if(((ItemBlock)item.get()).getBlock() instanceof IModelHelper){
					((IModelHelper)((ItemBlock)item.get()).getBlock()).initModels();
				} else {
					ModelLoader.setCustomModelResourceLocation(item.get(), 0, new ModelResourceLocation(modid + ":" + item.getName(), "inventory")); 
				}
			} else {
				ModelLoader.setCustomModelResourceLocation(item.get(), 0, new ModelResourceLocation(modid + ":" + item.getName(), "inventory")); 
			}
		}
		
		
		for(Map.Entry<IItemColor, Item[]> handler : itemColor.entrySet()){
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(handler.getKey(), handler.getValue());
		}
	}
}
