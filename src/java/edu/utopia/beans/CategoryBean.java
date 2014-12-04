/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Category;
import edu.utopia.model.CategoryEJB;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "CategoryBean")
@SessionScoped
public class CategoryBean implements Serializable
{

    @EJB
    private CategoryEJB catEjb;
    private Category category;
    private List<Category> categories;

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
    public String postCategory() {
        Category addedCategory = this.catEjb.createCategory(category);
        //clear the form after submit successfully
        category.setCategoryName("");
        category.setCategoryDescription("");
        return "addCategory";
    }

    public List<Category> getCategories() {
        return this.catEjb.findAllCategories();
    }

    public String editAction(Category category) {
        category.setEditable(true);
        catEjb.updateCategory(category);
        System.out.println("test.......");
        return null;
    }
    
    public String saveAction()
    {
        //List cats = (List<Category>)this.getCategories(); 
        List cats = categories;
        Iterator citer = categories.iterator();
        while(citer.hasNext())
        {
           Category cat = (Category)citer.next();
           cat.setEditable(false);
           this.catEjb.updateCategory(cat);
        }
        
        return null;
    }
    public String deleteAction(Category cat)
    {
        this.catEjb.deleteCategory(cat);
        return null;
    }
}
