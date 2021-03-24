package com.thiagoluigi.viajabessaandroidchallenge;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Package implements Serializable {

    @SerializedName("pacote")
    private String name;
    @SerializedName("valor")
    private float value;
    @SerializedName("link_imagem")
    private String imageLink;
    @SerializedName("pacote_id")
    private int id;
    @SerializedName("descricao")
    private String description;
    @SerializedName("localizacao")
    private String localImage;

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public String getValueTxt() {
        return "R$" + value;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalImage() {
        return localImage;
    }

    @Override
    public String toString() {
        return "Pacote: " + name +
                "\nValor: " + value +
                "\nLink da imagem: " + imageLink +
                "\nId do pacote: " + id +
                "\nDescrição: " + description +
                "\nLocalização: " + localImage + "\n";
    }

}
