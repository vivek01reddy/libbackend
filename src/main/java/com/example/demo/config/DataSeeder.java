package com.example.demo.config;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            Book b1 = new Book();
            b1.setTitle("The Great Gatsby");
            b1.setAuthor("F. Scott Fitzgerald");
            b1.setIsbn("9780743273565");
            b1.setCategory("Fiction");
            b1.setPrice(new BigDecimal("350.00"));
            b1.setStockQuantity(25);
            b1.setDescription("A classic tale of the American Dream, set on Long Island in the 1920s.");

            Book b2 = new Book();
            b2.setTitle("To Kill a Mockingbird");
            b2.setAuthor("Harper Lee");
            b2.setIsbn("9780061120084");
            b2.setCategory("Fiction");
            b2.setPrice(new BigDecimal("420.00"));
            b2.setStockQuantity(15);
            b2.setDescription("A novel about racial injustice and the loss of innocence in the American South.");

            Book b3 = new Book();
            b3.setTitle("1984");
            b3.setAuthor("George Orwell");
            b3.setIsbn("9780451524935");
            b3.setCategory("Fiction");
            b3.setPrice(new BigDecimal("399.00"));
            b3.setStockQuantity(30);
            b3.setDescription("A dystopian novel exploring total surveillance and government control.");

            Book b4 = new Book();
            b4.setTitle("A Brief History of Time");
            b4.setAuthor("Stephen Hawking");
            b4.setIsbn("9780553380163");
            b4.setCategory("Science");
            b4.setPrice(new BigDecimal("650.00"));
            b4.setStockQuantity(10);
            b4.setDescription("Explaining the origin and nature of the universe in accessible language.");

            Book b5 = new Book();
            b5.setTitle("The Selfish Gene");
            b5.setAuthor("Richard Dawkins");
            b5.setIsbn("9780198788607");
            b5.setCategory("Science");
            b5.setPrice(new BigDecimal("550.00"));
            b5.setStockQuantity(12);
            b5.setDescription("A landmark work in evolutionary biology, introducing the gene-centered view.");

            Book b6 = new Book();
            b6.setTitle("Clean Code");
            b6.setAuthor("Robert C. Martin");
            b6.setIsbn("9780132350884");
            b6.setCategory("Technology");
            b6.setPrice(new BigDecimal("1250.00"));
            b6.setStockQuantity(20);
            b6.setDescription("A handbook of agile software craftsmanship, emphasizing readability and maintainability.");

            Book b7 = new Book();
            b7.setTitle("Sapiens: A Brief History of Humankind");
            b7.setAuthor("Yuval Noah Harari");
            b7.setIsbn("9780062316097");
            b7.setCategory("History");
            b7.setPrice(new BigDecimal("799.00"));
            b7.setStockQuantity(40);
            b7.setDescription("An evolutionary journey of humanity, from prehistoric origins to the present.");

            Book b8 = new Book();
            b8.setTitle("The Silent Patient");
            b8.setAuthor("Alex Michaelides");
            b8.setIsbn("9781250301697");
            b8.setCategory("Mystery");
            b8.setPrice(new BigDecimal("499.00"));
            b8.setStockQuantity(8);
            b8.setDescription("A psychological thriller about a woman who shoots her husband and never speaks again.");

            Book b9 = new Book();
            b9.setTitle("Deep Work");
            b9.setAuthor("Cal Newport");
            b9.setIsbn("9781455586691");
            b9.setCategory("Other");
            b9.setPrice(new BigDecimal("450.00"));
            b9.setStockQuantity(18);
            b9.setDescription("Rules for focused success in a distracted world, emphasizing cognitive intensity.");

            Book b10 = new Book();
            b10.setTitle("Dune");
            b10.setAuthor("Frank Herbert");
            b10.setIsbn("9780441172719");
            b10.setCategory("Fantasy");
            b10.setPrice(new BigDecimal("599.00"));
            b10.setStockQuantity(35);
            b10.setDescription("The epic story of Paul Atreides on the desert planet Arrakis.");

            bookRepository.saveAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10));
            System.out.println(">> Database Seeded with 10 Sample Books!");
        }
    }
}
