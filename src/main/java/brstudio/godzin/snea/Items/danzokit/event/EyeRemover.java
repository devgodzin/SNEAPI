package brstudio.godzin.snea.Items.danzokit.event;

import brstudio.godzin.snea.Items.danzokit.DanzoKit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class EyeRemover {
    private static final Map<UUID, ChargeData> chargeMap = new HashMap<>();

    private static class ChargeData {
        public long startTime;
        public EntityPlayer targetPlayer;

        public ChargeData(long startTime, EntityPlayer targetPlayer) {
            this.startTime = startTime;
            this.targetPlayer = targetPlayer;
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof EntityPlayer) {
            EntityPlayer target = (EntityPlayer) event.getTarget();
            EntityPlayer player = event.getEntityPlayer();

            if (event.getHand() == EnumHand.MAIN_HAND) {
                ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
                if (heldItem.getItem() instanceof DanzoKit) {
                    chargeMap.put(player.getUniqueID(), new ChargeData(System.currentTimeMillis(), target));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (chargeMap.containsKey(event.player.getUniqueID())) {
            ChargeData chargeData = chargeMap.get(event.player.getUniqueID());
            long startTime = chargeData.startTime;
            EntityPlayer target = chargeData.targetPlayer;
            long currentTime = System.currentTimeMillis();

            ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
            if (!(heldItem.getItem() instanceof DanzoKit)) {
                chargeMap.remove(player.getUniqueID());
                return;
            }

            if (TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime) >= 5) {
                if (target != null) {
                    ItemStack helmet = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                    if (!helmet.isEmpty()) {
                        target.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        player.addItemStackToInventory(helmet);
                    }
                }
                chargeMap.remove(player.getUniqueID());
            }
        }
    }
}
