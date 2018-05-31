package com.krzykrucz.payment.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PurchaseMovieCommand {

    private String movieTitle;

    private String successViewUrl;

    private String cancelViewUrl;

}
