package com.tutuka.project.Tutuka.service;

import com.tutuka.project.Tutuka.config.StorageProperties;
import com.tutuka.project.Tutuka.util.ParseAndCompare;
import com.tutuka.project.Tutuka.util.ParseTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MatchingServiceImpl implements MatchingService {
    private final Path rootLocation;

    @Autowired
    public MatchingServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public ParseAndCompare match(String nameOne, String nameTwo) {

        Path nameOneLocation = rootLocation.resolve(nameOne);
        Path nameTwoLocation = rootLocation.resolve(nameTwo);

        ParseAndCompare compareClass = new ParseAndCompare(nameOneLocation.toString(),nameTwoLocation.toString());

        compareClass.parseFirstCSV(new ParseTransaction());
        compareClass.parseSecondCSVAndMatch(new ParseTransaction());

        compareClass.removeDuplicates();

        compareClass.countUnmatchedRecords();

        compareClass.findSimilaritiesFromUnmatchedRecords();

        return compareClass;

    }
}
