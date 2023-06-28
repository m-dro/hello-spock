package stubs

import spock.lang.Specification
import structure.Basket
import structure.Product
import structure.WarehouseInventory


class StubbingSpec extends Specification {

    def "if warehouse is empty, nothing can be shipped"() {
        given: "basket and TV"
        Basket basket = new Basket()
        Product tv = new Product(name: "bravia", price: 1200, weight: 18)

        and: "empty warehouse"
        WarehouseInventory warehouse = Stub(WarehouseInventory)
        warehouse.isEmpty() >> true
        basket.setWarehouseInventory(warehouse)

        when: "user checks out TV"
        basket.addProduct tv

        then: "order cannot be shipped"
        !basket.canShipCompletely()
    }

    def "if warehouse has both products, everything is fine"() {
        given: "basket, TV and camera"
        Basket basket = new Basket()
        Product tv = new Product(name: "bravia", price: 1200, weight: 18)
        Product camera = new Product(name: "panasonic", price: 350, weight: 2)

        and: "warehouse has enough stock"
        WarehouseInventory warehouse = Stub(WarehouseInventory)
        warehouse.isProductAvailable(_, 1) >> true
        basket.setWarehouseInventory(warehouse)

        when: "user checks out tv and camera"
        basket.addProduct tv
        basket.addProduct camera

        then: "order can be shipped"
        basket.canShipCompletely()
    }

    def "Inventory is always checked in the last possible moment"() {
        given: "a basket and a TV"
        Product tv = new Product(name: "bravia", price: 1200, weight: 18)
        Basket basket = new Basket()

        and: "a warehouse with fluctuating stock levels"
        WarehouseInventory warehouse = Stub(WarehouseInventory)
        warehouse.isProductAvailable("bravia", _) >>> true >> false
        warehouse.isEmpty() >>> [false, true]
        basket.setWarehouseInventory(warehouse)

        when: "user checks out the tv"
        basket.addProduct tv

        then: "order can be shipped right away"
        basket.canShipCompletely()

        when: "user wants another TV"
        basket.addProduct tv

        then: "order can no longer be shipped"
        !basket.canShipCompletely()
    }

    def "A problematic inventory means nothing can be shipped"() {
        given: "a basket and a TV"
        Product tv = new Product(name: "bravia", price: 1200, weight: 18)
        Basket basket = new Basket()

        and: "a warehouse with serious issues"
        WarehouseInventory warehouse = Stub(WarehouseInventory)
        warehouse.isProductAvailable("bravia", _) >> { throw new RuntimeException("critical error") }
        basket.setWarehouseInventory(warehouse)

        when: "user checks out the tv"
        basket.addProduct tv

        then: "order cannot be shipped"
        !basket.canShipCompletely()
    }
}