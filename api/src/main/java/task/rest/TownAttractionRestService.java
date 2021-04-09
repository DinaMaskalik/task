package task.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.entity.TownsAttractions;
import task.service.town.TownAttractionService;

@RestController
public class TownAttractionRestService {

    @Autowired
    TownAttractionService townAttractionService;

    @PostMapping(value = "/towns", consumes = "application/json")
    public ResponseEntity createTown(
            @RequestBody TownsAttractions townsAttractions
    ) {
        townAttractionService.createTown(townsAttractions);
        return new ResponseEntity(townsAttractions, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/towns/{name}")
    public ResponseEntity deleteTown(
            @PathVariable(value = "name") String name
    ){
        townAttractionService.deleteTown(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PutMapping(value = "/towns/{name}", consumes = "application/json")
    public ResponseEntity updateTown(
            @PathVariable(value = "name") String name,
            @RequestBody TownsAttractions townsAttractions
    ) {

        final TownsAttractions town = townAttractionService.findTown(name);

        if(town!=null) {
            town.setTown(townsAttractions.getTown());
            town.setAttractions(townsAttractions.getAttractions());
            townAttractionService.updateTown(town);

            return new ResponseEntity(townsAttractions, HttpStatus.CREATED);
        }

        return new ResponseEntity(townsAttractions, HttpStatus.NOT_FOUND);
    }
}
