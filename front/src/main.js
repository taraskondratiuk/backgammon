import Vue from "vue"
import VueRouter from "vue-router"
// import App from "./App.vue"
// import { auth } from "./firebase"
import Board from "@/components/Board";
import GamesList from "@/components/GamesList";
import CreateGame from "@/components/CreateGame";
import App from "@/App";

Vue.config.productionTip = false
Vue.use(VueRouter)

const routes = [
  { path: "/", component: App },
  { path: "/list", component: GamesList },
  { path: "/join/:gameId", component: Board },
  { path: "/create", component: CreateGame }

]

const router = new VueRouter({
  mode: "history",
  routes: routes
})

const app = new Vue({
  el: "#app",
  router,
  render: h => h(App)
})

export default {app, router}

