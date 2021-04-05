<template>
  <div id="app">
    <student-header></student-header>
    <student-table></student-table>
  </div>
</template>

<script lang="ts">


import Header from './components/Header.vue'
import StudentTable from './components/StudentTable.vue'


const app = {
  name: 'app',
  components: {
    'student-header':Header,
    'student-table':StudentTable
  },
  data: () => {
    return {

    }
  }
}
export default app
</script>
<style>

</style>