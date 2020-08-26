package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.configuration.SwaggerConfig;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(tags = { SwaggerConfig.TAG_1 })
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Produits
    @GetMapping(value = "Produits")
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    //Produits/{id}
    @ApiOperation(value= "Recupere un produit selon son ID")
    @GetMapping(value = "Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) throws ResponseStatusException {
        Product product = productDao.findById(id);
        if (product == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le produit avec l'id " + id + " n'existe pas");
        return product;
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product product1 = productDao.save(product);
        if (product == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        productDao.deleteById(id);
    }

    @GetMapping(value = "test/produits/{prixLimit}")
    public List<Product> testDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }


    @GetMapping(value = "testName/produits/{recherche}")
    public List<Product> testeDeRequetesName(@PathVariable String recherche) {
        return productDao.findByNomLike("%" + recherche + "%");
    }
}
