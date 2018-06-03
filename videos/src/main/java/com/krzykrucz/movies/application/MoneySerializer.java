package com.krzykrucz.movies.application;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.money.Money;

import java.io.IOException;

public class MoneySerializer extends JsonSerializer<Money> {

    @Override
    public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        jgen.writeNumberField("amount", value.getAmountMinorInt());
        jgen.writeStringField("str", value.getAmount().toString());
        jgen.writeStringField("symbol", value.getCurrencyUnit().getSymbol());
        jgen.writeStringField("currency", value.getCurrencyUnit().getCode());

        final String pretty = prettyPrintWithCents(value);
        jgen.writeStringField("pretty", pretty);

        jgen.writeEndObject();
    }

    private String prettyPrintWithCents(Money money) {
        return money.getCurrencyUnit().getSymbol() + money.getAmount().toPlainString();
    }
}