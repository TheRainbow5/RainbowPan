<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  provide() {
    return {

    }
  },
  data() {
    return {

    }
  },
  methods: {
  },
  components: {
  }
}
</script>

<style>
#app {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

* {
  margin: 0;
  padding: 0;
}
</style>