package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.admin.ProductAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.model.Warehouse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;

@WebServlet("/addProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ request
        String action = request.getParameter("action");
        String productName = request.getParameter("productName");
        String category = request.getParameter("categoryID");
        boolean isSale = "1".equals(request.getParameter("IsSale"));
        double discountPercentage = Double.parseDouble(request.getParameter("DiscountPercentage"));
        String priceParam = request.getParameter("price");
        String weight = request.getParameter("weight");
        String shortDescription = request.getParameter("shortDescription");
        String idParam = request.getParameter("id");
        String quantityParam = request.getParameter("quantity");
        String brands = request.getParameter("brandsID");

        // Xử lý dữ liệu
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;
        int categoryID = (category != null && !category.isEmpty()) ? Integer.parseInt(category) : 0;
        double price = (priceParam != null && !priceParam.isEmpty()) ? Double.parseDouble(priceParam) : 0.0;
        int Weight = (weight != null && !weight.isEmpty()) ? Integer.parseInt(weight) : 0;
        int quantity = (quantityParam != null && !quantityParam.isEmpty()) ? Integer.parseInt(quantityParam) : 0;
        int brandsID = (brands != null && !brands.isEmpty()) ? Integer.parseInt(brands) : 0;

        // Tính giá bán sau giảm
        if (isSale && discountPercentage > 0) {
            price = price - (price * discountPercentage / 100);
        } else {
            discountPercentage = 0;
        }

        // Xử lý upload ảnh
        String uploadPath = getServletContext().getRealPath("/") + "image/img-khoai1";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String imageURL = null;
        Part filePart = request.getPart("up-hinh-anh");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            imageURL = "image/img-khoai1/" + fileName;
        }

        // DAO
        ProductAdminDAO productDao = new ProductAdminDAO();
        boolean isSuccess = false;

        if ("add".equals(action)) {
            // Tạo đối tượng sản phẩm
            Products newProduct = new Products();
            newProduct.setProductName(productName);
            newProduct.setCategoryID(categoryID);
            newProduct.setBrandID(brandsID);
            newProduct.setIsSale(isSale ? 1 : 0);
            newProduct.setDiscountPercentage(discountPercentage);
            newProduct.setPrice(price);
            newProduct.setWeight(Weight);
            newProduct.setShortDescription(shortDescription);
            if (imageURL != null) newProduct.setImageURL(imageURL);

            // Gán warehouse
            Warehouse warehouse = new Warehouse();
            warehouse.setQuantity(quantity);
            newProduct.setWarehouse(warehouse);




            // Thêm sản phẩm + nhập kho
            isSuccess = productDao.addProduct(newProduct);
            response.sendRedirect("admin.jsp#");

        } else if ("edit".equals(action)) {
            Products existingProduct = productDao.getProductById(id);
            if (existingProduct != null) {
                existingProduct.setProductName(productName);
                existingProduct.setCategoryID(categoryID);
                existingProduct.setPrice(price);
                existingProduct.setIsSale(isSale ? 1 : 0);
                existingProduct.setDiscountPercentage(discountPercentage);
                existingProduct.setBrandID(brandsID);
                existingProduct.setWeight(Weight);
                existingProduct.setShortDescription(shortDescription);
                if (imageURL != null) existingProduct.setImageURL(imageURL);

                if (quantity > 0) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setProductId(existingProduct.getID());
                    warehouse.setQuantity(quantity);
                    existingProduct.setWarehouse(warehouse);
                }

                boolean isUpdated = productDao.updateProduct(existingProduct);

                response.sendRedirect("admin.jsp#");
            } else {
                response.sendRedirect("admin.jsp#");
            }
        }
    }
}
