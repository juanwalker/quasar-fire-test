package com.mercadolibre.quasarfireoperationjg;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.UtilService;

@SpringBootApplication
@RequestMapping(value = "/quasar-fire")
@ComponentScan(basePackages = {"service"})
@RestController
public class QuasarFireOperationJgApplication {

	private static final Logger log = LoggerFactory.getLogger(QuasarFireOperationJgApplication.class);

	private Satellite kenobi;
	private Satellite skyWalker;
	private Satellite sato;

	@Autowired
	private UtilService utilService;


	public static void main(String[] args) {
		SpringApplication.run(QuasarFireOperationJgApplication.class, args);

	}


	@RequestMapping(method = RequestMethod.POST, value = "/topsecret")
	@ResponseStatus(value = HttpStatus.OK)
	public TopSecretResponse topSecretSatellites(@RequestBody TopSecretRequest request) {
		log.debug("topSecretSatellites start params (" + request + ")");

		this.kenobi = request.searchSatellite(EnumSatellitesPositions.KENOBI.getName());
		this.skyWalker = request.searchSatellite(EnumSatellitesPositions.SKYWALKER.getName());
		this.sato = request.searchSatellite(EnumSatellitesPositions.SATO.getName());

		//Double[] location = this.getLocation(request.getSatellites()[0].getDistance(), request.getSatellites()[1].getDistance(), request.getSatellites()[2].getDistance());
		Double[] location = this.getLocation();
		//String message = this.getMessage(request.getSatellites()[0].getMessage(), request.getSatellites()[1].getMessage(),  request.getSatellites()[2].getMessage());
		String message = this.getMessage();

		log.debug("topSecretSatellites end");
		Position position = new Position(location);
		return new TopSecretResponse(position,message);
	}


	private Double[] getLocation()  {
		log.debug("getLocation start");
		Double[] coordinates;
			coordinates = this.utilService.trilateration(this.kenobi.getCoordX(), this.kenobi.getCoordY(), this.kenobi.getDistance(),
					this.skyWalker.getCoordX(), this.skyWalker.getCoordY(), this.skyWalker.getDistance(),
					this.sato.getCoordX(), this.sato.getCoordY(), this.sato.getDistance());


		log.debug("getLocation ends");
		return   coordinates;
	}

	private String getMessage() {
		log.debug("getMessage start");
		String str =this.utilService.mergeMessages(this.kenobi.getMessage(),this.skyWalker.getMessage(),this.sato.getMessage());
		log.debug("getMessage ends");
		return   str;
	}


	/**
	 *  Not a good POO signature as longer that distances and positinos and needed for this calculation as parameters.
	 *  Method not used reaplaced by getLocation() method using instance variables.
	 */

	private Double[] getLocation(Double distance1, Double distance2, Double distance3){
		log.debug("getLocation start params (" + distance1 + distance2 + distance3 + ")");

		Satellite[] satellites = this.instanceSatellitesUsingDistancesSignature(distance1, distance2, distance3);
		Double[] coordinates = this.utilService.calculateThreeCircleIntersection(satellites[0].getCoordX(),satellites[0].getCoordY(),satellites[0].getDistance(),
				satellites[1].getCoordX(),satellites[1].getCoordY(),satellites[1].getDistance(),
				satellites[2].getCoordX(),satellites[2].getCoordY(),satellites[2].getDistance());

		log.debug("getLocation end output coordinates" + coordinates[0]+ coordinates[1] );
		return  coordinates;
	}

	private Satellite[] instanceSatellitesUsingDistancesSignature(Double distance1,Double distance2,Double distance3){
		Satellite[] satellites = new Satellite[3];
		satellites[0] =new Satellite(EnumSatellitesPositions.KENOBI.getName(), distance1, null);
		satellites[1] =new Satellite(EnumSatellitesPositions.SKYWALKER.getName(), distance2, null);
		satellites[2] =new Satellite(EnumSatellitesPositions.SATO.getName(), distance3, null);
		return satellites;
	}

	/**
	 *  Reaplaced by getMessage() method using instance variables.
	 */


	private String getMessage(String[] inputMessage1,String[] inputMessage2,String[] inputMessage3){
		log.debug("getMessage start params (" + inputMessage1 + inputMessage2 + inputMessage3 + ")");
		String str =this.utilService.mergeMessages(inputMessage1,inputMessage2,inputMessage3);
		log.debug("getMessage ends return (" + str + ")");
		return str;
	}



	@RequestMapping(method = RequestMethod.POST, value = "/topsecret_split")
	@ResponseStatus(value = HttpStatus.CREATED)
	public TopSecretSplitResponse topSecretSatelliteKenobi( @RequestParam String satelliteName, @RequestBody TopSecretSplitRequest request ) {
		log.debug("topSecretSatellite start params (" + request + ")");
		switch(satelliteName) {
			case "kenobi":
				this.kenobi = new Satellite(satelliteName,request.getDistance(),request.getMessage());
				break;
			case "skywalker":
				this.skyWalker = new Satellite(satelliteName,request.getDistance(),request.getMessage());
			break;

			case "sato":
				this.sato = new Satellite(satelliteName,request.getDistance(),request.getMessage());
			break;

		}

		if (this.kenobi != null && this.skyWalker != null && this.sato != null) {
			Double[] location = this.getLocation();
			String message = this.getMessage();
			log.debug("topSecretSatellite end");
			Position position = new Position(location);
			return new TopSecretSplitResponse(position,message);
		}
		return null;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/topsecret_split")
	@ResponseStatus(value = HttpStatus.OK)
	public TopSecretSplitResponse topSecretSatellite(@RequestParam("satelliteName") String satelliteName ) {
		log.debug("topSecretSatellite start params (" + satelliteName + ")");

		if (this.kenobi != null && this.skyWalker != null && this.sato != null) {
			Double[] location = this.getLocation();
			String message = this.getMessage();
			log.debug("topSecretSatellite end");
			Position position = new Position(location);
			log.debug("topSecretSatellite end");
			return new TopSecretSplitResponse(position,message);
		}
		log.debug("topSecretSatellite end");
		return null;
	}





}
