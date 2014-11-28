/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Category;
import edu.utopia.facades.CategoryFacade;
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
    private CategoryFacade categoryFacade;
    private Category category;
    
    /**
     * Creates a new instance of CategoryBean
     */
    public CategoryBean() {
        this.category = new Category();
    }

    public CategoryFacade getCategoryFacade() {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
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
        this.categoryFacade.create(category);
        //clear the form after submit successfully
        category.setCategoryName("");
        category.setCategoryDescription("");
        return "addCategory";
    }
    public List<Category> getCategories()
    {
        return this.categoryFacade.findAll();
    }
    
}
