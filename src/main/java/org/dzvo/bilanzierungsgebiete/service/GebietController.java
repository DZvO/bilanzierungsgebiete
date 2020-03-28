package org.dzvo.bilanzierungsgebiete.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//FIXME improve REST level to 3
@RestController
public class GebietController {
    private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);
    final GebietRepository repo;

    public GebietController(GebietRepository repo) {
        this.repo = repo;
    }

    //SEARCH
    //FIXME add unit test
    //FIXME add more parameters

    /**
     * Is used to search for a certain "Bilanzierungsgebiet" by supplying the corresponding parameters.
     * If none are passed will return all "Bilanzierungsgebiet" currently in database.
     *
     * @param stromnetzbetreiber
     * @param bilanzierungsgebietEIC
     * @return Results of search
     */
    @RequestMapping(value = "/gebiet", method = RequestMethod.GET)
    public List<Gebiet> gebietListing(
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC
    ) {
        //FIXME improve constructor to make this more readable
        Gebiet q = new Gebiet();
        q.setStromnetzbetreiber(stromnetzbetreiber);
        q.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);

        Example<Gebiet> example = Example.of(q);
        return repo.findAll(example);
    }

    //CREATE
    //FIXME add unit test
    //FIXME add more parameters

    /**
     * Inserts a new entry, parameters not supplied will be set to null. Prevents duplicates from being entered.
     *
     * @param stromnetzbetreiber
     * @param bilanzierungsgebietEIC
     * @return The newly created entry or HTTP 409 if entry already exists
     */
    @RequestMapping(value = "/gebiet", method = RequestMethod.PUT)
    public ResponseEntity gebietInsert(
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC
    ) {
        //FIXME improve constructor to make this more readable
        Gebiet q = new Gebiet();
        q.setStromnetzbetreiber(stromnetzbetreiber);
        q.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);

        Example<Gebiet> example = Example.of(q);
        if (repo.findAll(example).size() != 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entry already exists");
        }

        repo.save(q);
        return ResponseEntity.ok(q);
    }

    //UPDATE
    //FIXME add unit test
    //FIXME add more parameters

    /**
     * Updates records values. Not supplied parameters are left as-is.
     *
     * @param id                     The id that identifies the record to update
     * @param stromnetzbetreiber
     * @param bilanzierungsgebietEIC
     * @return HTTP 200 and the new record itself if record to be updated was found, HTTP 404 if not
     */
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.POST)
    public ResponseEntity gebietUpdateId(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC
    ) {
        if (repo.existsById(Long.valueOf(id))) {
            Gebiet e = repo.findById(Long.valueOf(id)).get();
            if (stromnetzbetreiber != null) e.setStromnetzbetreiber(stromnetzbetreiber);
            if (bilanzierungsgebietEIC != null) e.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);

            repo.save(e);
            return ResponseEntity.ok(e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    //DELETE
    //FIXME add unit test

    /**
     * Delete a record by id.
     *
     * @param id
     * @return HTTP 200 if record was successfully deleted, 404 if not found.
     */
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.DELETE)
    public ResponseEntity gebietDeleteId(@PathVariable(value = "id") int id) {
        if (repo.existsById((long) id)) {
            repo.deleteById((long) id);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    //READ
    //FIXME add unit test

    /**
     * Return a record by Id
     *
     * @param id
     * @return The specified record or HTTP 404 if not found
     */
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.GET)
    public ResponseEntity gebietGetById(@PathVariable(value = "id") int id) {
        if (repo.existsById((long) id)) {
            return ResponseEntity.ok(repo.findById(Long.valueOf(id)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
}