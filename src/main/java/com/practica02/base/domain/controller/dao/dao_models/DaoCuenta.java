package com.practica02.base.domain.controller.dao.dao_models;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Cuenta;

public class DaoCuenta extends AdapterDao<Cuenta> {

    public Cuenta obj;
    private LinkedList<Cuenta> listaCuentaes;

    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoCuenta() {
        super(Cuenta.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Cuenta getObj() {
        if (obj == null) {
        this.obj = new Cuenta();
            
        }
        return this.obj;
    }

    public void setObj(Cuenta obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;

        } catch (Exception e) {
            //LOG DE ERRORES
            
            return false;
            // TODO: handle exception
        }

    }

    public Boolean updte(Integer pos) {
        try {
            this.update(obj, pos);
            return true;

        } catch (Exception e) {
            //LOG DE ERRORES
            
            return false;
            // TODO: handle exception
        }

    }

    public LinkedList<Cuenta> getListAll(){
        if ( listaCuentaes == null) {
            listaCuentaes = listAll();
        }
        return listaCuentaes;
    }



    public static void main(String[] args) {
        /*DaoBanda da = new DaoBanda();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Juan");
        da.getObj().setFecha(java.sql.Date.valueOf("2023-10-10"));
        //da.getObj().setApellido("Pérez Gonzales");
        //da.getObj().setNacionalida("Argentina");*/

        DaoCuenta da = new DaoCuenta();
        da.getObj().setId(da.listAll().getLength() + 1);

        da.getObj().setGmail("gmail");
        da.getObj().setClave("8575");
        da.getObj().setEstado(true);
        
        

        if(da.save()){
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }
        System.out.println("NUMERO EN LISTA: " +da.getListAll().getLength());

    }


    /*1. Cuenta

a. Crear el registro

b. Modificar

c. Listar */
    
    
}



//Cuenta

/*    private String nombre;
    private String duracion;
    private String id_genero;
    private Integer url;
    private TipoArchivoEnum tipo;
    private Integer id_album; */