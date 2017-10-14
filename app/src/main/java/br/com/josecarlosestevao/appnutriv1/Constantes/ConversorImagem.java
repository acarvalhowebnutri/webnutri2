package br.com.josecarlosestevao.appnutriv1.Constantes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Dell on 23/03/2017.
 */
public class ConversorImagem {

    public static byte[] converteBitmapPraByteArray(Bitmap imagem, int qualidadeDaImagem){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, qualidadeDaImagem, stream);
        return stream.toByteArray();

    }

    public static Bitmap converteByteArrayPraBitmap(byte[] imagem){
        return BitmapFactory.decodeByteArray(imagem,0,imagem.length);
    }
}
