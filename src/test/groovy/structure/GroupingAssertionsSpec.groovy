package structure

import spock.lang.Specification

class GroupingAssertionsSpec extends Specification{

    def "buying products reduces inventory availability"() {
        given: "an inventory with products"
        Product laptop = new Product(name:"toshiba",price:1200,weight:5)
        Product camera = new Product(name:"panasonic",price:350,weight:2)
        Product hifi = new Product(name:"jvc",price:600,weight:5)
        WarehouseInventory warehouse = new WarehouseInventory()
        warehouse.preload(laptop, 3)
        warehouse.preload(camera, 5)
        warehouse.preload(hifi, 2)

        and: "an empty basket"
        EnterprisyBasket basket = new EnterprisyBasket()
        basket.setWarehouseInventory(warehouse)
        basket.setCustomerResolver(new DefaultCustomerResolver())
        basket.setLanguage("English")
        basket.setNumberOfCaches(3)
        basket.enableAutoRefresh()

        when: "user gets a laptop and two cameras"
        basket.addProduct(laptop)
        basket.addProduct(camera, 2)

        and: "user completes the transaction"
        basket.checkout()

        then: "warehouse is updated accordingly"
        !warehouse.isEmpty()
        warehouse.getBoxesMovedToday() == 3
        warehouse.isProductAvailable("toshiba") == 2
        warehouse.isProductAvailable("panasonic") == 3
        warehouse.isProductAvailable("jvc") == 2
    }

    def "Buying products reduces the inventory availability - grouping"() {
        given: "an inventory with products"
        Product laptop = new Product(name:"toshiba",price:1200,weight:5)
        Product camera = new Product(name:"panasonic",price:350,weight:2)
        Product hifi = new Product(name:"jvc",price:600,weight:5)
        WarehouseInventory warehouse = new WarehouseInventory()
        warehouse.with {
            preload(laptop, 3)
            preload(camera, 5)
            preload(hifi, 2)
        }

        and: "an empty basket"
        EnterprisyBasket basket = new EnterprisyBasket()
        basket.with {
            setWarehouseInventory(warehouse)
            setCustomerResolver(new DefaultCustomerResolver())
            setLanguage("English")
            setNumberOfCaches(3)
            enableAutoRefresh()
        }
        when: "user gets a laptop and two cameras"
        basket.addProduct(camera,2)
        basket.addProduct(laptop)

        and: "user completes the transaction"
        basket.checkout()

        then: "warehouse is updated accordingly"
        with(warehouse){
            !isEmpty()
            getBoxesMovedToday() == 3
            isProductAvailable("toshiba") == 2
            isProductAvailable("panasonic") == 3
            isProductAvailable("jvc") == 2
        }
    }

    def "Buying products reduces the inventory availability - no parentheses version"() {
        given: "an inventory with products"
        Product laptop = new Product(name:"toshiba",price:1200,weight:5)
        Product camera = new Product(name:"panasonic",price:350,weight:2)
        Product hifi = new Product(name:"jvc",price:600,weight:5)
        WarehouseInventory warehouse = new WarehouseInventory()
        warehouse.with{
            preload laptop,3
            preload camera,5
            preload hifi,2
        }

        and: "an empty basket"
        EnterprisyBasket basket = new EnterprisyBasket()
        basket.with {
            setWarehouseInventory(warehouse)
            setCustomerResolver(new DefaultCustomerResolver())
            setLanguage "english"
            setNumberOfCaches 3
            enableAutoRefresh()
        }

        when: "user gets a laptop and two cameras"
        basket.with {
            addProduct camera,2
            addProduct laptop
        }

        and: "user completes the transaction"
        basket.checkout()

        then: "warehouse is updated accordingly"
        with(warehouse) {
            !isEmpty()
            getBoxesMovedToday() == 3
            isProductAvailable("toshiba") == 2
            isProductAvailable("panasonic") == 3
            isProductAvailable("jvc") == 2
        }

    }
}
