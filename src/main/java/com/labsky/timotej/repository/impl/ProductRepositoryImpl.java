package com.labsky.timotej.repository.impl;

import com.labsky.timotej.model.products.ProductFactory;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.ProductRepository;

import java.io.IOException;
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
        Path fileUrl = getFilePath();


        products = new ArrayList<>();
        try {
            this.products.addAll(loadProductsFromFile(fileUrl));
        } catch (IOException exception) {
            err.printf("ProductRepositoryImpl - exception in parsing file: %s%n", fileUrl);
        }

    }

    public static ProductRepository getInstance() {
        return instance;
    }

    @Override
    public Optional<Product> findByName(final String name) {
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

    private Path getFilePath() {
        URL fileUrl = ClassLoader.getSystemResource(WAREHOUSE_FILE_NAME);
        return Path.of(fileUrl.getPath());
    }

    private List<Product> loadProductsFromFile(final Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.map(ProductFactory::getProduct)
                    .collect(toList());
        }
    }
}
