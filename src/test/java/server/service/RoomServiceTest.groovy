package server.service

import base.BaseTestCase

class RoomServiceTest extends BaseTestCase {
    @Override
    def setup() {
        return super.setup()
    }

    @Override
    def cleanup() {
        return super.cleanup()
    }

    def "ComeIn"() {
        setup:
        println("come in setup")

        when:
        println("come in when")

        then:
        println("come in then")
    }

    def "SpeakIn"() {
        setup:
        println("speak in setup")

        when:
        println("speak in when")

        then:
        println("speak in then")
    }
}
