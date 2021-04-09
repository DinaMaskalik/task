package task.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.entity.TownsAttractions;
import task.service.town.TownAttractionService;
import task.utils.ToolsForWord;

@RestController
public class TownAttractionRestService {

    @Autowired
    TownAttractionService townAttractionService;

    @PostMapping(value = "/towns", consumes = "application/json")
    public ResponseEntity createTown(
            @RequestBody TownsAttractions townsAttractions
    ) {
        ToolsForWord toolsForWord = new ToolsForWord();
        townsAttractions.setTown(
                toolsForWord.getWordWithACapitalLetter(
                        townsAttractions.getTown()
                )
        );
        townAttractionService.createTown(townsAttractions);
        return new ResponseEntity(townsAttractions, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/towns/{name}")
    public ResponseEntity deleteTown(
            @PathVariable(value = "name") String name
    ) {
        ToolsForWord toolsForWord = new ToolsForWord();
        name = toolsForWord.getWordWithACapitalLetter(name);
        townAttractionService.deleteTown(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PutMapping(value = "/towns/{name}")
    public ResponseEntity<TownsAttractions> updateTown(
            @PathVariable(value = "name") String name,
            @RequestBody TownsAttractions townsAttractions
    ) {

        ToolsForWord toolsForWord = new ToolsForWord();
        name = toolsForWord.getWordWithACapitalLetter(name);

        final TownsAttractions town = townAttractionService.findTown(name);

        if (town != null) {
            town.setTown(name);

            town.setAttractions(townsAttractions.getAttractions());

            townAttractionService.updateTown(town);

            return new ResponseEntity<>(townsAttractions, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(townsAttractions, HttpStatus.NOT_FOUND);
    }
}
