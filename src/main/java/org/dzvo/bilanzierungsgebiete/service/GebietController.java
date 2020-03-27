package org.dzvo.bilanzierungsgebiete.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GebietController {
    private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);
    final GebietRepository repo;

    public GebietController(GebietRepository repo) {
        this.repo = repo;
    }


    @RequestMapping("/gebiet")
    public List<Gebiet> gebietListing(
            @RequestParam(value = "stromnetzbetreiber", required = false) String stromnetzbetreiber,
            @RequestParam(value = "bilanzierungsgebiet-eic", required = false) String bilanzierungsgebietEIC
    ) {
        Gebiet q = new Gebiet();
        q.setStromnetzbetreiber(stromnetzbetreiber);
        q.setBilanzierungsgebietEIC(bilanzierungsgebietEIC);

        Example<Gebiet> example = Example.of(q);
        return repo.findAll(example);
    }

    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.GET)
    public Gebiet gebietGetById(@PathVariable(value = "id") String id) {
        return repo.findById(Long.valueOf(id)).orElseThrow();
    }
}