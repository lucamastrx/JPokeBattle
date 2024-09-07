package it.jpokebattle.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Type;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;



/**
 * Classe di utilità per il caricamento e la manipolazione di qualsiasi file esterno.
 */
public class FileManager {
    // accesso al file resources.properties contenente i path a tutti i file
    private final static ResourceBundle src = ResourceBundle.getBundle("it/jpokebattle/properties/resources");
    private final static File dataDir = new File(System.getProperty("user.dir"), "data");
    private final static String ROOT_PATH = "/it/jpokebattle/";
    private static ObjectMapper objectMapper = new ObjectMapper();



    /**
     * Legge un file JSON e restituisce una mappa degli oggetti.
     *
     * @param jsonSrcName nome della risorsa del file JSON
     * @return            hashmap con i dati letti dal JSON
     */
    public static <K, V> HashMap<K, V> readJSON(String jsonSrcName, Class<V> valueType) {
        // Ottieni il percorso dal file di proprietà
        String relativePath = src.getString(jsonSrcName);
        File file = new File(relativePath);
        HashMap<K, V> map = new HashMap<>();

        try {
            // Controlla se il file esiste prima di leggerlo
            if (!file.exists()) {
                System.err.println("File non trovato: " + file.getAbsolutePath());
                return map;
            }

            // Usa una TypeReference specifica per deserializzare la mappa
            map = objectMapper.readValue(file, new TypeReference<HashMap<K, V>>() {
                @Override
                public Type getType() {
                    return TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, valueType);
                }
            });

            // System.out.println("Dati letti con successo da " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON.");
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Scrive una mappa di oggetti su un file JSON.
     *
     * @param map         hashmap da scrivere
     * @param jsonSrcName nome della risorsa del file JSON
     */
    public static <K, V> void writeJSON(HashMap<K, V> map, String jsonSrcName) {
        // Ottieni il percorso dal file di proprietà
        String relativePath = src.getString(jsonSrcName);
        File file = new File(relativePath);

        // Assicurati che la directory esista
        if (!file.getParentFile().exists()) {
            boolean dirsCreated = file.getParentFile().mkdirs();
            if (!dirsCreated) {
                System.err.println("Errore nella creazione delle directory: " + file.getParentFile().getAbsolutePath());
            }
        }

        try {
            // Controlla se la mappa è vuota
            if (map == null || map.isEmpty()) {
                System.err.println("La mappa è vuota o null.");
                return;
            }

            // Scrivi i dati nel file JSON
            objectMapper.writeValue(file, map);
            // System.out.println("Mappa salvata con successo su " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file JSON.");
            e.printStackTrace();
        }
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
     * @see it.jpokebattle.properties
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
     * @see it.jpokebattle.properties
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
     * @see it.jpokebattle.properties
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
     * @see it.jpokebattle.properties
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
     * @see it.jpokebattle.properties
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