package com.company.intecap.apibooks.respose;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {
    // ResponseRest: información para el cliente de como respondio el servicio. metadata: información de la respuesta. data: información de la respuesta.
    // HashMap: es una estructura de datos que almacena pares de valores, donde cada valor tiene una clave única. HashMap no permite valores duplicados, pero permite claves duplicadas.
    // ArrayList: es una estructura de datos que almacena una colección de objetos. ArrayList permite valores duplicados.

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>(); // clave: valor

    public ArrayList<HashMap<String, String>> getMetadata() {
        // getMetadata: obtiene la información de la respuesta.
        return metadata;
    }

    public void setMetadata(String tipo, String codigo, String dato) {
        // setMetadata: establece la información de la respuesta.
        HashMap<String, String> mapa = new HashMap<String,String>();
        mapa.put("tipo", tipo); // tipo: ok
        mapa.put("codigo", codigo); // codigo: 200
        mapa.put("dato", dato); // dato: respondio correctamente
        metadata.add(mapa);
    }
}
