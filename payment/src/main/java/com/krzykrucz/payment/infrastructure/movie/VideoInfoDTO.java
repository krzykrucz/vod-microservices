package com.krzykrucz.payment.infrastructure.movie;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoInfoDTO {

    private String title;

    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money price;
}
