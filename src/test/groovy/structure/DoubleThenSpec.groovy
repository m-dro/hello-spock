package structure

import spock.lang.Specification

class DoubleThenSpec extends Specification{

    def "adding products to basket increases its weight"(){
        given: "an empty basket"
        Basket basket = new Basket()

        and: "two products"
        Product tv = new Product(name: "Bravia", price: 1200, weight: 18)
        Product camera = new Product(name: "Panasonic", price: 350, weight: 2)

        when: "user gets the camera"
        basket.addProduct(camera)

        then: "basket weight is updated accordingly"
        basket.currentWeight == camera.weight

        when: "user gets the tv too"
        basket.addProduct(tv)

        then: "basket weight is updated accordingly"
        basket.currentWeight == camera.weight + tv.weight
    }
}
