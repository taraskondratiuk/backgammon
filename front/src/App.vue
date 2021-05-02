<template>
  <div id="app">
    <Board :chips="$data.map"/>
  </div>
</template>

<script>
import Board from "@/components/Board";
const mapObj = new Map()
mapObj.set(12, { color: "black", num: 15 })
mapObj.set(1, { color: "black", num: 2})
mapObj.set(2, { color: "white", num: 3})
mapObj.set(24, { color: "white", num: 15})

export default {
  name: 'App',
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
}
</style>
