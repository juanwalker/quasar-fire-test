package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Satellite {



    private String name;
    private EnumSatellitesPositions coords;
    private Double distance;
    private String message[];

    public Satellite()
    {
        super();
    }

    public Satellite(String name,Double distance,String[] message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
        this.coords = EnumSatellitesPositions.findCoords(name);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.coords = EnumSatellitesPositions.findCoords(name);
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public Double getCoordX(){
        return this.coords.getPositions()[0];
    }

    public Double getCoordY(){
        return this.coords.getPositions()[1];
    }
}
