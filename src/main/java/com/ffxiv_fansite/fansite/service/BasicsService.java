package com.ffxiv_fansite.fansite.service;

import com.ffxiv_fansite.fansite.model.AccessoryType;
import com.ffxiv_fansite.fansite.model.ArmorType;
import com.ffxiv_fansite.fansite.model.Expansion;
import com.ffxiv_fansite.fansite.model.Job;
import com.ffxiv_fansite.fansite.model.MajorCity;
import com.ffxiv_fansite.fansite.model.MinorCity;
import com.ffxiv_fansite.fansite.model.Role;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class BasicsService {
    public List<Expansion> getExpansions() {
        return Arrays.stream(Expansion.values()).toList();
    }

    public List<MajorCity> getMajorCities() {
        return Arrays.stream(MajorCity.values()).toList();
    }

    public List<MinorCity> getMinorCities() {
        return Arrays.stream(MinorCity.values()).toList();
    }

    public List<Job> getJobs() {
        return mapCsvToJobs(readCsv());
    }

    private List<String[]> readCsv() {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("jobs.csv").toURI());
            Reader reader = new BufferedReader(Files.newBufferedReader(path));

            CSVReader csvReader = new CSVReader(reader);
            return csvReader.readAll();
        } catch (IOException | CsvException | URISyntaxException e) {
            return new ArrayList<>();
        }
    }

    private List<Job> mapCsvToJobs(final List<String[]> csvLines) {
        csvLines.remove(0);

        return csvLines.stream()
                .map(this::mapOneCsvLineToJob)
                .toList();
    }

    private Job mapOneCsvLineToJob(final String[] csvLine) {
        return new Job(
                csvLine[0],
                csvLine[1],
                csvLine[2],
                Role.getRoleByName(csvLine[3]),
                MajorCity.getMajorCityByName(csvLine[4]),
                Integer.parseInt(csvLine[5]),
                ArmorType.getArmorTypeByName(csvLine[6]),
                AccessoryType.getAccessoryTypeByName(csvLine[7]),
                Expansion.getExpansionByName(csvLine[8]),
                csvLine[9],
                csvLine[10]
        );
    }
}
