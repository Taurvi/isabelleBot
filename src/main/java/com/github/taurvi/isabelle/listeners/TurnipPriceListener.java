package com.github.taurvi.isabelle.listeners;

import com.github.taurvi.isabelle.commands.CommandRegistry;
import com.github.taurvi.isabelle.commands.IsabelleCommand;
import com.github.taurvi.isabelle.models.TurnipPrice;
import com.github.taurvi.isabelle.repositories.TurnipRepository;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class TurnipPriceListener implements MessageCreateListener {
    private static final int TURNIP_PRICE_EXPIRATION_TIME_IN_MINS = 600;
    private Clock clock;
    private Map<String, IsabelleCommand> commandMap;
    private TurnipRepository turnipRepository;

    public TurnipPriceListener(Clock clock, CommandRegistry commandRegistry, TurnipRepository turnipRepository) {
        this.clock = clock;
        this.commandMap = commandRegistry.getCommandMap();
        this.turnipRepository = turnipRepository;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        IsabelleCommand turnipSetCommand = commandMap.get("turnip-set");
        IsabelleCommand turnipGetCommand = commandMap.get("turnip-get");
        IsabelleCommand turnipHelpCommand = commandMap.get("turnip-help");

        if (turnipSetCommand.isCommand(event.getMessage().getContent())) {
            executeTurnipSetCommand(turnipSetCommand, event);
        }

        if (turnipGetCommand.isCommand(event.getMessage().getContent())) {
            executeTurnipGetCommand(turnipGetCommand, event);
        }

        if (turnipHelpCommand.isCommand(event.getMessage().getContent())) {
            executeTurnipHelpCommand(turnipHelpCommand, event);
        }
    }

    private void executeTurnipSetCommand(IsabelleCommand command, MessageCreateEvent event) {
        String messageContent = event.getMessage().getContent();
        MessageAuthor author = event.getMessageAuthor();

        if (command.isValid(messageContent)) {
            String turnipPriceAsString = removeCommand(command, messageContent);
            int turnipPrice = Integer.valueOf(turnipPriceAsString);

            if (turnipPrice < 0) {
                command.executeError(event, "Are you really sure you want to sell us your turnips?");
            } else if (turnipPrice == 0) {
                command.executeError(event, "I don't think you should be giving those away!");
            } else {
                TurnipPrice turnip = createTurnip(author, turnipPrice);
                if (turnipRepository.read(Long.toString(author.getId())) == null) {
                    turnipRepository.create(turnip);
                } else {
                    turnipRepository.update(turnip);
                }
                command.executeSuccess(event,
                        String.format("You have successfully set your turnip price as: %s", turnipPriceAsString));
            }
        } else {
            command.executeError(event, "That doesn't look like a price!");
        }
    }

    private void executeTurnipGetCommand(IsabelleCommand command, MessageCreateEvent event) {
        StringBuilder reply = new StringBuilder();
        String pricePattern = "**%d**. %s: **%s**";

        List<TurnipPrice> turnipPriceList = turnipRepository.getAllSortedByPrice();
        int rank = 1;
        for(int index = 0; index < turnipPriceList.size(); index++) {
            TurnipPrice currentEntry = turnipPriceList.get(index);
            if (isStale(currentEntry)) {
                turnipRepository.delete(Long.toString(currentEntry.getId()));
            } else {
                reply.append(String.format(pricePattern, rank, currentEntry.getUserName(),
                        currentEntry.getPrice()));
                reply.append("\n\n");
                rank++;
            }
        }

        command.executeSuccess(event, reply.toString());
    }

    private void executeTurnipHelpCommand(IsabelleCommand command, MessageCreateEvent event) {
        StringBuilder reply = new StringBuilder();
        reply.append("```\n")
                .append("!turnip-get - Gets all the prices currently saved.\n")
                .append("!turnip-set {number} - Allows you to set a price for turnips. (ex: !turnip-set 100)\n")
                .append("```");

        command.executeSuccess(event, reply.toString());
    }

    private boolean isStale(TurnipPrice currentEntry) {
        Instant timestamp = Instant.parse(currentEntry.getTimestamp());
        Long difference = ChronoUnit.MINUTES.between(timestamp, Instant.now());
        return difference > TURNIP_PRICE_EXPIRATION_TIME_IN_MINS;
    }

    private String removeCommand(IsabelleCommand turnipSetCommand, String content) {
        return content.substring(turnipSetCommand.getFormattedCommand().length());
    }

    private TurnipPrice createTurnip(MessageAuthor user, int price) {
        return TurnipPrice.builder()
                .id(user.getId())
                .timestamp(clock.instant().toString())
                .userName(user.getDisplayName())
                .price(price)
                .build();
    }
}
