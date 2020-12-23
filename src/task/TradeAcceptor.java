package task;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TradeAcceptor {
    //key is unique string combination of internalOrderId and exchangeOrderId
    private static ConcurrentHashMap<String, Double> hashMap;//key:internal-exchange, value:filledQty
    //used to find other pair of internalOrderId
    private static ConcurrentHashMap<String, String> internalToExchangeMap;//key:internal, value:exchange
    //used to find other pair of exchangeOrderId
    private static ConcurrentHashMap<String, String> exchangeToInternalMap;//key:exchange, value:internal

    /**
     * @param tradeObject expected ExchangeOrder object, but in requirements was written that method input should be Object
     *                    <p>
     *                    prints to the system.out last valid parameters of ExchangeOrder object
     */
    public void acceptTradeData(Object tradeObject) {
        try {
            ExchangeOrder exchangeOrder = (ExchangeOrder) tradeObject;
            String exchangeId = exchangeOrder.getExchangeOrderId();
            String internalId = exchangeOrder.getInternalOrderId();
            double filledQty = exchangeOrder.getFilledQty();
            String key;
            //in case we are given both internalOrderId and exchangeOrderId
            //if key already exist, just update filledQty value
            //else, put new combination to hashMaps
            if (exchangeId != null && !exchangeId.isBlank() && internalId != null && !internalId.isBlank()) {
                key = internalId + "-" + exchangeId;
                if (hashMap.containsKey(key)) {
                    filledQty = correctFilledQtyValue(key, filledQty);
                } else {
                    hashMap.put(key, filledQty);
                    internalToExchangeMap.put(internalId, exchangeId);
                    exchangeToInternalMap.put(exchangeId, internalId);
                }
                printMessage(internalId, exchangeId, filledQty);
            } else if ((exchangeId == null || exchangeId.isBlank()) && internalId != null && !internalId.isBlank()) {//if only given internalOrderId
                processOneInput(internalId, filledQty, true);
            } else if (exchangeId != null && !exchangeId.isBlank() && (internalId == null || internalId.isBlank())) {//if only given exchangeOrderId
                processOneInput(exchangeId, filledQty, false);
            }
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    //used for synchronized call of acceptTradeDate method for testing purpose
    public void acceptTradeDataSync(List<ExchangeOrder> list) {
        for (Object obj : list) {
            acceptTradeData(obj);
        }
    }

    //true--internal

    /**
     * @param orderId   depending on type either internalOrderId or exchangeOrderId
     * @param filledQty
     * @param type      if true, orderId is internalOrderId, else exchangeOrderId
     */
    private static void processOneInput(String orderId, double filledQty, boolean type) {
        String pair = type ? internalToExchangeMap.get(orderId) : exchangeToInternalMap.get(orderId);
        if (pair != null && !pair.isBlank()) {
            String tempKey = type ? orderId + "-" + pair : pair + "-" + orderId;
            double value = correctFilledQtyValue(tempKey, filledQty);
            hashMap.put(tempKey, value);
            if (type) {
                printMessage(orderId, pair, value);
            } else {
                printMessage(pair, orderId, value);
            }

        }
    }

    //returns correct value of filledQty
    private static double correctFilledQtyValue(String key, double curr) {
        if (!hashMap.containsKey(key)) return curr;
        return hashMap.get(key) <= curr ? curr : hashMap.get(key);
    }

    //prints message to system out
    private static void printMessage(String internalId, String exchangeId, double filledQty) {
        //System.out.println(internalId + ":" + exchangeId + ":" + filledQty);
        System.out.println("[Internal:" + internalId + " Exchange:" + exchangeId + " FilledQty:" + filledQty + "]");
    }

    //clears all static fields
    public static void clearTradeAcceptor() {
        hashMap = new ConcurrentHashMap<>();
        internalToExchangeMap = new ConcurrentHashMap<>();
        exchangeToInternalMap = new ConcurrentHashMap<>();
    }
}
