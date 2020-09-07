package com.tutuka.project.Tutuka.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private String fileName;
    private String profileName;
    private LocalDateTime transactionDate;
    private Double transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private BigInteger transactionID;
    private int transactionType;
    private String walletReference;

    public Transaction() {

    }

    public Transaction(String fileName, String profileName, LocalDateTime transactionDate, Double transactionAmount,
                       String transactionNarrative, String transactionDescription, BigInteger transactionID,
                       int transactionType, String walletReference) {
        this.fileName = fileName;
        this.profileName = profileName;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionNarrative = transactionNarrative;
        this.transactionDescription = transactionDescription;
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.walletReference = walletReference;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public BigInteger getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(BigInteger transactionID) {
        this.transactionID = transactionID;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getWalletReference() {
        return walletReference;
    }

    public void setWalletReference(String walletReference) {
        this.walletReference = walletReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return getProfileName().equals(that.getProfileName()) &&
                getTransactionDate().equals(that.getTransactionDate()) &&
                getTransactionAmount().equals(that.getTransactionAmount()) &&
                getTransactionNarrative().equals(that.getTransactionNarrative()) &&
                getTransactionDescription().equals(that.getTransactionDescription()) &&
                getTransactionID().equals(that.getTransactionID()) &&
                getTransactionType() == that.getTransactionType() &&
                getWalletReference().equals(that.getWalletReference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileName(), getTransactionDate(), getTransactionAmount(), getTransactionNarrative(), getTransactionDescription(), getTransactionID(), getTransactionType(), getWalletReference());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "File Name ='" + fileName + '\'' +
                "Profile Name ='" + profileName + '\'' +
                ", Transaction Date =" + transactionDate +
                ", Transaction Amount =" + transactionAmount +
                ", Transaction Narrative ='" + transactionNarrative + '\'' +
                ", Transaction Description ='" + transactionDescription + '\'' +
                ", TransactionID =" + transactionID +
                ", Transaction Type =" + transactionType +
                ", Wallet Reference ='" + walletReference + '\'' +
                '}';
    }
}
