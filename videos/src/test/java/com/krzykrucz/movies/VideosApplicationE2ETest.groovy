package com.krzykrucz.movies

import com.google.common.collect.ImmutableSet
import com.krzykrucz.movies.domain.*
import com.krzykrucz.movies.infrastructure.viewer.CustomerClient
import com.krzykrucz.movies.infrastructure.viewer.CustomerDTO
import com.krzykrucz.movies.infrastructure.viewer.MovieDTO
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus

class VideosApplicationE2ETest extends AbstractE2ESpec {

    static final USD_10 = Money.of(CurrencyUnit.USD, 10)

    @Autowired
    VideoRepository videoRepository

    @Autowired
    MongoTemplate mongoTemplate

    @MockBean
    CustomerClient customerClient

    def setup() {
        loadVideo 'Harry Potter'
        loadVideo 'Godfather'
        Mockito.when(customerClient.currentCustomer)
                .thenReturn(new CustomerDTO(
                "John Smith",
                ImmutableSet.of(new MovieDTO("Harry Potter"))))

    }

    def cleanup() {
        mongoTemplate.getDb().drop()
    }

    def "should fetch all video infos"() {
        when:
        def res = get('/videos/all')

        then:
        res.status == HttpStatus.OK
        res.result.asyncResult*.title == ['Harry Potter', 'Godfather']
        res.result.asyncResult*.price.money == [USD_10, USD_10]
    }

    def "should fetch video info by title"() {
        when:
        def res = get("/videos/${title}")

        then:
        res.status == HttpStatus.OK
        res.result.asyncResult.title == title

        where:
        title          || _
        'Harry Potter' || _
        'Godfather'    || _
    }

    def "should fetch video content by title"() {
        when:
        def res = get('/videos/content/Harry Potter')

        then:
        res.status == HttpStatus.OK
        res.result.asyncResult.byteContent.length == 2107842
    }

    def "should not fetch not bought video content"() {
        when:
        def res = get('/videos/content/Godfather')

        then:
        res.status == HttpStatus.OK
        res.result.asyncResult == null
    }

    private def loadVideo(String title) {
        def videoFile = new File(getClass().getClassLoader().getResource('videos/sample.mp4').getFile())
        def sampleVideoInfo = new VideoInfo(
                new VideoId(UUID.randomUUID()),
                title,
                Price.fromUSD(10)
        )
        def sampleVideoContent = VideoContent.fromFile videoFile
        videoRepository.save sampleVideoInfo, sampleVideoContent
    }

}
