package task;

public class ExchangeOrder {
    private String internalOrderId;
    private String exchangeOrderId;
    private double filledQty;

    public ExchangeOrder() {

    }

    public ExchangeOrder(String internalOrderId, String exchangeOrderId, double filledQty) {
        this.internalOrderId = internalOrderId;
        this.exchangeOrderId = exchangeOrderId;
        this.filledQty = filledQty;
    }

    @Override
    public String toString() {
        return "ExchangeOrder{" +
                "internalOrderId='" + internalOrderId + '\'' +
                ", exchangeOrderId='" + exchangeOrderId + '\'' +
                ", filledQty=" + filledQty +
                '}';
    }

    public String getInternalOrderId() {
        return internalOrderId;
    }

    public void setInternalOrderId(String internalOrderId) {
        this.internalOrderId = internalOrderId;
    }

    public String getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(String exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public double getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(double filledQty) {
        this.filledQty = filledQty;
    }
}
