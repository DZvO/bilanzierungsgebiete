package org.dzvo.bilanzierungsgebiete.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GebietController {
    private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    final GebietRepository repo;

    public GebietController(GebietRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/gebiet")
    public List<Gebiet> gebietListing() {
        System.out.println(repo.findAll());
        return repo.findAll();
    }

    @RequestMapping(value = "/gebiet/{id}", method = RequestMethod.GET)
    public Gebiet gebietGetById(@PathVariable(value = "id") String id) {
        return repo.findById(Long.valueOf(id)).orElseThrow();
    }
}