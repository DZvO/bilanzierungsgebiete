package org.dzvo.bilanzierungsgebiete.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//FIXME Add javadoc/comments
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
    @RequestMapping(value = "/gebiet", method = RequestMethod.PUT)
    public Boolean gebietInsert(
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC
    ) {
        //FIXME improve constructor to make this more readable
        Gebiet q = new Gebiet();
        q.setStromnetzbetreiber(stromnetzbetreiber);
        q.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);

        repo.save(q);

        //FIXME check for duplicates
        return true;
    }

    //UPDATE
    //FIXME add unit test
    //FIXME add more parameters
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.POST)
    public Boolean gebietUpdateId(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC
    ) {
        if (repo.existsById(Long.valueOf(id))) {
            Gebiet e = repo.findById(Long.valueOf(id)).get();
            if (stromnetzbetreiber != null) e.setStromnetzbetreiber(stromnetzbetreiber);
            if (bilanzierungsgebietEIC != null) e.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);

            repo.save(e);
            return true;
        }
        return false;
    }

    //DELETE
    //FIXME add unit test
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.DELETE)
    public Boolean gebietDeleteId(@PathVariable(value = "id") String id) {
        if (repo.existsById(Long.valueOf(id))) {
            repo.deleteById(Long.valueOf(id));
            return true;
        }
        return false;
    }

    //READ
    //FIXME add unit test
    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.GET)
    public Gebiet gebietGetById(@PathVariable(value = "id") String id) {
        return repo.findById(Long.valueOf(id)).orElseThrow();
    }
}