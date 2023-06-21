import groovy.json.JsonSlurper
import groovy.xml.XmlSlurper
import spock.lang.Specification


class TextSpec extends Specification {

    def "demo for reading a text file"() {
        when: "a paragraph is processed"
        WordDetector wordDetector = new WordDetector();
        String inputText = new File("src/test/resources/quotes.txt").text
        wordDetector.parseText(inputText);

        then: "word frequency should be correct"
        wordDetector.wordsFound == 78

    }

    def "demo for reading XML file"() {
        when: "XML file is processed"
        def xmlRoot = new XmlSlurper().parse('src/test/resources/employee-data.xml')

        then: "XML content should be correct"
        xmlRoot.department.size() == 1
        xmlRoot.department.@name == "sales"
        xmlRoot.department.employee.size() == 2
        xmlRoot.department.employee[0].firstName == "Orlando"
        xmlRoot.department.employee[0].lastName == "Boren"
        xmlRoot.department.employee[0].age == 24
        xmlRoot.department.employee[1].firstName == "Diana"
        xmlRoot.department.employee[1].lastName == "Colgan"
        xmlRoot.department.employee[1].age == 28
    }

    def "demo for reading JSON file"() {
        when: "JSON  file is processed"
        def jsonRoot = new JsonSlurper().parse(new File('src/test/resources/employee-data.json'))

        then: "JSON content should be correct"
        jsonRoot.staff.department.name == "sales"
        jsonRoot.staff.department.employee.size() == 2
        jsonRoot.staff.department.employee[0].firstName == "Orlando"
        jsonRoot.staff.department.employee[0].lastName == "Boren"
        jsonRoot.staff.department.employee[0].age == "24"
        jsonRoot.staff.department.employee[1].firstName == "Diana"
        jsonRoot.staff.department.employee[1].lastName == "Colgan"
        jsonRoot.staff.department.employee[1].age == "28"
    }

}