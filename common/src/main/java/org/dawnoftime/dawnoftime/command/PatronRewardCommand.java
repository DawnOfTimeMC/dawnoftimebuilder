package org.dawnoftime.dawnoftime.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.dawnoftime.dawnoftime.config.PatronConfig;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;

/**
 * Registers and handles the /dotreward token command.
 * Gives 1 patron token matching the requested tier — used as a crafting ingredient for patron rewards.
 * All logic runs server-side — no client code here.
 *
 * Tier mapping:
 *   tier 1 → patreon_tier_1 (Silver Patreon Token)
 *   tier 2 → patreon_tier_2 (Golden Patreon Token)
 *   tier 3 → patreon_tier_3 (Crystal Patreon Token)
 *   tier 4 → patreon_tier_4 (Amethist Patreon Token)
 *   tier 5 → patreon_tier_5 (Everlasting Patreon Token)
 *   tier 6 → patreon_tier_6 (Heavenly Patreon Token)
 *
 * A player can claim any tier up to and including their own (cascade).
 * The patron list is fetched from GitHub and cached locally — see PatronConfig.
 */
public class PatronRewardCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("dotreward")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("token")
                    .then(Commands.argument("tier", IntegerArgumentType.integer(1, 6))
                        .executes(context -> {
                            int tier = IntegerArgumentType.getInteger(context, "tier");
                            return executeToken(context.getSource(), tier);
                        })
                    )
                )
        );
    }

    private static int executeToken(CommandSourceStack source, int requestedTier) {
        // Commands can be run from console too — ensure it's a player
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be used by a player."));
            return 0;
        }

        PatronConfig config = PatronConfig.load();

        String uuid = player.getStringUUID();
        int playerTier = getPlayerMaxTier(config, uuid);

        if (playerTier == 0) {
            source.sendFailure(Component.literal("You are not registered as a patron."));
            return 0;
        }

        if (requestedTier > playerTier) {
            source.sendFailure(Component.literal(
                "Your patron tier does not grant access to tier " + requestedTier + " rewards."
            ));
            return 0;
        }

        ItemStack token = getTierToken(requestedTier);
        if (token.isEmpty()) {
            source.sendFailure(Component.literal("No token configured for tier " + requestedTier + "."));
            return 0;
        }

        // Add to inventory; if full, drop at feet so tokens are never lost
        if (!player.getInventory().add(token)) {
            player.drop(token, false);
        }

        source.sendSuccess(() -> Component.literal(
            "You received a " + getTierTokenName(requestedTier) + "!"
        ), false);
        return 1;
    }

    /**
     * Returns the highest tier the player belongs to, or 0 if not a patron.
     * Highest tier is checked first to avoid misidentifying a high-tier patron as tier 1.
     */
    private static int getPlayerMaxTier(PatronConfig config, String uuid) {
        if (config.tier6.contains(uuid)) return 6;
        if (config.tier5.contains(uuid)) return 5;
        if (config.tier4.contains(uuid)) return 4;
        if (config.tier3.contains(uuid)) return 3;
        if (config.tier2.contains(uuid)) return 2;
        if (config.tier1.contains(uuid)) return 1;
        return 0;
    }

    private static ItemStack getTierToken(int tier) {
        return switch (tier) {
            case 1 -> new ItemStack(DoTBItemsRegistry.INSTANCE.PATREON_TIER_1.get(), 1);
            case 2 -> new ItemStack(DoTBItemsRegistry.INSTANCE.PATREON_TIER_2.get(), 1);
            case 3 -> new ItemStack(DoTBItemsRegistry.INSTANCE.PATREON_TIER_3.get(), 1);
            case 4 -> new ItemStack(DoTBItemsRegistry.INSTANCE.PATREON_TIER_4.get(), 1);
            case 5 -> new ItemStack(DoTBItemsRegistry.INSTANCE.PATREON_TIER_5.get(), 1);
            case 6 -> new ItemStack(DoTBItemsRegistry.INSTANCE.PATREON_TIER_6.get(), 1);
            default -> ItemStack.EMPTY;
        };
    }

    private static String getTierTokenName(int tier) {
        return switch (tier) {
            case 1 -> "Silver Patreon Token";
            case 2 -> "Golden Patreon Token";
            case 3 -> "Crystal Patreon Token";
            case 4 -> "Amethist Patreon Token";
            case 5 -> "Everlasting Patreon Token";
            case 6 -> "Heavenly Patreon Token";
            default -> "Unknown";
        };
    }
}
