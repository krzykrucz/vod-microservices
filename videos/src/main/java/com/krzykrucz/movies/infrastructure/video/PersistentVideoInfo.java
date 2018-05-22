package com.krzykrucz.movies.infrastructure.video;

import com.krzykrucz.movies.domain.Price;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = "videoId")
class PersistentVideoInfo {

    @Id
    private final UUID videoId;

    @Indexed(unique = true)
    private final String title;

    @ContentId
    @Setter
    private UUID videoContentId;

    @ContentLength
    @Setter
    private Long videoFileLength;

    private final Price price;


    PersistentVideoInfo() {
        videoId = null;
        title = null;
        price = null;
    }
}
