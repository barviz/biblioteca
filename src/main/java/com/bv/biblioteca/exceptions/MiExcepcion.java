package com.bv.biblioteca.exceptions;

/*se crea esta clase para diferenciar los errores de nuestra lógica de negocios
de los que ocurren en el sistema*/
public class MiExcepcion extends Exception {

    public MiExcepcion(String msg) {
        super(msg);
    }
}
