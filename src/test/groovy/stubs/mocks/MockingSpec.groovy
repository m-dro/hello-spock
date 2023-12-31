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

    /**
     * <div style="font-size: 30;background:#3c423f"><div style="font-size: 35;">&#128142 Using Mocks</div>
     * <br><ul>
     * <li>Testing how many times a method was invoked</li>
     * </ul>
     */
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

    /**
     * <div style="font-size: 30;background:#3c423f"><div style="font-size: 35;">&#128142 Using Mocks</div>
     * <br><ul>
     * <li>Testing specific order of method invokation with multiple then blocks</li>
     * </ul>
     */
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

    /**
     * <div style="font-size: 30;background:#3c423f"><div style="font-size: 35;">&#128142 Using Mocks</div>
     * <br><ul>
     * <li>Testing how many times specific methods were invoked</li>
     * <li>Stubbing what specific methods should return</li>
     * <li>Testing that no other methods were invoked</li>
     * </ul>
     */
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

    /**
     * <div style="font-size: 30;background:#3c423f"><div style="font-size: 35;">&#128142 Using Mocks</div>
     * <br><ul>
     * <li>Stubbing objects with Groovy's closures</li>
     * </ul>
     */
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