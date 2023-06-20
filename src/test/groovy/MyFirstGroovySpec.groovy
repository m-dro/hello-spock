import spock.lang.Specification

class MyFirstGroovySpec extends Specification {

    def "Multiply two numbers and return the result"() {
        when: "a new Multiplier class is created"
        def multi = new Multiplier();

        then: "3 times 7 is 21"
        multi.multiply(3, 7) == 21
    }

}
