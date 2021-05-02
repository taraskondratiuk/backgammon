<template>
  <circle
      cx="50"
      :cy="(num < 9) ? 600 - (70 * (num - 0.5)) : 600 - (70 * (num - 8))"
      r="35"
      :fill="color === 'white' ? '#F2F2F2' : '#000000'"
      stroke="#989898"/>
</template>

<script>
export default {
  name: "Chip",
  props: {
    color: String,
    num: Number
  }
}
</script>

<style scoped>

</style>

