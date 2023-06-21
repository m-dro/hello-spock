package assets;

import assets.CargoOrder;

public class Cargo {
    private String type;
    private CargoOrder cargoOrder;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CargoOrder getCargoOrder() {
        return cargoOrder;
    }

    public void setCargoOrder(CargoOrder cargoOrder) {
        this.cargoOrder = cargoOrder;
    }

    public float getTons() {
        return tons;
    }

    public void setTons(float tons) {
        this.tons = tons;
    }

    private float tons;
}
