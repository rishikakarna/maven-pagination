package com.lti.training.dao;
// model are product and productdao

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lti.training.model.Product;

public class ProductDao {

	public List<Product> fetchProducts(int from, int to) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			if (conn == null) {
				System.out.println("Connection error");
			}
			// below query will return pagination effect
			String sql = "select * from (select p.*, rownum r from product_details p) where r between ? and ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, from);
			pstmt.setInt(2, to);

			rs = pstmt.executeQuery(); // select

			List<Product> products = new ArrayList<Product>();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getString("pid"));
				product.setName(rs.getString("pname"));
				product.setPrice(Double.parseDouble(rs.getString("price")));
				product.setQuantity(Integer.parseInt(rs.getString("pquantity")));
				products.add(product);
			}
			return products;
		}

		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();// not good ,should throw particular exception //will displayed in server
								// console
			return null; // bad return
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
}
