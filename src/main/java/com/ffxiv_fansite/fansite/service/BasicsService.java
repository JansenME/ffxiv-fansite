package com.ffxiv_fansite.fansite.service;

import com.ffxiv_fansite.fansite.model.Expansion;
import com.ffxiv_fansite.fansite.model.MajorCity;
import com.ffxiv_fansite.fansite.model.MinorCity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
}
