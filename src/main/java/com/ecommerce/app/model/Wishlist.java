package com.ecommerce.app.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public Wishlist(int id, User user, Date createdDate, Product product) {
        this.id = id;
        this.user = user;
        this.createdDate = createdDate;
        this.product = product;
    }

    public Wishlist() {
    }

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
