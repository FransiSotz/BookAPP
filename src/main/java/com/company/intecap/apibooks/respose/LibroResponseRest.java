package com.company.intecap.apibooks.respose;

public class LibroResponseRest extends ResponseRest { // ResponseRest: contiene toda la estructura de  la metada la respuesta de la api


    private LibroResponse libroResponse = new LibroResponse(); // devolvera una lista de libros en formato json con la estructura de la clase LibroResponse y la metadata de la clase ResponseRest

    public LibroResponse getLibroResponse() {
        return libroResponse;
    }

    public void setLibroResponse(LibroResponse libroResponse) {
        this.libroResponse = libroResponse;
    }

}
