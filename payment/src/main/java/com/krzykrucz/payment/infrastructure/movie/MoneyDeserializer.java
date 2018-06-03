package com.krzykrucz.payment.infrastructure.movie;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;

public class MoneyDeserializer extends StdDeserializer<Money> {

    private static final long serialVersionUID = 1L;

    public MoneyDeserializer() {
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonNode moneyTree = jp.readValueAsTree();

        final int amount = moneyTree.get("amount").asInt();

        final JsonNode currencyNode = moneyTree.get("currency");
        final CurrencyUnit currency = currencyNode == null ? CurrencyUnit.USD : CurrencyUnit.of(currencyNode.asText());

        return Money.ofMinor(currency, amount);
    }
}