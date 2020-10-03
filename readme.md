# Quasar fire

This application has been build using spring boot, the application publishs two rest services which it's purpose is merge the message from the transmitter and find its location in the space using the satellites available to complete this objective.The program defines three satellites that have a fixed position in the space  to have the message and the position of the transmitter

Currently the project only retrieves the messages from the transmitter, the triangulation algorithm is not working yet as expected.The project can be runned in you local machine executing the following command under the folder were you checkout the source code.
```
 mvnw spring-boot:run 
```

The web services available that can be tested are:

POST localhost:8080/quasar-fire/topsecret/
In this web service you will be able to update all the information from the satellites 
```
{
   "satellites":[
      {
         "name":"kenobi",
         "distance":500,
         "message":[
            "este",
            "",
            "",
            "mensaje",
            ""
         ]
      },
      {
         "name":"skywalker",
         "distance":100,
         "message":[
            "",
            "es",
            "",
            "",
            "secreto"
         ]
      },
      {
         "name":"sato",
         "distance":600,
         "message":[
            "este",
            "",
            "un",
            "",
            ""
         ]
      }
   ]
}
```

GET localhost:8080/quasar-fire/topsecret_split?satelliteName=XXXX
In this web service you can fetch information regarding one of the available satellites (kenobi,skywalker,sato)

POST localhost:8080/quasar-fire/topsecret_split?satelliteName=XXXX
In this web service you will able to update the information regarding a particular webservice, for example the following JSON request can be used to update the satellite information
```
{
"distance": 100.0,
"message": ["este", "", "", "mensaje", ""]
}
```





