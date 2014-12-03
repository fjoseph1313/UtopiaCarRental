/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Category;
import edu.utopia.model.CategoryEJB;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "CategoryBean")
@RequestScoped
public class CategoryBean
{
    @EJB
    private CategoryEJB catEjb;
    private Category category;
    
    /**
     * Creates a new instance of CategoryBean
     */
    public CategoryBean() {
        this.category = new Category();
    }

    public CategoryEJB getCatEjb() {
        return catEjb;
    }

    public void setCatEjb(CategoryEJB catEjb) {
        this.catEjb = catEjb;
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    //method to create categories..
    public String postCategory()
    {
        Category addedCategory = this.catEjb.createCategory(category);
        //clear the form after submit successfully
        category.setCategoryName("");
        category.setCategoryDescription("");
        return "addCategory";
    }
    public List getCategories()
    {
        return this.catEjb.findAllCategories();
    }
    
}
