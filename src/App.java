import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class App {
    private static Scanner read = new Scanner(System.in);
    private static Map<Integer, String> categoriesMap = new TreeMap<>();
    private static Map<Integer, Producto> productsMap = new TreeMap<>();

    public static void main(String[] args) {
        int option;
        do {
            option = menu();
            switch (option) {
                case 1:
                    newProduct();
                    break;
                case 2:
                    menu2();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    newCategory();
                    break;
                case 5:
                    deleteCategory();
                    break;
                case 6:
                    showProducts();
                    break;
                case 7:
                    showCategories();
                    break;
                default:
                    break;
            }
        } while (option >= 1 && option < 7);
        System.out.println("Fin del programa");
    }

    public static int menu(){
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1. Crear producto");
        System.out.println("2. Cambiar stock");
        System.out.println("3. Eliminar producto");
        System.out.println("4. Añadir categoria");
        System.out.println("5. Eliminar categoria");
        System.out.println("6. Ver productos por categoria");
        System.out.println("7. Ver categorias");
        System.out.println("8. Salir");
        int opt = verifyInt("Ingrese la opcion: ");
        return opt;
    }

    public static void newProduct(){
        System.out.println("--- CREAR PRODUCTO ---");
        int ref = refProdAvaliable();
        System.out.print("Nombre: ");
        String name = read.nextLine();
        showCategories();
        int cat = catAvaliable();
        int quantity = verifyInt("Ingrese el stock del producto: ");
        double price = verifyDouble("Ingresa el precio del producto: ");
        Producto newProd = new Producto(ref, name, cat, quantity, price);
        productsMap.put(ref, newProd);
    }
    
    public static void menu2(){
        System.out.println("--- Cambio de stock ---");
        System.out.println("1. Añadir stock");
        System.out.println("2. Reducir stock");
        System.out.println("3. Volver al menu");
        int opt = verifyInt("Ingrese la opcion: ");
        do {
            switch (opt) {
                case 1:
                    System.out.println("--- Añadir Stock ---");
                    changeStock(true);
                    break;
                case 2:
                    System.out.println("--- Reducir Stock ---");
                    changeStock(false);
                    break;
                default:
                    break;
            }
        } while (opt > 0 && opt <= 2);
    }

    public static void changeStock(boolean add){
        int refProd = existProd();
        String name = productsMap.get(refProd).getName();
        int stock = productsMap.get(refProd).getStock();
        System.out.println("Producto: " + name + ", Stock actual: " + stock);
        if (add) {
            int quantity = verifyInt("Ingrese el stock a añadir: ");
            productsMap.get(refProd).addStock(quantity);
        }else{
            int quantity = 0;
            boolean quantityValid = false;
            while (!quantityValid) {
                quantity = verifyInt("Ingrese el stock a reducir (No debe ser mayor al actual): ");
                if (quantity > productsMap.get(refProd).getStock()) {
                    System.out.println("No es posible quitar esa cantidad, verifica");
                }else{ quantityValid = true;}
            }
            productsMap.get(refProd).reduceStock(quantity);
        }
        System.out.println("Producto: " + name + ", Nuevo stock: " + productsMap.get(refProd).getStock());
    }

    public static void deleteProduct(){
        System.out.println("--- Eliminar producto ---");
        int refProd = existProd();
        productsMap.remove(refProd);
        System.out.println("Producto eliminado correctamente");
    }

    public static void newCategory(){
        System.out.println("--- Añadir Categoria ---");
        boolean existCat = false;
        String newCat = "";
        while (!existCat) {
            System.out.println("Digite el nombre de la nueva categoria: ");
            newCat = read.nextLine();
            for(Map.Entry<Integer, String> entry : categoriesMap.entrySet()){
                if (entry.getValue().toLowerCase() == newCat.toLowerCase()) {
                    System.out.println("La categoria ya existe, verifica");
                }else{ 
                    existCat = true;
                    break;
                }
            }
        }
        categoriesMap.put(categoriesMap.size() + 1, newCat);
        System.out.println("La nueva categoria " + newCat + " ha sido creada correctamente.");
    }

    public static void deleteCategory(){
        System.out.println("--- Eliminar Categoria ---");
        showCategories();
        int cat = catAvaliable();
        System.out.println("Vas a eliminar la categoria: " + categoriesMap.get(cat));
        System.out.println("Esto eliminará los productos de esa categoria, estas seguro?\n1. Si\n2. No");
        int option = verifyInt("1. Si\n2. No\n");
        if (option == 1) {
            Iterator<Map.Entry<Integer, Producto>> iterator = productsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Producto> entry = iterator.next();
                if (entry.getValue().getCategory() == cat) {
                    iterator.remove();
                }
            }
        }else{
            System.out.println("Volveras al menu principal");
        }
    }

    public static void showProducts(){
        System.out.println("--- Ver productos por categoria ---");
        showCategories();
        int cat = catAvaliable();
        boolean foundProducts = false;
        System.out.println("Categoria: " + categoriesMap.get(cat));
        for(Map.Entry<Integer, Producto> entry : productsMap.entrySet()){
            if (entry.getValue().getCategory() == cat) {
                System.out.println(entry.getValue().toString());
                foundProducts = true;
            }
        }
        if (!foundProducts) {
            System.out.println("La categoria no tiene productos asignados");
        }
    }

    public static void showCategories(){
        System.out.println("--- Categorias disponibles ---");
        for(Map.Entry<Integer, String> entry : categoriesMap.entrySet()){
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static int verifyInt(String message){
        boolean isValid = false;
        int number = 0;
        while (!isValid) {
            System.out.print(message);
            try {
                number = read.nextInt();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println(" - ERROR, digita solo enteros");
                read.nextLine();
            }
        }
        return number;
    }

    public static double verifyDouble(String message){
        boolean isValid = false;
        double number = 0;
        while (!isValid) {
            System.out.print(message);
            try {
                number = read.nextDouble();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println(" - ERROR, digita solo enteros o decimales");
                read.nextLine();
            }
        }
        return number;
    }

    public static int catAvaliable(){
        boolean refAvaliable = false;
        int cat = 0;
        while (!refAvaliable) {
            cat = verifyInt("Ingrese la categoria :");
            if (!categoriesMap.containsKey(cat)) {
                System.out.println("La categoria no existe, verifica.");
                read.nextLine();
            }else{ refAvaliable = true; }
        }
        return cat;
    }

    public static int refProdAvaliable(){
        boolean refAvaliable = false;
        int ref = 0;
        while (!refAvaliable) {
            ref = verifyInt("Ingrese la referencia del producto: ");
            if (productsMap.containsKey(ref)) {
                System.out.println("La referencia esta asignada a otro producto, verifica");
                read.nextLine();
            }else{ refAvaliable = true; }
        }
        return ref;
    }

    public static int existProd(){
        boolean existRef = false;
        int refProd = 0;
        while (!existRef) {
            refProd = verifyInt("Ingresa la referencia del producto: ");
            if (!productsMap.containsKey(refProd)) {
                System.out.println("El producto no existe, verifica.");
                read.nextLine();
            }else{ existRef = true; }
        }
        return refProd;
    }

    public static void chargeData(){
        categoriesMap.put(1, "Computadores");
        categoriesMap.put(2, "Celulares");
        categoriesMap.put(3, "Electrodomésticos");
        categoriesMap.put(4, "TV");
        categoriesMap.put(5, "Accesorios");
        categoriesMap.put(6, "Videojuegos");
        categoriesMap.put(7, "Audio y video");

        Producto prod1 = new Producto(1, "audifonos", 1, 10, 25000);
        Producto prod2 = new Producto(2, "Teclado", 1, 10, 47500);
        productsMap.put(1, prod1);
        productsMap.put(2, prod2);
    }
}
