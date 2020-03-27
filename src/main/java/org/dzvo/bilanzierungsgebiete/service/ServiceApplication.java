package org.dzvo.bilanzierungsgebiete.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner importDataFromCSV(GebietRepository repo) {
        return (args) -> {
            CSVReader reader = null;
            try {
                List<Gebiet> beans = new CsvToBeanBuilder(new FileReader(new ClassPathResource("data/Bilanzierungsgebiete.csv").getPath()))
                        .withSeparator(';')
                        .withType(Gebiet.class)
                        .build().parse();

                for (Gebiet g : beans) {
                    repo.save(g);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}