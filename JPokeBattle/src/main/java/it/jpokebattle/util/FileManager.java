package it.jpokebattle.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.jpokebattle.model.area.AreaData;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe di utilità per il caricamento e la manipolazione di qualsiasi file esterno.
 */
public class FileManager {
    // accesso al file resources.properties contenente i path a tutti i file
    private final static ResourceBundle src = ResourceBundle.getBundle("it/jpokebattle/properties/resources");
    private final static File dataDir = new File(System.getProperty("user.dir"), "data");
    private final static String ROOT_PATH = "/it/jpokebattle/";
    private static Gson gson = new Gson();
    private static HashMap<String, AreaData> areaDataMap = setAreasMap();


    public static HashMap<String, AreaData> getAreaDataMap() {
        return areaDataMap;
    }


    /**
     *
     */
    private static HashMap<String, AreaData> setAreasMap() {
        HashMap<String, AreaData> dataMap = new HashMap<>();
        String path = getAbsPath("json.areas");
        Type areaListType = new TypeToken<List<AreaData>>() {}.getType();

        try {
            FileReader reader = new FileReader(path);
            List<AreaData> areasList = gson.fromJson(reader, areaListType);
            reader.close();

            for (AreaData area : areasList)
                dataMap.put(area.name, area);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }





    /**
     * Svuota il contenuto di un file.
     *
     * @param serSrcName
     */
    public static void clearSER(String serSrcName) {
        String path = src.getString(serSrcName);
        File file = new File(dataDir, path);

        try (FileOutputStream fileOut = new FileOutputStream(file)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A partire dal nome di risorsa ottengo il path assoluto del file aggiungendo il percorso
     * a partire dalla radice del file e, successivamente, traducendo il path prima in URL,
     * poi in URI e in fine in un percorso vero e prorio.
     */
    public static String getAbsPath(String fileSrcName) {
        String absPath = ROOT_PATH + src.getString(fileSrcName);

        try {
            absPath = Paths.get(FileManager.class.getResource(absPath).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return absPath;
    }

    /**
     *  A partire dal nome di risorsa aggiungo il path dalla radice del programma e
     *  ritorno L'URL sotto forma di stringa.
     *
     * @return URL del file tradotto in stringa
     */
    public static String getResource(String fileSrcName) {
        String path = ROOT_PATH + src.getString(fileSrcName);
        return FileManager.class.getResource(path).toString();
    }

    /**
     * Restituisce un file fxml a partire dal nome della risorsa.
     *
     * @param fxmlSrcName   nome di risorsa del file
     * @return              FXMLLoader contenente la scena caricata
     * @see it.ml.jpokebattle.properties
     * */
    public static FXMLLoader loadFXML(String fxmlSrcName) {
        String path =  ROOT_PATH + src.getString(fxmlSrcName);

        return new FXMLLoader(FileManager.class.getResource(path));
    }

    /**
     * Restituisce un'immagine a partire dal nome della risorsa.
     *
     * @param imageSrcName  nome di risorsa del file
     * @return              immagine caricata
     * @see it.ml.jpokebattle.properties
     * */
    public static Image loadImage(String imageSrcName) {
        String path = ROOT_PATH + src.getString(imageSrcName);
        URL imageUrl = FileManager.class.getResource(path);

        if (imageUrl == null) {
            // Gestione errore: file non trovato
            throw new IllegalArgumentException("File non trovato: " + path);
        }

        return new Image(imageUrl.toExternalForm());
    }

    /**
     * Restituisce il media player di un file audio.
     *
     * @param mediaSrcName  nome di risorsa del file
     * @see it.ml.jpokebattle.properties
     */
    public static MediaPlayer loadMP3(String mediaSrcName) {
        String path = ROOT_PATH + src.getString(mediaSrcName);
        Media media = new Media(FileManager.class.getResource(path).toExternalForm());
        return new MediaPlayer(media);
    }

    /**
     * Restituisce una hashmap a partire da un file ser.
     * Ogni hashmap è composta da stringhe e oggetti.
     *
     * @param serSrcName    nome di risorsa del file
     * @return              hashmap caricata
     * @see it.ml.jpokebattle.properties
     * */
    public static <V> HashMap<String, V> loadSER(String serSrcName) {
        String path = src.getString(serSrcName);
        HashMap<String, V> map = null;

        File file = new File(dataDir, path);
        if (!file.exists())
            return null;
        if (file.length() == 0)
            return null;

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            map = (HashMap<String, V>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Scrive su un file ser una hashmap.
     * Ogni hashmap è composta da stringhe e oggetti.
     *
     * @param map           hashmap da scrivere
     * @param serSrcName    nome di risorsa del file
     * @see it.ml.jpokebattle.properties
     * */
    public static <V> void writeSER(HashMap<String, V> map, String serSrcName) {
        String path = src.getString(serSrcName);
        File file = new File(dataDir, path);

        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}