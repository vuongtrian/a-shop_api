package com.webapi.ashop.entity;

import com.webapi.ashop.entity.enumObj.ProductSize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String color;
    private ProductSize size;
    private int quantity;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn()
    List<FileData> images;
}
