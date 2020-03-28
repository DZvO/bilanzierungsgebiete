package org.dzvo.bilanzierungsgebiete.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Gebiet {
    @JsonProperty("regelzonen-eic")
    public String getRegelzonenEIC() {
        return regelzonenEIC;
    }

    public void setRegelzonenEIC(String regelzonenEIC) {
        this.regelzonenEIC = regelzonenEIC;
    }

    public String getStromnetzbetreiber() {
        return stromnetzbetreiber;
    }

    public void setStromnetzbetreiber(String stromnetzbetreiber) {
        this.stromnetzbetreiber = stromnetzbetreiber;
    }

    public String getIln() {
        return iln;
    }

    public void setIln(String iln) {
        this.iln = iln;
    }

    public String getBdew() {
        return bdew;
    }

    public void setBdew(String bdew) {
        this.bdew = bdew;
    }

    public String getStromnetzbetreibernr() {
        return stromnetzbetreibernr;
    }

    public void setStromnetzbetreibernr(String stromnetzbetreibernr) {
        this.stromnetzbetreibernr = stromnetzbetreibernr;
    }

    @JsonProperty("bilanzierungsgebiet-eic")
    public String getBilanzierungsgebietEIC() {
        return bilanzierungsgebietEIC;
    }

    public void setBilanzierungsgebietEIC(String bilanzierungsgebietEIC) {
        this.bilanzierungsgebietEIC = bilanzierungsgebietEIC;
    }

    @JsonProperty("bilanzierungsgebiet-vnb")
    public String getVnbBilanzierungsgebiet() {
        return vnbBilanzierungsgebiet;
    }

    public void setVnbBilanzierungsgebiet(String vnbBilanzierungsgebiet) {
        this.vnbBilanzierungsgebiet = vnbBilanzierungsgebiet;
    }

    public String getBeginn() {
        return beginn;
    }

    public void setBeginn(String beginn) {
        this.beginn = beginn;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public String getAenderungsdatum() {
        return aenderungsdatum;
    }

    public void setAenderungsdatum(String aenderungsdatum) {
        this.aenderungsdatum = aenderungsdatum;
    }

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CsvBindByName(column = "Regelzonen-EIC")
    private String regelzonenEIC;
    @CsvBindByName(column = "Stromnetzbetreiber")
    private String stromnetzbetreiber;
    @CsvBindByName(column = "ILN")
    private String iln;
    @CsvBindByName(column = "BDEW")
    private String bdew;
    @CsvBindByName(column = "Stromnetz-betreiber-Nr.")
    private String stromnetzbetreibernr;
    @CsvBindByName(column = "Bilanzierungsgebiet-EIC")
    private String bilanzierungsgebietEIC;
    @CsvBindByName(column = "VNB-Bilanzierungsgebiet")
    private String vnbBilanzierungsgebiet;
    @CsvBindByName(column = "Beginn")
    private String beginn;
    @CsvBindByName(column = "Ende")
    private String ende;
    @CsvBindByName(column = "Aenderungsdatum")
    private String aenderungsdatum;

    public Gebiet() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gebiet gebiet = (Gebiet) o;
        return Objects.equals(id, gebiet.id) &&
                Objects.equals(regelzonenEIC, gebiet.regelzonenEIC) &&
                Objects.equals(stromnetzbetreiber, gebiet.stromnetzbetreiber) &&
                Objects.equals(iln, gebiet.iln) &&
                Objects.equals(bdew, gebiet.bdew) &&
                Objects.equals(stromnetzbetreibernr, gebiet.stromnetzbetreibernr) &&
                Objects.equals(bilanzierungsgebietEIC, gebiet.bilanzierungsgebietEIC) &&
                Objects.equals(vnbBilanzierungsgebiet, gebiet.vnbBilanzierungsgebiet) &&
                Objects.equals(beginn, gebiet.beginn) &&
                Objects.equals(ende, gebiet.ende) &&
                Objects.equals(aenderungsdatum, gebiet.aenderungsdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regelzonenEIC, stromnetzbetreiber, iln, bdew, stromnetzbetreibernr, bilanzierungsgebietEIC, vnbBilanzierungsgebiet, beginn, ende, aenderungsdatum);
    }

    @Override
    public String toString() {
        return String.format(
                "Regelzonen-EIC: %s\n" +
                        "Stromnetzbetreiber: %s\n" +
                        "ILN: %s\n" +
                        "BDEW: %s\n" +
                        "Stromnetz-betreiber-Nr.: %s\n" +
                        "Bilanzierungsgebiet-EIC: %s\n" +
                        "VNB-Bilanzierungsgebiet: %s\n" +
                        "Beginn: %s\n" +
                        "Ende: %s\n" +
                        "Aenderungsdatum: %s\n",
                regelzonenEIC, stromnetzbetreiber, iln, bdew, stromnetzbetreibernr, bilanzierungsgebietEIC,
                vnbBilanzierungsgebiet, beginn, ende, aenderungsdatum);
    }
}