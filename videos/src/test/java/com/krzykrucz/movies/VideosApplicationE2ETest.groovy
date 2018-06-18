package com.krzykrucz.movies

import com.google.common.collect.ImmutableSet
import com.krzykrucz.movies.domain.*
import com.krzykrucz.movies.infrastructure.viewer.CustomerClient
import com.krzykrucz.movies.infrastructure.viewer.CustomerDTO
import com.krzykrucz.movies.infrastructure.viewer.MovieDTO
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus

class VideosApplicationE2ETest extends AbstractE2ESpec {

    @Autowired
    VideoRepository videoRepository

    @Autowired
    MongoTemplate mongoTemplate

    @MockBean
    CustomerClient customerClient

    def setup() {
        loadVideo 'Harry Potter'
        loadVideo 'Godfather'
        Mockito.when(customerClient.getCurrentCustomer('John Smith'))
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
        res.json*.title == ['Harry Potter', 'Godfather']
        res.json*.price.pretty == ['$10.00', '$10.00']
    }

    def "should fetch video info by title"() {
        when:
        def res = get("/videos/${title}")

        then:
        res.status == HttpStatus.OK
        res.json.title == title

        where:
        title          || _
        'Harry Potter' || _
        'Godfather'    || _
    }

    def "should fetch video content by title"() {
        when:
        def res = get('/videos/content/Harry Potter?viewer=John Smith')

        then:
        res.status == HttpStatus.OK
        res.json.byteContent.size() == 2810456
    }

    def "should not fetch not bought video content"() {
        when:
        def res = get('/videos/content/Godfather?viewer=John Smith')

        then:
        res.status == HttpStatus.FORBIDDEN
        res.content == 'VIDEO_NOT_BOUGHT'
    }

    def "should not fetch non-existent video content"() {
        when:
        def res = get('/videos/content/Godfather2?viewer=John Smith')

        then:
        res.status == HttpStatus.NOT_FOUND
        res.content == 'VIDEO_NOT_EXISTS'
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
