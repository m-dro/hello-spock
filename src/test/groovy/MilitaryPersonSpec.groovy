import spock.lang.Specification


class MilitaryPersonSpec extends Specification {

    def "Testing getters and setters of a Java class"() {
        when: "a person has both first and last names and rank"
        MilitaryPerson person = new MilitaryPerson()
        person.firstname = "Susan"
        person.lastname = "Ivanova"
        person.setRank("Commander");

        then: "its title should be surname, name (rank)"
        person.createTitle() == "Ivanova, Susan (Commander)"
    }

}