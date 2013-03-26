package com.sic.controller;

import com.sic.entity.Aseguradora;
import com.sic.controller.util.JsfUtil;
import com.sic.controller.util.PaginationHelper;
import com.sic.facade.AseguradoraFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "aseguradoraController")
@SessionScoped
public class AseguradoraController implements Serializable {

    private Aseguradora current;
    private DataModel items = null;
    @EJB
    private com.sic.facade.AseguradoraFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AseguradoraController() {
    }

    public Aseguradora getSelected() {
        if (current == null) {
            current = new Aseguradora();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AseguradoraFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public void prepareView() {
        current = (Aseguradora) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       
    }

    public void prepareCreate() {
        current = new Aseguradora();
        selectedItemIndex = -1;
       
    }

    public void create() {
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AseguradoraCreated"));
           
        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            
        }
    }

    public void prepareEdit() {
        current = (Aseguradora) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        
    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AseguradoraUpdated"));
           
        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
           
        }
    }

    public void destroy() {
        current = (Aseguradora) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        
    }

    public void destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            
        } else {
            // all items were removed - go back to list
            recreateModel();
           
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AseguradoraDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public List<Aseguradora> autocomplete(String query) {
        List<Aseguradora> sugerencias = new ArrayList<Aseguradora>();
        for (Aseguradora x : ejbFacade.findAll()) {
            if (x.getNombreAseguradora().startsWith(query.toUpperCase())) {
                sugerencias.add(x);
            }
        }
        return sugerencias;
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public void next() {
        getPagination().nextPage();
        recreateModel();
       
    }

    public void previous() {
        getPagination().previousPage();
        recreateModel();
        
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
     public SelectItem[] getItemsSelect() {
        return JsfUtil.getSelectItems(ejbFacade.findAll());
    }

    @FacesConverter(forClass = Aseguradora.class)
    public static class AseguradoraControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AseguradoraController controller = (AseguradoraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aseguradoraController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Aseguradora) {
                Aseguradora o = (Aseguradora) object;
                return getStringKey(o.getIdAseguradora());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AseguradoraController.class.getName());
            }
        }
    }
}
