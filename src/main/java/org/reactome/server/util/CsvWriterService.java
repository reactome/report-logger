package org.reactome.server.util;

import com.opencsv.CSVWriter;
import org.reactome.server.domain.TargetDigester;
import org.springframework.stereotype.Service;


import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class CsvWriterService {

    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final String TARGET_FILE_SUFFIX = "_target.csv";
    private final String SINGLE_TARGET_FILE_SUFFIX = "_target_queried_by_one_user.csv";
    private final String SEARCH_FILE_SUFFIX = "_search.csv";
    private final String SINGLE_SEARCH_FILE_SUFFIX = "_search_terms_queried_by_one_user.csv";
    private final String HEADER = "Terms(%s), Hits, Unique Users";
    private final String SINGLE_TARGET_HEADER = "TARGETS queried ONLY by ONE user(%s)";
    private final String SINGLE_SEARCH_HEADER = "TERMS queried ONLY by ONE user(%s)";


    private File writeToCsv(File dir, LocalDateTime from, LocalDateTime to, List<TargetDigester> list, String fileSuffix, String headerColumns) {
        String fileName = from.format(ofPattern(DATE_FORMAT)) + "_" + to.format(ofPattern(DATE_FORMAT)) + fileSuffix;
        File csvFile = new File(dir, fileName);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(csvFile);
            // create CSVWriter object fileWriter object as parameter
            CSVWriter writer = new CSVWriter(outputFile);

            String summary = String.format(headerColumns, list.size());
            String[] header = summary.contains(",") ? summary.split(",") : new String[]{summary};
            //header
            writer.writeNext(header);
            // check the length of the header, write hits and unique user into CSV file when it has 3 columns
            if (header.length > 1) {
                for (TargetDigester item : list) {
                    writer.writeNext(new String[]{item.getTerm(), String.valueOf(item.getCount()), String.valueOf(item.getUniqueIPs())});
                }
            } else {
                for (TargetDigester item : list) {
                    writer.writeNext(new String[]{item.getTerm()});
                }
            }
            // closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFile;
    }

    public List<File> writeTargetToCSV(File dir, LocalDateTime from, LocalDateTime to, Map<String, List<TargetDigester>> map) {
        List<File> targetFiles = new ArrayList<>();
        File targetFile = writeToCsv(dir, from, to, map.get("targetRelevantSummary"), TARGET_FILE_SUFFIX, HEADER);
        File singleTargetFile = writeToCsv(dir, from, to, map.get("targetSingleSummary"), SINGLE_TARGET_FILE_SUFFIX, SINGLE_TARGET_HEADER);
        targetFiles.add(targetFile);
        targetFiles.add(singleTargetFile);
        return targetFiles;
    }

    public List<File> writeSearchToCSV(File dir, LocalDateTime from, LocalDateTime to, Map<String, List<TargetDigester>> map) {
        List<File> searchFiles = new ArrayList<>();
        File searchFile = writeToCsv(dir, from, to, map.get("searchRelevantSummary"), SEARCH_FILE_SUFFIX, HEADER);
        File singleSearchFile = writeToCsv(dir, from, to, map.get("searchSingleSummary"), SINGLE_SEARCH_FILE_SUFFIX, SINGLE_SEARCH_HEADER);
        searchFiles.add(searchFile);
        searchFiles.add(singleSearchFile);
        return searchFiles;
    }
}
