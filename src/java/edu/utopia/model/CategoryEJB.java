/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Category;
import edu.utopia.facades.CategoryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class CategoryEJB 
{
    @EJB
    private CategoryFacade categoryFacade;
    
    public @NotNull Category createCategory(@NotNull Category category)
    {
        this.categoryFacade.create(category);
        return category;
    }
    
    public Category findById(@NotNull Long id)
    {
        return this.categoryFacade.find(id);
    }
    
    public List<Category> findAllCategories()
    {
        return this.categoryFacade.findAll();
    }
    
    public void updateCategory(@NotNull Category category)
    {
        this.categoryFacade.edit(category);
    }
    public void deleteCategory(@NotNull Category category)
    {
        this.categoryFacade.remove(category);
    }
}
