package com.sic.controller;

import com.sic.entity.Usuario;
import com.sic.controller.util.JsfUtil;
import com.sic.controller.util.PaginationHelper;
import com.sic.entity.Event;
import com.sic.facade.UsuarioFacade;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.codec.digest.DigestUtils;
import org.xml.sax.SAXException;

@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private DataModel items = null;
    private Usuario user;
    private String idUser;
    private String passUser;
    private String currentUI;
    @EJB
    private com.sic.facade.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int loginIntents = 0;
    private Calendar calendar = new GregorianCalendar();

    ;

    public UsuarioController() {
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
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

    public void prepareList() {
        recreateModel();

    }

    public void prepareView() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;

    }

    public void create() {
        String encriptpass = DigestUtils.sha256Hex(current.getContrasenaUsuario());
        current.setContrasenaUsuario(encriptpass);
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));

        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void destroy() {
        current = (Usuario) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getCurrentUI() {
        return currentUI;
    }

    public void setCurrentUI(String currentUI) {
        this.currentUI = currentUI;
    }

    public void login() {
        if (loginIntents == 4) {
            try {
                Usuario bloqueado = getFacade().find(idUser);
                bloqueado.setEstadoUsuario(Boolean.FALSE);
                getFacade().edit(bloqueado);
                Event event = new Event(calendar.getTime(), "Intento de acceso", idUser,
                        "Se intento acceder a la cuenta de " + idUser
                        + ", la cuenta esta bloqueada", "Usuario");
                JsfUtil.writeLog(event);
                JsfUtil.addErrorMessage("Cuenta Bloqueada");
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            }

        } else {
            String encriptpass = DigestUtils.sha256Hex(passUser);
            Object[] parameters = {"idUsuario", getIdUser(), "contrasenaUsuario", encriptpass};
            try {
                setUser(getFacade().getSingleResult("Usuario.findByLogin", parameters));
                if (getUser().getEstadoUsuario()) {
                    Event event = new Event(calendar.getTime(), "Acceso al Sistema", idUser,
                            "Usuario " + idUser + " ingreso correctamente al sistema", "Usuario");
                    JsfUtil.writeLog(event);
                    JsfUtil.putSession(user, "usuarioActual");
                    setCurrentUI("UI/Home/calendar.xhtml");
                    JsfUtil.redirect("faces/home.xhtml");
                } else {
                    setUser(null);
                    JsfUtil.addErrorMessage("Cuenta Bloqueada, contactar al Administrador");
                    Event event = new Event(calendar.getTime(), "Bloqueo de cuenta", idUser,
                            "Se intento acceder 5 veces a la cuenta de " + idUser
                            + ", la cuenta ha sido bloqueada", "Usuario");
                    JsfUtil.writeLog(event);
                }
            } catch (Exception ex) {
                loginIntents++;
                setUser(null);
                JsfUtil.addErrorMessage(ex, "Usuario No Válido, Intentos restantes: ".concat(String.valueOf(5 - loginIntents)));
                Event event = new Event(calendar.getTime(), "Intento de Acceso", idUser,
                        "Se intento acceder a la cuenta de " + idUser
                        + " con password equivocado", "Usuario");
                try {
                    JsfUtil.writeLog(event);
                } catch (JAXBException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (FileNotFoundException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (SAXException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (ParserConfigurationException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (IOException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (TransformerConfigurationException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (TransformerException ex1) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    public void logout() {
        //setUser(null);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            JsfUtil.redirect("index.xhtml");
        } catch (IOException ex) {
            JsfUtil.addErrorMessage(ex, null);
        }
    }

    public void checkLogin() {
        if (getUser() != null) {
            try {
                JsfUtil.redirect("faces/home.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage(ex, null);
            }
        }
    }

    public void checkLogout() {
        if (getUser() == null) {
            try {
                JsfUtil.redirect("faces/index.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage(ex, null);
            }
        }
    }

    public void updateUI(ActionEvent event) {
        String ui = (String) event.getComponent().getAttributes().get("uiName");
        setCurrentUI("UI/" + ui + ".xhtml");
    }

    public void goHome() {
        try {
            JsfUtil.redirect("../../faces/home.xhtml");
        } catch (IOException ex) {
            //Error
        }
    }

    public void imprimir(ActionEvent event) {
        String tabla = (String) event.getComponent().getAttributes().get("tabla");
        try {
            JsfUtil.redirect("UI/Print/" + tabla + ".xhtml");
        } catch (IOException ex) {
            //Error
        }
    }

    public Locale getLocale() {
        return Locale.US;
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getIdUsuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsuarioController.class.getName());
            }
        }
    }
}
