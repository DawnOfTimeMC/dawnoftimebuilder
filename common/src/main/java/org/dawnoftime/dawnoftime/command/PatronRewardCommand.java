package org.dawnoftime.dawnoftime.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.dawnoftime.dawnoftime.config.PatronConfig;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;

/**
 * Registers and handles the /dotreward blocks command.
 * All logic runs server-side — no client code here.
 *
 * Tier mapping:
 *   tier 1 → chromatic_marble_statue_mars (64x)
 *   tier 2 → golden_marble_statue_mars    (64x)
 *   tier 3 → blackstone_marble_statue_mars (64x)
 *
 * A player can claim any tier up to and including their own (cascade).
 * The patron list is bundled inside the JAR — see data/dawnoftimebuilder/patrons.json.
 */
public class PatronRewardCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("dotreward")
                .then(Commands.literal("blocks")
                    .then(Commands.argument("tier", IntegerArgumentType.integer(1, 3))
                        .executes(context -> {
                            int tier = IntegerArgumentType.getInteger(context, "tier");
                            return executeBlocks(context.getSource(), tier);
                        })
                    )
                )
        );
    }

    private static int executeBlocks(CommandSourceStack source, int requestedTier) {
        // Commands can be run from console too — ensure it's a player
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be used by a player."));
            return 0;
        }

        // Patron list is bundled inside the JAR — no external file needed
        PatronConfig config = PatronConfig.load();

        if (config == null) {
            // PatronConfig.load already logged the parse error
            source.sendFailure(Component.literal("Patron configuration error. Please contact an administrator."));
            return 0;
        }

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

        ItemStack reward = getTierReward(requestedTier);
        if (reward.isEmpty()) {
            source.sendFailure(Component.literal("No reward configured for tier " + requestedTier + "."));
            return 0;
        }

        // Add to inventory; if full, drop at feet so items are never lost
        if (!player.getInventory().add(reward)) {
            player.drop(reward, false);
        }

        source.sendSuccess(() -> Component.literal(
            "You received 64 " + getTierBlockName(requestedTier) + "!"
        ), false);
        return 1;
    }

    /**
     * Returns the highest tier the player belongs to, or 0 if not a patron.
     * Tier 3 is checked first so a tier-3 patron is not misidentified as tier 1.
     */
    private static int getPlayerMaxTier(PatronConfig config, String uuid) {
        if (config.tier3.contains(uuid)) return 3;
        if (config.tier2.contains(uuid)) return 2;
        if (config.tier1.contains(uuid)) return 1;
        return 0;
    }

    private static ItemStack getTierReward(int tier) {
        return switch (tier) {
            case 1 -> new ItemStack(DoTBBlocksRegistry.INSTANCE.CHROMATIC_MARBLE_STATUE_MARS.get(), 64);
            case 2 -> new ItemStack(DoTBBlocksRegistry.INSTANCE.GOLDEN_MARBLE_STATUE_MARS.get(), 64);
            case 3 -> new ItemStack(DoTBBlocksRegistry.INSTANCE.BLACKSTONE_MARBLE_STATUE_MARS.get(), 64);
            default -> ItemStack.EMPTY;
        };
    }

    private static String getTierBlockName(int tier) {
        return switch (tier) {
            case 1 -> "Chromatic Marble Statue of Mars";
            case 2 -> "Golden Marble Statue of Mars";
            case 3 -> "Blackstone Marble Statue of Mars";
            default -> "Unknown";
        };
    }
}
