import spock.lang.Specification


class PersonSpec extends Specification {

    def "Testing getters and setters"() {
        when: "a person has both first and last names"
        SimplePerson person = new SimplePerson()
        person.firstname = "Susan"
        person.lastname = "Ivanova"

        then: "its title should be surname, name"
        person.createTitle() == "Ivanova, Susan"
    }

}

class SimplePerson {
    String firstname
    String lastname

    String createTitle() {
        return "$lastname, $firstname"
    }
}