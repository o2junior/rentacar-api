package br.com.rentacar.rentacarapi.config.adapter;

public interface Adapter<Source, Destination> {

    Destination adapt(Source source);

    Destination adapt(Source source, Destination destination);

}
