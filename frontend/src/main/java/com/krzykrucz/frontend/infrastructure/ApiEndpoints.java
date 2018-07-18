package com.krzykrucz.frontend.infrastructure;

public interface ApiEndpoints {

    ApiEndpoint<Void> AUTH_USER = ApiEndpoint.define("/user/login", Void.class);

    ApiEndpoint<VideoDto[]> GET_ALL_VIDEOS = ApiEndpoint.define("/videos/all", VideoDto[].class);

    ApiEndpoint<VideoContentDto> GET_VIDEO_CONTENT = ApiEndpoint.define("/videos/content/{title}/{viewerName}", VideoContentDto.class);

    ApiEndpoint<PaymentViewDto> BUY_VIDEO = ApiEndpoint.define("/payment/buy", PaymentViewDto.class);

    ApiEndpoint<Void> EXECUTE_PAYMENT = ApiEndpoint.define("/payment/success", Void.class);

}
