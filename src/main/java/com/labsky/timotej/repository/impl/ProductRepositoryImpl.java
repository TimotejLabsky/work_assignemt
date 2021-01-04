package com.labsky.timotej.repository.impl;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.ProductFactory;
import com.labsky.timotej.repository.ProductRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.util.stream.Collectors.toList;

/**
 * @author timotej
 */
public class ProductRepositoryImpl implements ProductRepository {

    private static final String WAREHOUSE_FILE_NAME = "warehouse.txt";
    private static final ProductRepository instance = new ProductRepositoryImpl();

    private final List<Product> products;

    private ProductRepositoryImpl() {
        products = new ArrayList<>();
        Path fileUrl = null;

        try {
            fileUrl = getFilePath();
            this.products.addAll(loadProductsFromFile(fileUrl));

        } catch (IOException exception) {
            err.printf("ProductRepositoryImpl - exception in parsing file: %s - %s%n", fileUrl, exception.getMessage());
            System.exit(1);
        } catch (Exception exception) {
            err.printf("ProductRepositoryImpl - exception in file path: %s%n", exception.getMessage());
            System.exit(1);

        }

    }

    public static ProductRepository getInstance() {
        return instance;
    }

    @Override
    public Optional<Product> findByName(final String name) throws ProductNotFoundException {
        return this.products.stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return this.products;
    }

    @Override
    public List<Product> findAllByName(final List<String> names) {
        return this.products.stream()
                .filter(p -> names.contains(p.getName()))
                .collect(toList());
    }

    @Override
    public Product save(final Product product) {
        return null;
    }

    private Path getFilePath() throws Exception {
        URL fileUrl = ClassLoader.getSystemResource(WAREHOUSE_FILE_NAME);

        if (fileUrl == null) {
            throw new Exception("Warehouse file not found in class path");
        }

        return Path.of(fileUrl.toURI());
    }

    private List<Product> loadProductsFromFile(final Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.map(ProductFactory::getProduct)
                    .collect(toList());
        }
    }
}
