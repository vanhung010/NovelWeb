package project.projectmanagernovel.entry;

import java.time.LocalDate;

public class WithdrawalRequest {
    private int idWithdrawalRequest;
    private double Amount;
    private String status;
    private LocalDate requestDate;

    public WithdrawalRequest(int idWithdrawalRequest, double amount, String status, LocalDate requestDate) {
        this.idWithdrawalRequest = idWithdrawalRequest;
        Amount = amount;
        this.status = status;
        this.requestDate = requestDate;
    }

    public WithdrawalRequest() {
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getIdWithdrawalRequest() {
        return idWithdrawalRequest;
    }

    public void setIdWithdrawalRequest(int idWithdrawalRequest) {
        this.idWithdrawalRequest = idWithdrawalRequest;
    }
}
