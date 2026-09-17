package com.busystem.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Sale {
    private final List<SaleItem> saleItemList;
    private final String saleId;
    private final LocalDateTime dateTime;
    private SaleStatus saleStatus;

    private Sale(String saleId, LocalDateTime dateTime, SaleStatus saleStatus,  List<SaleItem> saleItemList ) {
        this.saleId = Objects.requireNonNull(saleId, "SaleID cannot be null");
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
        this.saleStatus = saleStatus;
        this.saleItemList = Objects.requireNonNull(saleItemList, "Sale itemlist cannot be null");
    }
    
    public static Sale createNewSale(String saleId, LocalDateTime dateTime, List<SaleItem> saleItemList){
        
        return new Sale(saleId, dateTime, SaleStatus.PENDING, saleItemList);
    }

    
    public static Sale reconstituteSale(String saleId, LocalDateTime dateTime, SaleStatus saleStatus , List<SaleItem> saleItemList){

        return new Sale(saleId, dateTime, saleStatus, saleItemList);
    }






    public List<SaleItem> getSaleItemList() {
        return saleItemList;
    }

    public String getSaleId(){
            return saleId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void addItem(SaleItem item){
        Objects.requireNonNull(item, "SaleItem cannot be null");
        this.saleItemList.add(item);
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : saleItemList) {
            total = total.add(saleItem.getSubTotal());
        }
        return total;
    }

    public void markAsPaid(){
        if (!(saleStatus == SaleStatus.PENDING)) {
           throw new IllegalStateException("Cannot change a sale status that is not pending"); 
        }
        this.saleStatus = SaleStatus.PAID;
    }

    public void markAsCancelled(){
        if (!(saleStatus == SaleStatus.PENDING)) {
           throw new IllegalStateException("Cannot change a sale status that is not pending"); 
        }
        this.saleStatus = SaleStatus.CANCELLED;

    }

}
