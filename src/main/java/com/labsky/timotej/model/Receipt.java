package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

/**
 * @author timotej
 */
public record Receipt(
        Map<Product, Integer> products,
        UUID cashRegisterUuid,
        LocalDateTime time,
        UUID uuid,
        UUID contractorUui) {


    public Double getTotal() {
        return products.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }


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

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return """
                %s           
                Company:
                %s
                Employee:
                \tname: %s
                \temail: timotej.labsky@aardwark.com
                ---------------------------------------
                Cash register id: 
                %s
                Date: %s
                ---------------------------------------
                PRODUCT\t\tCOUNT\tUNIT PRICE(incl. tax)
                ---------------------------------------
                %s
                ---------------------------------------
                TOTAL:
                %.2f
                """
                .formatted(
                        centerMultiLine(LOGO),
                        centerMultiLine(companyAddress),
                        EMPLOYEE,
                        center(this.cashRegisterUuid.toString()),
                        dateFormat.format(this.time),
                        productsPrint(),
                        getTotal()
                );
    }

    private String productsPrint() {
        return this.products.keySet().stream()
                .map(product -> "%s\t%d\t\t%.2f %s%n".formatted(product.getName(),
                        products.get(product),
                        product.getPrice(),
                        product.getCurrency()))
                .collect(joining());
    }

    private String centerMultiLine(String s) {
        return Arrays.stream(s.split("\n"))
                .map(this::center)
                .map(line -> line + "\n")
                .collect(joining());
    }

    private String center(String s) {
        return WIDTH > s.length()
                ? " ".repeat((WIDTH - s.length()) / 2) + s + " ".repeat((WIDTH - s.length() + 1) / 2)
                : s;
    }
}
