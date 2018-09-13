package server.service

import server.model.TestModel
import spock.lang.Specification

class TestServiceTest extends Specification {
    def "Add"() {
        setup:
        TestModel mock = Mock()
        mock.getValue() >> 3

        when:
        TestService.add(a, mock) == c

        then:
        1 * mock.getValue()

        where:
        a | b || c
        1 | 2 || 4
        3 | 4 || 6
        5 | 6 || 8
    }
}
