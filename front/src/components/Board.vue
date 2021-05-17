<template>
  <div>
  <div class="row top">
    <Column flipped v-for="i in [13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]" :key="i" :id="i" :width="50" :chips="chips.get(i)"/>
  </div>
  <div class="row bottom">
      <Column @click.native="columnClickFunction(i)" v-for="i in [12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1]" :key="i" :width="100" :chips="chips.get(i)"/>
  </div>
  </div>

</template>

<script>
import Column from "./Column"
import * as db from "../firebase"
import {gameDocToObj} from "@/model/transformer"

db.gamesCol.doc("gameid123").onSnapshot(doc => {
  const currentdate = new Date();
  const datetime = "Last Sync: " + currentdate.getDate() + "/"
      + (currentdate.getMonth()+1)  + "/"
      + currentdate.getFullYear() + " @ "
      + currentdate.getHours() + ":"
      + currentdate.getMinutes() + ":"
      + currentdate.getSeconds() + ":" + currentdate.getMilliseconds();
  console.log("invoke time: " + datetime)
  console.log("Current data: ", doc.data())

  console.log("res: ", gameDocToObj(doc))
})

export default {
  name: "Board",
  components: {Column},
  props: {
    chips: Map,
    // selectedChip: Object
  },
  methods: {
    columnClickFunction(key) {
      console.log(`key is ${key}`)
      // console.log(typeof key)
      // if (this.selectedChip === undefined) {
      //   this.selectedChip = { color: this.chips.get(key).color, key: key }
      // } else {
      //   const originCol = this.chips.get(this.selectedChip.key)
      //   const destCol = this.chips.get(key)
      //
      //   this.chips.set(this.selectedChip.key, { color: originCol.color, num: originCol.num - 1})
      //   this.chips.set(key, {color: destCol.color, num: destCol.num + 1})
      // }
    }
  }
}
</script>

<style scoped>
.row {
  display: flex;
}
</style>