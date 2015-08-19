package com.gmartinsribeiro.recyclerviewapp.exception;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
public class NetworkException extends Exception{

    public NetworkException() {
    }

    public NetworkException(String detailMessage) {
        super(detailMessage);
    }
}
