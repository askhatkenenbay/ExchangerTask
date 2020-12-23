package task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args){
        List<ExchangeOrder> list = new ArrayList<>();
        list.add(new ExchangeOrder("", "eID1", 0));
        list.add(new ExchangeOrder("iId1", "eID1", 10));
        list.add(new ExchangeOrder("iId2", "", 0));
        list.add(new ExchangeOrder("iId2", "eID2", 7));
        list.add(new ExchangeOrder("iId2", "", 10));
        list.add(new ExchangeOrder("iId3", "", 0));
        list.add(new ExchangeOrder("", "eID3", 10));
        list.add(new ExchangeOrder("iId3", "eID3", 8));
        list.add(new ExchangeOrder("iId4", "", 12));
        list.add(new ExchangeOrder("", "eID4", 10));
        list.add(new ExchangeOrder("iId4", "eID4", 8));
        list.add(new ExchangeOrder("iId5", "", 0));
        list.add(new ExchangeOrder("", "eID5", 10));
        list.add(new ExchangeOrder("iId5", "eID5", 8));
        list.add(new ExchangeOrder("iId5", "", 17));
        System.out.println("---SYNC_START---");
        runExchangesSync(list);
        System.out.println("---ASYNC_START---");
        runExchanges(list);

    }

    public static void runExchanges(List<ExchangeOrder> list) {
        CountDownLatch latch = new CountDownLatch(1);
        TradeAcceptor.clearTradeAcceptor();
        for (ExchangeOrder exchangeOrder : list) {
            MyThread myThread = new MyThread(latch, exchangeOrder);
            new Thread(myThread).start();
        }
        latch.countDown();
    }

    public static void runExchangesSync(List<ExchangeOrder> list){
        TradeAcceptor.clearTradeAcceptor();
        TradeAcceptor tradeAcceptor = new TradeAcceptor();
        tradeAcceptor.acceptTradeDataSync(list);
    }
}
