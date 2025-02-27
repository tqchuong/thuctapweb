package fit.hcmuaf.edu.vn.foodmart.service;


import fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Products;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductService {
    private static ProductDAO productDAO = new ProductDAO();

    // Phương thức lấy tất cả sản phẩm
    public List<Products> getAllProducts() {
        return productDAO.getAllProducts();
    }
    public List<Products> getFlashSaleProducts() {
        ProductDAO productDAO = new ProductDAO();
        // Sử dụng ProductDAO để lấy danh sách sản phẩm có isSale = true
        List<Products> products = productDAO.getAllProducts(); // Lấy tất cả sản phẩm
        List<Products> flashSaleProducts = new ArrayList<>();

        for (Products product : products) {
            if (product.getIsSale()==1) {
                flashSaleProducts.add(product);
            }
        }

        return flashSaleProducts;
    }
    // Phương thức lấy sản phẩm theo danh mục
    public List<Products> getProductsByCategory(String categoryName) {
        return productDAO.getProductsByCategory(categoryName);
    }

    // Phương thức lấy chi tiết sản phẩm theo ID
    public Products getProductDetailsById(int productId) {
        // Gọi phương thức trong DAO để lấy chi tiết sản phẩm
        return productDAO.getProductDetailsById(productId);
    }

    // Phương thức lấy trung bình rating của sản phẩm
    public double getAverageRating(int productId) {
        return productDAO.getAverageRating(productId);
    }
    // Phương thức lấy danh sách sản phẩm liên quan ngẫu nhiên
    public List<Products> getRandomRelatedProducts(Products product) {
        int categoryId = product.getCategory().getCategoryID();
        List<Products> relatedProducts = productDAO.getProductsByCategory(categoryId);

        // Loại bỏ sản phẩm hiện tại khỏi danh sách sản phẩm liên quan
        relatedProducts.removeIf(p -> p.getID() == product.getID());

        Collections.shuffle(relatedProducts);
        if (relatedProducts.size() >= 2) {
            return relatedProducts.subList(0, 2);
        } else {
            return relatedProducts;
        }
    }
}


