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
        g1.setRegelzonenEIC("RZEIC");
        Gebiet g2 = new Gebiet();
        g2.setStromnetzbetreiber("SNB");

        controller.repo.save(g1);
        controller.repo.save(g2);

        List<Gebiet> list = new ArrayList<Gebiet>();
        list.add(g1);
        assertThat(list).isEqualTo(controller.gebietListing("RZEIC", null, null, null, null, null, null, null, null, null));

        list = new ArrayList<Gebiet>();
        list.add(g2);
        assertThat(list).isEqualTo(controller.gebietListing(null, "SNB", null, null, null, null, null, null, null, null));

        list = new ArrayList<Gebiet>();
        list.add(g1);
        list.add(g2);
        assertThat(list).isEqualTo(controller.gebietListing(null, null, null, null, null, null, null, null, null, null));
    }

    @Test
    public void gebietInsert() {
        Gebiet g1 = new Gebiet();
        g1.setStromnetzbetreiber("SNB");
        g1.setRegelzonenEIC("RZEIC");
        List<Gebiet> list = new ArrayList<Gebiet>();
        list.add(g1);

        var k = controller.gebietInsert("RZEIC", "SNB", null, null, null, null, null, null, null, null);
        assertThat(k.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        k = controller.gebietInsert("RZEIC", "SNB", null, null, null, null, null, null, null, null);
        assertThat(k.getStatusCode()).isEqualByComparingTo(HttpStatus.CONFLICT);

        var result = controller.gebietListing(null, null, null, null, null, null, null, null, null, null);
        assertThat(list).isEqualTo(result);
    }

    @Test
    public void gebietUpdateId() {
        var n = controller.gebietUpdateId(1, null, null, null, null, null, null, null, null, null, null);
        assertThat(n.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

        var k = controller.gebietInsert("RZEIC", "SNB", null, null, null, null, null, null, null, null);
        Gebiet g = (Gebiet) k.getBody();

        controller.gebietUpdateId(g.getId(), "123", "456", null, null, null, null, null, null, null, null);
        var changed = (Gebiet) controller.gebietGetById(g.getId()).getBody();
        assertThat(changed.getRegelzonenEIC()).isEqualTo("123");
    }

    @Test
    public void gebietDeleteId() {
        var non_existing = controller.gebietDeleteId(1);
        assertThat(non_existing.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

        var g = controller.gebietInsert("RZEIC", "SNB", null, null, null, null, null, null, null, null);
        var k = controller.gebietDeleteId(((Gebiet) g.getBody()).getId());
        assertThat(k.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        var search_deleted = controller.gebietGetById(((Gebiet) g.getBody()).getId());
        assertThat(search_deleted.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void gebietGetById() {
        var r = controller.gebietInsert("RZEIC", "SNB", null, null, null, null, null, null, null, null);
        var new_id = ((Gebiet) r.getBody()).getId();

        var resp = controller.gebietGetById(new_id);
        assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat((Gebiet) resp.getBody()).isEqualTo((Gebiet) r.getBody());
    }
}