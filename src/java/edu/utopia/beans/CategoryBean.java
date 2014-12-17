/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Category;
import edu.utopia.model.CategoryEJB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "CategoryBean")
@RequestScoped
public class CategoryBean implements Serializable {

    @EJB
    private CategoryEJB catEjb;
    private Category category;
    private int catSize;
    private String successMessage;

    public CategoryBean() {
        this.category = new Category();
    }

    public CategoryEJB getCatEjb() {
        return catEjb;
    }

    public void setCatEjb(CategoryEJB catEjb) {
        this.catEjb = catEjb;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCatSize() {
        return this.catEjb.findAllCategories().size();
    }

    public void setCatSize(int catSize) {
        this.catSize = catSize;
    }

    //method to create categories..
    public String postCategory() {
        Category addedCategory = this.catEjb.createCategory(category);
        //clear the form after submit successfully
        category.setCategoryName("");
        category.setCategoryDescription("");
        this.successMessage = "Category Added SucessFully";
        this.category = null;
        return "addCategory";
    }

    public List<Category> getCategories() {
        catSize = (int) this.catEjb.findAllCategories().size();
        System.out.println("size of the category list is ........ " + catSize);
        return this.catEjb.findAllCategories();
    }

    public String deleteAction(Category cat) {
        this.catEjb.deleteCategory(cat);
        return null;
    }
}
