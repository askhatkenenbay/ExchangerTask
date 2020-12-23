package task;

import java.util.concurrent.CountDownLatch;

public class MyThread implements Runnable {

    CountDownLatch latch;
    TradeAcceptor tradeAcceptor;
    ExchangeOrder exchangeOrder;

    public MyThread(CountDownLatch latch, ExchangeOrder exchangeOrder) {
        this.latch = latch;
        tradeAcceptor = new TradeAcceptor();
        this.exchangeOrder = exchangeOrder;
    }

    @Override
    public void run() {
        try {
            latch.await();//The thread keeps waiting till it is informed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //thread started from here
        tradeAcceptor.acceptTradeData(exchangeOrder);
    }
}
