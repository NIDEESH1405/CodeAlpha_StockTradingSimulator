import java.util.*;

/* ===================== MAIN CLASS ===================== */
public class StockTradingSimulator {

    /* ===================== STOCK CLASS ===================== */
    static class Stock {
        String symbol;
        double price;

        Stock(String symbol, double price) {
            this.symbol = symbol;
            this.price = price;
        }
    }

    /* ===================== TRANSACTION CLASS ===================== */
    static class Transaction {
        String type;
        String stock;
        int quantity;
        double price;

        Transaction(String type, String stock, int quantity, double price) {
            this.type = type;
            this.stock = stock;
            this.quantity = quantity;
            this.price = price;
        }
    }

    /* ===================== PORTFOLIO CLASS ===================== */
    static class Portfolio {
        double balance = 10000; // initial balance
        Map<String, Integer> holdings = new HashMap<>();
        List<Transaction> history = new ArrayList<>();

        void buyStock(Stock stock, int qty) {
            double cost = stock.price * qty;
            if (cost <= balance) {
                balance -= cost;
                holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + qty);
                history.add(new Transaction("BUY", stock.symbol, qty, stock.price));
                System.out.println("Stock bought successfully!");
            } else {
                System.out.println("Insufficient balance!");
            }
        }

        void sellStock(Stock stock, int qty) {
            int owned = holdings.getOrDefault(stock.symbol, 0);
            if (qty <= owned) {
                balance += stock.price * qty;
                holdings.put(stock.symbol, owned - qty);
                history.add(new Transaction("SELL", stock.symbol, qty, stock.price));
                System.out.println("Stock sold successfully!");
            } else {
                System.out.println("Not enough stocks to sell!");
            }
        }

        void showPortfolio() {
            System.out.println("\n----- PORTFOLIO SUMMARY -----");
            System.out.println("Balance: ₹" + balance);
            System.out.println("Holdings:");
            for (String s : holdings.keySet()) {
                System.out.println(s + " : " + holdings.get(s));
            }
        }
    }

    /* ===================== MARKET CLASS ===================== */
    static class Market {
        List<Stock> stocks = new ArrayList<>();

        Market() {
            stocks.add(new Stock("AAPL", 180));
            stocks.add(new Stock("GOOG", 2700));
            stocks.add(new Stock("TSLA", 900));
        }

        void displayMarket() {
            System.out.println("\n----- MARKET DATA -----");
            for (Stock s : stocks) {
                System.out.println(s.symbol + " : ₹" + s.price);
            }
        }

        Stock getStock(String symbol) {
            for (Stock s : stocks) {
                if (s.symbol.equalsIgnoreCase(symbol)) {
                    return s;
                }
            }
            return null;
        }
    }

    /* ===================== MAIN METHOD ===================== */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Market market = new Market();
        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\n1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    market.displayMarket();
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next();
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null)
                        portfolio.buyStock(buyStock, buyQty);
                    else
                        System.out.println("Stock not found!");
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next();
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null)
                        portfolio.sellStock(sellStock, sellQty);
                    else
                        System.out.println("Stock not found!");
                    break;

                case 4:
                    portfolio.showPortfolio();
                    break;

                case 5:
                    System.out.println("Thank you for trading!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
