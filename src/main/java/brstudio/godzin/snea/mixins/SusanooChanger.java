package brstudio.godzin.snea.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.narutomod.Chakra;
import net.narutomod.NarutomodModVariables;
import net.narutomod.PlayerTracker;
import net.narutomod.entity.EntitySusanooBase;
import net.narutomod.entity.EntitySusanooClothed;
import net.narutomod.entity.EntitySusanooSkeleton;
import net.narutomod.entity.EntitySusanooWinged;
import net.narutomod.item.ItemMangekyoSharinganEternal;
import net.narutomod.item.ItemRinnegan;
import net.narutomod.potion.PotionFeatherFalling;
import net.narutomod.procedure.ProcedureSusanoo;
import net.narutomod.procedure.ProcedureUtils;
import org.spongepowered.asm.mixin.*;

import static net.narutomod.procedure.ProcedureSusanoo.getSummonedSusanooId;

@Mixin(value = ProcedureSusanoo.class, remap = false)
public abstract class SusanooChanger{

    @Unique
    private static final double sNEAPI$ChakraCost = 1500.0;

    /**
     * @author Godzin
     * @reason Susanoo must cost more chakra in SNE
     */
    @Overwrite(remap = false)
    public static void upgrade(EntityPlayer player) {
        Entity susanoo = player.getRidingEntity();
        double playerXp = PlayerTracker.getBattleXp(player);
        if (susanoo instanceof EntitySusanooBase) {
            boolean hasLegs;
            EntitySusanooClothed.EntityCustom entityCustom;
            if (susanoo instanceof EntitySusanooSkeleton.EntityCustom) {
                hasLegs = ((EntitySusanooSkeleton.EntityCustom)susanoo).isFullBody();
                if (!hasLegs && playerXp >= 5000.0) {
                    if (Chakra.pathway(player).consume(sNEAPI$ChakraCost)) {
                        susanoo.setDead();
                        EntitySusanooBase entityCustom_b = new EntitySusanooSkeleton.EntityCustom(player, true);
                        player.world.spawnEntity(entityCustom_b);
                        player.getEntityData().setInteger("summonedSusanooID", (entityCustom_b.getEntityId()));
                    }
                } else if (hasLegs && playerXp >= 10000.0 && Chakra.pathway(player).consume(sNEAPI$ChakraCost)) {
                    susanoo.setDead();
                    entityCustom = new EntitySusanooClothed.EntityCustom(player, false);
                    player.world.spawnEntity(entityCustom);
                    player.getEntityData().setInteger("summonedSusanooID", (entityCustom.getEntityId()));
                }
            } else if (susanoo instanceof EntitySusanooClothed.EntityCustom) {
                hasLegs = ((EntitySusanooClothed.EntityCustom)susanoo).hasLegs();
                if (hasLegs && playerXp >= 36000.0) {
                    if (Chakra.pathway(player).consume(sNEAPI$ChakraCost)) {
                        susanoo.setDead();
                        EntitySusanooBase entityCustom_B = new EntitySusanooWinged.EntityCustom(player);
                        player.world.spawnEntity(entityCustom_B);
                        player.getEntityData().setInteger("summonedSusanooID", (entityCustom_B.getEntityId()));
                    }
                } else if (!hasLegs && playerXp >= 20000.0 && Chakra.pathway(player).consume(sNEAPI$ChakraCost)) {
                    susanoo.setDead();
                    entityCustom = new EntitySusanooClothed.EntityCustom(player, true);
                    player.world.spawnEntity(entityCustom);
                    player.getEntityData().setInteger("summonedSusanooID", (entityCustom.getEntityId()));
                }
            }
        }
    }

    /**
     * @author Godzin
     * @reason Susanoo must cost more chakra in SNE
     */
    @Overwrite(remap = false)
    public static void execute(EntityPlayer player) {
        System.out.println("Susanoo activated");
        World world = player.world;
        boolean flag = player.isCreative() || ProcedureUtils.hasItemInInventory(player, ItemRinnegan.helmet);
        ItemStack helmet = player.inventory.armorInventory.get(3);
        if (!player.getEntityData().getBoolean("susanoo_activated")) {
            if (helmet.hasTagCompound() && !helmet.getTagCompound().getBoolean("sharingan_blinded") && PlayerTracker.getBattleXp(player) >= 2000.0 && Chakra.pathway(player).consume(sNEAPI$ChakraCost)) {
                player.getEntityData().setBoolean("susanoo_activated", true);
                player.getEntityData().setDouble("susanoo_cd", NarutomodModVariables.world_tick + 2400.0);
                EntitySusanooBase entityCustom = new EntitySusanooSkeleton.EntityCustom(player);
                world.spawnEntity(entityCustom);
                player.getEntityData().setInteger("summonedSusanooID", (entityCustom.getEntityId()));
            }
        } else {
            double cooldown = player.getEntityData().getDouble("susanoo_ticks") * 0.25;
            cooldown *= ProcedureUtils.getCooldownModifier(player);
            player.getEntityData().setBoolean("susanoo_activated", true);
            player.getEntityData().setDouble("susanoo_ticks", 0.00);
            Entity entitySpawned = world.getEntityByID(getSummonedSusanooId(player));
            player.getEntityData().setInteger("summonedSusanooID", 0);
            if (entitySpawned != null) {
                world.removeEntity(entitySpawned);
            }

            if (!flag && helmet.getItem() != ItemMangekyoSharinganEternal.helmet) {
                player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (int)cooldown, 3));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, (int)cooldown, 2));
            }

            player.addPotionEffect(new PotionEffect(PotionFeatherFalling.potion, 60, 5));
        }
    }
}
