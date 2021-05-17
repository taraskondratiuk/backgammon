import firebase from "firebase"
import "firebase/auth"
import "firebase/firestore"

const firebaseConfig = {
    apiKey: "AIzaSyBoVTBMtAN_8CqBOVJPME50p8wEydfjjoE",
    authDomain: "backgammon-313018.firebaseapp.com",
    projectId: "backgammon-313018",
    storageBucket: "backgammon-313018.appspot.com",
    messagingSenderId: "58954589617",
    appId: "1:58954589617:web:fc2150ec0cc1b483f44990",
    measurementId: "G-GYK49Q32KY"
}

firebase.initializeApp(firebaseConfig)

// utils
const db = firebase.firestore()
const auth = firebase.auth()

const gamesCol = db.collection("games")

export {
    db,
    auth,
    gamesCol
}
