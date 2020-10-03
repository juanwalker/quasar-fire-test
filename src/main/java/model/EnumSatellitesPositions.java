package model;

public enum EnumSatellitesPositions {

    KENOBI("kenobi" ,new Double[] { -500.0,-200.0 }),
    SKYWALKER("skywalker", new Double[] { 100.0, -100.0 }),
    SATO("sato",new Double[] { 500.0, 100.0 });


    private String name;
    private Double[] positions;

    EnumSatellitesPositions(String name,Double[] positions){
        this.name = name;
        this.positions = positions;
    }

    public static EnumSatellitesPositions findCoords(String name){
        for( EnumSatellitesPositions value: values()){
            if(value.getName().equals(name)){
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double[] getPositions() {
        return positions;
    }

    public void setPositions(Double[] positions) {
        this.positions = positions;
    }
}
