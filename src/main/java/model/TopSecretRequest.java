package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopSecretRequest {

    private Satellite satellites[];

    public Satellite[] getSatellites() {
        return satellites;
    }

    public void setSatellites(Satellite[] satellites) {
        this.satellites = satellites;
    }

    public Satellite searchSatellite(String name){
        for(Satellite satellite: this.getSatellites()){
            if (satellite.getName().equals(name)){
                return satellite;
            }
        }
        return null;
    }

}
