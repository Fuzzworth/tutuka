package com.tutuka.project.Tutuka.util;

import com.tutuka.project.Tutuka.exception.StorageException;
import com.tutuka.project.Tutuka.model.Transaction;

import java.io.FileInputStream;
import java.io.IOException;

import java.math.BigInteger;
import java.util.*;

public class ParseAndCompare {

    private String firstCSVFile, secondCSVFile;
    private int firstCSVFileTotalRecords, secondCSVFileTotalRecords, CSVFilesMatchedRecords, firstCSVFileUnmatchedRecords,
            secondCSVFileUnmatchedRecords, firstCSVFileMalformedRecords, secondCSVFileMalformedRecords;
    private HashSet<Transaction> unmatchedRecords, secondCSVFileHashSet, firstCSVFileTransactionDuplicates,
            secondCSVFileTransactionDuplicates;
    private HashSet<String> firstCSVFileMalformedRecordSet, secondCSVFileMalformedRecordSet,
            firstCSVFileMalformedRecordSetDuplicated, secondCSVFileMalformedRecordSetDuplicated;
    private HashMap<BigInteger, HashSet<Transaction>> similaritiesMap;

    public ParseAndCompare(String firstCSVFile, String secondCSVFile) {
        this.firstCSVFile = firstCSVFile;
        this.secondCSVFile = secondCSVFile;
        this.CSVFilesMatchedRecords = 0;
        this.firstCSVFileUnmatchedRecords = 0;
        this.secondCSVFileUnmatchedRecords = 0;
        this.firstCSVFileTotalRecords = 0;
        this.secondCSVFileTotalRecords = 0;
        this.firstCSVFileMalformedRecords = 0;
        this.secondCSVFileMalformedRecords = 0;
        this.firstCSVFileMalformedRecordSet = new HashSet<String>();
        this.secondCSVFileMalformedRecordSet = new HashSet<String>();
        this.unmatchedRecords = new HashSet<Transaction>();
        this.secondCSVFileHashSet = new HashSet<Transaction>();
        this.firstCSVFileTransactionDuplicates = new HashSet<Transaction>();
        this.secondCSVFileTransactionDuplicates = new HashSet<Transaction>();
        this.firstCSVFileMalformedRecordSetDuplicated = new HashSet<String>();
        this.secondCSVFileMalformedRecordSetDuplicated = new HashSet<String>();
        this.similaritiesMap = new HashMap<BigInteger, HashSet<Transaction>>();

    }

    public HashSet<Transaction> parseFirstCSV(ParseTransaction transactionParser) {

        boolean firstLine = true;
        Scanner sc = null;

        try (FileInputStream inputStream = new FileInputStream(this.firstCSVFile)) {

            sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Transaction tx = transactionParser.parseCSVLine(line, this.firstCSVFile);
                firstCSVFileTotalRecords++;

                if (tx == null) {
                    boolean isDuplicated = this.firstCSVFileMalformedRecordSet.add(line);
                    this.firstCSVFileMalformedRecords++;
                    if (!isDuplicated) {
                        this.firstCSVFileMalformedRecordSetDuplicated.add(line);
                    }
                } else {
                    boolean isDuplicated = this.unmatchedRecords.add(tx);
                    if (!isDuplicated) {
                        this.firstCSVFileTransactionDuplicates.add(tx);
                    }
                }

            }

        } catch (IOException e) {
            throw new StorageException("Failed to retrieve file " + this.firstCSVFile, e);
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return unmatchedRecords;

    }

    public HashSet<Transaction> parseSecondCSVAndMatch(ParseTransaction transactionParser) {

        boolean firstLine = true;
        Scanner sc = null;

        try (FileInputStream inputStream = new FileInputStream(this.secondCSVFile)) {

            sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Transaction tx = transactionParser.parseCSVLine(line, this.secondCSVFile);
                this.secondCSVFileTotalRecords++;

                if (tx == null) {
                    boolean isDuplicated = this.secondCSVFileMalformedRecordSet.add(line);
                    this.secondCSVFileMalformedRecords++;
                    if (!isDuplicated) {
                        this.secondCSVFileMalformedRecordSetDuplicated.add(line);
                    }
                } else {
                    boolean isDuplicated = this.secondCSVFileHashSet.add(tx);
                    if (!isDuplicated) {
                        this.secondCSVFileTransactionDuplicates.add(tx);
                    }
                }

                performMatch(tx);

            }

        } catch (IOException e) {
            throw new StorageException("Failed to retrieve file " + this.secondCSVFile, e);
        } finally {
            if (sc != null) {
                sc.close();
            }

            secondCSVFileHashSet.clear();
        }

        return unmatchedRecords;

    }

    public void performMatch(Transaction tx) {
        if (this.unmatchedRecords.contains(tx) && tx != null) {
            this.unmatchedRecords.remove(tx);
            this.CSVFilesMatchedRecords++;

        } else {
            this.unmatchedRecords.add(tx);
        }
    }

    public void countUnmatchedRecords() {
        for (Transaction tx : this.unmatchedRecords) {
            if (tx.getFileName().equals(this.firstCSVFile)) {
                this.firstCSVFileUnmatchedRecords++;
            } else {
                this.secondCSVFileUnmatchedRecords++;
            }
        }

        this.firstCSVFileUnmatchedRecords += this.firstCSVFileMalformedRecordSet.size();
        this.secondCSVFileUnmatchedRecords += this.secondCSVFileMalformedRecordSet.size();
    }

    public void removeDuplicates() {
        this.unmatchedRecords.removeAll(this.firstCSVFileTransactionDuplicates);
        this.firstCSVFileMalformedRecordSet.removeAll(this.firstCSVFileMalformedRecordSetDuplicated);
        this.unmatchedRecords.removeAll(this.secondCSVFileTransactionDuplicates);
        this.secondCSVFileMalformedRecordSet.removeAll(this.secondCSVFileMalformedRecordSetDuplicated);
    }

    public void findSimilaritiesFromUnmatchedRecords() {

        this.similaritiesMap.clear();

        for (Transaction tx : this.unmatchedRecords) {

            if (!this.similaritiesMap.containsKey(tx.getTransactionID())) {
                this.similaritiesMap.put(tx.getTransactionID(), new HashSet<Transaction>());
            }

            for (Transaction zx : this.unmatchedRecords) {
                if (!tx.equals(zx)) {
                    if (tx.getTransactionID().equals(zx.getTransactionID())
                            || tx.getTransactionDate().equals(zx.getTransactionDate())
                            || tx.getTransactionAmount() == zx.getTransactionAmount()
                            || tx.getTransactionNarrative().equals(zx.getTransactionNarrative())
                            || tx.getWalletReference().equals(zx.getWalletReference())
                    ) {
                        this.similaritiesMap.get(tx.getTransactionID()).add(zx);
                    }
                }
            }
        }
    }

    public String getFirstCSVFile() {
        return firstCSVFile;
    }

    public String getSecondCSVFile() {
        return secondCSVFile;
    }

    public int getFirstCSVFileTotalRecords() {
        return firstCSVFileTotalRecords;
    }

    public int getSecondCSVFileTotalRecords() {
        return secondCSVFileTotalRecords;
    }

    public int getCSVFilesMatchedRecords() {
        return CSVFilesMatchedRecords;
    }

    public int getFirstCSVFileUnmatchedRecords() {
        return firstCSVFileUnmatchedRecords;
    }

    public int getSecondCSVFileUnmatchedRecords() {
        return secondCSVFileUnmatchedRecords;
    }

    public int getFirstCSVFileMalformedRecords() {
        return firstCSVFileMalformedRecords;
    }

    public int getSecondCSVFileMalformedRecords() {
        return secondCSVFileMalformedRecords;
    }

    public HashSet<Transaction> getUnmatchedRecords() {
        return unmatchedRecords;
    }

    public HashSet<Transaction> getFirstCSVFileTransactionDuplicates() {
        return firstCSVFileTransactionDuplicates;
    }

    public HashSet<Transaction> getSecondCSVFileTransactionDuplicates() {
        return secondCSVFileTransactionDuplicates;
    }

    public HashSet<String> getFirstCSVFileMalformedRecordSet() {
        return firstCSVFileMalformedRecordSet;
    }

    public HashSet<String> getSecondCSVFileMalformedRecordSet() {
        return secondCSVFileMalformedRecordSet;
    }

    public HashSet<String> getFirstCSVFileMalformedRecordSetDuplicated() {
        return firstCSVFileMalformedRecordSetDuplicated;
    }

    public HashSet<String> getSecondCSVFileMalformedRecordSetDuplicated() {
        return secondCSVFileMalformedRecordSetDuplicated;
    }

    public HashMap<BigInteger, HashSet<Transaction>> getSimilaritiesMap() {
        return similaritiesMap;
    }
}