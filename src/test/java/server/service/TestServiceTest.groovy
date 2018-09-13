package server.service

import server.model.TestModel
import spock.lang.Specification

class TestServiceTest extends Specification {
    def "Add"() {
        setup:
        TestModel mock = Mock()
        mock.getValue() >> 3

        expect:
        TestService.add(a, mock) == c

        where:
        a | b || c
        1 | 2 || 4
        3 | 4 || 6
        5 | 6 || 8
    }
}
