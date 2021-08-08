package me.taras.maketurnfun;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import me.taras.lib.Game;
import me.taras.lib.model.*;

import java.util.*;
import java.util.logging.*;

public class Main implements HttpFunction {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    private static final Gson gson = new Gson();
    
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
    
        MakeTurn makeTurn = null;
        try {
            makeTurn = gson.fromJson(request.getReader(), MakeTurn.class);
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
    
        DocumentReference docRef = db.collection("games").document(makeTurn.getGameId());
    
        DocumentSnapshot doc = docRef.get().get();
    
        if (!doc.exists()) {
            String msg = "game with id " + makeTurn.getGameId() + " doesnt exist!";
            logger.warning(msg);
            response.setStatusCode(400, msg);
            return;
        }
        
        GameFullData gameData = doc.toObject(GameFullData.class);
        
        if (!gameData.getCurrentTurnPlayerId().equals(makeTurn.getPlayerId())) {
            String msg = "player with id  " + makeTurn.getPlayerId() + " isnt allowed to make turn now!";
            logger.warning(msg);
            response.setStatusCode(400, msg);
            return;
        }
        List<TurnWithDices> gameHistory = gameData.getHistory();
        gameHistory.add(new TurnWithDices(gameData.getAllDices(), makeTurn.getTurn(), gameData.getCurrentTurnColor(), gameData.getCurrentTurnPlayerId()));
        
        GameHistoryWithState gameHistoryWithState = new GameHistoryWithState(
                gameData.getHistory(),
                gameData.getCurrentTurnColor(),
                gameData.getCurrentTurnNum(),
                gameData.getAvailableDices(),
                gameData.getAllDices(),
                gameData.getAvailableTurns(),
                gameData.getCurrentTurnNum()
        );
            
        Game game = Game.Companion.initLong(gameHistoryWithState);
            
        TurnRes turnRes = game.makeTurn(makeTurn.getTurn());
        
        String resPlayerId;
        if (turnRes.getGameState().getCurrentTurn() == gameData.getCurrentTurnColor()) {
            resPlayerId = gameData.getCurrentTurnPlayerId();
        } else {
            resPlayerId = gameData.getCurrentTurnPlayerId().equals(gameData.getPlayer1Id()) ? gameData.getPlayer2Id() : gameData.getPlayer1Id();
        }
        if (turnRes.isTurnSuccessful()) {
            GameFullData updatedGameData = new GameFullData(
                    gameData.getPlayer1Id(),
                    gameData.getPlayer2Id(),
                    turnRes.getGameState().getAllDices(),
                    turnRes.getGameState().getAvailableDices(),
                    gameData.getNumViewers(),
                    gameHistory,
                    turnRes.getGameState().getAvailableTurns(),
                    turnRes.getGameState().getCurrentTurn(),
                    turnRes.getGameState().getTurnNum(),
                    resPlayerId,
                    gameData.getCreatedAt(),
                    turnRes.isGameOver(),
                    gameData.getLink(),
                    gameData.getParams()
            );
            
            docRef.set(updatedGameData);
        } else {
            logger.warning("invalid turn! " + makeTurn);
            response.setStatusCode(400, "invalid turn! " + makeTurn);
        }
    }
    
}
