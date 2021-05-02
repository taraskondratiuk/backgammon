<template>
  <svg :style="(flipped ? 'transform: scale(1,-1)' : '') + `;width: ${width}; height: ${width * 6}`" viewBox="0 0 100 600" fill="none" xmlns="http://www.w3.org/2000/svg">
    <rect width="100" height="600" fill="#EBEBEF"/>
    <path d="M50 175L93.3013 599.5H6.69873L50 175Z" :fill="highlighted ? '#A1BDE8' : '#7D8796'"/>
    <Chip v-for="i in chips === undefined ? [] : Array.from(Array(chips.num), (_, x) => x + 1) " :num="i" :color="chips.color" :key="i"/>
  </svg>
</template>

<script>
import Chip from "@/components/Chip";
export default {
  name: "Column",
  components: {Chip},
  props: {
    flipped: Boolean,
    width: Number,
    chips: Object,
    highlighted: Boolean
  }
}
</script>

<style scoped>

</style>