package me.acablade.lavyukseliyor.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.lavyukseliyor.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * @author Acablade/oz
 */
public class StartingItemsFeature extends AbstractFeature {
    public StartingItemsFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getAbstractPhase().getGame().getGameData().getPlayerList().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> {
                    player.getInventory().clear();
                    player.getInventory().addItem(
                            new ItemBuilder(Material.NETHERITE_PICKAXE)
                                    .setUnbreakable(true)
                                    .withEnchant(Enchantment.DIG_SPEED,3, true)
                                    .getItemStack()
                    );
                    player.getInventory().addItem(
                            new ItemBuilder(Material.COBBLESTONE)
                                    .withAmount(64)
                                    .getItemStack()
                    );
                    player.getInventory().addItem(
                            new ItemBuilder(Material.COBBLESTONE)
                                    .withAmount(64)
                                    .getItemStack()
                    );
                    player.getInventory().addItem(
                            new ItemBuilder(Material.OAK_LOG)
                                    .withAmount(16)
                                    .getItemStack()
                    );
                    player.getInventory().addItem(
                            new ItemBuilder(Material.COOKED_BEEF)
                                    .withAmount(16)
                                    .getItemStack()
                    );
                });
    }
}
