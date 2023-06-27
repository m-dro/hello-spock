import spock.lang.Specification
import spock.lang.Unroll

class MyFirstGroovySpec extends Specification {

    def "Multiply two numbers and return the result"() {
        when: "a new Multiplier class is created"
        def multi = new Multiplier()

        then: "3 times 7 is 21"
        multi.multiply(3, 7) == 21
    }

    def "Multiply pairs of numbers and return the result"() {
        given: "a Multiplier"
        def multi = new Multiplier()

        expect: "that it calculates the product of two numbers"
        multi.multiply(first, second) == product

        where: "some scenarios are"
        first   | second    || product
        1       | 1         || 1
        2       | 5         || 10
        -5      | 5         || -25
        -5      | -3        || 15
        0       | 25        || 0
    }

    @Unroll("Testing the Multiplier where #first times #second is #product - ALTERNATIVE")
    def "Multiply pairs of numbers and return the result - ALTERNATIVE"() {
        given: "a Multiplier"
        def multi = new Multiplier()

        expect: "that it calculates the product of two numbers"
        multi.multiply(first, second) == product

        where: "some scenarios are"
        first   | second    || product
        1       | 1         || 1
        2       | 5         || 10
        -5      | 5         || -25
        -5      | -3        || 15
        0       | 25        || 0
    }

    def "Multiplying #first and #second always produces a negative number"() {
        given: "a Multiplier"
        def multi = new Multiplier()

        expect: "that it calculates the product of two numbers"
        multi.multiply(first, second) < 0

        where: "some scenarios are"
        first << (20..80)
        second << (-65..-5)
    }

}
