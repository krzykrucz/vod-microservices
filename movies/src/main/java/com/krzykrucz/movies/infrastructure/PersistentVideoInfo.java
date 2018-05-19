package com.krzykrucz.movies.infrastructure;

import com.krzykrucz.movies.domain.Price;
import lombok.*;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private UUID videoContentId;

    @ContentLength
    private Long videoFileLength;

    private final Price price;

}
