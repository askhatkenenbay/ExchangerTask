# Tестовое задание 2
Есть класс ExchangeOrder, который служит для обмена информацией

@Getter
@Setter
public class ExchangeOrder(){
private String internalOrderId;
private String exchangeOrderId;
private double filledQty;
}

Эти объекты могут переданы в следующий метод класса TradeAcceptor:

public class TradeAcceptor(){              public void acceptTradeData(Object tradeObject){             }                            
}

В параллельных потоках в acceptTradeData передаются объекты:

ExchangeOrder exchangeOrder1 = new ExchangeOrder("","eID1",0)
ExchangeOrder exchangeOrder2 = new ExchangeOrder("iId1","eID1",10)ExchangeOrder exchangeOrder3 = new ExchangeOrder("iId2","",0)
ExchangeOrder exchangeOrder4 = new ExchangeOrder("iId2","eID2",7)
ExchangeOrder exchangeOrder5 = new ExchangeOrder("iId2","",10)ExchangeOrder exchangeOrder6 = new ExchangeOrder("iId3","",0)
ExchangeOrder exchangeOrder7 = new ExchangeOrder("","eID3",10)
ExchangeOrder exchangeOrder8 = new ExchangeOrder("iId3","eID3",8)ExchangeOrder exchangeOrder9 = new ExchangeOrder("iId4","",12)
ExchangeOrder exchangeOrder10 = new ExchangeOrder("","eID4",10)
ExchangeOrder exchangeOrder11 = new ExchangeOrder("iId4","eID4",8)ExchangeOrder exchangeOrder12 = new ExchangeOrder("iId5","",0)
ExchangeOrder exchangeOrder13 = new ExchangeOrder("","eID5",10)
ExchangeOrder exchangeOrder14 = new ExchangeOrder("iId5","eID5",8)
ExchangeOrder exchangeOrder15 = new ExchangeOrder("iId5","",17)

1.Реализовать метод вызова acceptTradeData и передачи ему объектов ExchangeOrder в параллельных потоках, количество потоков = количеству объектов ExchangeOrder
2.Написать метод обработки acceptTradeData, который будет обновлять входящие объекты ExchangeOrder по ID, выводить последнее валидное значение  объекта ExchangeOrder (like .toString()), валидным объектом считается объект, у которого
internalOrderId!=empty or null and exchangeOrderId!=empty or null , filledQty объектов может только увеличиваться и не может становиться меньше текущего значения
internalOrderId and exchangeOrderId уникальны в рамках 1 цикла работы данного приложения,
т.е.

ExchangeOrder exchangeOrderX = new ExchangeOrder("iIdX","",0)
ExchangeOrder exchangeOrderY = new ExchangeOrder("","eIDX",1)
ExchangeOrder exchangeOrderZ = new ExchangeOrder("iIdX","eIDX",2)

Это информация об одной и той же единице информации.

#Приложение для решения 2 задачи [Java 11]

1. Создание класса ExchangeOrder

2. Создание класса MyThread который имплементируют интерфейс Runnable

3. Использовать CountDownLatch чтобы все потоки стартовали вместе

4. Предположить что между exchangeOrderId and internalOrderId one-to-one связь

5. Создать класс TradeAcceptor and implement acceptTradeData(Object tradeObject) method

6. Создать синхронизированную версию и тестировать
7. Тестировать асинхронизированную версию

8.Refactoring if needed


#Описание взаимодействия модулей

1.Main.java создает объекты ExchangeOrder и вызывает синхронизированную и асинхронизированную версию acceptTradeData(Object tradeObject)

2. При вызове асинхронной версий, создаёт потоки MyThread.java количество который равна количеству объектов ExchangeOrder

3. После команды старта для всех потоков, каждый поток вызывает метод acceptTradeData(Object tradeObject)


#Описание реализации модулей

1.Main.java создаёт объекты и вызывает методы

2.ExchangeOrder.java хранить информацию

3.MyThread.java implements Runnable interface, takes as input CountDownLatch and ExchangeOrder,
CountDownLatch нужен чтобы все потоки начали одновременно; ExchangeOrder передаётся методу acceptTradeData

4.TradeAcceptor.java хранить информацию в ConcurrentHashMap для многопоточного доступа

