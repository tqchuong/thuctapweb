    package fit.hcmuaf.edu.vn.foodmart.Cart;

    import fit.hcmuaf.edu.vn.foodmart.model.OrderItem;
    import fit.hcmuaf.edu.vn.foodmart.model.Products;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.concurrent.atomic.AtomicReference;

    public class Cart {
        Map<Integer, CartProduct> data = new HashMap<>();

        public boolean add(Products p, int quantity) {
            if (data.containsKey(p.getID())) {
                // Nếu sản phẩm đã tồn tại, tăng số lượng hiện tại lên
                update(p.getID(), data.get(p.getID()).getQuantity() + quantity);
            } else {
                // Nếu sản phẩm chưa tồn tại, thêm mới với số lượng chỉ định
                CartProduct cartProduct = convert(p);
                cartProduct.setQuantity(quantity);

                // Sử dụng giá đã giảm
                if (p.getIsSale() ==1) {
                    double discountedPrice = p.getPrice() * (1 - p.getDiscountPercentage() / 100);
                    cartProduct.setPrice(discountedPrice); // Sử dụng giá đã giảm
                } else {
                    cartProduct.setPrice(p.getPrice()); // Sử dụng giá gốc
                }

                data.put(p.getID(), cartProduct);
            }
            return true;
        }

        public int getProductTypesCount() {
            return data.size(); // Đếm số key trong Map, tương ứng với số loại sản phẩm
        }

        public int getTotalQuantity() {
            return data.values().stream()
                    .mapToInt(CartProduct::getQuantity)
                    .sum();
        }

        public boolean update(int id, int quantity) {

                if (!data.containsKey(id) || quantity < 1) return false;

                CartProduct cartProduct = data.get(id);
                cartProduct.setQuantity(quantity);
                data.put(id, cartProduct);
                return true;
            }

        public boolean remove(int id) {
            if (!data.containsKey(id)) return false;

            data.remove(id);
            return true;
        }
        public Double getTotalAmount() {
            AtomicReference<Double> total = new AtomicReference<>(Double.valueOf(0.0));

            data.values().stream().forEach(cartProduct ->
                    total.updateAndGet(v -> v + (cartProduct.getQuantity() * cartProduct.getPrice()))
            );

            return total.get();
        }
        public List<CartProduct> getlist() {
            return new ArrayList<>(data.values());
        }

        private CartProduct convert(Products p) {
            CartProduct re = new CartProduct();
            re.setId(p.getID());
            re.setProductName(p.getProductName());
            re.setPrice(p.getPrice());
            re.setImageURL(p.getImageURL());
            re.setWeight(p.getWeight());
            re.setQuantity(1);
            return re;
        }

    }
