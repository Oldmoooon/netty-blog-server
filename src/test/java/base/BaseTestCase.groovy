package base

import spock.lang.Specification

class BaseTestCase extends Specification {
    def setupSpec() {
        println("setup spec")
    }

    def cleanupSpec() {
        println("cleanup spec")
    }

    def setup() {
        println("setup")
    }

    def cleanup() {
        println("cleanup")
    }
}
