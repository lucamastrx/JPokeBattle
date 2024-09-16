package it.jpokebattle.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
 * Classe di utilità per il caricamento e la manipolazione di vari tipi di file esterni.
 * Questa classe fornisce metodi per leggere e scrivere file JSON e SER, caricare file FXML, immagini e file multimediali,
 * oltre a funzioni di utilità per gestire i percorsi e le risorse dei file.
 */
public class FileManager {

    // accesso al file resources.properties contenente i percorsi  a tutti i file
    private final static ResourceBundle src = ResourceBundle.getBundle("it/jpokebattle/properties/resources");
    private final static File dataDir = new File(System.getProperty("user.dir"), "data");
    private final static String ROOT_PATH = "/it/jpokebattle/";
    private static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()) // Registra il modulo per gestire LocalDateTime
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Evita di scrivere le date come timestamp




    /**
     * Legge un file JSON e restituisce una mappa degli oggetti.
     *
     * @param jsonSrcName nome della risorsa del file JSON
     * @param valueType   classe dei valori contenuti nella mappa
     * @param <K>         tipo della chiave nella mappa
     * @param <V>         tipo del valore nella mappa
     * @return            una mappa con i dati letti dal file JSON
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
     * @param map         mappa da scrivere nel file
     * @param jsonSrcName nome della risorsa del file JSON
     * @param <K>         tipo della chiave nella mappa
     * @param <V>         tipo del valore nella mappa
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
                return; // Termina l'esecuzione se la creazione della directory fallisce
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
     * Carica una mappa da un file serializzato (.ser).
     *
     * @param serSrcName nome della risorsa del file .ser
     * @param <V>        tipo del valore nella mappa
     * @return           una mappa con i dati letti dal file serializzato
     */
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
     * Scrive una mappa su un file serializzato (.ser).
     *
     * @param map        mappa da scrivere
     * @param serSrcName nome della risorsa del file .ser
     * @param <V>        tipo del valore nella mappa
     */
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



    /**
     * Svuota il contenuto di un file serializzato (.ser).
     *
     * @param serSrcName nome della risorsa del file da svuotare
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
     * Restituisce il percorso assoluto di un file a partire dal nome della risorsa.
     *
     * @param fileSrcName nome della risorsa del file
     * @return            percorso assoluto del file
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
     * Restituisce l'URL di una risorsa sotto forma di stringa.
     *
     * @param fileSrcName nome della risorsa
     * @return            URL della risorsa come stringa
     */
    public static String getResource(String fileSrcName) {
        String path = ROOT_PATH + src.getString(fileSrcName);
        return FileManager.class.getResource(path).toString();
    }

    /**
     * Carica un file FXML a partire dal nome della risorsa.
     *
     * @param fxmlSrcName nome della risorsa del file FXML
     * @return            un FXMLLoader contenente la scena caricata
     */
    public static FXMLLoader loadFXML(String fxmlSrcName) {
        String path =  ROOT_PATH + src.getString(fxmlSrcName);

        return new FXMLLoader(FileManager.class.getResource(path));
    }

    /**
     * Carica un'immagine a partire dal nome della risorsa.
     *
     * @param imageSrcName nome della risorsa dell'immagine
     * @return             l'immagine caricata
     */
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
     * Carica un file audio MP3 e restituisce un MediaPlayer.
     *
     * @param mediaSrcName nome della risorsa del file audio
     * @return             un oggetto MediaPlayer che riproduce l'audio
     */
    public static MediaPlayer loadMP3(String mediaSrcName) {
        String path = ROOT_PATH + src.getString(mediaSrcName);
        Media media = new Media(FileManager.class.getResource(path).toExternalForm());
        return new MediaPlayer(media);
    }


}