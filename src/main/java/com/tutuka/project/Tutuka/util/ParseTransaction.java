package com.tutuka.project.Tutuka.util;

import com.tutuka.project.Tutuka.model.Transaction;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParseTransaction {

    private String profileName;
    private LocalDateTime transactionDate;
    private Double transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private BigInteger transactionID;
    private int transactionType;
    private String walletReference;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction parseCSVLine(String line, String fileNameInput){

        String[] transactionArray = line.split(",",-1);

        try {

            setProfileName(transactionArray[0].strip());
            setTransactionDate(LocalDateTime.parse(transactionArray[1], FORMATTER));
            setTransactionAmount(Double.valueOf(transactionArray[2]));
            setTransactionNarrative(transactionArray[3].strip());
            setTransactionDescription(transactionArray[4].strip());
            setTransactionID(new BigInteger(transactionArray[5]));
            setTransactionType(Integer.parseInt(transactionArray[6]));
            setWalletReference(transactionArray[7].strip());

            return new Transaction(fileNameInput,this.profileName,this.transactionDate,this.transactionAmount,
                    this.transactionNarrative,this.transactionDescription,this.transactionID,this.transactionType,
                    this.walletReference);

        } catch (Exception e){

            return null;

        }

    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public void setTransactionID(BigInteger transactionID) {
        this.transactionID = transactionID;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public void setWalletReference(String walletReference) {
        this.walletReference = walletReference;
    }

}
