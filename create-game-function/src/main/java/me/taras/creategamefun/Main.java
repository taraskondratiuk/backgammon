package me.taras.creategamefun;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import me.taras.lib.model.GameFullData;
import me.taras.lib.model.GameParams;

import java.util.*;
import java.util.logging.*;
import java.util.stream.*;

public class Main implements HttpFunction {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    private static final Gson gson = new Gson();
    
    
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        System.out.println("scopes " + GoogleCredentials.getApplicationDefault().getRequestMetadata().entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue().stream().collect(Collectors.joining(", ")) + "\n"));
        GameParams params = gson.fromJson(request.getReader(), GameParams.class);
        
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(System.getenv("GCLOUD_PROJECT"))
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        Firestore db = firestoreOptions.getService();
        
        DocumentReference docRef = db.collection("games").document(params.getGameId());
        
        if (docRef.get().get().exists()) {
            logger.warning("game with id " + params.getGameId() + " already exists!");
        } else {
            GameFullData game = new GameFullData(
                    params.getPlayer1Id(),
                    "",
                    Collections.emptyList(),
                    Collections.emptyList(),
                    0,
                    Collections.emptyList(),
                    Collections.emptyList(),
                    params.getFirstTurnColor(),
                    1,
                    params.getPlayer1Id(),
                    System.currentTimeMillis(),
                    false,
                    System.getenv("BASE_FRONT_URL") + "/join/" + params.getGameId(),
                    params
            );
            docRef.set(game);
            logger.info("created game with id " + params.getGameId());
        }
    }
}