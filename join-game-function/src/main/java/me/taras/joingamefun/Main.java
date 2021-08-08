package me.taras.joingamefun;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import me.taras.lib.model.*;

import java.util.*;
import java.util.logging.*;

public class Main implements HttpFunction {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    private static final Gson gson = new Gson();
    
    
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        
        JoinGame joinGame = null;
        try {
            joinGame = gson.fromJson(request.getReader(), JoinGame.class);
        } catch (Exception e) {
            logger.warning("failed to decode request: " + e.getMessage());
            response.setStatusCode(400, "invalid request body");
            return;
        }
        
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(System.getenv("GCLOUD_PROJECT"))
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        Firestore db = firestoreOptions.getService();
        
        DocumentReference docRef = db.collection("games").document(joinGame.getGameId());
        
        DocumentSnapshot doc = docRef.get().get();
        if (!doc.exists()) {
            String msg = "game with id " + joinGame.getGameId() + " doesnt exist!";
            logger.warning(msg);
            response.setStatusCode(400, msg);
        } else if (!doc.toObject(GameFullData.class).getPlayer2Id().isEmpty()) {
            String msg = "game with id " + joinGame.getGameId() + " is already in play!";
            logger.warning(msg);
            response.setStatusCode(400, msg);
        } else if (doc.toObject(GameFullData.class).isFinished()) {
            String msg = "game with id " + joinGame.getGameId() + " is already finished!";
            logger.warning(msg);
            response.setStatusCode(400, msg);
        } else {
            GameFullData oldGame = doc.toObject(GameFullData.class);
    
            GameState generatedGameState = me.taras.lib.Game.Companion.initLong(oldGame.getParams().getFirstTurnColor()).gameState();
            
            String firstTurnPlayerId;
            if (oldGame.getParams().getPlayer1Color() == generatedGameState.getCurrentTurn()) {
                firstTurnPlayerId = oldGame.getPlayer1Id();
            } else {
                firstTurnPlayerId = joinGame.getPlayer2Id();
            }
            GameFullData updatedGame = new GameFullData(
                    oldGame.getPlayer1Id(),
                    joinGame.getPlayer2Id(),
                    generatedGameState.getAllDices(),
                    generatedGameState.getAvailableDices(),
                    oldGame.getNumViewers(),
                    oldGame.getHistory(),
                    generatedGameState.getAvailableTurns(),
                    generatedGameState.getCurrentTurn(),
                    oldGame.getCurrentTurnNum(),
                    firstTurnPlayerId,
                    oldGame.getCreatedAt(),
                    oldGame.isFinished(),
                    oldGame.getLink(),
                    oldGame.getParams()
            );
            docRef.set(updatedGame);
            logger.info("user with id " + joinGame.getPlayer2Id() + " joined game with id " + joinGame.getGameId());
        }
    }
}