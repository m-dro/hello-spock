package assets

import spock.lang.Specification

class AssetSpec extends Specification {

    def "should easily create test objects"() {
        given: "ObjectGraphBuilder object initialised for assets"
        ObjectGraphBuilder builder = new ObjectGraphBuilder()
        builder.classNameResolver = "assets"
        when: "Builder is set up with some data"
        AssetInventory shipRegistry = builder.assetInventory() {
            ship (name: "Sea Spirit", destination: "Chiba") {
                crewMember (firstName: "Michael", lastName: "Curiel", age: 43)
                crewMember (firstName: "Sean", lastName: "Parker", age: 28)
                crewMember (firstName: "Lilian", lastName: "Zimmerman", age: 32)
                cargo (type: "Cotton", tons: 5.4) {
                    cargoOrder (buyer: "Rei Hosokawa", city: "Yokohama", price: 34000)
                }
                cargo(type:"Olive Oil", tons:3.0) {
                    cargoOrder ( buyer: "Hirokumi Kasaya", city: "Kobe", price: 27000)
                }
            }
            ship ( name: "Calypso I", destination: "Bristol") {
                crewMember(firstName: "Eric", lastName: "Folkes", age:35)
                crewMember(firstName: "Louis", lastName: "Lessard", age:22)
                cargo(type: "Oranges", tons:2.4) {
                    cargoOrder ( buyer: "Gregory Schmidt", city: "Manchester", price: 62000)
                }
            }
            ship ( name: "Desert Glory", destination: "Los Angeles") {
                crewMember(firstName: "Michelle", lastName: "Kindred", age: 38)
                crewMember(firstName: "Kathy", lastName: "Parker", age: 21)
                cargo(type: "Timber", tons: 4.8) {
                    cargoOrder ( buyer: "Carolyn Cox", city: "Sacramento", price: 18000)
                }
            }
        }
        then: "Data objects have correct values"
        shipRegistry.ships.size() == 3
        shipRegistry.ships[0].name == "Sea Spirit"
        shipRegistry.ships[1].crewMembers.size() == 2
        shipRegistry.ships[1].crewMembers[0].firstName == "Eric"
        shipRegistry.ships[2].cargos[0].type == "Timber"
        shipRegistry.ships[2].cargos[0].cargoOrder.city == "Sacramento"
    }
}
