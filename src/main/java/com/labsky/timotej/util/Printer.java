package com.labsky.timotej.util;

import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

/**
 * @author timotej
 */
public class Printer {

    public static final int DELAY = 50;
    public static final int WIDTH = 40;
    public static final String LOGO = """
            _   _ _                          \s
            | | | (_)_ __ ___   _ __ ___   ___\s
            | |_| | | '__/ _ \\ | '_ ` _ \\ / _ \\
            |  _  | | | |  __/ | | | | | |  __/
            |_| |_|_|_|  \\___| |_| |_| |_|\\___|
            \s
            """;
    public static final String companyAddress = """
            Aardwark s.r.o
            Trnavská cesta 84
            821 01 Bratislava 2
            Slovenská republika
            """;
    public static final String EMPLOYEE = "Timotej Lábský";


    public static void print(Receipt receipt) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        String mainString = """
                %s           
                Company:
                %s
                Employee:
                \tname: %s
                \temail: timotej.labsky@aardwark.com
                -------------------------------------------
                Cash register id: 
                %s
                Date: %s
                -------------------------------------------
                %s
                -------------------------------------------
                TOTAL:
                \t%.2f
                """.formatted(
                centerMultiLine(LOGO),
                centerMultiLine(companyAddress),
                EMPLOYEE,
                center(receipt.cashRegisterUuid().toString()),
                dateFormat.format(receipt.time()),
                productsPrint(receipt.products()),
                receipt.getTotal());

        animatedPrint(mainString);

    }

    private static void animatedPrint(String mainString) {
        for (String s : mainString.split("\n")) {
            out.println(s);
            try {
                TimeUnit.MILLISECONDS.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String productsPrint(Map<Product, Integer> products) {
        return products.keySet().stream()
                .map(p -> getProductString(p, products))
                .collect(joining());
    }

    private static String getProductString(Product product, Map<Product, Integer> products) {
        int count = products.get(product);
        return """
                %s
                \t%d pcs\t%.2f %s\t%.2f %s
                """.formatted(product.getName(),
                count,
                product.getPrice(),
                product.getCurrency(),
                product.getPrice() * count,
                product.getCurrency()

        );
    }
    
    private static String centerMultiLine(String s) {
        return Arrays.stream(s.split("\n"))
                .map(Printer::center)
                .map(line -> line + "\n")
                .collect(joining());
    }

    private static String center(String s) {
        return WIDTH > s.length()
                ? " ".repeat((WIDTH - s.length()) / 2) + s + " ".repeat((WIDTH - s.length() + 1) / 2)
                : s;
    }
}
