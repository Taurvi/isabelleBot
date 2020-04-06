package com.github.taurvi.isabelle.commands;

public class CommandException extends RuntimeException {
    public CommandException(String message, Throwable error) {
        super(message, error);
    }
}
