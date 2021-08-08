package me.taras.creategamefun;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.taras.lib.model.GameFullData;
import me.taras.lib.model.GameParams;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.logging.*;

public class Main {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    private static final Gson gson = new Gson();
    
    
    public static JsonObject main(JsonObject args) throws Exception {

       System.out.println(args);
        GameParams params = gson.fromJson(args, GameParams.class);

        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(args.get("AUTH_FILE").toString().getBytes())))
                        .build();
        Firestore db = firestoreOptions.getService();
        
        DocumentReference docRef = db.collection("games").document(params.getGameId());
        
        if (docRef.get().get().exists()) {
            logger.warning("game with id " + params.getGameId() + " already exists!");
            JsonObject resp = new JsonObject();
            resp.addProperty("statusCode", 400);
            return resp;
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
            JsonObject resp = new JsonObject();
            resp.addProperty("statusCode", 200);
            return resp;
        }
        
    }
}