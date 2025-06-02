package com.ffxiv_fansite.fansite.service;

import com.ffxiv_fansite.fansite.model.Crafter;
import com.ffxiv_fansite.fansite.model.Expansion;
import com.ffxiv_fansite.fansite.model.Gatherer;
import com.ffxiv_fansite.fansite.model.Job;
import com.ffxiv_fansite.fansite.model.MajorCity;
import com.ffxiv_fansite.fansite.model.MinorCity;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
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
        List<String[]> csvLines = readCsv("jobs.csv");

        csvLines.remove(0);

        return csvLines.stream()
                .map(Job::mapCsvLineToJob)
                .toList();
    }

    public List<Crafter> getCrafters() {
        List<String[]> csvLines = readCsv("crafters.csv");

        csvLines.remove(0);

        return csvLines.stream()
                .map(Crafter::mapCsvLineToCrafter)
                .toList();
    }

    public List<Gatherer> getGatherers() {
        List<String[]> csvLines = readCsv("gatherers.csv");

        csvLines.remove(0);

        return csvLines.stream()
                .map(Gatherer::mapCsvLineToGatherer)
                .toList();
    }

    private List<String[]> readCsv(final String filename) {
        try (Reader reader = new BufferedReader(Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI())));
             CSVReader csvReader = new CSVReader(reader);) {
            return csvReader.readAll();
        } catch (IOException | CsvException | URISyntaxException e) {
            return new ArrayList<>();
        }
    }
}
