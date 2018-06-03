package com.krzykrucz.payment.infrastructure.movie;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

@Data
@NoArgsConstructor
class VideoInfoDTO {

    private String title;

    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money price;
}
