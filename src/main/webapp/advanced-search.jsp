
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="advanced-search">
    <div class="container">
        <div class="advanced-search-category">
            <span>Phân loại </span>
            <select id="advanced-search-category-select" name="" onchange="searchProducts()">
                <option >Tất cả</option>
                <option value="1">Gạo</option>
                <option value="2">Bắp</option>
                <option value="3">Khoai</option>
                <option value="4">Khác</option>
            </select>
        </div>
        <div class="advanced-search-price">
            <span>Giá từ</span>
            <input id="min-price" onchange="searchProducts()" placeholder="tối thiểu" type="number">
            <span>đến</span>
            <input id="max-price" onchange="searchProducts()" placeholder="tối đa" type="number">
            <button id="advanced-search-price-btn"><i class="fa-solid fa-magnifying-glass-dollar"></i></button>
        </div>
        <div class="advanced-search-control">
            <button id="sort-ascending" onclick="searchProducts(1)"><i class="fa-solid fa-arrow-up-short-wide"></i>
            </button>
            <button id="sort-descending" onclick="searchProducts(2)"><i
                    class="fa-solid fa-arrow-down-wide-short"></i>
            </button>
            <button id="reset-search" onclick="searchProducts(0)"><i class="fa-solid fa-arrow-rotate-right"></i>
            </button>
            <button onclick="closeSearchAdvanced()"><i class="fa-solid fa-xmark"></i></button>
        </div>
    </div>
</div>
