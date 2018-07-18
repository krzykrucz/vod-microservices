package com.krzykrucz.user

import com.krzykrucz.user.domain.UserFactory
import com.krzykrucz.user.domain.UserName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus

class AuthenticationE2ETest extends AbstractE2ESpec {

    @Autowired
    UserFactory userFactory

    @Autowired
    MongoTemplate mongoTemplate

    def cleanup() {
        mongoTemplate.getDb().drop()
    }

    def "should authenticate valid credentials"() {
        given:
        userInDatabase('Barbossa', 'unsafe')

        when:
        def credentials = [userIdentifier: 'Barbossa', password: 'unsafe']
        def res = post('/user/login', credentials)

        then:
        res.status == HttpStatus.OK
    }

    def "should not authenticate invalid credentials"() {
        given:
        userInDatabase('Barbossa', 'unsafe')

        when:
        def credentials = [userIdentifier: idCredential, password: passwordCredential]
        def res = post('/user/login', credentials)

        then:
        res.status == HttpStatus.FORBIDDEN

        where:
        idCredential | passwordCredential
        'Barbossa2'  | 'unsafe'
        'Barbossa'   | 'unsafe2'
    }

    def userInDatabase(String username, String password) {
        userFactory.createNewUser(new UserName(username), password)
    }
}
