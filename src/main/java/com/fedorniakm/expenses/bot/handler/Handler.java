package com.fedorniakm.expenses.bot.handler;

public interface Handler<T> {

    void handle(final T input);

}
