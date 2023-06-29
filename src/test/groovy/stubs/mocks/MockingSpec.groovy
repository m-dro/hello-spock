package stubs.mocks

import spock.lang.Specification
import structure.BillableBasket
import structure.CreditCardProcessor
import structure.Customer
import structure.Product
import structure.WarehouseInventory


class MockingSpec extends Specification {

    BillableBasket basket
    Customer customer
    Product tv

    def setup() {
         basket = new BillableBasket()
         customer = new Customer(name: "John", vip: false, creditCard: "testCard")
         tv = new Product(name: "bravia", price: 1200, weight: 12)
    }


    def "credit card connection is always closed down"(){
        given: "credit card service"
        CreditCardProcessor service = Mock(CreditCardProcessor)
        basket.setCreditCardProcessor(service)

        when: "user checks out tv"
        basket.addProduct tv
        basket.checkout customer

        then: "connection is always closed at the end"
        1 * service.shutdown()
    }

    def "credit card connection is closed down as last operation"(){
        given: "credit card service"
        CreditCardProcessor service = Mock(CreditCardProcessor)
        basket.setCreditCardProcessor(service)

        when: "user checks out tv"
        basket.addProduct tv
        basket.checkout customer

        then: "credit card is charged and"
        1 * service.sale(_, _)

        then: "connection is always closed at the end"
        1 * service.shutdown()
    }

    def "warehouse is queried for each product - strict"() {
        given: "camera and warehouse with limitless stock"
        Product camera = new Product(name: "panasonic", price: 350, weight: 2)
        WarehouseInventory warehouse = Mock(WarehouseInventory)
        basket.setWarehouseInventory(warehouse)

        when: "user checks out both products"
        basket.addProduct tv
        basket.addProduct camera
        boolean readyToShip = basket.canShipCompletely()

        then: "order can be shipped"
        2 * warehouse.isProductAvailable(_, _) >> true
        1 * warehouse.isEmpty() >> false
        0 * warehouse._
    }

    def "vip status is correctly passed to credit card"() {
        given: "camera"
        Product camera = new Product(name: "panasonic", price: 350, weight: 2)

        and: "credit card service"
        CreditCardProcessor creditCardService = Mock(CreditCardProcessor)
        basket.setCreditCardProcessor(creditCardService)

        when: "user checks out two products"
        basket.addProduct tv
        basket.addProduct camera
        basket.checkout(customer)

        then: "credit card is charged"
        1 * creditCardService.sale(1550, {client -> client.vip == false})

    }

    def cleanup() {
        basket.clearAllProducts()
    }
}