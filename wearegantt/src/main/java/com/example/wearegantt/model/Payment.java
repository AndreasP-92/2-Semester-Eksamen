package com.example.wearegantt.model;

public class Payment {
    private int payment_id;
    private int payment_price;
    private String payment_date;
    private int fk_userId;

    public Payment(int payment_id, int payment_price, String payment_date, int fk_userId) {
        this.payment_id = payment_id;
        this.payment_price = payment_price;
        this.payment_date = payment_date;
        this.fk_userId = fk_userId;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getPayment_price() {
        return payment_price;
    }

    public void setPayment_price(int payment_price) {
        this.payment_price = payment_price;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public int getFk_userId() {
        return fk_userId;
    }

    public void setFk_userId(int fk_userId) {
        this.fk_userId = fk_userId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payment_id=" + payment_id +
                ", payment_price=" + payment_price +
                ", payment_date='" + payment_date + '\'' +
                ", fk_userId=" + fk_userId +
                '}';
    }
}
