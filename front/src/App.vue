<template>
  <div id="app">
    <Board class="board" :chips="$data.map"/>
<!--    <div>-->
<!--      <router-link to="/list" replace>list of games</router-link>-->
<!--      <br/>-->
<!--      <router-link to="/create">createGame</router-link>-->
<!--      <br/>-->
<!--      <router-link to="/">home</router-link>-->
<!--    </div>-->
<!--    <router-view></router-view>-->
  </div>
</template>

<script>
import Board from "@/components/Board";
const mapObj = new Map()
mapObj.set(12, { color: "black", num: 11 })
mapObj.set(8, { color: "black", num: 4})
mapObj.set(24, { color: "white", num: 13})
mapObj.set(23, { color: "white", num: 1})
mapObj.set(20, { color: "white", num: 1})

export default {
  name: "App",
  components: {
    Board
  },
  data: function() {
    return {
      map: mapObj
    };
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  margin-left: 500px;
  /*background-color: azure;*/

}
.temp {
background-color: azure;
}
</style>
