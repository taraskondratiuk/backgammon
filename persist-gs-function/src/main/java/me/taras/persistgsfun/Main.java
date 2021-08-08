package me.taras.persistgsfun;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import me.taras.lib.model.GameFullData;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import java.util.stream.*;

public class Main implements HttpFunction {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    private static final Gson gson = new Gson();
    
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
    
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(System.getenv("GCLOUD_PROJECT"))
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        Firestore db = firestoreOptions.getService();
    
        CollectionReference games = db.collection("games");
    
        Query query1 = games.whereEqualTo("finished", true);
        Query query2 = games.whereLessThan("createdAt", System.currentTimeMillis() - 60 * 1000 * 60); // 1 hour
    
       List<GameFullData> gamesToPersist = deleteAndReturnGames(query1);
       gamesToPersist.addAll(deleteAndReturnGames(query2));
    
       if (!gamesToPersist.isEmpty()) {
           logger.info("saving " + gamesToPersist.size() + " games to google storage");
           String resFileStr = gamesToPersist.stream().map(gson::toJson).collect(Collectors.joining("\n"));
           Storage storage = StorageOptions.getDefaultInstance().getService();
    
           BlobId blobId = BlobId.of("backgammon", "games/" + System.currentTimeMillis() + ".json");
           BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
           storage.create(blobInfo, resFileStr.getBytes());
           logger.info(gamesToPersist.size() + " games saved to google storage");
       } else {
           logger.info("no games to persist");
       }
       
    }
    
    List<GameFullData> deleteAndReturnGames(Query query) throws ExecutionException, InterruptedException {
        return query.get().get().getDocuments().stream().map(doc -> {
            GameFullData data = doc.toObject(GameFullData.class);
            doc.getReference().delete();
            return data;
        }).collect(Collectors.toList());
    }
}
