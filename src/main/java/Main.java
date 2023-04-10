import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryManager categoryManager = new CategoryManager();
    private static final ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for Add Category");
            System.out.println("Please input 2 for Edit Category by id");
            System.out.println("Please input 3 for Delete Category by id");
            System.out.println("Please input 4 for Add product");
            System.out.println("Please input 5 for Edit product By id");
            System.out.println("Please input 6 for Delete Product By id");
            System.out.println("Please input 7 for Print Sum of products");
            System.out.println("Please input 8 for Print Max of price product");
            System.out.println("Please input 9 for Print Min of price product");
            System.out.println("Please input 10 for Print Avg of price product");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    editCategoryById();
                    break;
                case "3":
                    deleteCategoryById();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    editProductById();
                    break;
                case "6":
                    deleteProductById();
                    break;
                case "7":
                    printSumOfProducts();
                    break;
                case "8":
                    printMaxProductPrice();
                    break;
                case "9":
                    printMinProductPrice();
                    break;
                case "10":
                    printAvgProductPrice();
                    break;
            }
        }
    }

    private static void printAvgProductPrice() {
        System.out.println(productManager.getPriceAvg());
    }

    private static void printMinProductPrice() {
        System.out.println(productManager.getMinProductPrice());
    }

    private static void printMaxProductPrice() {
        System.out.println(productManager.getMaxProductPrice());
    }

    private static void printSumOfProducts() {
        System.out.println(productManager.getSum());
    }

    private static void deleteProductById() {
        printAllProducts();
        System.out.println("Please choose product by id");
        int id = Integer.parseInt(scanner.nextLine());
        if (productManager.getById(id) != null) {
            productManager.removeById(id);
        } else {
            System.out.println("Wrong id please try again!");
        }
    }

    private static void editProductById() {
        System.out.println("Please choose product id");
        printAllProducts();
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.println("Please choose new category");
        printAllCategories();
        int categoryId = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(categoryId) != null && productManager.getById(productId) != null) {
            System.out.println("Please input product name,description,price,quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");
            Product product = new Product();
            product.setId(productId);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            product.setCategory(categoryManager.getById(categoryId));
            productManager.update(product);
            System.out.println("Product was updated!");
        } else {
            System.out.println("Wrong id please try again!");
        }
    }

    private static void addProduct() {
        printAllCategories();
        System.out.println("Please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("Please input product name,description,price,quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");
            Product product = new Product();
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            product.setCategory(categoryManager.getById(id));
            productManager.save(product);
        } else {
            System.out.println("Wrong id please try again!");
        }
    }

    private static void deleteCategoryById() {
        printAllCategories();
        System.out.println("Please choose category by id");
        int id = Integer.parseInt(scanner.nextLine());
        if (productManager.getProductByCategory(id) != null || categoryManager.getById(id) == null) {
            System.out.println("You can`t delete category by id " + id + " please input another id!");
        } else {
            categoryManager.removeById(id);
            System.out.println("Category was deleted!");
        }
    }

    private static void editCategoryById() {
        printAllCategories();
        System.out.println("Please choose category by id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("Please input name");
            String name = scanner.nextLine();
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            categoryManager.update(category);
            System.out.println("Category was updated");
        } else {
            System.out.println("Wrong id please try again!");
        }
    }

    private static void addCategory() {
        System.out.println("Please input category name");
        String name = scanner.nextLine();
        Category category = new Category();
        category.setName(name);
        categoryManager.save(category);
    }

    private static void printAllCategories() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
    }

    private static void printAllProducts() {
        List<Product> all = productManager.getAll();
        for (Product product : all) {
            System.out.println(product);
        }
    }
}
