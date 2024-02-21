import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

class Product implements Comparable<Product>{
    private final String name;
    private final boolean domestic;
    private final double price;
    private final Double weight;
    private final String description;

    public Product(String name, boolean domestic, double price, Double weight, String description) {
        this.name = name;
        this.domestic = domestic;
        this.price = price;
        this.weight = weight;
        this.description = description;
    }

    public String getDescription() {
        return description.length() > 10 ? String.format("%s...", description.substring(0, 10)) : description;
    }

    public String getWeight() {
        return weight==null ? "N/A" : String.format("%.0fg",weight);
    }

    public double getPrice() {
        return price;
    }

    public boolean isDomestic() {
        return domestic;
    }

    @Override
    public String toString() {
        return String.format("... %s\n\tPrice: $%.1f\n\t%s\n\tWeight: %s", name, price, getDescription(), getWeight());
    }

    @Override
    public int compareTo(Product other) {
        return this.name.compareTo(other.name);
    }
}

public class McaPreInterview {
    static List<Product> domesticProducts(List<Product> products) {
        return products.stream().sorted().filter(Product::isDomestic).collect(Collectors.toList());
    }
    static List<Product> importedProducts(List<Product> products) {
        return products.stream().sorted().filter(product -> !product.isDomestic()).collect(Collectors.toList());
    }
    static void print(List<Product> products) {
        products.forEach(System.out::println);
    }
    static double totalPrice(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
    static int count(List<Product> products) {
        return products.size();
    }
    public static void main(String[] args) throws IOException {

        URL url = new URL("https://interview-task-api.mca.dev/qr-scanner-codes/alpha-qr-gFpwhsQ8fkY1");
        List<Product> products = new Gson().fromJson(new InputStreamReader(url.openStream()), new TypeToken<List<Product>>() {}.getType());

        List<Product> domesticProducts = domesticProducts(products);
        List<Product> importedProducts = importedProducts(products);
        System.out.println(". Domestic");
        print(domesticProducts);
        System.out.println(". Imported");
        print(importedProducts);
        System.out.printf("Domestic cost: $%.1f%n", totalPrice(domesticProducts));
        System.out.printf("Imported cost: $%.1f%n", totalPrice(importedProducts));
        System.out.printf("Domestic count: %d%n", count(domesticProducts));
        System.out.printf("Imported count: %d%n", count(importedProducts));
    }

}
