package com.paritytrading.philadelphia.client.command;

import com.paritytrading.philadelphia.client.TerminalClient;
import java.util.Scanner;
import java.util.stream.Stream;

class HelpCommand implements Command {

    @Override
    public void execute(TerminalClient client, Scanner arguments) throws CommandException {
        if (arguments.hasNext()) {
            Command command = Commands.find(arguments.next());

            if (arguments.hasNext())
                throw new CommandException();

            if (command != null)
                displayCommandHelp(client, command);
            else
                displayGeneralHelp(client);
        } else {
            displayGeneralHelp(client);
        }
    }

    private void displayGeneralHelp(TerminalClient client) {
        client.printf("Commands:\n");

        int maxCommandNameLength = calculateMaxCommandNameLength();

        for (Command command : Commands.all())
            client.printf("  %-" + maxCommandNameLength + "s  %s\n", command.getName(), command.getDescription());

        client.printf("\nType 'help <command>' for command specific help.\n");
    }

    private void displayCommandHelp(TerminalClient client, Command command) {
        client.printf("Usage: %s\n\n  %s\n\n", command.getUsage(), command.getDescription());
    }

    private int calculateMaxCommandNameLength() {
        return Stream.of(Commands.names()).mapToInt(String::length).max().orElse(0);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display the help";
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }

}
