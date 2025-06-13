package com.ffxiv_fansite.fansite.service;

import com.ffxiv_fansite.fansite.configuration.ExecutionTimeLogger;
import com.ffxiv_fansite.fansite.model.Aetheryte;
import com.ffxiv_fansite.fansite.model.Crafter;
import com.ffxiv_fansite.fansite.model.Expansion;
import com.ffxiv_fansite.fansite.model.Gatherer;
import com.ffxiv_fansite.fansite.model.Job;
import com.ffxiv_fansite.fansite.model.MajorCity;
import com.ffxiv_fansite.fansite.model.MinorCity;
import com.ffxiv_fansite.fansite.model.Region;
import com.ffxiv_fansite.fansite.model.Zone;
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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BasicsService {
    @ExecutionTimeLogger
    public List<Expansion> getExpansions() {
        return Arrays.stream(Expansion.values()).toList();
    }

    @ExecutionTimeLogger
    public List<MajorCity> getMajorCities() {
        return Arrays.stream(MajorCity.values()).toList();
    }

    @ExecutionTimeLogger
    public List<MinorCity> getMinorCities() {
        return Arrays.stream(MinorCity.values()).toList();
    }

    @ExecutionTimeLogger
    public List<Job> getJobs() {
        List<String[]> csvLines = readCsv("jobs.csv");

        csvLines.remove(0);

        return csvLines.stream()
                .map(Job::mapCsvLineToJob)
                .toList();
    }

    @ExecutionTimeLogger
    public List<Crafter> getCrafters() {
        List<String[]> csvLines = readCsv("crafters.csv");

        csvLines.remove(0);

        return csvLines.stream()
                .map(Crafter::mapCsvLineToCrafter)
                .toList();
    }

    @ExecutionTimeLogger
    public List<Gatherer> getGatherers() {
        List<String[]> csvLines = readCsv("gatherers.csv");

        csvLines.remove(0);

        return csvLines.stream()
                .map(Gatherer::mapCsvLineToGatherer)
                .toList();
    }

    @ExecutionTimeLogger
    public List<Region> getRegions() {
        return Arrays.stream(Region.values()).toList();
    }

    @ExecutionTimeLogger
    public List<Zone> getZones() {
        return Arrays.stream(Zone.values()).toList();
    }

    @ExecutionTimeLogger
    public Map<Region, List<Aetheryte>> getAetherytesByRegion() {
        EnumMap<Region, List<Aetheryte>> map = new EnumMap<>(Region.class);

        Arrays.stream(Region.values()).toList()
                .forEach(value -> map.put(value, new ArrayList<>()));

        return filterEmptyListInMap(fillRegionMapWithAetherytes(map));
    }

    private List<String[]> readCsv(final String filename) {
        try (Reader reader = new BufferedReader(Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI())));
             CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        } catch (IOException | CsvException | URISyntaxException e) {
            return new ArrayList<>();
        }
    }

    private Map<Region, List<Aetheryte>> fillRegionMapWithAetherytes(final Map<Region, List<Aetheryte>> map) {
        List<Aetheryte> aetherytes = Arrays.stream(Aetheryte.values()).toList();

        aetherytes.forEach(aetheryte -> fillAetheryteIntoRightRegion(map, aetheryte));

        return map;
    }

    private void fillAetheryteIntoRightRegion(final Map<Region, List<Aetheryte>> map, final Aetheryte aetheryte) {
        Region region = checkTheRegion(aetheryte);

        for (Map.Entry<Region, List<Aetheryte>> entry : map.entrySet()) {
            if(entry.getKey().equals(region)) {
                entry.getValue().add(aetheryte);
            }
        }
    }

    private Region checkTheRegion(final Aetheryte aetheryte) {
        return aetheryte.getZone().getRegion();
    }

    private Map<Region, List<Aetheryte>> filterEmptyListInMap(Map<Region, List<Aetheryte>> regionListMap) {
        for (Map.Entry<Region, List<Aetheryte>> entry : regionListMap.entrySet()) {
            if(entry.getValue().isEmpty()) {
                regionListMap.remove(entry.getKey());
            }
        }

        return regionListMap;
    }
}
