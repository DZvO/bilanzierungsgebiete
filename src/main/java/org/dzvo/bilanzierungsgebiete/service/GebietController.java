package org.dzvo.bilanzierungsgebiete.service;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//FIXME improve REST level to 3
@RestController
public class GebietController {
    final GebietRepository repo;
    public GebietController(GebietRepository repo) {
        this.repo = repo;
    }

    /**
     * SEARCH
     * Is used to search for a certain "Bilanzierungsgebiet" by supplying the corresponding parameters.
     * If none are passed will return all "Bilanzierungsgebiet" currently in database.
     *
     * @return Results of search
     */
    @RequestMapping(value = "/gebiet", method = RequestMethod.GET)
    public List<Gebiet> gebietListing(
            @RequestParam(value = "regelzonen-eic", required = false) String regelzonenEIC,
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "iln", required = false) String iln,
            @RequestParam(value = "bdew", required = false) String bdew,
            @RequestParam(value = "stromnetzbetreibernr", required = false) String stromnetzbetreibernr,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC,
            @RequestParam(value = "bilanzierungsgebiet-vnb", required = false) String vnbBilanzierungsgebiet,
            @RequestParam(value = "beginn", required = false) String beginn,
            @RequestParam(value = "ende", required = false) String ende,
            @RequestParam(value = "aenderungsdatum", required = false) String aenderungsdatum
    ) {
        Gebiet q = new Gebiet(regelzonenEIC,
                stromnetzbetreiber,
                iln,
                bdew,
                stromnetzbetreibernr,
                bilanzierungsgebietEIC,
                vnbBilanzierungsgebiet,
                beginn,
                ende,
                aenderungsdatum);

        Example<Gebiet> example = Example.of(q);
        return repo.findAll(example);
    }

    /**
     * CREATE
     * Inserts a new entry, parameters not supplied will be set to null. Prevents duplicates from being entered.
     *
     * @return The newly created entry or HTTP 409 if entry already exists
     */
    @RequestMapping(value = "/gebiet", method = RequestMethod.PUT)
    public ResponseEntity gebietInsert(
            @RequestParam(value = "regelzonen-eic", required = false) String regelzonenEIC,
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "iln", required = false) String iln,
            @RequestParam(value = "bdew", required = false) String bdew,
            @RequestParam(value = "stromnetzbetreibernr", required = false) String stromnetzbetreibernr,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC,
            @RequestParam(value = "bilanzierungsgebiet-vnb", required = false) String vnbBilanzierungsgebiet,
            @RequestParam(value = "beginn", required = false) String beginn,
            @RequestParam(value = "ende", required = false) String ende,
            @RequestParam(value = "aenderungsdatum", required = false) String aenderungsdatum
    ) {
        Gebiet q = new Gebiet(regelzonenEIC,
                stromnetzbetreiber,
                iln,
                bdew,
                stromnetzbetreibernr,
                bilanzierungsgebietEIC,
                vnbBilanzierungsgebiet,
                beginn,
                ende,
                aenderungsdatum);

        Example<Gebiet> example = Example.of(q);
        if (repo.findAll(example).size() != 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entry already exists");
        }

        repo.save(q);
        return ResponseEntity.ok(q);
    }

    /**
     * UPDATE
     * Updates records values. Not supplied parameters are left as-is.
     *
     * @param id The id that identifies the record to update
     * @return HTTP 200 and the new record itself if record to be updated was found, HTTP 404 if not
     */
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.POST)
    public ResponseEntity gebietUpdateId(
            @PathVariable(value = "id") long id,
            @RequestParam(value = "regelzonen-eic", required = false) String regelzonenEIC,
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "iln", required = false) String iln,
            @RequestParam(value = "bdew", required = false) String bdew,
            @RequestParam(value = "stromnetzbetreibernr", required = false) String stromnetzbetreibernr,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC,
            @RequestParam(value = "bilanzierungsgebiet-vnb", required = false) String vnbBilanzierungsgebiet,
            @RequestParam(value = "beginn", required = false) String beginn,
            @RequestParam(value = "ende", required = false) String ende,
            @RequestParam(value = "aenderungsdatum", required = false) String aenderungsdatum
    ) {
        if (repo.existsById(id)) {
            Gebiet e = repo.findById(id);
            if (regelzonenEIC != null) e.setRegelzonenEIC(regelzonenEIC);
            if (stromnetzbetreiber != null) e.setStromnetzbetreiber(stromnetzbetreiber);
            if (iln != null) e.setIln(iln);
            if (bdew != null) e.setBdew(bdew);
            if (stromnetzbetreibernr != null) e.setStromnetzbetreibernr(stromnetzbetreibernr);
            if (bilanzierungsgebietEIC != null) e.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);
            if (vnbBilanzierungsgebiet != null) e.setVnbBilanzierungsgebiet(vnbBilanzierungsgebiet);
            if (beginn != null) e.setBeginn(beginn);
            if (ende != null) e.setEnde(ende);
            if (aenderungsdatum != null) e.setAenderungsdatum(aenderungsdatum);

            repo.save(e);
            return ResponseEntity.ok(e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    /**
     * DELETE
     * Delete a record by id.
     *
     * @param id ID of record to delete
     * @return HTTP 200 if record was successfully deleted, 404 if not found.
     */
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.DELETE)
    public ResponseEntity gebietDeleteId(@PathVariable(value = "id") long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    /**
     * READ
     * Return a record by Id
     *
     * @param id ID of record to return
     * @return The specified record or HTTP 404 if not found
     */
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.GET)
    public ResponseEntity gebietGetById(@PathVariable(value = "id") long id) {
        if (repo.existsById(id)) {
            return ResponseEntity.ok(repo.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
}