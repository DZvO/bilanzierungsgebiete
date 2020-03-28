package org.dzvo.bilanzierungsgebiete.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GebietControllerTest {
    @Autowired
    private GebietController controller;

    @BeforeTestExecution
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @BeforeEach
    public void clearRepo() {
        controller.repo.deleteAll();
    }

    @Test
    public void gebietListing() {
        //FIXME improve syntax by using better constructor
        Gebiet g1 = new Gebiet();
        g1.setBilanzierungsgebietEIC("BilEIC1");
        Gebiet g2 = new Gebiet();
        g2.setStromnetzbetreiber("SNB");

        controller.repo.save(g1);
        controller.repo.save(g2);

        List<Gebiet> list = new ArrayList<Gebiet>();
        list.add(g1);
        assertThat(list).isEqualTo(controller.gebietListing(null, "BilEIC1"));

        list = new ArrayList<Gebiet>();
        list.add(g2);
        assertThat(list).isEqualTo(controller.gebietListing("SNB", null));

        list = new ArrayList<Gebiet>();
        list.add(g1);
        list.add(g2);
        assertThat(list).isEqualTo(controller.gebietListing(null, null));
    }

    @Test
    public void gebietInsert() {
        Gebiet g1 = new Gebiet();
        g1.setStromnetzbetreiber("SNB");
        g1.setBilanzierungsgebietEIC("BEIC");
        List<Gebiet> list = new ArrayList<Gebiet>();
        list.add(g1);

        var k = controller.gebietInsert("SNB", "BEIC");
        assertThat(k.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        k = controller.gebietInsert("SNB", "BEIC");
        assertThat(k.getStatusCode()).isEqualByComparingTo(HttpStatus.CONFLICT);

        var result = controller.gebietListing(null, null);
        assertThat(list).isEqualTo(result);
    }

    @Test
    public void gebietUpdateId() {
        var n = controller.gebietUpdateId(1, null, null);
        assertThat(n.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

        var k = controller.gebietInsert("SNB", "BEIC");
        var d = controller.gebietInsert("BNS", "ASDF");
        Gebiet g = (Gebiet) k.getBody();

        var r = controller.gebietUpdateId(g.getId(), "BNS", "ASDF");
        assertThat(controller.gebietGetById(g.getId())).isEqualTo(d);
    }

    @Test
    public void gebietDeleteId() {
        var non_existing = controller.gebietDeleteId(1);
        assertThat(non_existing.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

        var g = controller.gebietInsert("SNB", "BEIC");
        var k = controller.gebietDeleteId(((Gebiet) g.getBody()).getId());
        assertThat(k.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        var search_deleted = controller.gebietGetById(((Gebiet) g.getBody()).getId());
        assertThat(search_deleted.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void gebietGetById() {
        var r = controller.gebietInsert("SNB", "BEIC");
        var new_id = ((Gebiet) r.getBody()).getId();

        var resp = controller.gebietGetById(new_id);
        assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat((Gebiet) resp.getBody()).isEqualTo((Gebiet) r.getBody());
    }
}