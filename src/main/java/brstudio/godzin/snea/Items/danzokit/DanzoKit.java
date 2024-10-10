package brstudio.godzin.snea.Items.danzokit;

import brstudio.godzin.snea.Init.IHasModel;
import brstudio.godzin.snea.Init.ModItems;
import brstudio.godzin.snea.SNEA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.narutomod.creativetab.TabModTab;

public class DanzoKit extends Item implements IHasModel {

    public DanzoKit() {
        setTranslationKey("danzo_kit");
        setRegistryName("danzo_kit");
        setMaxStackSize(1);
        setMaxDamage(5);
        setCreativeTab(TabModTab.tab);
        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        SNEA.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW; // Usa a animação de arco
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        // Inicia o "carregamento" do item, ativando a animação de arco
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }
}
