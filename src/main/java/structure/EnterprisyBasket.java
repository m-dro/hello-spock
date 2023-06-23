package structure;

import java.util.Map;

public class EnterprisyBasket extends Basket{

    private WarehouseInventory warehouseInventory  = null;

    public void enableAutoRefresh()
    {

    }

    public void setNumberOfCaches(int number)
    {

    }

    public void setCustomerResolver(DefaultCustomerResolver defaultCustomerResolver)
    {

    }

    public void setWarehouseInventory(WarehouseInventory warehouseInventory)
    {
        this.warehouseInventory = warehouseInventory;
    }

    public void setLanguage(String language)
    {

    }

    public void checkout()
    {
        for(Map.Entry<Product,Integer> entry:contents.entrySet())
        {
            warehouseInventory.subtract(entry.getKey().getName(),entry.getValue());
        }
    }
}
